import React, {Component} from "react";
import {Modal} from "antd";
import _ from "lodash";

export class ModalWrapper extends Component {

    bindOnOk(onOkMethod) {
        this.onOkMethod = onOkMethod;
    }

    handleOk = () => {
        if (this.onOkMethod) {
            this.onOkMethod();
        }
        const {onOk = _.noop} = this.props;
        onOk();
    };

    wrap = () => {
        const {children} = this.props;
        return React.Children.map(children, child => {
            const {props: source} = child;
            const props = _.assign({}, source, {modalInterface: this});
            return _.assign({}, child, {props});
        });
    };

    render() {
        return (
            <Modal {..._.omit(this.props, ["onOk", "children"])} onOk={this.handleOk}>
                {this.wrap()}
            </Modal>
        );
    }
}