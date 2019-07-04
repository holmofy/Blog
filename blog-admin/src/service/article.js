import ajax from "util/ajax.js";

const path = "/api/article";

export function findArticleById(id) {
    return ajax(path).path(id).get();
}

export function search(query, page = 1, size = 15) {
    return ajax(path).query({...query, page, size}).get();
}

export function saveArticle(article) {
    return ajax(path).payload(article).post();
}

export function updateArticle(id, article) {
    return ajax(path).path(id).payload(article).put();
}

export function deleteArticle(id) {
    return ajax(path).path(id).delete();
}

export function likeTitlePrefix(titlePrefix, size) {
    return ajax(path).path("prefix").query({titlePrefix, size}).get();
}

export function likeFuzzyTitle(fuzzyTitle, size) {
    return ajax(path).path("fuzzy-title").query({fuzzyTitle, size}).get();
}