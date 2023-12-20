from fastapi import FastAPI, File, Response, UploadFile, status
from image_data_converter.image_to_data import convert_to_dict
from PIL import Image
from pydantic import BaseModel
from data_calculator.calculate_han_yaku import calculate
import io

app = FastAPI()

class HandData(BaseModel):
    data: dict
    information : dict

@app.post("/image_to_data")
async def create_file(response: Response, file: bytes = File()):
    image = Image.open(io.BytesIO(file))
    image.save("upload.png")
    try:
        return convert_to_dict(image)
    except:
        result = {
            "message": "something wrong"
        }
        print(result)
        response.status_code = status.HTTP_400_BAD_REQUEST
        return result

@app.get("/calculate")
def calculate_yaku(handData: HandData):
    print(handData)
    return calculate(handData.data, handData.information)
