FROM negrosa-base

RUN apt-get install -y maven python3 python3-pip

RUN pip3 install ezdxf matplotlib

WORKDIR /app

COPY pom.xml /app

RUN mvn package -Dmaven.test.skip=true -Dmaven.main.skip -Dspring-boot.repackage.skip

COPY . /app

RUN mvn package -Dmaven.test.skip=true

EXPOSE 8000

CMD java -jar target/*.jar