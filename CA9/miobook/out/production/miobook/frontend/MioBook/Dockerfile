FROM node:20 AS builder

WORKDIR /app

COPY package*.json ./

RUN npm install

COPY . .

RUN npm run build

FROM nginx:1.25-alpine

COPY --from=builder /app/dist /usr/share/nginx/html

COPY nginx/default.conf.template /etc/nginx/templates/default.conf.template

ENV VITE_BACKEND_HOST=backend
ENV VITE_BACKEND_PORT=8080

EXPOSE 5173

CMD ["nginx", "-g", "daemon off;"]
