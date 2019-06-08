const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');

/**
 * https://webpack.js.org/configuration/dev-server
 */
function buildDevServer() {
    return {
        contentBase: './dist',
        port: 3000,
        compress: true,
        historyApiFallback: {
            rewrites: [
                {from: /^\/*$/, to: '/'}
            ]
        },
        proxy: {
            '/api': 'http://localhost:2000'
        }
    };
}

/**
 * https://babeljs.io/docs/en/presets
 * https://babeljs.io/docs/en/plugins
 * @returns {{presets: *[], plugins: *[]}}
 */
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
            ["@babel/plugin-proposal-decorators", {legacy: true}],
            ["@babel/plugin-proposal-class-properties", {loose: true}],
            ["@babel/plugin-transform-runtime"]
        ]
    };
}

/**
 * https://webpack.js.org/configuration/resolve
 */
function buildResolve() {
    return {
        extensions: ['.js', '.jsx', '.json'],
        alias: {
            util: path.resolve(__dirname, "src/util"),
            service: path.resolve(__dirname, "src/service"),
            layout: path.resolve(__dirname, "src/layout"),
            common: path.resolve(__dirname, "src/common"),
            module: path.resolve(__dirname, "src/module"),
            component: path.resolve(__dirname, "src/component"),
            ui: path.resolve(__dirname, "src/ui"),
            config$: path.resolve(__dirname, "src/config.js")
        }
    };
}

/**
 * https://webpack.js.org/loaders/babel-loader
 */
const babelLoaderRule = {
    test: /\.js[x]?$/,
    include: path.resolve(__dirname, 'src'),
    use: {
        loader: 'babel-loader',
        options: babelOptions()
    }
};

/**
 * https://webpack.js.org/loaders/less-loader
 */
const lessLoaderRule = {
    test: /\.less$/,
    use: [
        {
            loader: 'style-loader', // creates style nodes from JS strings
        },
        {
            loader: 'css-loader', // translates CSS into CommonJS
            options: {
                modules: true
            }
        },
        {
            loader: 'less-loader', // compiles Less to CSS
        }
    ]
};

module.exports = {
    mode: 'development',
    entry: './src/index.js',
    output: {
        // 表示在引入静态资源时，从根路径开始引入
        publicPath: '/',
        filename: 'bundle.js',
        path: path.resolve(__dirname, 'dist')
    },
    devServer: buildDevServer(),
    module: {
        rules: [babelLoaderRule, lessLoaderRule]
    },
    resolve: buildResolve(),
    plugins: [
        new HtmlWebpackPlugin({
            title: "开发中...",
            template: "./public/index.html"
        }),
    ]
};