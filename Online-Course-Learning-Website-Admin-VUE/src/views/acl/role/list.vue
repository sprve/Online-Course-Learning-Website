<template>
  <div class="app-container">
    <el-form :inline="true" size="small">
      <el-form-item label="角色名称" prop="roleName">
        <el-input v-model="searchObj.roleName" placeholder="请输入角色名称" />
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          icon="el-icon-search"
          @click="fetchData()"
        >查询</el-button
        >
        <el-button type="default" @click="resetData()">清空</el-button>
      </el-form-item>
    </el-form>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          v-if="hasPerm('role.add')"
          type="primary"
          size="mini"
          @click="handleAdd()"
        >新增</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-if="hasPerm('role.remove')"
          type="danger"
          size="mini"
          @click="removeRows()"
        >批量删除</el-button
        >
      </el-col>
    </el-row>
    <el-table
      v-loading="listLoading"
      :data="roleList"
      stripe
      style="width: 100%"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" />
      <el-table-column prop="roleId" label="角色编号" />
      <el-table-column prop="roleName" label="角色名称" />
      <el-table-column prop="roleCode" label="权限字符" />
      <el-table-column prop="gmtCreate" label="创建时间" />
      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <el-button
            v-if="hasPerm('role.update')"
            type="text"
            size="mini"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
          >修改</el-button>
          <el-button
            v-if="hasPerm('role.remove')"
            type="text"
            size="mini"
            icon="el-icon-delete"
            @click="removeDataById(scope.row)"
          >删除</el-button>
          <router-link :to="'/acl/role/distribution/' + scope.row.roleId">
            <el-button
              v-if="hasPerm('role.acl')"
              type="text"
              size="mini"
              icon="el-icon-info"
            >分配菜单</el-button>
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
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="角色名称" prop="roleName">
          <el-input v-model="form.roleName" placeholder="请输入角色名称" />
        </el-form-item>
        <el-form-item prop="roleKey">
          <span slot="label">
            <el-tooltip content="控制器中定义的权限字符，如：@PreAuthorize(`@ss.hasRole('admin')`)" placement="top">
              <i class="el-icon-question"/>
            </el-tooltip>
            权限字符
          </span>
          <el-input v-model="form.roleCode" placeholder="请输入权限字符" />
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"/>
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
import roleApi from '@/api/acl/role'
export default {
  data() {
    return {
      listLoading: true,
      total: 0,
      page: 1,
      limit: 10,
      searchObj: {},
      multipleSelection: [],
      roleList: [],
      form: {},
      title: '',
      open: false,
      rules: {
        roleName: [
          { required: true, message: '角色名称不能为空', trigger: 'blur' }
        ],
        roleCode: [
          { required: true, message: '权限字符不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '添加角色'
    },
    reset() {
      this.form = {
        roleId: undefined,
        roleName: undefined,
        roleCode: undefined,
        remark: undefined
      }
    },
    submitForm: function() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          if (this.form.roleId != undefined) {
            roleApi.updateRole(this.form).then(response => {
              this.$message({
                message: '修改成功',
                type: 'success'
              })
              this.open = false
              this.fetchData()
            })
          } else {
            roleApi.addRole(this.form).then(response => {
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
      const roleId = row.roleId
      roleApi.getById(roleId).then(response => {
        this.form = response.data.item
        this.open = true
        this.title = '修改角色'
      })
    },
    changeSize(size) {
      this.limit = size
      this.fetchData(1)
    },
    fetchData(page = 1) {
      this.page = page
      roleApi.getPageList(this.page, this.limit, this.searchObj).then((response) => {
        this.roleList = response.data.items
        this.total = response.data.total
        this.listLoading = false
      })
    },
    resetData() {
      this.searchObj = {}
      this.fetchData()
    },
    removeDataById(row) {
      const roleId = row.roleId
      roleApi.getCheckRole(roleId).then(res => {
        if (!res.success) {
          this.$message.warning(res.message)
        } else {
          this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            return roleApi.removeById(roleId)
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
        }
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
        return roleApi.removeRows(idList)
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
    }
  }
}
</script>
