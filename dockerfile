FROM openjdk:17-jdk-slim
RUN apt-get update
RUN apt-get -y install libgtk2.0-0
RUN apt-get -y install libnss3
RUN apt-get install -y libatk-bridge2.0-0
RUN apt-get install -y libdrm2
RUN apt-get install -y libxkbcommon-x11-0
RUN apt-get install -y libgbm-dev
RUN apt-get install -y libasound2
RUN apt-get install -y libxshmfence-dev
RUN apt-get install -y libxxf86vm-dev
RUN apt-get install -y xvfb
RUN apt-get install -y dbus

ENV DISPLAY :99
COPY ./target /app
RUN mkdir /chromium
COPY /home/me/chromium /chromium
RUN mkdir/chromium/rootcache
RUN mkdir/chromium/rootcache/cache



CMD /go.sh
ADD ./stuff/go.sh /go.sh
