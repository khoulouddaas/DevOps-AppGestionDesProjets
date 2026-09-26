# frontend/Dockerfile
FROM node:22-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

# Angular 17+ (new application builder) outputs to dist/<project>/browser.
# The wildcard below picks up the single project folder automatically.
FROM nginx:alpine
COPY --from=build /app/dist/*/browser/. /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
