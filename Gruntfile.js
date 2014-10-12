module.exports = function(grunt) {
	var port = grunt.option('port') || 8000;
	// Project configuration
	grunt.initConfig({
	  
	  pkg: grunt.file.readJSON('reveal.js/package.json'),
	  
	  connect: {
			server: {
				options: {
					port: port,
					base: '.'
				}
			}
		},
		
		watch: {
			main: {
				files: [ 'Gruntfile.js', 'index.html']
			}
		}
	});
	
	grunt.loadNpmTasks( 'grunt-contrib-connect' );
	grunt.loadNpmTasks( 'grunt-contrib-watch' );
	grunt.registerTask( 'serve', [ 'connect', 'watch'] );
	grunt.registerTask( 'default', ['serve']);
};
