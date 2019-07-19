#基础镜像
FROM tomcat:8.5.9

#作者
LABEL maintainer="laidongxin"

#运行安装telnet和nc
RUN apt-get install -y telnet nc; exit 0

#
VOLUME ["/home/laidongxin/tomcat"]

#TOMCAT环境变量
ENV CATALINA_BASE:   /usr/local/tomcat2 \
    CATALINA_HOME:   /usr/local/tomcat2 \
    CATALINA_TMPDIR: /usr/local/tomcat2/temp \
    JRE_HOME:        /usr

#启动入口
ENTRYPOINT ["catalina.sh","run"]

#健康检查
# HEALTHCHECK --interval=10s --timeout=3s \
#   CMD nc -z localhost 5198 >/dev/null || exit 1

#拷贝war包到tomcat
COPY target/spring-boot-security-0.0.1-SNAPSHOT.war ${CATALINA_HOME}/webapps/