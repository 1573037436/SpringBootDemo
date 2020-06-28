<template>
  <div @click="userlist()">
      <el-form :inline="true" :model="user" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="user.username" placeholder="用户名称" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="userlist()">查询</el-button>
        <el-button type="primary" @click="addOrUpdateHandle()">新增</el-button>
        <el-button type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>
      </el-form-item>
    </el-form>
      <el-row>
    <el-table
      :data="users"
      border
      v-loading="dataListLoading"
       @selection-change="selectionChangeHandle"
      style="width: 100%;">
      <!--标题 prop 为列名-->
      <el-table-column
        type="selection"
        header-align="center"
        align="center"
        width="50">
      </el-table-column>
      <el-table-column
        prop="id" 
        v-if="show"
        header-align="center"
        align="center"
        width="80"
        label="ID">
      </el-table-column>
      <el-table-column
        prop="username"
        header-align="center"
        align="center"
        label="用户名">
      </el-table-column>
      <el-table-column
        prop="mobile"
        header-align="center"
        align="center"
        label="手机号">
      </el-table-column>
      <el-table-column
        prop="email"
        header-align="center"
        align="center"
        label="邮箱">
      </el-table-column>
      <el-table-column
        prop="birthday"
        header-align="center"
        align="center"
        label="生日">
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
              <el-dropdown-item @click.native="handleView(scope.row)">查看</el-dropdown-item>
              <el-dropdown-item divided @click.native="handleEdit(scope.row)">编辑</el-dropdown-item>
              <el-dropdown-item divided @click.native="handleDel(scope.row)">删除</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </template>
      </el-table-column>
    </el-table>
      </el-row>
      <el-row>
      <!--分页控件 -->
      <el-pagination
        @size-change="sizeChangeHandle"
        @current-change="currentChangeHandle"
        :current-page="pageIndex"
        :page-sizes="[10, 20, 50, 100]"
        :page-size="pageSize"
        :total="totalPage"
        layout="total, sizes, prev, pager, next, jumper">
      </el-pagination>
    </el-row>
    <user-add ref="userform" @refreshDataList="userlist"></user-add>
  </div>
</template>

<script>
 import UserAdd from '@/components/admin/UserAdd'
  export default {
    name: 'UserList',
    components:{UserAdd},
    data() {
      return {
        // 隐藏id属性，配合v-if使用
        show: false,
        user:{
          username:''
        },
        users: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        // 选中的数据
        dataListSelections: []
      }
    },

    mounted: function (){
        this.userlist()
    },
    methods:{
        userlist(){
            var _this = this
            this.$axios.get('/user/list',
                {
                    params:{
                    'current': this.pageIndex,
                    'size': this.pageSize}  
                }
            ).then(resp => {
            if (resp && resp.status === 200) {
                // 返回查询的users数据
                console.log('loadusers and response data is '+resp.data)
                _this.users = resp.data.records
                // 总的数量
                _this.totalPage=resp.data.total
                //console.log("user name is ------"+_this.users[0].username)
            }
            })
        },
        // 每页数
        sizeChangeHandle (val) {
            this.pageSize = val
            this.pageIndex = 1
            this.userlist()
        },
        // 当前页
        currentChangeHandle (val) {
            this.pageIndex = val
            this.userlist()
        },
        // 多选事件
        selectionChangeHandle (val) {
            this.dataListSelections = val
        },
        // 批量删除
        deleteHandle(){
           //var _this = this
           //dataListSelections 为选中的数据即选中的user数组
           var ids= this.dataListSelections.map(item => item.id).join()//获取所有选中行的id组成的字符串，以逗号分隔
           console.log('dataListSelections---'+ids)
            this.$confirm('是否确认批量删除用户?', '警告', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() =>{
                this.$axios.post('/user/deleteBatchIds',{ids:ids}).then(resp => {
              if (resp && resp.status === 200) {
                this.userlist()
              }
            })
            }).catch(function (err) {

            })
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
        handleEdit (user) {
            console.log('*********user  is '+user.username)
            this.$refs.userform.userAddVisible = true
            this.$refs.userform.user = {
            id:user.id,
            username: user.username,
            mobile:user.mobile
            //category: {
                //id: item.category.id.toString(),
                //name: item.category.name
            //}
            }
        },
        handleDel (row) {
        var _this = this
        this.$confirm('是否确认删除"' + row.username + '"用户?', '警告', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
        }).then(function () {
            return _this.$axios.delete('/user/deleteById/' + row.id)
        }).then(({data}) => {
            _this.userlist()
            _this.$message({
            showClose: true,
            message: '删除成功',
            type: 'success'
            })
        }).catch(function (err) {
        })
        },
        addOrUpdateHandle () {
            this.userAddVisible = true
            this.$nextTick(() => {
                this.$refs.userform.init()
            })
        },
    }
  }
</script>