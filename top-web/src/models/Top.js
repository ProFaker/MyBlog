import * as usersService from '../services/users';

export default {
  namespace: 'Top',
  state: {
    result:''
  },
  reducers: {
    save(state, { payload: { data: result } }) {
      return { ...state, result };
    },
  },
  effects: {
    *fetch( {payload: {} }, { call, put }) {
      console.log('start')
      const  result  = yield call(usersService.login);
      console.log("返回值：",JSON.stringify( result))
      yield put({
        type: 'save',
        payload: {
          result
        },
      });
    },
     //文件上传
    *uploadFile({payload : {file}},{ call , put }){
      const ret = yield call(usersService.upload,file)
    }
  },
  subscriptions: {
    setup({ dispatch, history }) {
      return history.listen(({ pathname, query }) => {
        // if (pathname === '/ ') {
         // dispatch({ type: 'fetch', payload: query });
        // }
      });
    },
  },
};
