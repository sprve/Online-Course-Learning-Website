import axios from 'axios'
import { MessageBox, Message } from 'element-ui'
import cookie from 'js-cookie'

// 创建axios实例
const service = axios.create({
  // baseURL: 'http://localhost:9001', //访问nginx端口
  baseURL: 'http://101.42.171.11:8000', //访问网关端口
  timeout: 20000 // 请求超时时间
})

//第三步 创建拦截器 http request 拦截器
service.interceptors.request.use(
  config => {
  //debugger
  //判断cookie里面是否有名称是sprve_token数据
  if (cookie.get('sprve_token')) {
    //把获取cookie值放到header里面
    config.headers['token'] = cookie.get('sprve_token');
  }
    return config
  },
  err => {
  return Promise.reject(err);
})

// http response 拦截器
service.interceptors.response.use(
  response => {
    //debugger
    if (response.data.code == 80000) {
        // 返回 错误代码-1 清除ticket信息并跳转到登录页面
        //debugger
        window.location.href="/login"
        return
    }else{
      if (response.data.code !== 20000) {
          Message({
            message: response.data.message || 'error',
            type: 'error',
            duration: 5 * 1000
          })
      } else {
        return response;
      }
    }
  },
  error => {
    return Promise.reject(error.response)   // 返回接口返回的错误信息
});

export default service
