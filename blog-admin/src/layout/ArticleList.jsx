import React, {Component} from 'react';
import {getArticles} from "service/article.js";
import {Card, Spin} from "antd";
import _ from "lodash";
import css from "./ArticleList.css";

const ArticleItem = ({item}) => {
    const {id, title, created, modified, views, comments, categoryName} = item;
    return (
        <div key={id} className={css.article}>
            <h3>{title}<sub>{modified}</sub></h3>
            <p>{views}</p>
        </div>
    );
};

const tabList = [{key: true, tab: "post"}, {key: false, tab: "draft"}];

export class ArticleList extends Component {

    state = {};

    componentDidMount() {
        this.fetchData(true);
    }

    async fetchData(published, page) {
        const {size} = this.state;
        this.setState({loading: true});
        const data = await getArticles(published, page, size);
        this.setState({data, loading: false});
    }

    onTabChange = (published) => {
        this.fetchData(published, 1);
    };

    render() {
        const {loading, data = {}} = this.state;
        const {content} = data;
        return (
            <Card tabList={tabList} onTabChange={this.onTabChange}>
                <Spin spinning={loading} tip="Loading...">
                    {_.map(content, item => <ArticleItem key={item.id} item={item}/>)}
                </Spin>
            </Card>
        );
    }
}