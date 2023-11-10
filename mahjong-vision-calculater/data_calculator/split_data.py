import numpy as np

# greedy method: x축 기준으로 제일 가까운 두 패의 거리 보다 k 배 이상 떨어진 경우 두 패는 다른 그룹이라고 판별
# 배율을 늘리고 줄이면 그룹의 개수는 오름차순 -> binary search 적용 가능?
def split_hand_binary_search(hand: list, group: int):
    print("split hand to group")

    low = 0
    high = 1e9
    while low < high:
        groups = [[hand[0]]]
        mid = (low + high) / 2
        for i in range(1, len(hand)):
            if abs(np.linalg.norm(np.array(hand[i-1]['pos']) - np.array(hand[i]['pos']))) <= mid:
                groups[-1].append(hand[i])
            else:
                groups.append([hand[i]])
        if len(groups) == group:
            print("split hand group result")
            for idx, val in enumerate(groups):
                print(str(idx+1)+"th group")
                print(val)
                print("---------------------")
            return groups
        elif len(groups) < group:
            high = mid - 1
        else:
            low = mid + 1
    print("oh.. that's something wrong. i can't find group")
    return []

# 후로 개수는 입력받아야함
def split_huro(huro_hand: list, size: int):
    print("huro hand split")
    result = {"chi": [], "pong": [], "ankkang": [], "minkkang": []}
    hand = []
    for i in huro_hand:
        hand.append(i['name'])
    if split_huro_dfs(hand, 0, size, result):
        print("success!")
        print(result)
        return result
    print("some error occur when split huro")
    return {}

def is_valid_chi(hand1, hand2, hand3):
    if hand1[1] == 'z' or hand2[1] == 'z' or hand3[1] == 'z':
        return False
    if hand1[1] != hand2[1] or hand1[1] != hand3[1] or hand2[1] != hand3[1]:
        return False
    hand = sorted([hand1, hand2, hand3])
    return int(hand1[0])+1 == int(hand2[0]) and int(hand2[0])+1 == int(hand3[0])

def all_same(*arg):
    return all(arg[0] == i for i in arg)

def split_huro_dfs(huro_hand: list, idx: int, size: int, result: dict):
    if size == 0:
        return idx == len(huro_hand)
    # chi
    if idx+2 < len(huro_hand) and is_valid_chi(huro_hand[idx], huro_hand[idx+1], huro_hand[idx+2]):
        result['chi'].append([huro_hand[idx], huro_hand[idx+1], huro_hand[idx+2]])
        if split_huro_dfs(huro_hand, idx+3, size-1, result):
            return True
        result['chi'].pop()
    # pong
    if idx+2 < len(huro_hand) and all_same(huro_hand[idx], huro_hand[idx+1], huro_hand[idx+2]):
        result['pong'].append([huro_hand[idx]]*3)
        if split_huro_dfs(huro_hand, idx+3, size-1, result):
            return True
        result['pong'].pop()
    
    # minkkang
    if idx+3 < len(huro_hand) and all_same(huro_hand[idx], huro_hand[idx+1], huro_hand[idx+2], huro_hand[idx+3]):
        result['minkkang'].append([huro_hand[idx]]*4)
        if split_huro_dfs(huro_hand, idx+3, size-1, result):
            return True
        result['minkkang'].pop()
    
    # ankkang
    if idx+1 < len(huro_hand) and all_same(huro_hand[idx], huro_hand[idx+1]):
        result['ankkang'].append([huro_hand[idx]]*4)
        if split_huro_dfs(huro_hand, idx+3, size-1, result):
            return True
        result['ankkang'].pop()
    return False