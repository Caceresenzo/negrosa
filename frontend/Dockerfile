FROM negrosa-base

RUN curl -sL https://deb.nodesource.com/setup_12.x | bash -
RUN apt-get install -y nodejs

COPY docker/nginx-default /etc/nginx/sites-available/default
COPY . /app

WORKDIR /app

RUN npm install && npm run build

EXPOSE 80

CMD nginx -g "daemon off;"