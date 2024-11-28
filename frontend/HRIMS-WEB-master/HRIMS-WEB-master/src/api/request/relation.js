import instance from '../request.js'

// 绑定关系
export const distributeRelation = (data) => {
    return instance({
        url: '/sys/relation/dist',
        method: 'post',
        data
    })
}

// 接触关系
export const releaseRelation = (data) => {
    return instance({
        url: '/sys/relation/release',
        method: 'post',
        data
    })
}