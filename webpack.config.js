var path = require('path');
var webpack = require('webpack')
var SRC = path.resolve(__dirname, 'frontend/webapp/js');
var DEST = path.resolve(__dirname, 'src/main/resources/assets/');

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