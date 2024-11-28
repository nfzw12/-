import { createStore } from 'vuex'

export default createStore({

    state: {
        userId: ''
    },
    // 同步调用
    mutations: {
        setUserId(state, val){
            state.userId = val
        }
    },

    // 异步调用
    actions: {
    },

    modules: {
    }
})