var path = require('path');
var webpack = require('webpack')
var ROOT = path.resolve(__dirname, 'src/main/resources/assets');
var SRC = path.resolve(ROOT, 'js');
var DEST = path.resolve(ROOT, 'js');

var config = {
	devtool : 'source-map',
	entry : SRC + '/index.js',
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