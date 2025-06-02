FROM node:20 AS builder

WORKDIR /app

COPY package*.json ./

RUN npm install

COPY . .

RUN npm run build

FROM nginx:1.25-alpine

ENV BACKEND_HOST=localhost:8080

COPY --from=builder /app/dist /usr/share/nginx/html

COPY nginx/default.conf.template /etc/nginx/templates/default.conf.template

ENV PORT=5174

EXPOSE 5174

CMD ["nginx", "-g", "daemon off;"]
