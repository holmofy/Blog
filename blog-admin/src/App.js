import React from "react";
import {BrowserRouter as Router, NavLink, Route, Switch} from "react-router-dom";
import {DashboardPage} from "./layout/DashboardPage.jsx";
import {ArticleList} from "./layout/ArticleList.jsx";
import {Icon, Layout, Menu} from "antd";
import css from "./App.css";

const {Header, Sider, Content, Footer} = Layout;

const RouteContent = () => {
    return (
        <Switch>
            <Route path="/" exact component={DashboardPage}/>
            <Route path="/article" component={ArticleList}/>
        </Switch>
    );
};

const Sidebar = ({collapsed}) => {
    return (
        <Sider trigger={null} collapsible collapsed={collapsed}>
            <div className="logo"/>
            <Menu theme="dark" mode="inline" defaultSelectedKeys={['1']}>
                <Menu.Item key="1">
                    <Icon type="dashboard"/>
                    <span>面板</span>
                    <NavLink to="/"/>
                </Menu.Item>
                <Menu.Item key="2">
                    <Icon type="form"/>
                    <span>文章</span>
                    <NavLink to="/article"/>
                </Menu.Item>
                <Menu.Item key="3">
                    <Icon type="upload"/>
                    <span>nav 3</span>
                </Menu.Item>
            </Menu>
        </Sider>
    );
};

export default class App extends React.Component {

    state = {collapsed: false};

    toggle = () => this.setState({collapsed: !this.state.collapsed});

    render() {
        return (
            <Router>
                <Layout>
                    <Sidebar collapsed={this.state.collapsed}/>
                    <Layout>
                        <Header className={css.header}>
                            <Icon className="trigger" onClick={this.toggle}
                                  type={this.state.collapsed ? 'menu-unfold' : 'menu-fold'}/>
                        </Header>
                        <Content className={css.content}><RouteContent/></Content>
                        {/*<Footer>赣ICP备17009276号 &copy; 2016 - 2018 power by 胡飞飞</Footer>*/}
                    </Layout>
                </Layout>
            </Router>
        );
    }
}

