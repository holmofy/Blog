import ajax from "util/ajax.js";

const path = "/api/category";

export function findAll() {
    return ajax(path).get();
}