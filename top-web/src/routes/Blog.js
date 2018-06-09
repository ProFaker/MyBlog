
import React from 'react';
import { connect } from 'dva';
import styles from './Blog.css';
import { Form, Card, Select, List, Tag, Icon, Pagination, Row, Col, Button, Input } from 'antd';
import SiderComponent from '../components/MyBlog/SiderComponent'

function Blog({dispatch, children,location,blog }) {



  let classify = blog.classify



  function onMenuClick(key){
      dispatch({
          type: "blog/changeClassify",
          payload : key
      })
  }
  


  return (
      <div id='container' style={{width:'100%'}}>
        <div style={{width:'200px',height:'100%',float:'left'}}>
          <SiderComponent 
            classify = {classify}
            onMenuClick={onMenuClick}/>
        </div>
        <div id='article' style={{position:'absolute',float:'right',marginTop:'10px',height:'100%',
                    width:' -webkit-fill-available',marginRight:'10px',marginLeft:'210px',overflowY:'auto'}}>
          {
            children
          }
        </div>
      </div>
  );
}

function mapStateToProps(state) {
  var  blog = state.blog
  return {
    blog
  };
}

export default connect(mapStateToProps)(Blog);