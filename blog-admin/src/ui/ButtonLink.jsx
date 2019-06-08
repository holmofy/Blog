import React from "react";
import classNames from "classnames";
import commonCss from "common/common.less";
import {push} from "util/router.js";

export class ButtonLink extends React.Component {

    onClick = (e) => {
        let {url, disabled, query, onClick, history} = this.props;
        if (disabled) {
            return;
        }
        if (onClick) {
            onClick(e);
        } else if (url) {
            push(url, query);
        }
    };

    render() {
        let {value, className, disabled, children} = this.props;
        return (
            <a className={classNames(className, disabled ? commonCss.darkGrey : '')}
               href="javascript:void(0)"
               onClick={this.onClick}>{value || children}</a>
        );
    }

}