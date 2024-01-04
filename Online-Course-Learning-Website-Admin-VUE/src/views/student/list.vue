<template>
  <div class="app-container">
    <el-alert :closable="false" title="" type="warning"/>
    <br>
    <el-form v-show="showSearch" ref="queryForm" :model="queryParams" :inline="true" size="small" label-width="68px">
      <el-form-item label="手机号" prop="mobile">
        <el-input
          v-model="queryParams.mobile"
          placeholder="请输入手机号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          v-if="hasPerm('student.add')"
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
        >新增</el-button>
      </el-col>
    </el-row>
    <el-table v-loading="loading" :data="studentList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="用户ID" align="center" prop="id" />
      <el-table-column label="用户手机号" align="center" prop="mobile" />
      <el-table-column label="用户昵称" align="center" prop="nickname" />
      <el-table-column label="用户性别" align="center" prop="sex">
        <template slot-scope="scope">{{ scope.row.sex == 1 ? '女' : '男' }}</template>
      </el-table-column>
      <el-table-column label="用户年龄" align="center" prop="age" />
      <el-table-column label="用户头像" align="center" prop="avatar">
        <template slot-scope="scope">
          <img :src="scope.row.avatar" :alt="scope.row.nickname" width="100">
        </template>
      </el-table-column>
      <el-table-column label="注册时间" align="center" prop="gmtCreate" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            v-if="hasPerm('student.update')"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
          >修改</el-button>
          <el-button
            v-if="hasPerm('student.remove')"
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      :current-page="page"
      :page-size="limit"
      :total="total"
      style="padding: 30px 0; text-align: right;"
      layout="total, prev, pager, next, jumper"
      @current-change="getList"
    />
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="用户ID" prop="id">
          <el-input v-model="form.id" :disabled="true" placeholder="请输入用户ID" />
        </el-form-item>
        <el-form-item label="手机号" prop="mobile">
          <el-input v-model="form.mobile" placeholder="请输入用户手机号" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password" placeholder="请输入用户密码" />
        </el-form-item>
        <el-form-item label="用户昵称" prop="nickname">
          <el-input v-model="form.nickname" placeholder="请输入用户昵称" />
        </el-form-item>
        <el-form-item label="用户性别" prop="sex">
          <el-select v-model="form.sex" placeholder="请选择性别">
            <el-option
              v-for="dict in sexOption"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="用户年龄" prop="age">
          <el-input v-model="form.age" placeholder="请输入用户年龄" />
        </el-form-item>
        <el-form-item label="用户头像">
          <el-upload
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload"
            :action="BASE_API+'/eduoss/fileoss'"
            class="avatar-uploader">
            <img :src="form.avatar" style="width:100px">
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import stuentApi from '@/api/edu/student'
export default {
  name: 'Role',
  data() {
    return {
      loading: false,
      ids: [],
      single: true,
      multiple: true,
      showSearch: true,
      page: 1,
      limit: 10,
      total: 0,
      studentList: [],
      title: '',
      open: false,
      queryParams: {
        mobile: ''
      },
      BASE_API: process.env.BASE_API,
      OSS_PATH: process.env.OSS_PATH,
      form: {},
      sexOption: [
        {
          value: '2',
          label: '男'
        },
        {
          value: '1',
          label: '女'
        }
      ],
      rules: {
        mobile: [
          { required: true, message: '用户名称不能为空', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '用户密码不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList(page = 1) {
      this.page = page
      this.loading = true
      stuentApi.listStudent(this.page, this.limit, this.queryParams).then(response => {
        this.studentList = response.data.rows
        this.total = response.data.total
        this.loading = false
      })
    },
    cancel() {
      this.open = false
      this.reset()
    },
    reset() {
      this.form = {
      }
    },
    handleQuery() {
      this.getList()
    },
    resetQuery() {
      this.handleQuery()
    },
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '添加用户信息'
      this.form.avatar = this.OSS_PATH + '/pictures/load.png'
    },
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      stuentApi.getStudent(id).then(response => {
        this.form = response.data.data
        this.open = true
        this.title = '修改用户信息'
      })
    },
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            stuentApi.updateStudent(this.form).then(response => {
              this.$message({
                type: 'success',
                message: '修改成功!'
              })
              this.open = false
              this.getList()
            })
          } else {
            stuentApi.addStudent(this.form).then(response => {
              this.$message({
                type: 'success',
                message: '添加成功!'
              })
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    handleAvatarSuccess(res, file) {
      this.form.avatar = res.data.url
    },
    beforeAvatarUpload(file) {
      const isLt2M = file.size / 1024 / 1024 < 2
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!')
      }
      return isLt2M
    },
    handleDelete(row) {
      this.$confirm('此操作将永久删除该学生, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        stuentApi.delStudent(row.id).then(response => {
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
          this.getList()
        })
      })
    }
  }
}
</script>
