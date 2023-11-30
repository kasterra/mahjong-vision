from mahjong.hand_calculating.hand import HandCalculator
from mahjong.tile import TilesConverter
from mahjong.meld import Meld
import data_calculator.split_data
import numpy as np

# 손패, 도라, 오름패를 입력 받기
def calculate(hand: list, dora: list, agari: str):
    # 임시로 북을 여기서 빼보자
    for idx, val in enumerate(hand):
        if val['name'] == '4z':
            del hand[idx]
            break
    
    # 오름 패를 제외한 hand의 크기는 13, 후로가 있을 경우 그룹 2개로 나눠야 함
    sz = 13
    group = 2
    
    min_dist = abs(np.linalg.norm(np.array(hand[0]['pos']) - np.array(hand[1]['pos'])))
    for i in range(1, len(hand)):
        min_dist = min(min_dist, abs(np.linalg.norm(np.array(hand[i-1]['pos']) - np.array(hand[i]['pos']))))
    s, dist = data_calculator.split_data.split_hand_binary_search(hand, group)

    huro = []
    is_menzen = False
    if min_dist * 1.5 > dist:
        is_menzen = True
    else:
        huro_size = (sz - len(s[0])) // 3
        huro = data_calculator.split_data.split_huro(s[-1], huro_size)
    
    return HandCalculator.estimate_hand_value()

def hand_to_136_array(hand):
    man = ''
    pin = ''
    sou = ''
    honors = ''
    for i in hand:
        if i['name'][1] == 'm':
            man += i['name'][0]
        elif i['name'][1] == 's':
            sou += i['name'][0]
        elif i['name'][1] == 'p':
            pin += i['name'][0]
        else:
            honors += i['name'][0]
    return TilesConverter.string_to_136_array(sou=sou, man=man, pin=pin, honors=honors)

def huro_to_meld_array(huro):
    result = []
    converter = {'chi': Meld.CHI, 'pong': Meld.PON, 'ankkang': Meld.SHOUMINKAN, 'minkkang': Meld.KAN}
    for i in converter:
        for j in result[converter]:
            result.append(Meld(meld_type=converter[j], tiles=)