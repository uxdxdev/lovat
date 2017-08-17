var path = require('path');
var webpack = require('webpack')
var ROOT = path.resolve(__dirname, 'src/main/resources');
var SRC = path.resolve(ROOT, 'webapp/js');
var DEST = path.resolve(ROOT, 'assets/');

var config = {
	devtool : 'source-map',
	entry : SRC + '/App.js',
	output : {
		path : DEST,
		filename : 'bundle.js'
	},	
	module : {
		loaders : [ {
			test : /\.js?$/,
			exclude : /node_modules/,
			loaders : 'babel-loader',
			include : SRC,
			query : {
				presets : [ 'es2015', 'react' ]
			}
		} ]
	}
}

module.exports = config;