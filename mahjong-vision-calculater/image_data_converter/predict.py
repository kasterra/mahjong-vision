from ultralytics import YOLO
from PIL import Image

# Load a pretrained YOLOv8n model
model = YOLO('model.pt')

# Define path to the image file
source = 'result_origin.jpg'

# Run inference on the source
results = model.predict(source, boxes=True, imgsz=(3000,4000))  # list of Results objects

for r in results:
    im_array = r.plot()  # plot a BGR numpy array of predictions
    im = Image.fromarray(im_array[..., ::-1])  # RGB PIL image
    im.show()  # show image
    im.save('results.jpg')  # save image