FROM openjdk:8-jre-alpine

ENV APPS_DIR=/opt/apps
ENV APP_DIR=$APPS_DIR/spring-rest-base
ENV CONFIG_DIR=$APP_DIR/config
ENV LOGS_DIR=$APP_DIR/logs

RUN mkdir -p $APP_DIR

COPY target/spring-rest-base*tar.gz /tmp
RUN tar xvf /tmp/spring-rest-base*tar.gz -C /tmp

RUN cp -r /tmp/spring-rest-base*/* $APP_DIR

RUN ln -s $APP_DIR/bin/spring-rest-base*jar $APP_DIR/bin/spring-rest-base.jar 

COPY spring-rest-base.start.sh $APP_DIR/run.sh

RUN chmod +x $APP_DIR/run.sh

CMD sleep 20 && ./run.sh

WORKDIR $APP_DIR

