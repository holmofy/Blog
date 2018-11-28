const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');

module.exports = {
    entry: './src/index.js',
    output: {
        filename: 'bundle.js',
        path: path.resolve(__dirname, 'dist')
    },
    devServer: {
        contentBase: './dist',
        port: 3000
    },
    module: {
        rules: [{
            test: /\.js[x]?$/,
            include: path.resolve(__dirname, 'src'),
            use: {
                loader: 'babel-loader',
                options: {
                    presets: ["react", "env", "stage-0"]
                }
            }
        }, {
            test: /\.css$/,
            use: [
                {loader: 'style-loader'},
                {
                    loader: 'css-loader',
                    options: {
                        modules: true
                    }
                }
            ]
        }]
    },
    resolve: {
        extensions: ['.js', '.jsx', '.json'],
        alias: {
            util: path.resolve(__dirname, "src/util"),
            service: path.resolve(__dirname, "src/service"),
            layout: path.resolve(__dirname, "src/layout"),
            common: path.resolve(__dirname, "src/common"),
            ui: path.resolve(__dirname, "src/ui"),
            config$: path.resolve(__dirname, "src/config.js")
        }
    },
    plugins: [
        new HtmlWebpackPlugin({
            title: "开发中...",
            template: "./public/index.html"
        }),
    ]
};