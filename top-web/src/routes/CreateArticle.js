import React from 'react';
import { connect } from 'dva';
import styles from './CreateArticle.css';
import MainLayout from '../components/MainLayout/MainLayout';
import AddArticle from '../components/MyBlog/AddArticles'


function CreateArticle({dispatch,blog}) {

  function addArticle(data){
      console.log("--------",data)
      dispatch({
          type : 'createArticle/create',
          payload :data
      })
  }


  function saveArticle(data){
      dispatch({
          type : 'createArticle/save',
          payload :data
      })
  }

  let classify = blog.classify
  return (
    <div className={styles.normal}>
    <AddArticle 
      onSubmit = {addArticle}
      onChange = {saveArticle}
      classify = {classify}
    ></AddArticle>
    </div>
  );
}

function mapStateToProps(states) {
    let blog = states.blog
  return {
    blog
  };
}

export default connect(mapStateToProps)(CreateArticle);
