<template>
  <div class="app-container">
    <el-form :inline="true" size="small">
      <el-form-item label="用户名" prop="username">
        <el-input
          v-model="searchObj.username"
          placeholder="请输入用户名"
          clearable
        />
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="fetchData()"
        >查询</el-button
        >
        <el-button
          type="default"
          size="mini"
          @click="resetData()"
        >清空</el-button
        >
      </el-form-item>
    </el-form>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          v-if="hasPerm('user.add')"
          type="primary"
          size="small"
          @click="handleAdd()"
        >新增</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-if="hasPerm('user.remove')"
          type="danger"
          size="small"
          @click="removeRows()"
        >批量删除</el-button
        >
      </el-col>
    </el-row>
    <el-table
      v-loading="listLoading"
      :data="userList"
      stripe
      style="width: 100%"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" />
      <el-table-column prop="userId" label="用户编号" />
      <el-table-column prop="username" label="用户名" />
      <el-table-column prop="nickName" label="用户昵称" />
      <el-table-column prop="phonenumber" label="手机号" />
      <el-table-column prop="email" label="邮箱" />
      <el-table-column prop="sex" label="性别">
        <template slot-scope="scope">{{ scope.row.sex == 0 ? '男' : '女' }}</template>
      </el-table-column>
      <el-table-column prop="gmtCreate" label="创建时间" width="180" />
      <el-table-column label="操作" width="230" align="center">
        <template slot-scope="scope">
          <el-button
            v-if="hasPerm('user.update')"
            type="text"
            size="mini"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
          >修改</el-button>
          <el-button
            v-if="hasPerm('user.remove')"
            type="text"
            size="mini"
            icon="el-icon-delete"
            @click="removeDataById(scope.row)"
          >删除</el-button>
          <router-link :to="'/acl/user/role/' + scope.row.userId">
            <el-button
              v-if="hasPerm('user.assgin')"
              type="text"
              size="mini"
              icon="el-icon-info"
            >分配角色</el-button>
          </router-link>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      :current-page="page"
      :total="total"
      :page-size="limit"
      :page-sizes="[5, 10, 20, 30, 40, 50, 100]"
      style="padding: 30px 0; text-align: right"
      layout="sizes, prev, pager, next, jumper, ->, total, slot"
      @current-change="fetchData"
      @size-change="changeSize"
    />
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="用户名" prop="username">
              <el-input v-model="form.username" placeholder="请输入用户名" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="用户昵称" prop="nickName">
              <el-input v-model="form.nickName" placeholder="请输入用户昵称" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="手机号" prop="phonenumber">
              <el-input v-model="form.phonenumber" placeholder="请输入手机号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="form.email" placeholder="请输入邮箱" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="用户密码" prop="password">
              <el-input v-model="form.password" placeholder="请输入用户密码" maxlength="20" show-password />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="性别" prop="sex">
              <el-select v-model="form.sex" placeholder="请选择性别">
                <el-option
                  v-for="dict in sexOption"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="备注" prop="remark">
              <el-input
                v-model="form.remark"
                type="textarea"
                placeholder="请输入内容"
              />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import userApi from '@/api/acl/user'
export default {
  data() {
    return {
      listLoading: true,
      total: 0,
      page: 1,
      limit: 10,
      searchObj: {},
      single: true,
      multiple: true,
      multipleSelection: [],
      userList: [],
      form: {},
      sexOption: [
        {
          value: '0',
          label: '男'
        },
        {
          value: '1',
          label: '女'
        }
      ],
      title: '',
      open: false,
      rules: {
        username: [
          { required: true, message: '用户名称不能为空', trigger: 'blur' },
          {
            min: 2,
            max: 20,
            message: '用户名称长度必须介于 2 和 20 之间',
            trigger: 'blur'
          }
        ],
        nickName: [
          { required: true, message: '用户昵称不能为空', trigger: 'blur' }
        ],
        email: [
          {
            type: 'email',
            message: '请输入正确的邮箱地址',
            trigger: ['blur', 'change']
          }
        ],
        phonenumber: [
          {
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: '请输入正确的手机号码',
            trigger: 'blur'
          }
        ],
        password: [
          { required: true, message: '用户密码不能为空', trigger: 'blur' },
          { min: 5, max: 20, message: '用户密码长度必须介于 5 和 20 之间', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    reset() {
      this.form = {
        userId: undefined,
        username: undefined,
        nickName: undefined,
        password: undefined,
        phonenumber: undefined,
        email: undefined,
        sex: undefined,
        remark: undefined
      }
    },
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '添加用户'
    },
    submitForm: function() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          if (this.form.userId != undefined) {
            userApi.updateUser(this.form).then(response => {
              this.$message({
                message: '修改成功',
                type: 'success'
              })
              this.open = false
              this.fetchData()
            })
          } else {
            userApi.addUser(this.form).then(response => {
              this.$message({
                message: '添加成功',
                type: 'success'
              })
              this.open = false
              this.fetchData()
            })
          }
        }
      })
    },
    cancel() {
      this.open = false
      this.reset()
    },
    handleUpdate(row) {
      this.reset()
      const userId = row.userId
      userApi.getById(userId).then(response => {
        this.form = response.data.item
        this.open = true
        this.title = '修改用户'
        this.form.password = ''
      })
    },
    changeSize(size) {
      this.limit = size
      this.fetchData(1)
    },
    fetchData(page = 1) {
      this.page = page
      userApi.getPageList(this.page, this.limit, this.searchObj).then((response) => {
        this.userList = response.data.items
        this.total = response.data.total
        this.listLoading = false
      })
    },
    resetData() {
      this.searchObj = {}
      this.fetchData()
    },
    removeDataById(row) {
      const userId = row.userId
      this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        return userApi.removeById(userId)
      }).then((response) => {
        this.fetchData(this.page)
        if (response.success) {
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
        }
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    handleSelectionChange(selection) {
      this.multipleSelection = selection
    },
    removeRows() {
      if (this.multipleSelection.length === 0) {
        this.$message({
          type: 'warning',
          message: '请选择要删除的记录!'
        })
        return
      }
      this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        var idList = []
        this.multipleSelection.forEach((item) => {
          idList.push(item.id)
        })
        return userApi.removeRows(idList)
      })
        .then((response) => {
          this.fetchData(this.page)
          if (response.success) {
            this.$message({
              type: 'success',
              message: '删除成功!'
            })
          }
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
    }
  }
}
</script>
