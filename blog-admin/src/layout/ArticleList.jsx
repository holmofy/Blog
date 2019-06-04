import React from 'react';
import {deleteArticle, getArticles, likeFuzzyTitle, likeTitlePrefix} from "service/article.js";
import {AutoComplete, Card, Icon, Input, List, message, Modal, Select} from "antd";
import css from "./ArticleList.css";
import commonCss from "common/common.css";
import {ButtonLink} from "ui";
import _ from "lodash";

const ArticleItem = ({item, onEdit, onView, onDeny, onDelete}) => {
    let {id, title, createTime, updateTime, views, comments} = item;
    return (
        <div key={id} className={css.article}>
            <h2 onClick={onEdit}>{title}</h2>
            <div className={commonCss.flexBox}>
                <div>
                    <span className={commonCss.span}>
                        <Icon type="eye"/>
                        <span className={css.num}>{views}</span>
                    </span>
                    <span className={commonCss.span}>
                        <Icon type="message"/>
                        <span className={css.num}>{comments}</span>
                    </span>
                    <span className={commonCss.span}>创建时间：{createTime}</span>
                    <span className={commonCss.span}>最后修改时间：{updateTime}</span>
                </div>
                <div className={commonCss.flex1}/>
                <div>
                    <ButtonLink className={commonCss.span} onClick={onView}>查看</ButtonLink>
                    <ButtonLink className={commonCss.span} onClick={onDeny}>禁止评论</ButtonLink>
                    <ButtonLink className={commonCss.span} onClick={onDelete}>删除</ButtonLink>
                </div>
            </div>
        </div>
    );
};

const SearchForm = ({dataSource, onCompleteSearch, onSearch}) => {
    let options = _.map(dataSource, ({id, title}) => (
        <Select.Option key={id} value={_.toString(id)}>{title}</Select.Option>)
    );
    return (
        <AutoComplete dataSource={options} onSearch={onCompleteSearch}>
            <Input.Search placeholder="输入文章标题" onSearch={onSearch}/>
        </AutoComplete>
    );
};

const tabList = [{key: true, tab: "已发表"}, {key: false, tab: "草稿箱"}];

export class ArticleList extends React.Component {

    state = {};

    componentDidMount() {
        this.fetchData(true);
    }

    fetchData(published, page, size) {
        this.setState({loading: true});
        getArticles(published, page, size).then((data) => this.setState({data, loading: false}));
    }

    handleTabChange = (published) => {
        this.fetchData(published, 1);
    };

    handleSizeChange = (curr, size) => {
        this.fetchData(this.state.published, curr, size);
    };

    handlePageChange = (page, size) => {
        this.fetchData(this.state.published, page, size);
    };

    handleCompleteSearch = (value) => {
        likeTitlePrefix(value).then(searchSuggest => this.setState({searchSuggest}))
    };

    handleSearch = (value) => {
        likeFuzzyTitle(value).then(data => console.log(data));
    };

    handleDelete = (articleId) => {
        Modal.confirm({
            content: "确认删除改文章",
            onOk: () => deleteArticle(articleId)
                .then(() => message.success("删除成功"))
                .catch(err => message.error(`删除失败: ${err}`))
        });
    };

    render() {
        let {data = {}, searchSuggest = []} = this.state;
        let {content, totalElements} = data;
        console.log(searchSuggest);
        return (
            <Card title="文章列表" tabList={tabList} onTabChange={this.handleTabChange}
                  extra={<SearchForm dataSource={searchSuggest}
                                     onCompleteSearch={this.handleCompleteSearch}
                                     onSearch={this.handleSearch}/>}>
                <List dataSource={content}
                      loading={this.state.loading}
                      renderItem={item => <ArticleItem key={item.id} item={item}
                                                       onDelete={this.handleDelete}/>}
                      pagination={{
                          showSizeChanger: true,
                          showQuickJumper: true,
                          total: totalElements,
                          onShowSizeChange: this.handleSizeChange,
                          onChange: this.handlePageChange
                      }}/>
            </Card>
        );
    }
}