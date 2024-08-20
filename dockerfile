FROM osrimage:latest

COPY ./app /app
CMD /go.sh
ADD ./stuff/go.sh /go.sh
RUN chmod a+x /go.sh
