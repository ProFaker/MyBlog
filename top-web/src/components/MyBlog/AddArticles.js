import React, { Component } from 'react';
import { Form, Button, Col, Row, Input, TreeSelect, Upload, Icon,message } from 'antd';
import { connect } from 'dva';
import { Host } from '../../constants';
import RichEdit from '../RichEdit/RichEdit';

const TreeNode = TreeSelect.TreeNode;
const FormItem = Form.Item;

const { TextArea } = Input;

let classify = null;
const uploadPath = `${Host}uploadImage`;
class AddArticle extends Component {

  constructor(pops) {
    super(pops);
    this.state = {
      blogTypeId: '',
      blogTitle: '',
      blogTag: '',
      blogSummary: '',
      blogType: '',
      groupId: '',
      imgSrc: '',
    };
  }

  handleSumbit = (e) => {
    const data = {
      blogTitle: this.state.blogTitle,
      blogTag: this.state.blogTag,
      blogSummary: this.state.blogSummary,
      blogType: this.state.blogTypeId,
      blogContent: this.state.blogContent,
      groupId: this.state.blogTypeId,
      userId: 1,
    };
    const onSubmit = this.props.onSubmit;
    if (onSubmit) { onSubmit(data); }
    message.success("添加成功");
  }

  onChange(){
    const data = {
      blogTitle: this.state.blogTitle,
      blogTag: this.state.blogTag,
      blogSummary: this.state.blogSummary,
      blogType: this.state.blogTypeId,
      blogContent: this.state.blogContent,
      groupId: this.state.blogTypeId,
      userId: 1,
    };
    const onChange = this.props.onChange;
    if (onChange) { onChange(data); }
  }

  getContent = (content) => {
    this.setState({
      blogContent: content,
    });
    this.onChange();
  }

  onChangeblogTitle = (e) => {
    this.setState({
      blogTitle: e.target.value,
    });
    this.onChange();
  }
  onChangeblogSummary = (e) => {
    this.setState({
      blogSummary: e.target.value,
    });
    this.onChange();
  }
  onChangeblogType = (value, label) => {
    this.setState({
      blogTypeId: value,
      blogType: label[0],
    });
    this.onChange();
  }
  onChangeblogTag = (e) => {
    this.setState({
      blogTag: e.target.value,
    });
    this.onChange();
  }

  findParentNode(classify, key) {
    const arr = [];
    classify.map((item, key) => {
      if (item.parentId === '' || item.parentId === 0) { arr.push(item); }
    });
    return arr;
  }

  findSonNode(nodesId, classify) {
    const arr = [];
    classify.map((item, key) => {
      if (item.parentId === nodesId) {
        arr.push(
          <TreeNode key={`${item.id}_`} title={item.displayName} value={item.id} />,
                );
      }
    });
    return arr;
  }
  createNodes(classify) {
    const arr = this.findParentNode(classify);
    const nodes = [];
    arr.map((node, key) => {
      nodes.push(
        <TreeNode key={`${node.id}_`} title={node.displayName} value={node.id}>
          {
              this.findSonNode(node.id, classify)
          }
        </TreeNode>,
            );
    });
    return nodes;
  }

  getReturn = (e) => {
    console.log(e);
    if (e.file.response !== null && e.file.response !== undefined) {
      this.setState({
        imgSrc: `<img src='${e.file.response.msg}'/>`,
      });
    }
  }

  render() {
    classify = this.props.classify;
    let { createArticle } = this.props;
    let data = createArticle.data;
    return (
      <div style={{ lineHeight: '40px', width: '100%', paddingLeft: '10%', paddingRight: '10%', marginBottom: '20px', overflowY: 'auto' }}>

        <div >
          <p style={{ fontSize: '20px', textAlign: 'center' }}>创建文章</p>
          <br />
        </div>
        <Row>
          {<Input value={this.state.imgSrc} />}
          <Col span={24} style={{ position: 'absolute', textAlign: 'right', marginRight: '120px', marginBottom: '20px' }}>
            <Button type="primary" onClick={this.handleSumbit}>提交</Button>
          </Col>
        </Row>
        <div>
          <Upload
            name="file" id="file" action={uploadPath}
            listType="picture"
            onChange={this.getReturn}
                    >

            <Button type="ghost">
              <Icon type="upload" /> 点击上传
                        </Button>

          </Upload>
        </div>
                文章标题 ： <Input placeholder="文章标题" defaultValue={data.blogTitle} size="large" onChange={this.onChangeblogTitle} />
                文章分类 ：
                <TreeSelect
                  style={{ width: '100%' }}
                  value={this.state.blogType}
                  dropdownStyle={{ maxHeight: 400, overflow: 'auto' }}
                  placeholder="Please select"
                  size="large"
                  defaultValue={data.blogType}
                  allowClear
                  treeDefaultExpandAll
                  onChange={this.onChangeblogType}
                >
                  {
                        this.createNodes(classify)
                    }
                </TreeSelect>
        <br />
                文章标签 ： <Input placeholder="文章标签" defaultValue={data.blogTag} size="large" onChange={this.onChangeblogTag} />
                文章摘要 ： <Input placeholder="文章摘要" size="large" onChange={this.onChangeblogSummary} defaultValue={data.blogSummary} style={{marginBottom:'75px'}} />
            <div style={{ overflowY: 'auto',height:'50vh' ,borderBottom: '1px solid rgb(247, 242, 242)'}}>
                <RichEdit EditRich={(content) => this.getContent(content)} defaultValue={data.blogContent === null && data.blogContent === undefined ? '' : data.blogContent} />
            </div>
      </div>
    );
  }
}
const mapStateToProps = ({ createArticle }) => {
  return { createArticle };
};
export default connect(mapStateToProps)(AddArticle);

