from image_data_converter.image_to_data import convert
from image_data_converter.image_convert import cut_right_down
from data_calculator.calculate_han_yaku import calculate
from PIL import Image

if __name__ == "__main__":
    print("convert image to data")
    # TODO: 이 부분은 데이터를 동적으로 넣어줘야 함
    img = Image.open('result_origin.jpg')
    print(calculate(convert(cut_right_down(img)), False))