/*
 * Solo - A small and beautiful blogging system written in Java.
 * Copyright (c) 2010-present, b3log.org
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * @file frontend tool.
 *
 * @author <a href="http://vanessa.b3log.org">Liyuan Li</a>
 * @author <a href="http://88250.b3log.org">Liang Ding (Solo Author)</a>
 * @author <a href="https://github.com/adlered">adlered (Bolo Author)</a>
 */

'use strict'
const gulp = require('gulp')
const concat = require('gulp-concat')
const terser = require('gulp-terser')
const sass = require('gulp-sass')(require('sass'));
const rename = require('gulp-rename')
const autoprefixer = require('gulp-autoprefixer')
const del = require('del')

function sassSkinProcess () {
  return gulp.src('./src/main/webapp/skins/*/css/*.scss').
  pipe(sass({
    outputStyle: 'compressed',
    includePaths: ['node_modules'],
  }).on('error', sass.logError)).
  pipe(autoprefixer({
    cascade: false,
  })).
  pipe(gulp.dest('./src/main/webapp/skins/'))
}

function sassWatch () {
  gulp.watch(['./src/main/webapp/skins/*/css/*.scss'], sassSkinProcess)
  gulp.watch(['./src/main/webapp/scss/*.scss'], sassCommonProcess)
}

function sassCommonProcess () {
  return gulp.src('./src/main/webapp/scss/*.scss').
  pipe(sass({
    outputStyle: 'compressed',
    includePaths: ['node_modules'],
  }).on('error', sass.logError)).
  pipe(autoprefixer({
    cascade: false,
  })).
  pipe(gulp.dest('./src/main/webapp/scss/'))
}

gulp.task('watch', gulp.series(sassWatch))

function minJS () {
  // minify js
  return gulp.src('./src/main/webapp/js/*.js').
  pipe(rename({suffix: '.min'})).
  pipe(terser({
    output: {
      ascii_only: true,
    },
  })).
  pipe(gulp.dest('./src/main/webapp/js/'))
}

function miniAdmin () {
  // concat js
  const jsJqueryUpload = [
    './src/main/webapp/js/admin/admin.js',
    './src/main/webapp/js/admin/editor.js',
    './src/main/webapp/js/admin/tablePaginate.js',
    './src/main/webapp/js/admin/article.js',
    './src/main/webapp/js/admin/comment.js',
    './src/main/webapp/js/admin/articleList.js',
    './src/main/webapp/js/admin/draftList.js',
    './src/main/webapp/js/admin/pageList.js',
    './src/main/webapp/js/admin/others.js',
    './src/main/webapp/js/admin/linkList.js',
    './src/main/webapp/js/admin/preference.js',
    './src/main/webapp/js/admin/themeList.js',
    './src/main/webapp/js/admin/pluginList.js',
    './src/main/webapp/js/admin/userList.js',
    './src/main/webapp/js/admin/categoryList.js',
    './src/main/webapp/js/admin/commentList.js',
    './src/main/webapp/js/admin/plugin.js',
    './src/main/webapp/js/admin/main.js',
    './src/main/webapp/js/admin/about.js',
    './src/main/webapp/js/admin/toolBox.js',
    './src/main/webapp/js/admin/usite.js']
  return gulp.src(jsJqueryUpload).
  pipe(terser({
    output: {
      ascii_only: true,
    },
  })).
  pipe(concat('admin.min.js')).
  pipe(gulp.dest('./src/main/webapp/js/admin'))

}

function miniAdminLibs () {
  // concat js
  const jsJqueryUpload = [
    './src/main/webapp/js/lib/jquery/jquery.min.js',
    './src/main/webapp/js/lib/jquery/jquery.bowknot.min.js',
    './src/main/webapp/js/lib/jquery/jquery.showtips.js',
    './src/main/webapp/js/lib/jquery/jquery.cookie.min.js' ]
  return gulp.src(jsJqueryUpload).
  pipe(terser({
    output: {
      ascii_only: true,
    },
  })).
  // https://github.com/b3log/solo/issues/12522
  pipe(concat('admin-lib.min.js')).
  pipe(gulp.dest('./src/main/webapp/js/lib/compress/'))

}

function miniPjax () {
  // concat js
  const jsPjax = [
    './src/main/webapp/js/lib/jquery/jquery-3.1.0.min.js',
    './src/main/webapp/js/lib/jquery/jquery.pjax.js',
    './src/main/webapp/js/lib/nprogress/nprogress.js']
  return gulp.src(jsPjax).
  pipe(terser({
    output: {
      ascii_only: true,
    },
  })).
  pipe(concat('pjax.min.js')).
  pipe(gulp.dest('./src/main/webapp/js/lib/compress/'))
}

function minSkinJS () {
  // minify js
  return gulp.src('./src/main/webapp/skins/*/js/*.js').
  pipe(rename({suffix: '.min'})).
  pipe(terser({
    output: {
      ascii_only: true,
    },
  })).
  pipe(gulp.dest('./src/main/webapp/skins/'))
}

function cleanProcess () {
  return del([
    './src/main/webapp/js/*.min.js',
    './src/main/webapp/skins/*/js/*.min.js'])
}

gulp.task('default',
    gulp.series(cleanProcess, sassSkinProcess, sassCommonProcess,
        gulp.parallel(minSkinJS, minJS),
        gulp.parallel(miniPjax, miniAdmin, miniAdminLibs)))
