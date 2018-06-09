import request from '../utils/request';
import { PAGE_SIZE ,Host } from '../constants';

const host = Host


export function addArticle(data){
    // return request(`/api/login`);
    return request({
      
      url: host+'addBlog',
      method: 'post',
      data
    })
  }

  
//获取分类
export function getClassify(){
  // return request(`/api/login`);
  let data = ""
  return request({
    
    url: host+'getClassifyByUserId',
    method: 'post',
    data
  })
}


export function getBlogById(data){
  return request({
    
    url: host+'getBlogById',
    method: 'post',
    data
  })
}



//获取分类
export function selectByGroupIdAndOffset(data){
  return request({
    url: host+'getArticleByGroupId',
    method: 'POST',
    data
  })
}

//获取文章全部内容
export function readFile(data){
  return request({
    url: host+'readFileByPath',
    method: 'POST',
    data
  })
}

//获取分类中文章总数
export function getClassifyCount(data){
  return request({
    url : host + 'getClassifyCount',
    method : 'POST',
    data
  })
}

//获取分类
export function search(data){
  return request({
    url: host+'query/blog/article',
    method: 'post',
    data
  })
}