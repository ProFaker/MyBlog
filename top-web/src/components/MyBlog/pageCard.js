import React,{Component} from 'react';
import RaisedButton from 'material-ui/RaisedButton';
import MarkDown from '../MarkDown'
import marked from 'react-marked'
import { Card, Row, Col, Icon, Button } from "antd"

const style= {
    button: {
        float: "right",
        marginRight: 200,
        marginTop: "2%"
    },
    title: { width: "450px" },
    text: { fontSize: "16px", padding: "24px" },
    link: { zIndex: 100 },
    header: { width: "50%" }
}
class   PageCard extends Component{

 

    render() {
        const { post,route,articleId ,getDetail} = this.props;
        const IconText = ({ type, text }) => (
            <span>
              <Icon type={type} size="large" />
              {text}
            </span>
          );
        return (
            <div>

                <Card title={post.blogTitle} bordered={true} extra={
                    <div >
                        标签： {post.blogTag}
                    </div>
                }>
                    <div style={{width:'100%'}} >
                        {
                              post.blogSummary
                        }
                    </div>
                        <br/><br/><br/>
                    <Button style={{ float: "right" }} type="primary" onClick={() => {
                            getDetail(articleId)
                            
						}}>READ</Button>
                    <div style={{ float: "left" }}> 
                    <IconText type="star-o"  text={post.star} />,
                    <IconText type="like-o"  text={post.like} />,
                    <IconText type="message"  text={post.message} />
                    </div>
                </Card>
            </div>);
    }
}
export default PageCard;
