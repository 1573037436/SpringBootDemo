<template>
  <el-container>
    <el-aside style="width: 200px;margin-top: 20px">
      <switch></switch>
      <SideMenu></SideMenu>
    </el-aside>
    <el-main>
      <!--显示书籍信息 -->
      <book class="book-area"></book>
    </el-main>
  </el-container>
</template>

<script>
  import SideMenu from './SideMenu'
  import Book from './Book'
  export default {
    name: 'AppLibrary',
    components: {SideMenu, Book}
  }
</script>

<style scoped>
  .book-area {
    width: 990px;
    margin-left: auto;
    margin-right: auto;
  }
</style>

