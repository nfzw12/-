import instance from '../request.js'

// 获取未绑定的企业
export const unbindEntList = (data) => {
    return instance({
        url: '/sys/ent/unbind',
        method: 'post',
        data
    })
}