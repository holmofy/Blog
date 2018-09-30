import React from 'react';
import {getArticles} from "service/article.js";
import _ from "lodash";

const ArticleItem = ({item}) => {
    return (
        <div>列表项</div>
    );
};

export class ArticleList extends React.Component {

    state = {};

    componentDidMount() {
        this.fetchData();
    }

    fetchData(page, size) {
        getArticles(page, size).then((data) => this.setState({data}));
    }

    render() {
        let {data = {}} = this.state;
        let {content} = data;
        console.log(data);
        return (
            <div>
                {_.map(content, item => <ArticleItem key={_.get(item, "id")} item={item}/>)}
            </div>
        );
    }
}