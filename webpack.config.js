var path = require('path');
var SRC = path.resolve(__dirname, 'frontend/webapp/js');
var DEST = path.resolve(__dirname, 'src/main/resources/assets/js');
var CopyWebpackPlugin = require('copy-webpack-plugin');

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
	},
    plugins: [
        new CopyWebpackPlugin([
            {
                from: './src/main/resources/assets/styles.css',
                to: path.resolve(__dirname, 'build/resources/main/assets/styles.css')
            }
        ])]
}

module.exports = config;