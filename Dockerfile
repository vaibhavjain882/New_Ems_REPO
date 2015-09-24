FROM www.cybage-docker-registry.com:9080/jenkins_node_slave:1.3

RUN ln -s /usr/local/bin/node /usr/bin/node
RUN ln -s /usr/local/bin/gulp /usr/bin/gulp

#adding all the source code to /data/artifact
RUN mkdir -p "/data/artifact"
WORKDIR /data/artifact/

#Adding the required source code and dependencies for running the application

#for CI
ADD node_modules node_modules
ADD models models
ADD html html
ADD examples examples
ADD jshint-report jshint-report
ADD lib lib
ADD coverage coverage
ADD target target
ADD mocha-report mocha-report
ADD routes routes
ADD gatling gatling
ADD tests tests
ADD app.js gulpfile.js package.json selenium-server-standalone-2.47.1.jar nightwatch.json nightwatch.js ./

#Setting timezone to match time zone of sonar server
RUN echo "Asia/Kolkata" > /etc/timezone && dpkg-reconfigure --frontend noninteractive tzdata

VOLUME ['./tmp']
 
EXPOSE 3010
