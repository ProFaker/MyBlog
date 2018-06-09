import request from '../utils/request';
import { PAGE_SIZE ,Host } from '../constants';

const host = Host

export function fetch({ page }) {
  return request(`/api/users?_page=${page}&_limit=${PAGE_SIZE}`);
}

export function login(data){
  // return request(`/api/login`);
  return request({
    
    url: host+'login',
    method: 'post',
    data
  })
}

export function upload(data){

  return request({

    url : host+'uploadImage',
    method : 'post',
    data

  })
}