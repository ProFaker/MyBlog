import React from 'react';
import styles from './MainLayout.less';
import { routerRedux } from 'dva/router'
import { connect } from 'dva';
import { Layout, Menu, Breadcrumb, Icon} from 'antd';
import { Link, Route, Redirect } from 'dva/router';
import SiderComponent from '../MyBlog/SiderComponent'
const { SubMenu } = Menu;
const { Header ,Sider ,Content } = Layout;

function MainLayout({ dispatch,children, location }) {


  const route = ['/home','/blog','/Search','/createArticle']

  function changeNav(e){
    let path = route[e.key-1]
    dispatch({
      type: "blog/changeNav",
      payload: path
    })
  }

  let defaultVal = [];
  for(let i=0;i<route.length;i++){
      if(location.hash.toString().indexOf(route[i])!=-1){
          defaultVal.push(i+1);
      }
  }
  
  return (
    <Layout style={{overflowX:'hidden',overflowY:'hidden'}}>
        <Header className="header" style={{padding: 0}}>
            <div className={styles.logo}>
                <Link to="/">
                    <img src="https://gw.alipayobjects.com/zos/rmsportal/iwWyPinUoseUxIAeElSx.svg" alt="logo" />
                    <h1>汪建军</h1>
                </Link>
            </div>
            <Menu
            theme="dark"
              mode="horizontal"
              onClick={(e)=>changeNav(e)}
              defaultSelectedKeys={[{defaultVal}]}
              style={{ lineHeight: '64px',marginLeft:'200px' }}
              >
              <Menu.Item key="1" className={styles.nav}>首页</Menu.Item>
              <Menu.Item key="2" className={styles.nav}>我的博客</Menu.Item>
              <Menu.Item key="3" className={styles.nav}>搜索</Menu.Item>
              <Menu.Item key="4" className={styles.nav}>添加文章</Menu.Item>
            </Menu>
        </Header>
        <Content className={styles.content}>
          {
            children
          }
        </Content>
    </Layout>
  );
}

export default connect()(MainLayout);