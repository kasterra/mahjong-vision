import mahjong
import data_calculator.split_data

def calculate(datum: list, isAgari: bool):
    # 임시로 북을 여기서 빼보자
    for idx, val in enumerate(datum):
        if val['name'] == '4z':
            del datum[idx]
            break
    
    sz = 14
    group = 3
    if not isAgari:
        sz -= 1
        group -= 1
    
    s = data_calculator.split_data.split_hand_binary_search(datum, group)
    huro_size = (sz - len(s[0])) // 3
    return data_calculator.split_data.split_huro(s[-1], huro_size)