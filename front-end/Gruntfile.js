'use strict';

module.exports = function (grunt) {

  /* global process*/

  // configures browsers to run test against
  // any of [ 'PhantomJS', 'Chrome', 'Firefox', 'IE']
  var TEST_BROWSERS = ((process.env.TEST_BROWSERS || '').replace(/^\s+|\s+$/, '') || 'PhantomJS').split(/\s*,\s*/g);


  require('time-grunt')(grunt);
  require('load-grunt-tasks')(grunt);


  grunt.initConfig({

    pkg: grunt.file.readJSON('package.json'),

    config: {
      sources: 'lib',
      dist: 'dist',
      assets: 'assets',
      tests: 'test',
      bower_components: 'bower_components'
    },

    jshint: {
      src: [
        ['<%= config.sources %>']
      ],
      gruntfile: [
        'Gruntfile.js'
      ],
      options: {
        jshintrc: true
      }
    },

    karma: {
      options: {
        configFile: '<%= config.tests %>/config/karma.unit.js',
      },
      single: {
        singleRun: true,
        autoWatch: false,

        browsers: TEST_BROWSERS,

        browserify: {
          debug: false,
          transform: [ 'brfs' ]
        }
      },
      unit: {
        browsers: TEST_BROWSERS
      }
    },
    browserify: {
      options: {
        transform: [ 'brfs' ],
        browserifyOptions: {
          builtins: [ 'fs' ],
          commondir: false
        },
        bundleOptions: {
          detectGlobals: false,
          insertGlobalVars: []
        }
      },
      app: {
        files: {
          '<%= config.dist %>/bringste.js': [ '<%= config.sources %>/bringste.js' ]
        }
      },
      watch: {
        options: {
          watch: true,
          keepalive: true
        },
        files: {
          '<%= config.dist %>/bringste.js': [ '<%= config.sources %>/bringste.js' ]
        }
      }
    },
    uglify: {
      dist: {
        files: {
          '<%= config.dist %>/bringste.min.js': [ '<%= config.dist %>/bringste.js' ]
        }
      }
    },
    copy: {
      resources: {
        files: [
          // index.html
          { expand: true, cwd: '<%= config.sources %>', src: [ '*.html' ], dest: '<%= config.dist %>' },

          // assets
          {
            expand: true,
            cwd: '<%= config.assets %>',
            src: [
              '**/*'
            ],
            dest: '<%= config.dist %>'
          },

          {
            expand: true,
            cwd: '<%= config.bower_components %>/ratchet/dist',
            src: [
              'css/**/*',
              'fonts/**/*',
              'js/**/*'
            ],
            dest: '<%= config.dist %>/vendor/ratchet'
          }
        ]
      }
    },

    connect: {
      options: {
        port: 9025,
        livereload: 19025,
        hostname: 'localhost'
      },
      livereload: {
        options: {
          open: true,
          base: [
            '<%= config.dist %>'
          ]
        }
      }
    },

    watch: {
      resources: {
        files: [
          '<%= config.assets %>/img/**/*',
          '<%= config.assets %>/css/**/*',
          '<%= config.sources %>/*.html'
        ],
        tasks: [ 'copy:resources' ]
      },
      livereload: {
        options: {
          livereload: '<%= connect.options.livereload %>'
        },
        files: [
          '<%= config.dist %>/**/*.*'
        ]
      }
    }
  });

  grunt.registerTask('test', 'karma:single');

  grunt.registerTask('build', [ 'browserify:app', 'copy', 'uglify' ]);

  grunt.registerTask('serve', [
    'build',
    'connect:livereload',
    'watch'
  ]);

  grunt.registerTask('default', [ 'test', 'build' ]);
};
