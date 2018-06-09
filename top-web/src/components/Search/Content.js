import React from 'react';

class Content extends React.Component{

    componentDidMount(){
        let {content} = this.props;
        let main = document.getElementById("main");
        if(main){
            main.innerHTML = content;
        }
    }
    render(){
       
        return(
            <div id="main" style={{width:'80%',height:'95%',marginLeft:'10%',marginRight:'10%'}}>
                
            </div>
        )
    }
}
export default Content;