import numpy as np
from image_data_converter.image_convert import cut_middle
from data_calculator.split_data import split_hand_binary_search
from data_calculator.split_data import split_huro
from ultralytics import YOLO
from PIL import Image

def convert_to_split_data(img: Image):
    # Load a pretrained YOLOv8n model
    model = YOLO('/Users/skeep/mahjong-vision/mahjong-vision-calculater/model.pt')

    x, y = img.size

    # Run inference on the source
    predicts = model.predict(img, boxes=True, conf=0.7)  # list of Results objects

    result = []
    for r in predicts:
        name = r.names
        for i in range(len(r.boxes.cls)):
            xyxy = r.boxes.xyxy[i]
            result.append({
                'name': name[int(r.boxes.cls[i])],
                'pos': [(xyxy[0]+xyxy[2])/2, (xyxy[1]+xyxy[3])/2]
            })
    result.sort(key=lambda data: data['pos'])
    return result

# api function /image_to_data
def convert_to_dict(img: Image):
    result = {
        "win": "",
        "dora": [],
        "hand": [],
        "huro": {
            "chi": [],
            "pong": [],
            "ankkang": [],
            "minkkang": []
        }
    }

    # assert two group
    group = 2
    sz = 13

    win_dora, hand_huro = cut_middle(img)
    win_dora_data = convert_to_split_data(win_dora)
    hand_huro_data = convert_to_split_data(hand_huro)

    win_dora_split, _ = split_hand_binary_search(win_dora_data, group)
    win, dora = win_dora_split[0], win_dora_split[1]

    hand_huro_split, dist = split_hand_binary_search(hand_huro_data, group)
    min_dist = 1e15
    for i in range(1, len(hand_huro_data)):
        min_dist = min(min_dist, abs(np.linalg.norm(np.array(hand_huro_data[i-1]['pos']) - np.array(hand_huro_data[i]['pos']))))

    hand, huro = hand_huro_split[0], hand_huro_split[1]

    if min_dist * 1.5 > dist:
        hand, huro = hand_huro_data, []
    else:
        huro_size = (sz - len(hand)) // 3
        result['huro'] = split_huro(huro, huro_size)
    
    result['win'] = win[0]['name']
    for i in dora:
        result['dora'].append(i['name'])
    for i in hand:
        result['hand'].append(i['name'])
    return result
