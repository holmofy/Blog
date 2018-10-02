import React from 'react';
import {getArticles} from "service/article.js";
import {Card, Spin} from "antd";
import _ from "lodash";
import css from "./ArticleList.css";

const ArticleItem = ({item}) => {
    let {id, title, createTime, updateTime, views, comments, categoryName} = item;
    return (
        <div key={id} className={css.article}>
            <h2>{title}<sub>{updateTime}</sub></h2>
            <p>{views}</p>
        </div>
    );
};

const tabList = [{key: true, tab: "post"}, {key: false, tab: "draft"}];

export class ArticleList extends React.Component {

    state = {};

    componentDidMount() {
        this.fetchData(true);
    }

    fetchData(published, page) {
        let {size} = this.state;
        this.setState({loading: true});
        getArticles(published, page, size).then((data) => this.setState({data, loading: false}));
    }

    onTabChange(published) {
        this.fetchData(published, 1);
    }

    render() {
        let {data = {}} = this.state;
        let {content} = data;
        console.log(data);
        return (
            <Card tabList={tabList} onTabChange={(key) => this.onTabChange(key)}>
                <Spin spinning={this.state.loading} tip="Loading...">
                    {_.map(content, item => <ArticleItem key={item.id} item={item}/>)}
                </Spin>
            </Card>
        );
    }
}