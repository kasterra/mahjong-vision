from PIL import Image

def cut_right_down(img: Image):
    x, y = img.size
    return img.rotate(180).crop((x/10, y/4*3, x, y))