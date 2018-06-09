import React, { Component } from 'react';
import { routerRedux } from 'dva/router';
import { connect } from 'dva';
import { Layout, Menu, Breadcrumb, Icon } from 'antd';

const { SubMenu } = Menu;
const { Sider } = Layout;

class SiderComponent extends Component {

  changeItem(key) {


  }

  findParentNode(classify) {
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
                <Menu.Item key={item.id} >{item.displayName}</Menu.Item>,
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
            <SubMenu key={node.id} title={<span><Icon type="user" />{node.displayName}</span>}>
                {
                        this.findSonNode(node.id, classify)
                    }
              </SubMenu>,
            );
      });
    return nodes;
  }

  handlerOnclick(e) {
    const key = e.key;
    const onMenuClick = this.props.onMenuClick;
    if (onMenuClick) { onMenuClick(key); }
  }

  render() {
    const classify = this.props.classify;
    console.log('-------', classify);

    return (
        <Sider width={200} style={{ height: '100%' }}>
            <Menu
                theme="dark"
                mode="inline"
                onClick={e => this.handlerOnclick(e)}
                defaultSelectedKeys={['1']}
                defaultOpenKeys={['sub1']}
                style={{ height: '100%', borderRight: 0 }}
                >
                {
                        this.createNodes(classify)
                    }
              </Menu>
          </Sider>
      );
  }
}

export default SiderComponent;
