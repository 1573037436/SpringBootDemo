import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
// 引入自定义的组件,我们在一个组件中通过导入引用了其它组件，也可以称之为父子组件，
// 但想要通过 <router-view/> 控制子组件的显示，则需要进行路由的相关配置
import Login from '@/components/Login'
import AppIndex from '@/components/AppIndex'
import Home from '@/components/Home'
import LibraryIndex from '../components/library/LibraryIndex'
import AdminMenu from '@/components/admin/AdminMenu'

Vue.use(Router)

export default new Router({
  mode:'history', // url中带#的模式被称为hash模式
  routes: [
    {
      path: '/home',
      name: 'Home',
      component: Home,
      // 注意我们并没有把首页的访问路径设置为 /home/index，仍然可以通过 /index 访问首页，
      // 这样配置其实是感受不到 /home 这个路径的存在的。之后再添加新的页面，可以直接在 children 中增添对应的内容。
      redirect: '/index',
      children: [
        {
          path: '/admin',
          name: 'AdminMenu',
          component: AdminMenu
        },
        {
          path: '/index',
          name: 'AppIndex',
          component: AppIndex,
          //meta: {
           // requireAuth: true
          //}
        },
        {
          path: '/library',
          name: 'Library',
          component: LibraryIndex,
          //meta: {
            //requireAuth: true
          //}
        }
      ]
    },
    {
      path: '/',
      name: 'HelloWorld',
      component: HelloWorld
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    }
  ]
})
