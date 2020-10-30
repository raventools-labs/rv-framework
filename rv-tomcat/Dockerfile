FROM maven:3-openjdk-8-slim

ENV CATALINA_HOME /opt/tomcat
ENV CATALINA_BASE /opt/tomcat
ENV CATALINA_PID  /opt/tomcat/catalina_pid
ENV CATALINA_TMPDIR /opt/tomcat/temp
ENV WEBAPPS  /opt/tomcat/webapps
ENV JAVA_OPTS "-Duser.language=es -Duser.region=ES -Dcatalina.home=/opt/tomcat/ -Dwtp.deploy=/opt/tomcat/webapps"
 
COPY target/rv-tomcat*.jar $CATALINA_HOME/tomcat.jar
COPY target/lib $CATALINA_HOME/lib

WORKDIR $CATALINA_HOME

RUN mkdir $WEBAPPS

CMD java -jar $JAVA_OPTS $CATALINA_HOME/tomcat.jar