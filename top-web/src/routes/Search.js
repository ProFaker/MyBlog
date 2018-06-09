import React from 'react';
import { connect } from 'dva';
import styles from './Search.css';
import MainLayout from '../components/MainLayout/MainLayout'
import Searcher from '../components/Search/SearchBlog'

function Search({blog,dispatch}) {

  function handleSearch(value){
      let data = {"content":value}
      dispatch({
         type:'blog/handleSearch',
         payload:data
      })
  }
  
  let searchResult = blog.searchResult
  return (
      <div className={styles.normal}>
        <Searcher
           handleSearch={(value)=>handleSearch(value)}
           searchResult = {searchResult}
        >
        </Searcher>
      </div>
  );
}

function mapStateToProps(state) {
  let blog = state.blog
  return {
    blog
  };
}

export default connect(mapStateToProps)(Search);
