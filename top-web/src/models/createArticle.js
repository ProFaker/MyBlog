import * as usersService from '../services/blog';

export default {
  namespace: 'createArticle',
  state: {
    data: {
      blogTitle: "",
      blogTag: "",
      blogSummary: "",
      blogType: "",
      blogContent:"",
    },
  },
  reducers: {
    save(state,{payload:data}){
      console.log("save"+data)
      state.data=data
      return {...state}
  }
  },
  effects: {
    *create({payload : data} ,{put ,call}){  //生成文章
        let result = yield call(usersService.addArticle , data)
    },
    *saveArticle({payload:data}, { put, call }){  // 保存文章到本地
      yield put({
        type: 'save',
        payload:  data 
      }); 
    }
  },
  subscriptions: {},
};
