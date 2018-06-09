import React from 'react';
import { connect } from 'dva';
import styles from './LoadArticle.css';
import PageCard from '../components/MyBlog/pageCard'
import {  Pagination } from 'antd';

function LoadArticle({location,blog,dispatch }){  

 

  function getDetail(id){
      dispatch({
        type:"blog/changeCurrentView",
        payload: id
      })
  } 

  function loadArtcle(article){
    let arr = []
    article.map((item,key)=>{
        arr.push(<PageCard key={item.id} articleId={item.id} post={item}   getDetail={getDetail}
                          route={{ route: location.pathname }} />)
    })
    return arr;
  }

  function changePage(page,pageSize){
      dispatch({
          type :'blog/changePagination',
          payload :{page,pageSize}
      })
  }
  let article = blog.article
  let total = blog['total']
  return (
    
    <div className={styles.normal}>
        {
          loadArtcle(article)
        }
        <div style={{position:'fixed', bottom: "30px", textAlign: "center",left:'45%'}} >
          <Pagination  pageSize={5}  defaultCurrent={1} total={total} onChange={changePage}/>
        </div>
    </div>
  );
}

function mapStateToProps(state) {
  let blog = state.blog
  return {
    blog
  };
}

export default connect(mapStateToProps)(LoadArticle);
