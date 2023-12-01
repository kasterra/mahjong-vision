from image_data_converter.image_to_data import convert_to_dict
from image_data_converter.image_convert import cut_right_down
from data_calculator.calculate_han_yaku import calculate
from data_calculator.calculate_han_yaku import get_additonal_information
from PIL import Image

if __name__ == "__main__":
    print("convert image to data")
    # TODO: 이 부분은 데이터를 동적으로 넣어줘야 함
    img = Image.open('example.png')
    d = convert_to_dict(img)
    print(d)
    x = get_additonal_information(d)
    print(x)
    y = calculate(d, {
        "win_method": "tsumo",
        "riichi": 0,
        "seat_wind": "N",
        "prevalent_wind": "N",
        "chankkang": False,
        "haitei": False,
        "rinshan": False,
        "aka": 1
    })
    print(y)
