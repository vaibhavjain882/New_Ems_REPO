var gulp = require('gulp');
var nodemon = require('gulp-nodemon');
var istanbul = require('gulp-istanbul');
var jshint = require('gulp-jshint');
var mocha = require('gulp-mocha');
var sonar=require('gulp-sonar');
var util = require('util');
var gulp = require('gulp'),
    nightwatch = require('gulp-nightwatch');
//var nightwatch=require('gulp-nightwatch');
 
gulp.task('run', function(){
    nodemon({
        script: 'app.js',
        ext: 'js',
        env: {
            PORT:3010
        },
        ignore: ['./node_modules/**']
    })
    .on('restart',function(){
        console.log('Restarting');
    });
});


gulp.task('lint', function() {
  return gulp.src('*.js')
    .pipe(jshint())
    .pipe(jshint.reporter('gulp-jshint-html-reporter', {
      filename: 'jshint-report' + '/jshint-output.html',
      
    }));
});

 
gulp.task('mocha', function () {
    return gulp.src('tests/mochatest.js', {read: false})
        // gulp-mocha needs filepaths so you can't have any plugins before it 
        .pipe(mocha({reporter: 'good-mocha-html-reporter', //good-mocha-html-reporter, spec, nyan
    timeout: 1500000,
    bail: false,
    savePath: 'mocha-report', // the path to desired location
    filename: 'report.html', // filename gets attached at the end of savePath
    mode: 'Verbose'}));
});



gulp.task('test', function () {
  gulp.src(['*.js'])
    .pipe(istanbul()) // Covering files
    .pipe(istanbul.hookRequire()) // Force `require` to return covered files

        .pipe(istanbul.writeReports()) // Creating the reports after tests ran
     .pipe(istanbul.enforceThresholds({ thresholds: { global: 90 } })) // Enforce a coverage of at least 90%
       // .on('end', cb);
});




gulp.task('sonar', function () {
    var options = {
        sonar: {
            host: {
                url: 'http://localhost:9000'
            },
           /* jdbc: {
                url: 'jdbc:mysql://localhost:3306/sonar',
                username: 'sonar',
                password: 'sonar'
            },*/
            projectKey: 'sonar:my-project:1.0.0',
            projectName: 'AngularProject',
            projectVersion: '1.0.0',
            // comma-delimited string of source directories 
            sources: 'app.js',
            language: 'js',
            sourceEncoding: 'UTF-8',
            javascript: {
                lcov: {
                    reportPath: 'sonar_report/lcov.info'
                }
            }
        }
    }
 
    // gulp source doesn't matter, all files are referenced in options object above 
   return gulp.src('app.js', { read: false })
        .pipe(sonar(options))
        .on('error', util.log);
});

gulp.task('nightwatch', function() {
  gulp.src('nightwatch.js')
    .pipe(nightwatch({
      configFile: './nightwatch.json'
      }))
    });



gulp.task('default',['test','mocha','lint'],function(){});
