FROM negrosa-base

RUN curl -sL https://deb.nodesource.com/setup_12.x | bash -
RUN apt-get install -y nodejs

WORKDIR /app

COPY package.json .
RUN npm install

COPY docker/nginx-default /etc/nginx/sites-available/default

COPY . /app
RUN npm run build

EXPOSE 80

CMD nginx -g "daemon off;"