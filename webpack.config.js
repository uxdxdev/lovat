var path = require('path');
var webpack = require('webpack')
var ROOT = path.resolve(__dirname, 'src/main/resources/assets');
var SRC = path.resolve(ROOT, 'jsx');
var DEST = path.resolve(__dirname, 'src/main/resources/assets/js');

module.exports = {
  devtool: 'source-map',
  entry: {
    app: SRC + '/index.jsx',
  },
  resolve: {
    modules: [
      path.resolve(ROOT, 'jsx')
    ],
    extensions: ['*', '.js', '.jsx']    
  },
  output: {
    path: DEST,
    filename: 'bundle.js',
    publicPath: '/js/'
  },
  module: {
    loaders: [
      {
        test: /\.jsx?$/,  // Notice the regex here. We're matching on js and jsx files.
        loaders: 'babel-loader',
        include: SRC,
        query: {
          presets: ['es2015', 'react']
        }
      }
    ]
  }
}
