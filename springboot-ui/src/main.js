import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import store from './store'



// 设置反向代理，前端请求默认发送到 http://localhost:8443/api
var axios = require('axios')
axios.defaults.baseURL = 'http://localhost:8443/api'
// 全局注册，之后可在其他组件中通过 this.$axios 发送数据
Vue.prototype.$axios = axios
Vue.config.productionTip = false

Vue.use(ElementUI)

//使用钩子函数router.beforeEach，意思是在访问每一个路由前调用
//首先判断访问的路径是否需要登录，如果需要，判断 store 里有没有存储 user 的信息，如果存在，则放行，
//否则跳转到登录页面，并存储访问的页面路径（以便在登录后跳转到访问页）


router.beforeEach((to, from, next) => {
  if (store.state.user.username && to.path.startsWith('/admin')) {
      initAdminMenu(router, store)
  }
  // 已登录状态下访问 login 页面直接跳转到后台首页
  if (store.state.username && to.path.startsWith('/login')) {
    next({
      path: 'admin/dashboard'
    })
  }
  if (to.meta.requireAuth) {
    if (store.state.user.username) {
      axios.get('/authentication').then(resp => {
       if (resp) next()
      })
    } else {
      next({
        path: 'login',
        query: {redirect: to.fullPath}
      })
    }
  } else {
    next()
  }
}
)

const initAdminMenu = (router, store) => {
  if (store.state.adminMenus.length > 0) {
    return
  }
  //动态获取菜单信息
  axios.get('/menu').then(resp => {
    if (resp && resp.status === 200) {
      var fmtRoutes = formatRoutes(resp.data)
      router.addRoutes(fmtRoutes)
      store.commit('initAdminMenu', fmtRoutes)
    }
  })
}

const formatRoutes = (routes) => {
  let fmtRoutes = []
  routes.forEach(route => {
    console.log("route url is ----"+route.url)
    //判断菜单是否包含子项
    if (route.nodes) {
      route.nodes = formatRoutes(route.nodes)
    }

    // 构造需要的字段
    let fmtRoute = {
      path: route.url,
      component: resolve => {
        require(['./components/admin/' + route.component + '.vue'], resolve)
      },
      name: route.name,
      nodes: route.nodes,
      icon: route.icon
    }
    fmtRoutes.push(fmtRoute)
  })
  return fmtRoutes
}


/* eslint-disable no-new */
new Vue({
  el: '#app',
  render: h => h(App),//ES6的箭头函数
  //createElement就是h,一个形参，没有具体意义，可以随意改变书写
  //render:(function(createElement){
  //return createElement(App);
  //})
  router,
  store,
  components: { App },
  template: '<App/>'
})

