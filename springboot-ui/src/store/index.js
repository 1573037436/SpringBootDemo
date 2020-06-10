import Vue from 'vue'
import Vuex from 'vuex'
// 前端拦截器

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
      // 对象user记录用户信息，方便日后扩展(如权限验证)
    user: {
      //localStorage，即本地存储，在项目打开的时候会判断本地存储中是否有 user 这个对象存在，如果存在就取出来并获得 username 的值，
      //否则则把 username 设置为空。这样我们只要不清除缓存，登录的状态就会一直保存。
      username: window.localStorage.getItem('user' || '[]') == null ? '' : JSON.parse(window.localStorage.getItem('user' || '[]')).username
    },
    adminMenus:[]
  },
  //更改 Vuex 的 store 中的状态的唯一方法是提交 mutation。Vuex 中的 mutation 非常类似于事件
  mutations: {
    //存储user信息
    login (state, user) {
      state.user = user
      window.localStorage.setItem('user', JSON.stringify(user))
    },
    initAdminMenu (state, menus) {
      state.adminMenus = menus
    }   
  }
})
