<template>
  <el-dialog
    title="新增"
    :visible.sync="userAddVisible"
    @close="clear"
    :append-to-body="true">
    <el-form :inline="true" :model="user" :rules="dataRule" ref="user" @keyup.enter.native="dataFormSubmit()" label-width="100px">
      <el-form-item label="id" prop="id" v-if="false">
        <el-input v-model="user.id"></el-input>
      </el-form-item>
      <el-form-item label="用户名" prop="username">
        <el-input v-model="user.username" placeholder="请输入用户名"></el-input>
      </el-form-item>
      <el-form-item label="手机号" prop="mobile">
        <el-input v-model="user.mobile" placeholder="请输入手机号"></el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="userAddVisible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<style scoped>
</style>

<script>
export default {
  name:'UserAdd',
  data () {
    return {
      userAddVisible: false,
      user: {
        username: '',
        mobile: ''
      },
      dataRule: {
        username: [
          { required: true, message: '用户名不能为空', trigger: 'blur' }
        ],
        mobile: [
          { required: true, message: '手机号不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    // 初始化
    init() {
      this.userAddVisible = true
      this.$nextTick(() => {
        //重置user form对象
        this.$refs['user'].resetFields()
      })
    },
    clear () {
        this.user = {
          id: '',
          username: '',
          mobile: ''
          }
        },
    // 表单提交
    dataFormSubmit () {
      this.$refs['user'].validate((valid) => {
        if (valid) {
          this.$axios.post('/user/addOrUpdateUser',
            {
              'id':this.user.id,
              'username': this.user.username,
              'mobile': this.user.mobile
            }
          ).then(({data}) => {
            if (data && data === 200) {
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500,
                onClose: () => {
                   this.userAddVisible = false
                  // 添加成功，重新刷新数据
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
