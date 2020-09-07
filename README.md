
# clone project
git clone -b docker_v2 --single-branch \
  https://github.com/gopibhaskar/cicd-k8s-elk.git music \
  && cd "$_"

# Apache Tomcat image with build artifact

FROM tomcat:8.5.57-jdk8-openjdk

MAINTAINER Gopibhaskar <gopibhaskar@outlook.com>

ENV APP_FILE spring-music.war

COPY spring-music.war /usr/local/tomcat/webapps/

COPY tomcat-users.xml /usr/local/tomcat/conf/tomcat-users.xml



