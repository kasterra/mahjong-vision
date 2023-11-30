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
    handConfig = HandConfig()
    if information['win_method'] == "tsumo":
        handConfig.is_tsumo = True
    
    if information['riicci'] == 1:
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