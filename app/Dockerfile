FROM negrosa-base

RUN apt-get install -y unzip xz-utils zip libglu1-mesa openjdk-8-jdk

RUN mkdir /app-builder
WORKDIR /app-builder

RUN mkdir -p Android/sdk
ENV ANDROID_SDK_ROOT /app-builder/Android/sdk
ENV ANDROID_HOME /app-builder/Android/sdk
RUN mkdir -p .android && touch .android/repositories.cfg

RUN wget -O sdk-tools.zip https://dl.google.com/android/repository/sdk-tools-linux-4333796.zip
RUN unzip sdk-tools.zip && rm sdk-tools.zip
RUN mv tools Android/sdk/tools
RUN cd Android/sdk/tools/bin && yes | ./sdkmanager --licenses
RUN cd Android/sdk/tools/bin && ./sdkmanager "build-tools;29.0.2" "patcher;v4" "platform-tools" "platforms;android-29" "sources;android-29"
ENV PATH "$PATH:/app-builder/Android/sdk/platform-tools"

RUN git clone https://github.com/flutter/flutter.git
ENV PATH "$PATH:/app-builder/flutter/bin"

RUN flutter channel stable

RUN flutter doctor

CMD cd /app && \
	flutter pub get && \
	flutter build apk