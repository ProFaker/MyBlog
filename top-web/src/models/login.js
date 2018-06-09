import * as usersService from '../services/users';
import loginModal from './login'
import { routerRedux } from 'dva/router'

export default {
  namespace: 'login',
  state: {
    isLogin: false
  },
  reducers: {
    logins(state,{payload:isLogin}){
        console.log(isLogin)
        state.isLogin=isLogin
        return {...state}
    }
  },
  effects: {
    //用户登录
    *login({payload:data}, { put, call }){
      const datas = yield call(usersService.login, data)
      for(let key in datas){
         if(key === 'code')
            loginModal.state.isLogin = datas[key] === 0 ? true : false
      } 

      let isLogin = loginModal.state.isLogin

      //登录成功，跳转
      if(isLogin)
        yield put(routerRedux.push('/blog'))
      
      yield put({
        type: 'logins',
        payload:  isLogin 
    }); 
    },
  },
  subscriptions: {},
};
