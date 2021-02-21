FROM negrosa-base

RUN apt-get install -y maven python3 python3-pip

RUN pip3 install ezdxf matplotlib

COPY . /app

WORKDIR /app

RUN mvn package -Dmaven.test.skip=true

EXPOSE 8000

CMD java -jar target/*.jar