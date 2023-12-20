from mahjong.hand_calculating.hand import HandCalculator
from mahjong.tile import TilesConverter
from mahjong.meld import Meld
from mahjong.hand_calculating.hand_config import HandConfig
import mahjong.constants
import numpy as np

# api function /check_information
def get_additonal_information(data):
    result = {
        "win_method": True,
        "ricci": True,
        "seat_wind": True,
        "prevalent_wind": True,
        "chankkang": True,
        "haitei": True,
        "rinsynn": True,
        "aka": 0
    }
    if len(data['hand']) != 13:
        result['ricci'] = False
    if len(data['huro']['ankkang']) == 0 and len(data['huro']['minkkang']) == 0:
        result['rinsynn'] = False
    if data['win'][1] != 'z' and data['win'][0] == '5':
        result['aka'] += 1
    for i in data['hand']:
        if i[1] != 'z' and i[0] == '5':
            result['aka'] += 1
    return result

# api function /calculate
def calculate(data, information):
    menzen = False
    if len(data['huro']['minkkang']) + len(data['huro']['chi']) + len(data['huro']['pong']) == 0:
        menzen = True
    config = information_to_hand_config(information)
    win_tile = hand_to_136_array([data['win']])[0]
    dora = hand_to_136_array(data['dora'])
    hand = hand_to_136_array(data['hand'])
    hand.append(win_tile)
    huro = huro_to_meld_array(data['huro'], hand)
    hand.sort()
    calculator = HandCalculator()
    cal = calculator.estimate_hand_value(tiles=hand,melds=huro,dora_indicators=dora,win_tile=win_tile,config=config)
    print("input hand")
    print(data)
    print(information)
    print("calculated hand value")
    print(cal)
    if cal.error != None:
        return {
            "yaku": "chonbo",
        }
    result = {
        "yaku": [],
        "fu": [],
    }
    for i in cal.yaku:
        result['yaku'].append({'name': i.name.replace(' ', ''), 'han': i.han_closed if menzen else i.han_open })
    for i in cal.fu_details:
        result['fu'].append(i['reason'])
    if information['win_method'] == "ron":
        result['score'] = cal.cost['main']
    else:
        result['score'] = [cal.cost['main'], cal.cost['additional']]
    print('result')
    print(result)
    return result

def information_to_hand_config(information):
    handConfig = HandConfig()
    if information['win_method'] == "tsumo":
        handConfig.is_tsumo = True
    
    if information['riichi'] == 1:
        handConfig.is_riichi = True
    elif information['riichi'] == 2:
        handConfig.is_daburu_riichi = True

    str_to_constant = {
        "E": mahjong.constants.EAST,
        "S": mahjong.constants.SOUTH,
        "W": mahjong.constants.WEST,
        "N": mahjong.constants.NORTH
    }

    handConfig.player_wind = str_to_constant[information['seat_wind']]
    handConfig.round_wind = str_to_constant[information['prevalent_wind']]
    
    handConfig.is_chankan = information['chankkang']
    if handConfig.is_tsumo:
        handConfig.is_haitei = information['haitei']
    else:
        handConfig.is_houtei = information['haitei']
    
    handConfig.is_rinshan = information['rinshan']
    return handConfig

def hand_to_136_array(hand):
    man = ''
    pin = ''
    sou = ''
    honors = ''
    for i in hand:
        if i[1] == 'm':
            man += i[0]
        elif i[1] == 's':
            sou += i[0]
        elif i[1] == 'p':
            pin += i[0]
        else:
            honors += i[0]
    return TilesConverter.string_to_136_array(sou=sou, man=man, pin=pin, honors=honors)

def huro_to_meld_array(huro, hand):
    result = []
    converter = {'chi': Meld.CHI, 'pong': Meld.PON, 'ankkang': Meld.KAN, 'minkkang': Meld.KAN}
    for i in converter:
        for j in huro[i]:
            ary = hand_to_136_array(j)
            for k in ary:
                hand.append(k)
            if i == 'ankkang':
                result.append(Meld(meld_type=converter[i], tiles=hand_to_136_array(j), opened=False))
            else:
                result.append(Meld(meld_type=converter[i], tiles=hand_to_136_array(j)))
    return result
