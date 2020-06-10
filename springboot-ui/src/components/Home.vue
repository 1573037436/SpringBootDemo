<template>
  <div>
    <!--模板中使用的自定义组件的命名同时支持PascalCase（驼峰命名）和 kebab-case（连字符分隔命名）两种规则，即<NavMenu>或<nav-menu> -->
    <!--<nav-menu></nav-menu>-->
    <NavMenu></NavMenu>
    <router-view/><!--也就是子页面（组件）显示的地方 -->
  </div>
</template>

<script>
  import NavMenu from './common/NavMenu'
  export default {
    name: 'Home',
    components: {NavMenu}
  }
</script>

<style scoped>

</style>

