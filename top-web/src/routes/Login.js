import React from 'react';
import { connect } from 'dva';
import styles from './Login.css';
import LoginModal from '../components/Login/LoginModal'
import MainLayout from '../components/MainLayout/MainLayout'

function Login({dispatch,login}) {

  function submit(data){
    dispatch({
      type:'login/login',
      payload: data
    })
  }

  let ret=login.ret
  return (
    <div className={styles.normal}>
      <LoginModal
        onclickHandler={submit}
        ret={login.ret}
      >
      </LoginModal>
    </div>
  );
}

function mapStateToProps(state) {
  let login=state.login
  return {
    login
  };
}

export default connect(mapStateToProps)(Login);
