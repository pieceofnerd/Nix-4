FROM tomcat:10.0.7-jdk16-openjdk

COPY target/jeebox.war /usr/local/tomcat/webapps/jeebox.war

EXPOSE 8080
CMD ["catalina.sh", "run"]