import React from "react";
import commonCss from "common/common.less";
import {Button, Form, Icon, Input, Layout} from "antd";
import {Copyright} from "ui";
import {login} from "service/session.js";
import {dispatch} from "module/store.js";
import {userLogin} from "module/actions.js";
import {push} from "util/router.js";

const {Content, Footer} = Layout;
const FormItem = Form.Item;

@Form.create({})
class LoginForm extends React.Component {

    handleSubmit = (e) => {
        e.preventDefault();
        this.props.form.validateFields((err, values) => {
            if (!err) {
                this.login(values);
            }
        });
    };
    login = async ({username, password}) => {
        const user = await login(username, password);
        dispatch(userLogin(user));
        push("/");
    };
    decorateField = (id, options, FieldComponent) => {
        const {getFieldDecorator} = this.props.form;
        return getFieldDecorator(id, options)(FieldComponent);
    };
    renderUsername = () => {
        return this.decorateField("username", {rules: [{required: true, message: "请输入用户名"}]}, (
            <Input size="large" placeholder="请输入用户名"
                   prefix={<Icon type="user" className={commonCss.darkGrey}/>}/>
        ));
    };
    renderPassword = () => {
        return this.decorateField("password", {rules: [{required: true, message: "请输入密码"}]}, (
            <Input size="large" type="password" placeholder="请输入密码"
                   prefix={<Icon type="lock" className={commonCss.darkGrey}/>}/>
        ))
    };

    render() {
        return (
            <Form className={commonCss.centerSelf}
                  style={{
                      width: 390,
                      padding: "80px 0"
                  }}
                  onSubmit={this.handleSubmit}>
                <FormItem>{this.renderUsername()}</FormItem>
                <FormItem>{this.renderPassword()}</FormItem>
                <FormItem>
                    <Button size="large" type="primary" htmlType="submit"
                            style={{width: "100%"}}>登录</Button>
                </FormItem>
            </Form>
        );
    }
}

const LoginPage = () => (
    <Layout className={commonCss.fullHeight}
            style={{
                background: "#f0f2f5 url(https://gw.alipayobjects.com/zos/rmsportal/TVYTbAXWheQpRcWDaDMu.svg) no-repeat center 110px",
                backgroundSize: "100%"
            }}>
        <Content>
            <h2 className={commonCss.centerContent} style={{marginTop: 140}}>
                Backend
            </h2>
            <LoginForm/>
        </Content>
        <Footer><Copyright/></Footer>
    </Layout>
);

export default LoginPage;