from ultralytics import YOLO
from PIL import Image

def convert(img: Image):
    # Load a pretrained YOLOv8n model
    model = YOLO('model.pt')

    x, y = img.size

    # Run inference on the source
    predicts = model.predict(img, boxes=True, imgsz=(x, y))  # list of Results objects

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