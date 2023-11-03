import express from "express";
import morgan from "morgan";

const app = express();
const PORT = 16005;

app.use(morgan("dev"));

app.get("/", (req, res) => {
  res.send("Hello, World!");
});

app.listen(PORT, "0.0.0.0", () => {
  console.log(`Server is running at http://0.0.0.0:${PORT}`);
});
