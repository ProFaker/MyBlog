import MDReactComponent from 'markdown-react-js';
import React ,{Component} from 'react'
const ReactMarkdown = require('react-markdown')
import jQuery from 'jquery';
window.$ = jQuery;
const input = '# This is a header\n\nAnd this is a paragraph'
class MarkDown extends Component{
  
  componentDidMount(){
    let content = this.props.content;
    let index = this.props.index;
    // content = content.replace("&lt;","<")
    // content = content.replace("&gt;",">")
    let ct = document.getElementById(index)
    if(ct){
      ct.innerHTML = content;
    }
  }

    render() {
        let index = this.props.index;
        return (
          <div id={index} style={{padding:'10px 10px 80px 10px'}}/> 
        );
      }
}

export default MarkDown;

