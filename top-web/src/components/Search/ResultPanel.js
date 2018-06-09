import React,{Component} from 'react';
import { mdReact } from 'markdown-react-js';
import { Card } from 'antd';
import { connect } from 'dva';
import $ from 'jquery'
import MarkDown from '../MarkDown'

function ResultPanel({blog}){

    let post = this.props.post
    let content = post.blogContent
    return (
        <div style={{height:"100%"}}>
                    <Card title={<p style={{fontSize:28}}>{post.blogTitle}</p>} bordered={true} extra={
                    <div style={{height:"100%"}}>
                        标签： 
                            <a href="" size="large">{blog.blogTag}</a>
                            
                    </div>
                }>
                    
                <MarkDown content={content} />
                    
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

export default connect(mapStateToProps)(ResultPanel);