module.exports = {
    devServer: {
		disableHostCheck: true,
		proxy: {
			'/api/': {
				target: 'http://localhost:8000/',
				changeOrigin: true,
				//logLevel: 'debug',
				pathRewrite: { "^/api/": "/" }
			},
		},
    }
};