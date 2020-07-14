{
  mode: 'development',
  resolve: {
    modules: [
      'node_modules'
    ]
  },
  plugins: [],
  module: {
    rules: [
      {
        test: /\.js$/,
        use: [
          'kotlin-source-map-loader'
        ],
        enforce: 'pre'
      }
    ]
  },
  entry: {
    main: [
      '/Users/yt8492/StudioProjects/Calculator/build/js/packages/Calculator-webfront/kotlin/Calculator-webfront.js'
    ]
  },
  output: {
    path: '/Users/yt8492/StudioProjects/Calculator/webfront/build/distributions',
    filename: [Function: filename],
    library: 'webfront',
    libraryTarget: 'umd'
  },
  devtool: 'eval-source-map',
  devServer: {
    inline: true,
    lazy: false,
    noInfo: true,
    open: true,
    overlay: false,
    port: 8080,
    contentBase: [
      '/Users/yt8492/StudioProjects/Calculator/webfront/build/processedResources/Js/main'
    ]
  }
}