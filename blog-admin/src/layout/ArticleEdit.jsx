import React, {Component} from 'react';
import {findArticleById} from "service/article.js";
import 'codemirror/lib/codemirror.css';
import 'tui-editor/dist/tui-editor.min.css';
import 'tui-editor/dist/tui-editor-contents.min.css';
import {Editor} from '@toast-ui/react-editor'

export class ArticleEdit extends Component {

    state = {};

    componentDidMount() {
        const params = this.props.match.params;
        this.fetchData(params.id);
    }

    async fetchData(id) {
        const article = await findArticleById(id);
        this.setState({article});
    }

    render() {
        console.log(this.state);

        const input = '# This is a header\n\nAnd this is a paragraph'
        return (
            <Editor
                initialValue={input}
                previewStyle="vertical"
                height="600px"
                initialEditType="markdown"
                useCommandShortcut={true}
                exts={[
                    {
                        name: 'chart',
                        minWidth: 100,
                        maxWidth: 600,
                        minHeight: 100,
                        maxHeight: 300
                    },
                    'scrollSync',
                    'colorSyntax',
                    'uml',
                    'mark',
                    'table'
                ]}
            />
        );
    }
}