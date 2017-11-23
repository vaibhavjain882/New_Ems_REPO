FROM monostream/nodejs-gulp-bower 

#adding all the source code to /data/artifact
RUN mkdir -p "/data/application"

EXPOSE 3010
