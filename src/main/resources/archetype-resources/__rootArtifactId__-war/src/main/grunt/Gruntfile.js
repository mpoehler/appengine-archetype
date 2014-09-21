module.exports = function(grunt) {

    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),

        clean: ["dist", '.tmp'],

        copy: {
            production: {
                files: [
                    // just copy everything except WEB-INF to dist folder
                    {expand: true, cwd: '../src/main/webapp', src: ['**/*', '!WEB-INF'], dest: 'dist/'},

                    // copy bower dependency tree
                    {expand: true, src: ['bower_components/**'], dest: 'dist/'},
                ]
            },
            dev: {
                files: [
                    // copy bower dependency tree
                    {expand: true, src: ['bower_components/**'], dest: '../target/${rootArtifactId}-war-${version}/'},
                ]
            }
        },

        rev: {
            files: {
                src: ['dist/css/app.css', 'dist/js/app.js']
            }
        },

        useminPrepare: {
            html: ['**/*.jsp' ]
        },

        usemin: {
            html: [ 'dist/**/*.jsp' ],
            options: {
                root: 'app'
            }
        },

        uglify: {
            options: {
                report: 'min',
                mangle: false
            }
        }
    });

    grunt.loadNpmTasks('grunt-contrib-clean');
    grunt.loadNpmTasks('grunt-contrib-copy');
    grunt.loadNpmTasks('grunt-contrib-concat');
    grunt.loadNpmTasks('grunt-contrib-cssmin');
    grunt.loadNpmTasks('grunt-contrib-uglify');
    grunt.loadNpmTasks('grunt-rev');
    grunt.loadNpmTasks('grunt-usemin');

    // run everything to build a compact js application
    grunt.registerTask('production', [ 'copy:production', 'useminPrepare', 'concat', 'uglify', 'cssmin', 'rev', 'usemin' ]);

    // only copy the bower dependencies to the exploded war directory
    grunt.registerTask('dev', [ 'copy:dev' ]);
};
