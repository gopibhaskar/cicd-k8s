# MongoDB image with host-based data volume

FROM mongo:latest

MAINTAINER Gopibhaskar <gopibhaskar@outlook.com>
ENV REFRESHED_AT 2016-07-30

VOLUME ["/data/db"]
WORKDIR /data

CMD ["mongod", "--smallfiles"]
