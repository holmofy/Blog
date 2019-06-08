import React, {Component, Fragment} from 'react';
import {deleteArticle, getArticles, likeFuzzyTitle, likeTitlePrefix} from "service/article.js";
import {AutoComplete, Card, Icon, Input, List, message, Modal, Select, Tooltip} from "antd";
import css from "./ArticleList.less";
import commonCss from "common/common.less";
import {ButtonLink} from "ui";
import _ from "lodash";
import {ArticleModal} from "component/ArticleModal.jsx";
import {ModalWrapper} from "ui/Modal.jsx";
import {NavLink, withRouter} from "react-router-dom";
import {pickQuery, searchAssign} from "util/router.js";
import {pageableToPagination} from "ui/Pager.jsx";

const ArticleItem = ({item, onEdit, onView, onDeny, onDelete}) => {
    let {id, title, created, modified, views, comments} = item;
    return (
        <div key={id} className={css.article}>
            <h2 onClick={onEdit}><NavLink to={"/article/" + id}>{title}</NavLink></h2>
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
                    <span className={commonCss.span}>创建时间：{created}</span>
                    <span className={commonCss.span}>最后修改时间：{modified}</span>
                </div>
                <div className={commonCss.flex1}/>
                <div>
                    <ButtonLink className={commonCss.span} onClick={() => onView(id)}>查看</ButtonLink>
                    <ButtonLink className={commonCss.span} onClick={() => onDeny(id)}>禁止评论</ButtonLink>
                    <ButtonLink className={commonCss.span} onClick={() => onDelete(id)}>删除</ButtonLink>
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

const CardExtra = ({searchSuggest, onNew, onCompleteSearch, onSearch}) => (
    <Fragment>
        <Tooltip title="新建">
            <Icon className={css.edit} type="edit" onClick={onNew}/>
        </Tooltip>
        <SearchForm dataSource={searchSuggest}
                    onCompleteSearch={onCompleteSearch}
                    onSearch={onSearch}/>
    </Fragment>
);

@withRouter
export class ArticleList extends Component {

    tabList = [{key: true, tab: "已发表"}, {key: false, tab: "草稿箱"}];

    paramConfig = {published: 'boolean', page: 'number', size: 'number'};

    state = {};

    componentDidMount() {
        const {location} = this.props;
        const {published = true, page = 1, size = 10} = pickQuery(location, this.paramConfig);
        this.fetchData(published, page, size);
    }

    async fetchData(published, page, size) {
        this.setState({published, page, size, loading: true});
        const data = await getArticles(published, page, size);
        this.setState({data, loading: false});
    }

    handleTabChange = (published) => searchAssign({published, page: 1});

    handlePagerChange = (page, size) => searchAssign({page, size});

    handleCompleteSearch = async (value) => {
        const searchSuggest = await likeTitlePrefix(value);
        this.setState({searchSuggest});
    };

    handleSearch = async (value) => {
        const data = await likeFuzzyTitle(value);
        console.log(data);
    };

    handleDelete = (articleId) => {
        Modal.warning({
            content: "确认删除改文章",
            onOk: async () => {
                try {
                    await deleteArticle(articleId);
                    message.success("删除成功");
                } catch (e) {
                    console.log(e)
                }
            }
        });
    };
    handleNew = () => {
        this.setState({showModal: true});
    };

    handleModalClose = () => {
        this.setState({showModal: false});
    };

    render() {
        const {data = {}, searchSuggest = []} = this.state;
        const {content, totalElements, pageable} = data;
        return (
            <Card title="文章列表" activeTabKey={_.toString(this.state.published)}
                  tabList={this.tabList} onTabChange={this.handleTabChange}
                  extra={<CardExtra searchSuggest={searchSuggest}
                                    onSearch={this.handleSearch} onNew={this.handleNew}
                                    onCompleteSearch={this.handleCompleteSearch}/>}>
                <List dataSource={content}
                      loading={this.state.loading}
                      renderItem={item => <ArticleItem key={item.id} item={item}
                                                       onDelete={this.handleDelete}/>}
                      pagination={{
                          showSizeChanger: true,
                          showQuickJumper: true,
                          total: totalElements,
                          onShowSizeChange: this.handlePagerChange,
                          onChange: this.handlePagerChange,
                          ...pageableToPagination(pageable)
                      }}/>
                <ModalWrapper title="新建" visible={this.state.showModal}
                              onCancel={this.handleModalClose}>
                    <ArticleModal/>
                </ModalWrapper>
            </Card>
        );
    }
}