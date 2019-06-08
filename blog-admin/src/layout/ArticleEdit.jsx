import React, {Component} from 'react';

export class ArticleEdit extends Component {
    render() {
        let match = this.props.match.params;
        return (
            <div>编辑也面:{match.id}</div>
        );
    }
}