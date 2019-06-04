import React from "react";
import {Pagination} from "antd";
import _ from "lodash";

export class Pager extends React.Component {

    render() {
        let {align, visible} = this.props;
        let props = _.omit(this.props, ['align']);
        return visible ? (
            <div style={{textAlign: align, margin: "10px 0"}}>
                <Pagination {...props}/>
            </div>
        ) : null;
    }
}