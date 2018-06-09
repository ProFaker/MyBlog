import * as usersService from '../services/users';
import * as blogService from '../services/blog';
import * as pageInfos from '../constant/pageInfo';
import * as classifys from '../constant/classify';
import { routerRedux} from 'dva/router';
import blogModel from './blog'


export default {
  namespace: 'blog',
  state: {
    //当前分类文章总数
    total:'',
    //分类
    classify:[],
    //分类下显示的文章简介
    article: [],
    //选中标题
    selectedNav:"",
    //查看文章
    currentView:  "",
    //搜索结果
    searchResult :[]
  },
  reducers: {
    save(state, { payload: { article , classify } }) {
      state.article = article
      state.classify = classify
      return { ...state,article,classify};
    },
    navchanged(state, { payload:  selectedNav }){
      state.selectedNav  = selectedNav
      return {...state ,selectedNav}
    },
    loadCurrentView(state, { payload:  currentView }){
      state.currentView = currentView
      return {...state ,currentView}
    },
    changeShowArticle(state, { payload: { article ,total } }){
      state.article = article;
      state.total = total
      return {...state ,article,total}
    },
    search(state, { payload: searchResult }){
      state.searchResult = searchResult
      return {...state}
    }
  },
  effects: {
    //初始化数据
    *query({ payload: { page = 1 } }, { call, put }) {
      const classify = yield call(blogService.getClassify)
      let data=pageInfos['pageInfos']
      blogModel.state.article = data;
      let article = blogModel.state.article
      yield put({
        type: 'save',
        payload: {
          article,
          classify
        },
      });
    },
    
    *changeNav({ payload:  path }, { call, put }){
      blogModel.state.selectedNav = path;
      let selectedNav = blogModel.state.selectedNav
      yield put(routerRedux.push(path))
      yield put({
         type : "navchanged",
         payload: {
            selectedNav
         }
      })
    },
    //显示分类下文章
    *changeClassify({ payload:  key }, { call, put }){
      let params = {"groupId":key ,"offset":"0","limit":"5"}
      let data = yield call(blogService.selectByGroupIdAndOffset,params)
      blogModel.state.article = data;
      let article = blogModel.state.article
      params = {"groupId": key}
      data = yield call(blogService.getClassifyCount ,params)
      if(data['code'] === 0){
        blogModel.state.total = data['msg']
      }
      let total = blogModel.state.total
      yield put({
          type: 'changeShowArticle',
          payload:{
            article,
            total 
          }
      })
      yield put(routerRedux.push("/blog/loadArticle"))
    },

    //显示文章详细
    *changeCurrentView({ payload:  id }, { call, put }){
      let data = {"id": id}
      const blog = yield call(blogService.getBlogById,data); 

      if(blog !== null){
         let params = {path : blog.blogContent}
         let content = yield call(blogService.readFile,params)
         blog.blogContent = content['msg']
      }
     
     
      blogModel.currentView = blog;
      let currentView = blogModel.currentView;
      yield put({
        type : 'loadCurrentView',
        payload :{
          currentView
        }
      })
      yield put(routerRedux.push("blog/pageContent/"+id))
    },
    //改变页码
    *changePagination({ payload: { page ,pageSize}}, { call, put }){

      if(blogModel.state.article != null){
          let groupId = blogModel.state.article[0]['groupId']
          let params = {"groupId":groupId,"offset": (page-1)*pageSize,"limit":pageSize}
          let data = yield call(blogService.selectByGroupIdAndOffset,params)
          blogModel.state.article = data;
          let article = blogModel.state.article
          let total = blogModel.state.total
          yield put({
              type: 'changeShowArticle',
              payload:{
                article,
                total
              }
          })
      }
    },
    *handleSearch({ payload:data }, { call, put }){
         let result = yield call(blogService.search,data)
         blogModel.searchResult = result
         yield put({
           type:'search',
           payload:{
             result
           }
         })
    }
  },
  subscriptions: {
    setup({ dispatch, history }) {
      return history.listen(({ pathname }) => {
        if (pathname === '/blog') {
          dispatch({ type: 'query', payload: {} });
        }
      });
    },
  },
};