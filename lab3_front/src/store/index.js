import { createStore } from 'vuex'

export default createStore({
  state: {
    routes_store: ''
  },
  getters: {
  },
  mutations: {
    change_routes(state, now_routes) {
      state.routes_store = now_routes
    }
  },
  actions: {
  },
  modules: {
  }
})
