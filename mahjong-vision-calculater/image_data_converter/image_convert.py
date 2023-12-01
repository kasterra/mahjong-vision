from PIL import Image

def cut_right_down(img: Image):
    x, y = img.size
    return img.rotate(180).crop((x/10, y/4*3, x, y))

def cut_middle(img: Image):
    x, y = img.size
    return img.crop((0, 0, x, y/2)), img.crop((0, y/2, x, y))