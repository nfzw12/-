import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    employeeId: null // 初始化为 null，稍后从某个地方获取实际值
  },
  mutations: {
    setEmployeeId(state, id) {
      state.employeeId = id;
    }
  },
  actions: {
    updateEmployeeId({ commit }, newId) {
      commit('setEmployeeId', newId);
    },
    fetchEmployeeId({ commit }, id) {
      // 这里可能有一个异步操作来获取 id，但现在我们直接提交它
      commit('setEmployeeId', id);
    }
  },
  getters: {
    getEmployeeId: state => state.employeeId
  }
});