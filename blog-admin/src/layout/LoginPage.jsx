import React from "react";
import commonCss from "common/common.css";
import classNames from "classnames";
import css from "./LoginPage.css";
import {Button, Form, Icon, Input} from "antd";

export class LoginPage extends React.Component {

    handleSubmit = (e) => {
        console.log(this.props.form);
    };

    render() {
        return (
            <Form className={classNames(commonCss.centerSelf, css.main)}
                  onSubmit={this.handleSubmit}>
                <Form.Item>
                    <Input size="large" placeholder="请输入用户名"
                           prefix={<Icon type="user" className={css.icon}/>}/>
                </Form.Item>
                <Form.Item>
                    <Input size="large" type="password" placeholder="请输入密码"
                           prefix={<Icon type="lock" className={css.icon}/>}/>
                </Form.Item>
                <Form.Item>
                    <Button size="large" type="primary" htmlType="submit"
                            className={css.btn}>登录</Button>
                </Form.Item>
            </Form>
        );
    }
}