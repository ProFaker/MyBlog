import React from 'react';
import { Editor } from 'react-draft-wysiwyg';
import { Row, Col, Card } from 'antd';
// import * as Icons from 'images/icons';
import styles from './RichEditor.less';
import 'react-draft-wysiwyg/dist/react-draft-wysiwyg.css';
import draftToMarkdown from 'draftjs-to-markdown';
import draftToHtml from 'draftjs-to-html';
import htmlToDraft from 'html-to-draftjs';

const rawContentState = { entityMap: { 0: { type: 'IMAGE', mutability: 'MUTABLE', data: { src: 'http://i.imgur.com/aMtBIep.png', height: 'auto', width: '100%' } } }, blocks: [{ key: '9unl6', text: '', type: 'unstyled', depth: 0, inlineStyleRanges: [], entityRanges: [], data: {} }, { key: '95kn', text: ' ', type: 'atomic', depth: 0, inlineStyleRanges: [], entityRanges: [{ offset: 0, length: 1, key: 0 }], data: {} }, { key: '7rjes', text: '', type: 'unstyled', depth: 0, inlineStyleRanges: [], entityRanges: [], data: {} }] };
class RichEditor extends React.Component {

  state = {
    content: '',
    contentState: rawContentState,
    editorState: '',
  }

  handleChange=(content) => {
    this.setState({
      content: draftToHtml(content),
    });
    console.log(this.state.content);
    const onChange = this.props.onChange;
    if (onChange) { onChange(draftToHtml(content)); }
  }
  getIcons() {
    const icons = [
      'source | undo redo | bold italic underline strikethrough fontborder emphasis | ',
      'paragraph fontfamily fontsize | superscript subscript | ',
      'forecolor backcolor | removeformat | insertorderedlist insertunorderedlist | selectall | ',
      'cleardoc  | indent outdent | justifyleft justifycenter justifyright | touppercase tolowercase | ',
      'horizontal date time  | image emotion spechars | inserttable',
    ];
    return icons;
  }
  getPlugins() {
    return {
      image: {
        uploader: {
          name: 'file',
          url: '/api/upload',
        },
      },
    };
  }
  render() {
    const { content, editorState } = this.state;
    const Icons = [
      'source | undo redo | bold italic underline strikethrough fontborder emphasis | ',
      'paragraph fontfamily fontsize | superscript subscript | ',
      'forecolor backcolor | removeformat | insertorderedlist insertunorderedlist | selectall | ',
      'cleardoc  | indent outdent | justifyleft justifycenter justifyright | touppercase tolowercase | ',
      'horizontal date time  | image emotion spechars | inserttable',
    ];
    return (
      <div>
        <Editor
          toolbarClassName={styles.toolbar}
          wrapperClassName={styles.wrapper}
          editorClassName={styles.editor}
          onContentStateChange={this.handleChange}
          toolbar={{
            inline: {
              bold: { icon: Icons.bold, className: 'demo-option-custom' },
              italic: { icon: Icons.italic, className: 'demo-option-custom' },
              underline: { icon: Icons.underline, className: 'demo-option-custom' },
              strikethrough: { icon: Icons.strikethrough, className: 'demo-option-custom' },
              monospace: { className: 'demo-option-custom' },
              superscript: { icon: Icons.superscript, className: 'demo-option-custom' },
              subscript: { icon: Icons.subscript, className: 'demo-option-custom' },
            },
            blockType: { className: 'demo-option-custom-wide', dropdownClassName: 'demo-dropdown-custom' },
            fontSize: { className: 'demo-option-custom-medium' },
            list: {
              unordered: { icon: Icons.unordered, className: 'demo-option-custom' },
              ordered: { icon: Icons.ordered, className: 'demo-option-custom' },
              indent: { icon: Icons.indent, className: 'demo-option-custom' },
              outdent: { icon: Icons.outdent, className: 'demo-option-custom' },
            },
            textAlign: {
              left: { icon: Icons.left, className: 'demo-option-custom' },
              center: { icon: Icons.center, className: 'demo-option-custom' },
              right: { icon: Icons.right, className: 'demo-option-custom' },
              justify: { icon: Icons.justify, className: 'demo-option-custom' },
            },
            fontFamily: { className: 'demo-option-custom-wide', dropdownClassName: 'demo-dropdown-custom' },
            colorPicker: { className: 'demo-option-custom', popupClassName: 'demo-popup-custom' },
            link: {
              popupClassName: 'demo-popup-custom',
              link: { icon: Icons.link, className: 'demo-option-custom' },
              unlink: { icon: Icons.unlink, className: 'demo-option-custom' },
            },
            emoji: { className: 'demo-option-custom', popupClassName: 'demo-popup-custom' },
            embedded: { className: 'demo-option-custom', popupClassName: 'demo-popup-custom' },
            image: { uploadCallback: this.uploadImageCallBack, alt: { present: true, mandatory: true } },
            remove: { icon: Icons.eraser, className: 'demo-option-custom' },
            history: {
              undo: { icon: Icons.undo, className: 'demo-option-custom' },
              redo: { icon: Icons.redo, className: 'demo-option-custom' },
            },
		  }}
        />
        {/* <Card title="同步转换MarkDown" bordered={false}>
				<pre style={{whiteSpace: 'pre-wrap'}}>{draftToMarkdown(content)}</pre>
				</Card> */}
      </div>);
  }
}
export default RichEditor;
