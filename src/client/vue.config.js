const TerserPlugin = require("terser-webpack-plugin")

module.exports = {
    runtimeCompiler: true,
    configureWebpack: {
        resolve: {
            alias: {
                'balm-ui-plus': 'balm-ui/dist/balm-ui-plus.js',
                'balm-ui-css': 'balm-ui/dist/balm-ui.css'
            }
        },
        optimization: {
            minimize: true,
            minimizer: [new TerserPlugin()],
            splitChunks: {
                chunks: 'all'
            }
        },
    },
    pages: {
        index: {
            entry: 'src/main.ts',
            title: 'Weeki',
        },
    }
}