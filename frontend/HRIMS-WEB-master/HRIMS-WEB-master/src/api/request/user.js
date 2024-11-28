import instance from '../request.js'

// 登录
export const login = (data) => {
    return instance({
        url: '/sys/login',
        method: 'post',
        data
    })
}

// 获取用户列表
export const userList = (data) => {
    return instance({
        url: '/sys/ordinary',
        method: 'post',
        data
    })
}

export const userDetail = (userId) => {

}