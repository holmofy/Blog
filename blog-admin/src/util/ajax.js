import axios from 'axios';
import _ from "lodash";
import config from "config";

/**
 * Axios相关配置
 * @see https://www.npmjs.com/package/axios
 */

/* Creating an instance */
const instance = axios.create({
    baseURL: config.server.path, // 本地做反向代理
    timeout: 5000,
    responseType: 'json',
    // withCredentials: true, // 是否允许带cookie这些
});

instance.interceptors.response.use((response) => {
    return response.data
}, (err) => Promise.reject(err));

function childUrl(child) {
    let baseUrl = this.url;
    if (!baseUrl) {
        this.url = child;
        return this;
    }
    if (_.last(baseUrl) === '/') {
        baseUrl = baseUrl.slice(0, -1);
    }
    this.url = _.head(child) === '/' ? baseUrl + child : baseUrl + '/' + child;
    return this;
}

function query(queryObj) {
    this.params = queryObj;
    return this;
}

function payload(payload) {
    this.data = payload;
    return this;
}

function addHeader(key, value) {
    this.headers = _.assign(this.headers, {[key]: value});
    return this;
}

function get() {
    this.method = "get";
    return instance.request(this);
}

function post() {
    this.method = "post";
    return instance.request(this);
}

function _delete_() {
    this.method = "delete";
    return instance.request(this);
}

function put() {
    this.method = "put";
    return instance.request(this);
}


function ajax(url) {
    return {
        url,
        childUrl: childUrl,
        query: query,
        payload: payload,
        addHeader: addHeader,
        get: get,
        post: post,
        'delete': _delete_,
        put: put
    };
}

export default ajax;