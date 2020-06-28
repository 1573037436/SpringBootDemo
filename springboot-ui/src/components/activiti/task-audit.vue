<template>
  <el-dialog
    title="审批"
    :visible.sync="visible"
    :append-to-body="true">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="100px">
      <el-form-item label="审批意见" prop="comment">
        <el-input v-model="dataForm.comment" placeholder="请输入审批意见"></el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<style scoped>
</style>

<script>
export default {
  data () {
    return {
      visible: false,
      taskId: '',
      dataForm: {
        comment: ''
      },
      dataRule: {
        comment: [
          { required: true, message: '审批意见不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    // 初始化
    init(taskId) {
      this.visible = true
      this.taskId = taskId
      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()
      })
    },
    // 表单提交
    dataFormSubmit () {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          this.$http({
            url: this.$http.adornUrl('/task/handleTask'),
            method: 'post',
            data: {
              'taskId': this.taskId,
              'comment': this.dataForm.comment
            }
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500,
                onClose: () => {
                  this.visible = false
                  this.$emit('refreshDataList')
                }
              })
            } else {
              this.$message.error(data.msg)
            }
          }).catch(function (err) {
            console.log(err)
          })
        }
      })
    }
  }
}
</script>
