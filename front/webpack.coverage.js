const path = require('path');

console.log('>>> webpack.coverage.js CHARGE - instrumentation Istanbul activee <<<');

module.exports = {
  module: {
    rules: [
      {
        test: /\.ts$/,
        enforce: 'post',
        include: path.join(__dirname, 'src'),
        exclude: [
          /\.(e2e|spec)\.ts$/,
          /node_modules/,
          /(ngfactory|ngstyle)\.js/
        ],
        use: {
          loader: 'babel-loader',
          options: {
            plugins: ['babel-plugin-istanbul']
          }
        }
      }
    ]
  }
};