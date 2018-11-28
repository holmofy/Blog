import ajax from "util/ajax.js";

const path = "/api/article";

export const findArticleById = (id) => ajax(path).path(id).get();

export const getArticles = (published, page = 1, size = 15) => ajax(path).query({published, page, size}).get();

export const saveArticle = (article) => ajax(path).payload(article).post();

export const updateArticle = (id, article) => ajax(path).path(id).payload(article).put();

export const deleteArticle = (id) => ajax(path).path(id).delete();

export const likeTitlePrefix = (titlePrefix, size) => ajax(path).path("prefix").query({titlePrefix, size}).get();

export const likeFuzzyTitle = (fuzzyTitle, size) => ajax(path).path("fuzzy-title").query({fuzzyTitle, size}).get();