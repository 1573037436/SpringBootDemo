<template>
  <div class="mod-model">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.modelName" placeholder="模型名称" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
        <el-button type="primary" @click="addOrUpdateHandle()">新增</el-button>
        <el-button type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>
      </el-form-item>
    </el-form>
    <el-table
      :data="dataList"
      border
      v-loading="dataListLoading"
      @selection-change="selectionChangeHandle"
      style="width: 100%;">
      <el-table-column
        type="selection"
        header-align="center"
        align="center"
        width="50">
      </el-table-column>
      <el-table-column
        prop="id"
        header-align="center"
        align="center"
        width="80"
        label="ID">
      </el-table-column>
      <el-table-column
        prop="category"
        header-align="center"
        align="center"
        label="流程分类">
      </el-table-column>
      <el-table-column
        prop="name"
        header-align="center"
        align="center"
        label="模型名称">
      </el-table-column>
      <el-table-column
        prop="key"
        header-align="center"
        align="center"
        label="模型标识">
      </el-table-column>
      <el-table-column
        prop="desc"
        header-align="center"
        align="center"
        label="模型描述">
      </el-table-column>
      <el-table-column
        prop="createTime"
        header-align="center"
        align="center"
        width="180"
        label="创建时间">
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
          <el-dropdown :show-timeout="0" placement="bottom">
            <el-button type="primary">
              功能<i class="el-icon-arrow-down el-icon--right"></i>
            </el-button>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item @click.native="handleView(scope.row)">模型图</el-dropdown-item>
              <el-dropdown-item divided @click.native="handleDeploy(scope.row)">部署</el-dropdown-item>
              <el-dropdown-item divided @click.native="handleDel(scope.row)">删除</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="sizeChangeHandle"
      @current-change="currentChangeHandle"
      :current-page="pageIndex"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="pageSize"
      :total="totalPage"
      layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>
    <!-- 弹窗, 新增 -->
    <model-add v-if="modelAddVisible" ref="modelAdd" @refreshDataList="getDataList"></model-add>
  </div>
</template>

<style scoped>
</style>

<script>
import ModelAdd from './model-add'

export default {
  name: 'model',
  data () {
    return {
      dataForm: {
        modelName: ''
      },
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      dataListSelections: [],
      modelAddVisible: false
    }
  },
  components: {
    ModelAdd
  },
  created () {
    //该周期钩子函数在实例创建完成后被立即调用, activated被keep-alive 缓存的组件激活时调用。该钩子在服务器端渲染期间不被调用。
    this.getDataList()
  },
  methods: {
    // 获取数据列表
    getDataList () {
      console.log('***********************获取数据列表*****************************')
      this.dataListLoading = true
      this.$axios.get('/model/getModelPage',
        {
          params:{
            'current': this.pageIndex,
            'size': this.pageSize,
            'descs': 'create_time'}  
        }
      ).then(({data}) => {
        if (data && data.code === 0) {
          this.handleRecords(data.data.records)
          this.dataList = data.data.records
          this.totalPage = data.data.pages
        } else {
          this.dataList = []
          this.totalPage = 0
        }
        this.dataListLoading = false
      })
    },
    // 每页数
    sizeChangeHandle (val) {
      this.pageSize = val
      this.pageIndex = 1
      this.getDataList()
    },
    // 当前页
    currentChangeHandle (val) {
      this.pageIndex = val
      this.getDataList()
    },
    // 多选
    selectionChangeHandle (val) {
      this.dataListSelections = val
    },
    handleView (row) {
      const name = `模型id为${row.id}的${row.name}流程图`,
      src = `/detail/${row.id}`;
      console.log(src+"=============="+name)
      this.$router.push({
        path: src,
        query: {
          name: name
        }
      })
    },
    handleDeploy (row) {
      var _this = this
      this.$confirm('是否确认部署ID为"' + row.id + '"的模型?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function () {
        return _this.$axios.post('/model/deploy/'+row.id)
      }).then(({data}) => {
        _this.getDataList()
        _this.$message({
          showClose: true,
          message: '部署成功',
          type: 'success'
        })
      }).catch(function (err) {
      })
    },
    handleDel (row) {
      var _this = this
      this.$confirm('是否确认删除ID为"' + row.id + '"的模型?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function () {
        return _this.$axios.delete('/model/removeModelById/' + row.id)
      }).then(({data}) => {
         _this.getDataList()
        _this.$message({
          showClose: true,
          message: '删除成功',
          type: 'success'
        })
      }).catch(function (err) {
      })
    },
    addOrUpdateHandle () {
      this.modelAddVisible = true
      this.$nextTick(() => {
        this.$refs.modelAdd.init()
      })
    },
    /**
     * 处理数据
     */
    handleRecords(records) {
      for(var i=0; i< records.length; i++){
        records[i].desc = JSON.parse(records[i].metaInfo).description
      }
    }
  }
}
</script>
