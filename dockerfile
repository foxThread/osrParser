FROM osrimage:latest

COPY ./target /app
CMD /go.sh
ADD ./stuff/go.sh /go.sh
