const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');

function babelOptions() {
    return {
        "presets": [
            "@babel/react",
            [
                "@babel/env",
                {
                    "targets": {
                        "chrome": "58",
                        "ie": "11"
                    },
                    "debug": true
                }
            ]
        ],
        "plugins": [
            "@babel/plugin-proposal-class-properties",
            ["@babel/plugin-proposal-decorators", {decoratorsBeforeExport: true}],
            ["@babel/plugin-transform-runtime"]
        ]
    };
}

module.exports = {
    entry: './src/index.js',
    output: {
        filename: 'bundle.js',
        path: path.resolve(__dirname, 'dist')
    },
    devServer: {
        contentBase: './dist',
        port: 3000,
        historyApiFallback: {
            rewrites: [
                {from: /^\/$/, to: '/'}
            ]
        },
    },
    module: {
        rules: [{
            test: /\.js[x]?$/,
            include: path.resolve(__dirname, 'src'),
            use: {
                loader: 'babel-loader',
                options: babelOptions()
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
            config$: path.resolve(__dirname, "src/config.js")
        }
    },
    plugins: [
        new HtmlWebpackPlugin({
            title: "开发",
            template: "./public/index.html"
        })
    ]
};