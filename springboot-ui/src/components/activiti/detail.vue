<template>
  <div class="execution">
    <basic-container>
      <div class="header">
        <span class="header__title">{{name}}</span>
        <el-button class="header__btn"
                   type="primary"
                   @click="breaks">关闭返回列表
        </el-button>
      </div>
    </basic-container>
    <basic-container>
      <!--iframe中显示activiti的编辑器 -->
      <iframe class="iframe" :src="src"></iframe>
    </basic-container>
  </div>
</template>

<script>
import BasicContainer from '@/components/basic-container/index'

export default {
  components: {
    BasicContainer
  },
  data() {
    return {
      id: '',
      name: '',
      // 后端静态文件夹下的activiti的编辑器
      actUrl: 'http://localhost:8443/modeler.html?modelId='
    }
  },
  created() {
    this.id = this.$route.params.id
    this.name = this.$route.query.name
  },
  computed: {
    src() {
      console.log('detail.vue src() '+this.actUrl+this.id)
      return `${this.actUrl}${this.id}`
    }
  },
  methods: {
    breaks() {
       this.$router.push({path: '/model'})
    }
  }
}
</script>

<style lang="scss" scoped>
  .execution {
    height: 99%;

    .header {
      position: relative;
      display: flex;
      align-items: center;

      &__title {
        font-size: 22px;
      }

      &__btn {
        position: absolute;
        right: 10px;
      }
    }

    .iframe{
      width: 100%;
      height: auto;
      min-height: 450px;
      border: 0;
      overflow: hidden;
      -webkit-box-sizing: border-box;
      box-sizing: border-box;
      background-color: #222;
    }
  }
</style>
