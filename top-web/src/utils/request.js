import fetch from 'dva/fetch';
import jQuery from 'jquery';

const $= jQuery

function checkStatus(response) {
  if (response.status >= 200 && response.status < 300) {
    return response;
  }

  const error = new Error(response.statusText);
  error.response = response;
  throw error;
}

/**
 * Requests a URL, returning a promise.
 *
 * @param  {string} url       The URL we want to request
 * @param  {object} [options] The options we want to pass to "fetch"
 * @return {object}           An object containing either "data" or "err"
 */
export default async function request(options) {
  let ret=null;
  let {
    method,
    data,
    url,
  } = options
  
  let formData=new FormData();
  for(let key in data){
     formData.append(key,data[key])
  }
  

  const response = await fetch(url,
  {
    method :method,
    mode: "cors",
    xhrFields: {withCredentials: true},
    credentials: "include",
    crossDomain: true,
    headers: {
      'Accept': 'application/json',
    },
    body: formData
  }).then(function(res){
    return res.json()
    }).then(data=>{

      return data
    }).catch(err=>{console.log(err)})
  return response
  
}