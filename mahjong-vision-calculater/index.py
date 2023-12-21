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
    print("api call")
    image = Image.open(io.BytesIO(file))
    image = image.rotate(270)
    image.save("upload.png")
    try:
        return convert_to_dict(image)
    except:
        # 목업 데이터
        return {
            "win": "5s",
            "dora": ["6p", "7s", "1p", "8p"],
            "hand": ["2m", "3m", "4m", "7m", "8m", "9m", "5p", "5p", "3s", "4s"],
            "huro": {
                "chi": [],
                "pong": [],
                "ankkang": [["8s", "8s", "8s", "8s"]],
                "minkkang": []
            }
        }
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
