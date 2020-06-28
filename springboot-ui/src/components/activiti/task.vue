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
        prop="taskId"
        header-align="center"
        align="center"
        width="150"
        label="任务ID">
      </el-table-column>
      <el-table-column
        prop="taskName"
        header-align="center"
        align="center"
        label="任务名称">
      </el-table-column>
      <el-table-column
        prop="time"
        header-align="center"
        align="center"
        label="提交时间">
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
              <el-dropdown-item @click.native="handleAudit(scope.row)">审批</el-dropdown-item>
              <el-dropdown-item divided @click.native="handleComment(scope.row)">批注</el-dropdown-item>
              <el-dropdown-item divided @click.native="handlePic(scope.row)">流程图</el-dropdown-item>
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
    <!-- 弹窗, 审批 -->
    <task-audit v-if="taskAuditVisible" ref="taskAudit" @refreshDataList="getDataList"></task-audit>
    <!-- 弹窗, 批注列表 -->
    <task-comment v-if="taskCommentVisible" ref="taskComment" @refreshDataList="getDataList"></task-comment>
    <el-dialog title="流程图" :visible.sync="showPicDialog">
      <img :src="actPicUrl" width="100%">
    </el-dialog>
  </div>
</template>

<style scoped>
</style>

<script>
import TaskAudit from './task-audit'
import TaskComment from './task-comment'

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
      taskAuditVisible: false,
      taskCommentVisible: false,
      showPicDialog: false,
      backendUrl: 'http://localhost:8443',
      actPicUrl: ''
    }
  },
  components: {
    TaskAudit,
    TaskComment
  },
  created () {
    this.getDataList()
  },
  methods: {
    // 获取数据列表
    getDataList () {
      this.dataListLoading = true
      this.$axios.get('/task/todoPage',{
        params: {
          'current': this.pageIndex,
          'size': this.pageSize
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
    handleAudit(row) {
      this.taskAuditVisible = true
      this.$nextTick(() => {
        this.$refs.taskAudit.init(row.taskId)
      })
    },
    handleComment (row) {
      var _this = this;
      this.$confirm(
        '是否确认操作ID为"' + row.taskId + '"的流程?',
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
    },
    handlePic (row, index) {
      this.actPicUrl = this.backendUrl +
        `/process/resource/` +
        row.deploymentId +
        "/" +
        row.processonDefinitionId +
        "/image";
      this.showPicDialog = true;
    }
  }
}
</script>
