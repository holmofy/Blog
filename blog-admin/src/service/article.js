import ajax from "util/ajax.js";

const path = "/api/article";

export const findArticleById = (id) => ajax(path).appendChild(id).get();

export const getArticles = (page = 1, size = 15) => ajax(path).query({page, size}).get();

export const likeTitlePrefix = (titlePrefix, size) => ajax(path).appendChild("prefix").query({titlePrefix, size}).get();