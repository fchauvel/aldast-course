const gulp = require('gulp');
const sass = require('gulp-sass');
const del = require('del');
const connect = require('gulp-connect');

gulp.task('install-reveal.js', () =>  {
    const source = 'src/assets/lib/reveal.js';
    const target = 'site/lib/reveal.js';
    gulp.src([`${source}/dist/**/*`])
        .pipe(gulp.dest(`${target}/dist`));
    return gulp.src([`${source}/plugin/**/*`])
        .pipe(gulp.dest(`${target}/plugin`));
});

gulp.task('install-reveal-plugins', () => {
 return gulp
        .src(['src/assets/lib/reveal.js/plugin/**/*'])
        .pipe(gulp.dest('site/lib/reveal.js/plugin'));

});

gulp.task('install-images', () => {
 return gulp
        .src('src/assets/images/**/*')
        .pipe(gulp.dest('site/images'));
});


gulp.task('install-reveal-theme', () => {
    const source = 'src/assets/lib/reveal.js-theme';
    const target = 'site/lib/reveal.js-theme/';
    gulp.src(`${source}/scss/nord-reveal.scss`)
        .pipe(sass().on('error', sass.logError))
        .pipe(gulp.dest(target));
     gulp.src(`${source}/scss/nord.css`)
        .pipe(gulp.dest(target));
    return gulp.src(`${source}/js/nord-reveal.js`)
        .pipe(gulp.dest(target));
});

gulp.task('install-site', () =>  {
    return gulp
        .src(['src/**/*.html', '!src/assets/**/*.html'])
        .pipe(gulp.dest('site/'));

});

gulp.task('install-timeline', () => {
    const source = 'src/assets/lib/timeline';
    const target = 'site/lib/timeline';
    return gulp.src(`${source}/**/timeline.scss`)
        .pipe(sass().on('error', sass.logError))
        .pipe(gulp.dest(target));
});

gulp.task('build',
          gulp.parallel([
              'install-reveal.js',
              'install-reveal-plugins',
              'install-reveal-theme',
              'install-timeline',
              'install-images',
              'install-site']));

gulp.task('clean', () => {
    return del([
        'site',
    ]);
});


gulp.task('serve', () => {
    const port = 8000;
    const root = "./site";
    connect.server({
        root: root,
        port: port,
        host: '0.0.0.0',
        livereload: true
    });

    gulp.watch(['src/**/*.html',
                'src/**/*.scss',
                'src/**/*.css'],
               gulp.series('build'));

});


gulp.task('default',
          gulp.series(['build']));
