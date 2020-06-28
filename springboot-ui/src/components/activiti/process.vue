<template>
  <div class="mod-model">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.modelName" placeholder="模型名称" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
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
        prop="processonDefinitionId"
        header-align="center"
        align="center"
        width="150"
        label="流程ID">
      </el-table-column>
      <el-table-column
        prop="key"
        header-align="center"
        align="center"
        label="模型标识">
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
        prop="revision"
        header-align="center"
        align="center"
        label="版本号">
      </el-table-column>
      <el-table-column
        prop="deploymentTime"
        header-align="center"
        align="center"
        label="部署时间">
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
              <el-dropdown-item @click.native="handlePic(scope.row, scope.index)">流程图</el-dropdown-item>
              <el-dropdown-item divided v-if="scope.row.suspend" @click.native="handleStatus(scope.row, 'active')">激活</el-dropdown-item>
              <el-dropdown-item divided v-if="!scope.row.suspend" @click.native="handleStatus(scope.row, 'suspend')">失效</el-dropdown-item>
              <el-dropdown-item divided v-if="!scope.row.suspend" @click.native="handleStartProcess(scope.row)">发起流程</el-dropdown-item>
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
    <el-dialog title="流程图" :visible.sync="showPicDialog">
      <img :src="actPicUrl" width="100%">
    </el-dialog>
  </div>
</template>

<style scoped>
</style>

<script>
export default {
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
      showPicDialog: false,
      backendUrl: 'http://localhost:8443/api',
      actPicUrl: ''
    }
  },
  created () {
    this.getDataList()
  },
  methods: {
    // 获取数据列表
    getDataList () {
      this.dataListLoading = true
      this.$axios.get('/process/getProcessPage',{
        params:  {
          'current': this.pageIndex,
          'size': this.pageSize,
          'descs': 'create_time'
        }
      }  
      ).then(({data}) => {
        if (data && data.code === 0) {
          this.dataList = data.data.records
          this.totalPage = data.data.pages
        } else {
          this.dataList = []
          this.totalPage = 0
        }
        this.dataListLoading = false
      }).catch(function (err) {
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
    handlePic (row, index) {
      this.actPicUrl = this.backendUrl +
        `/process/resource/` +
        row.deploymentId +
        "/" +
        row.processonDefinitionId +
        "/image";
      this.showPicDialog = true;
    },
    handleStatus(row, type) {
      var _this = this;
      this.$confirm(
        '是否确认操作ID为"' + row.processonDefinitionId + '"的流程?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }
      ).then(function () {
        return _this.$http({
          url: _this.$http.adornUrl('/process/status/' + row.processonDefinitionId+ '/' + type),
          method: 'put'
        })
      }).then(({data}) => {
        _this.getDataList();
        _this.$message({
          showClose: true,
          message: "操作成功",
          type: "success"
        });
      }).catch(function (err) {
      })
    },
    handleStartProcess (row) {
      var _this = this;
      this.$confirm(
        '是否确认发起部署ID为"' + row.deploymentId + '"的流程?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }
      ).then(function () {
        return _this.$http({
          url: _this.$http.adornUrl('/process/submit/' + row.deploymentId),
          method: 'post'
        })
      }).then(({data}) => {
        _this.getDataList();
        _this.$message({
          showClose: true,
          message: "操作成功",
          type: "success"
        });
      }).catch(function (err) {
      })
    },
    handleDel (row) {
      var _this = this;
      this.$confirm(
        '是否确认删除部署ID为"' + row.deploymentId + '"的流程?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }
      ).then(function () {
        return _this.$http({
          url: _this.$http.adornUrl('/process/deleteProcIns/' + row.deploymentId),
          method: 'delete'
        })
      }).then(({data}) => {
        _this.getDataList();
        _this.$message({
          showClose: true,
          message: "操作成功",
          type: "success"
        });
      }).catch(function (err) {
      })
    }
  }
}
</script>
