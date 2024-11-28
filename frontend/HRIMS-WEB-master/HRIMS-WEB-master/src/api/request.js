// 引入 axios
import axios from 'axios'
import serverConfig from "../config/index.js";
import { Notify } from 'notiflix/build/notiflix-notify-aio'

// 请求超时设置
const timeout = 6000

const instance = axios.create({
    baseURL: serverConfig.baseURL,
    timeout
})

// 设置拦截器

// http拦截-在axios请求发出之前给每一个接口携带token
instance.interceptors.request.use(
    config => {
        if (serverConfig.useTokenAuthorization) {
            let token = localStorage.getItem('hrims-token')
            if (token) {
                config.headers['hrims-token'] = token
            }
        }

        // 如果没有设置请求头
        if (!config.headers["content-type"]) {
            if (config.method === 'post') {
                // post 请求
                config.headers["content-type"] = "application/json";
            } else {
                config.headers["content-type"] = "application/json"; // 默认类型
            }
        }
        return config
    },
    err => {
        return Promise.reject(err)
    }
)

// http拦截-在axios请求发出之后
instance.interceptors.response.use(
    response => {
        return response.data;
    },
    error => {
        if (error.response) {
            Notify.error(error.response)
            // todo 跳转登录界面
        }
        // 返回错误信息
        return Promise.reject(error.response)
    }
)


export default instance