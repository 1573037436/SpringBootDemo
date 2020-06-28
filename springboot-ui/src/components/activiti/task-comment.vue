<template>
  <el-dialog
    title="新增"
    :visible.sync="visible"
    :append-to-body="true">
    <el-form :inline="true" :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="100px">
      <el-form-item label="模型标识" prop="key">
        <el-input v-model="dataForm.key" placeholder="请输入模型标识"></el-input>
      </el-form-item>
      <el-form-item label="流程分类" prop="category">
        <el-input v-model="dataForm.category" placeholder="请输入流程分类"></el-input>
      </el-form-item>
      <el-form-item label="模型名称" prop="name">
        <el-input v-model="dataForm.name" placeholder="请输入模型名称"></el-input>
      </el-form-item>
      <el-form-item label="模型描述" prop="desc">
        <el-input v-model="dataForm.desc" placeholder="请输入模型描述"></el-input>
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
      dataForm: {
        key: '',
        category: '',
        name: '',
        desc: ''
      },
      dataRule: {
        key: [
          { required: true, message: '模型标识不能为空', trigger: 'blur' }
        ],
        category: [
          { required: true, message: '流程分类不能为空', trigger: 'blur' }
        ],
        name: [
          { required: true, message: '模型名称不能为空', trigger: 'blur' }
        ],
        desc: [
          { required: true, message: '模型描述不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    // 初始化
    init() {
      this.visible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()
      })
    },
    // 表单提交
    dataFormSubmit () {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          this.$http({
            url: this.$http.adornUrl('/model/createModel'),
            method: 'post',
            data: {
              'category': this.dataForm.category,
              'name': this.dataForm.name,
              'key': this.dataForm.key,
              'desc': this.dataForm.desc
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
          })
        }
      })
    }
  }
}
</script>
