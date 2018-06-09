import React from 'react';
import styles from './LoginModal.css';
import { Input, Icon ,Button } from 'antd';


class LoginModal extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      userName: '',
      password:''
    };
  }
  emitEmpty = () => {
    this.userNameInput.focus();
    this.setState({ userName: '' });
  }
  onChangeUserName = (e) => {
    this.setState({ userName: e.target.value });
  }

  onChangePssword = (e) => {
    this.setState({ password: e.target.value });
  }

  submit(userName,password){
      let data={'userName':userName,'password':password,'remember':1}
      let onClickHandler=this.props.onclickHandler
      if(onClickHandler)
         onClickHandler(data)
  }

  render() {
    const { userName,password } = this.state;
    let ret=this.props.ret
    //const suffix = userName ? <Icon type="close-circle" onClick={this.emitEmpty} /> : null;
    return (
      <div className={styles.login}>
        <h1>{JSON.stringify(ret)}</h1>
        <Input
          placeholder="用户名"
          prefix={<Icon type="user" />}
          onChange={this.onChangeUserName}
          ref={node => this.userNameInput = node}
        />
        <br/><br/><br/>
        <Input
          placeholder="密 码"
          prefix={<Icon type="lock" />}
          onChange={this.onChangePssword}
        />
        <Button type='primary' className={styles.canel}>取消</Button><Button type='primary' className={styles.submit} onClick={()=>this.submit(userName,password)}>确定</Button>
      </div>
    );
  }
}

export default LoginModal;
