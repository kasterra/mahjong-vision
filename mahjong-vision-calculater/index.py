from fastapi import FastAPI, File, UploadFile
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
async def create_file(file: bytes = File()):
    return convert_to_dict(Image.open(io.BytesIO(file)))

@app.get("/calculate")
def calculate_yaku(handData: HandData):
    print(handData)
    return calculate(handData.data, handData.information)