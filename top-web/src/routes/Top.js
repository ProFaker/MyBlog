import React from 'react';
import { connect } from 'dva';
import styles from './Top.css';
import TopModal from '../components/TopModal'
import MainLayout from '../components/MainLayout/MainLayout'

function Top({dispatch}) {

  function uploadFileClick(file){
    console.log("file",file)
      dispatch({
          type: 'Top/uploadFile',
          payload: {file}
      });
  }

  return (
    <MainLayout location={location}>
      <div className={styles.normal}>
        <TopModal 
        uploadFileClick={uploadFileClick}/>
      </div>
    </MainLayout>
  );
}

function mapStateToProps() {
  return {};
}

export default connect(mapStateToProps)(Top);
