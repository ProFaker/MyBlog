import React,{Component} from 'react';
import styles from './TopModal.css';
import { Button ,Upload ,Icon} from 'antd';

class TopModal extends Component {

  constructor(pops){
    super(pops);
    this.state = {
      fileList: [{
        uid: -1,
        name: 'xxx.png',
        status: 'done',
        url: 'https://zos.alipayobjects.com/rmsportal/jkjgkEfvpUPVyRjUImniVslZfWPnJuuZ.png',
      }],
    };
  }

  handleUpload(e){
      console.log('=====',e.fileList[0])
      let uploadFileClick = this.props.uploadFileClick
      if(uploadFileClick)
        uploadFileClick(e.file)
  }

  render(){

    return (  
      <div className={styles.normal}>
          <Upload name="file" id='file' action="http://localhost:8080/uploadImage" 
            listType="picture"
            fileList={this.state.fileList}>
            <Button type="ghost">
                <Icon type="upload" /> 点击上传
            </Button>
          </Upload>
      </div>
    );
  }

}

export default TopModal;
