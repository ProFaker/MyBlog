import React,{Component} from 'react';
import { mdReact } from 'markdown-react-js';
import { Card } from 'antd';
import { connect } from 'dva';
import * as usersService from '../services/blog';
import $ from 'jquery'
import MarkDown from '../components/MarkDown'
import uuid from 'uuid';

function PageContent({blog}){

    let post = blog.currentView.currentView
    let content = post.blogContent
    return (
        <div style={{height:"100%",paddingBottom:'80px'}}>
            <Card title={<p style={{fontSize:28}}>{post.blogTitle}</p>} bordered={true} extra={
                    <div style={{height:"95%"}}>
                        标签： 
                            <a href="" size="large">{post.blogTag}</a>
                    </div>
                }>
                <MarkDown index={uuid()} content={content} />
                    
            </Card>
        </div>
    );
    
}
function mapStateToProps(state) {
    let blog=state.blog
    return {
        blog
    };
}

export default connect(mapStateToProps)(PageContent);