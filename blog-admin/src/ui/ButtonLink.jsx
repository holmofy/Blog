import React from "react";
import _ from "lodash";
import classNames from "classnames";
import css from "./ButtonLink.css";

export class ButtonLink extends React.Component {

    onClick(e) {
        let {url, disabled, query, onClick} = this.props;
        if (disabled) {
            return;
        }
        if (onClick) {
            onClick(e);
        } else if (url) {
        }
    }

    render() {
        let {value, className, disabled, children} = this.props;
        let props = _.omit(this.props, ['value', 'className', 'disabled', 'children', 'onClick']);
        return (
            <a className={classNames(className, disabled ? css.disabled : '')} href="javascript:void(0)"
               onClick={(e) => this.onClick(e)} {...props}>{value || children}</a>
        );
    }

}