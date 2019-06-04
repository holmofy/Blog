import React from "react";
import {BrowserRouter as Router, NavLink, Route, Switch} from "react-router-dom";
import {DashboardPage} from "./layout/DashboardPage.jsx";
import {ArticleList} from "./layout/ArticleList.jsx";
import {Icon, Layout, Menu} from "antd";
import css from "./App.css";

const {Header, Sider, Content, Footer} = Layout;

const Sidebar = ({location, collapsed}) => {
    console.log(location)
    return (
        <Sider trigger={null} collapsible collapsed={collapsed}>
            <Menu selectedKeys={[location.pathname]} defaultSelectedKeys={['1']}
                  theme="dark" mode="inline">
                <Menu.Item key="/dashboard">
                    <Icon type="dashboard"/>
                    <span>dashboard</span>
                    <NavLink to="/dashboard"/>
                </Menu.Item>
                <Menu.Item key="/article">
                    <Icon type="form"/>
                    <span>article</span>
                    <NavLink to="/article"/>
                </Menu.Item>
                <Menu.Item key="/tmp">
                    <Icon type="upload"/>
                    <span>nav 3</span>
                    <NavLink to="/tmp"/>
                </Menu.Item>
            </Menu>
        </Sider>
    );
};

class Root extends React.Component {

    state = {collapsed: false};

    toggle = () => this.setState(({collapsed}) => ({collapsed: !collapsed}));

    render() {
        return (
            <Layout>
                <Sidebar location={this.props.location}
                         collapsed={this.state.collapsed}/>
                <Layout>
                    <Header className={css.header}>
                        <Icon className="trigger" onClick={this.toggle}
                              type={this.state.collapsed ? 'menu-unfold' : 'menu-fold'}/>
                    </Header>
                    <Content className={css.content}>
                        <Switch>
                            <Route path="/dashboard" exact component={DashboardPage}/>
                            <Route path="/article" component={ArticleList}/>
                        </Switch>
                    </Content>
                    {/*<Footer>赣ICP备17009276号 &copy; 2016 - 2018 power by 胡飞飞</Footer>*/}
                </Layout>
            </Layout>
        );
    }
}

const App = () => <Router><Route path="/" component={Root}/></Router>;

export default App;

