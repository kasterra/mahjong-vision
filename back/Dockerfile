FROM node:18.18

WORKDIR /usr/src/app

COPY *.json ./
COPY .babelrc ./
COPY package*.json ./

RUN apt update && apt install -y net-tools
RUN npm install
RUN npm install -g nodemon @babel/core @babel/node @babel/preset-env @babel/preset-typescript

EXPOSE 16005

CMD ["npm", "run", "dev"]