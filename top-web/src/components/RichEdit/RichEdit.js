import { Editor } from 'react-draft-wysiwyg';
import { Button } from 'antd';
import 'react-draft-wysiwyg/dist/react-draft-wysiwyg.css';
import React from 'react';
import { EditorState, convertToRaw, ContentState } from 'draft-js';
import draftToHtml from 'draftjs-to-html';
import htmlToDraft from 'html-to-draftjs';
import style from './RichEdit.css';

class RichEdit extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            editorState: EditorState.createEmpty(),
        };
    }

    componentDidMount() {
        // 回显
        let { defaultValue } = this.props;
        const blocksFromHtml = htmlToDraft(defaultValue);
        const { contentBlocks, entityMap } = blocksFromHtml;
        const contentState = ContentState.createFromBlockArray(contentBlocks, entityMap);
        const editorStates = EditorState.createWithContent(contentState);

        this.setState({
            editorState: editorStates
        });
    }

    onEditorStateChange = (editorState) => {
        let htmlText = draftToHtml(convertToRaw(editorState.getCurrentContent()));
        this.setState({
            editorState
        });
        let EditRich = this.props.EditRich;
        if (EditRich) {
            EditRich(htmlText);
        }
    }

    render() {
        const { onChange, editorClassName } = this.props;
        return (
            <div id="subArea" style={{ overflowY: 'auto', height: '100%' }}>
                <Editor id="edit"
                    editorState={this.state.editorState}
                    localization={{ locale: 'zh' }}  // 中文
                    toolbarClassName={style.toolbarClassName}
                    wrapperClassName={style.wrapperClassName}
                    editorClassName={style.editorClassName}
                    placeholder="开始写点什么吧..."
                    onEditorStateChange={this.onEditorStateChange}
                    toolbar={{
                        options: ['inline', 'blockType', 'fontSize', 'fontFamily', 'list', 'textAlign', 'colorPicker', 'link', 'embedded', 'emoji', 'remove', 'history'], // image
                    }}
                />
            </div>
        );
    }
}
export default RichEdit;