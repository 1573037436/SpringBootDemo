<template>
  <div>
    <el-container>
      <!-- 边栏-->
    <el-aside style="width:200px;">
    <el-menu
      :default-active="'/admin/users'"
      class="el-menu-admin"
      router
      mode="vertical"
      background-color="#545c64"
      text-color="#fff"
      active-text-color="#ffd04b">
      
      <!--循环遍历菜单 -->
      <template v-for="(item,i) in adminMenus">
        <!--index 没有用但是必需字段且为 string -->
        <!--一级菜单-->
        <el-submenu :key="i" :index="i + ''" style="text-align: left">
          <span slot="title" style="font-size: 17px;">
            <i :class="item.icon"></i>
            {{item.name}}
          </span>
          <!--二级菜单-->
          <el-menu-item v-for="child in item.nodes" :key="child.url" :index="child.url" 
          @click="$router.push({ name: child.path})">
            <i :class="child.icon"></i>
            {{ child.name}} 
          </el-menu-item>
        </el-submenu>
      </template>
    </el-menu>
    </el-aside>
    <el-main>
      <user-list></user-list>
    </el-main>
    </el-container>
  </div>
</template>

<script>
    import UserList from '@/components/admin/UserList'
    export default {
      name: 'AdminMenu',
      computed: {
        adminMenus () {
          var menus=this.$store.state.adminMenus
          return menus
        }
      },
      // 引用组件
      components:{UserList}
    }
</script>
<style>
element.style {
    background-color: #f5f7fa;
}
.el-menu {
    background-color: #FFF;
  }
  .el-submenu {
    width: 200px;
}
</style>
