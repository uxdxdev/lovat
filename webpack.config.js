var path = require('path');
var webpack = require('webpack')
var ROOT = path.resolve(__dirname, 'src/main/resources/assets');
var SRC = path.resolve(ROOT, 'jsx');
var DEST = path.resolve(ROOT, 'js');
var BUILD_ROOT = path.resolve(__dirname, 'build/resources/main/assets');
var BUILD_DEST = path.resolve(BUILD_ROOT, 'js');

module.exports = {	
	resolve : {
		modules : [ path.resolve(ROOT, 'jsx') ],
		extensions : [ '*', '.js', '.jsx' ]
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

module.exports = [
                  {
                	  devtool : 'source-map',	                  
                	  entry : {
                			app : SRC + '/index.jsx',
                		},
                		output : {
                			path : DEST,
                			filename : 'bundle.js',
                			publicPath : '/js/'
                		},
                  },
                  {                	 
                	  devtool : 'source-map',	
                	  entry : {
                			app : SRC + '/index.jsx',
                		},
                		output : {
                			path : BUILD_DEST,
                			filename : 'bundle.js',
                			publicPath : '/js/'
                		},
                  }
                  ];