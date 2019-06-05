import React, {Component} from "react";
import {BrowserRouter as Router, NavLink, Redirect, Route, Switch} from "react-router-dom";
import {DashboardPage} from "layout/DashboardPage.jsx";
import {ArticleList} from "layout/ArticleList.jsx";
import {Icon, Layout, Menu} from "antd";
import commonCss from "common/common.css";
import css from "./App.css";
import _ from "lodash";
import LoginPage from "layout/LoginPage.jsx";
import {Copyright} from "ui";

const {Header, Sider, Content, Footer} = Layout;

const Sidebar = ({location, collapsed}) => {
    const path = _.chain(location.pathname).split("/").get(1).value();
    return (
        <Sider trigger={null} collapsible collapsed={collapsed} className={commonCss.fullHeight}>
            <Menu selectedKeys={[path || "dashboard"]}
                  theme="dark" mode="inline">
                <Menu.Item key="dashboard">
                    <Icon type="dashboard"/>
                    <span>dashboard</span>
                    <NavLink to="/dashboard"/>
                </Menu.Item>
                <Menu.Item key="article">
                    <Icon type="form"/>
                    <span>article</span>
                    <NavLink to="/article"/>
                </Menu.Item>
                <Menu.Item key="tmp">
                    <Icon type="upload"/>
                    <span>nav 3</span>
                    <NavLink to="/tmp"/>
                </Menu.Item>
            </Menu>
        </Sider>
    );
};

class Root extends Component {

    state = {collapsed: false};

    toggle = () => this.setState(({collapsed}) => ({collapsed: !collapsed}));

    render() {
        return (
            <Layout style={{minWidth: 740}}>
                <Sidebar location={this.props.location}
                         collapsed={this.state.collapsed}/>
                <Layout>
                    <Header className={css.header}>
                        <Icon className="trigger" onClick={this.toggle}
                              type={this.state.collapsed ? 'menu-unfold' : 'menu-fold'}/>
                    </Header>
                    <Content className={css.content}>
                        <Switch>
                            <Route path="/" exact render={() => <Redirect to="/dashboard"/>}/>
                            <Route path="/dashboard" component={DashboardPage}/>
                            <Route path="/article" component={ArticleList}/>
                        </Switch>
                    </Content>
                    <Footer><Copyright/></Footer>
                </Layout>
            </Layout>
        );
    }
}

export default class App extends Component {
    render() {
        return (
            <Router>
                <Switch>
                    <Route path="/login" component={LoginPage}/>
                    <Route path="/" component={Root}/>
                </Switch>
            </Router>
        );
    }
};

