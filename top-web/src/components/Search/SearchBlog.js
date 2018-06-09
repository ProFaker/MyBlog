import React,{Component} from 'react'
import { Input ,Button,Card} from 'antd';
import styles from './SearchBlog.css'
const Search = Input.Search;
import ResultPanel from './ResultPanel'
import MarkDown from '../MarkDown'
import Content from './Content';
import uuid from 'uuid';

class SearchBlog extends Component{

    constructor(prop){
        super(prop);
        this.state={
            search : true,
            content : ""
        }
    }   
    
    handlerSearch=(value)=>{
        let handleSearch = this.props.handleSearch
        this.setState({
            search : true
        })
        if(handleSearch){
            handleSearch(value)
        }
    }

    handleClick=(item)=>{

        this.setState({
            search : false,
            content : item,
        })
    }

     loadArtcle(article){
        let arr = []
        for(let i=0;article.result !== undefined && i<article.result.length;i++){
            let item = article.result[i];
            let id = uuid();
            arr.push(
                <Card key={id} title={item.title} style={{width:'100%',height:'150px',cursor:'pointer'}} onClick={()=>this.handleClick(item.completeContent)}>
                    <div className={styles.content} >
                           <MarkDown index={id} content={item.content} /> 
                    </div>
                </Card>
            )
        }
        return arr;
      }


    render(){

        let searchResult = this.props.searchResult
        let content = this.state.content
        return(
            <div style={{width:'100%',height:'95%',overflowY:'auto',bottom:'50px'}}>
                <Search placeholder='搜索' style={{width:'80%',height:'100%',marginLeft:'10%',marginRight:'10%',marginTop:'10px', borderRadius:'0px', height:'32px'}} onSearch={(value)=>this.handlerSearch(value)} />
                {
                    this.state.search ? 
                    <div style={{marginBottom:'30px',width:'80%',height:'88%',overflowY:'auto' ,position:'absolute',marginLeft:'10%',marginRight:'10%' ,marginTop:'30px'}}>

                        {
                            this.loadArtcle(searchResult)
                        }
                    </div>:
                    <Content content={this.state.content}/>
                }
              

            </div>
        )
    }
}

export default SearchBlog;
