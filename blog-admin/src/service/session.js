import ajax from "util/ajax.js";

const path = "/api/session";

export function login(principal, credential) {
    return ajax(path).query({principal, credential}).post();
}

export function logout() {
    return ajax(path).delete();
}