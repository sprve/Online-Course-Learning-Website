<template>
  <div class="app-container">
    <el-input
      v-model="filterText"
      placeholder="支持模糊查询"
      clearable
      size="small"
      prefix-icon="el-icon-search"
      style="margin-bottom: 30px"/>
    <el-table
      :data="menuList"
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
      style="width: 100%;margin-bottom: 20px;"
      row-key="id"
      border
      default-expand-all>
      <el-table-column
        prop="name"
        label="名称"
        sortable
        width="180"/>
      <el-table-column
        prop="path"
        label="访问路径"
        sortable
        width="180"/>
      <el-table-column
        prop="component"
        label="组件路径"
        sortable
        width="180"/>
      <el-table-column
        prop="permissionValue"
        label="权限值"/>
      <el-table-column
        label="操作">
        <template slot-scope="scope">
          <el-button
            v-if="(scope.row.level == 1 || scope.row.level == 2) && hasPerm('permission.add')"
            type="text"
            size="mini"
            @click="() => {dialogFormVisible = true, menu.pid = scope.row.id}">添加菜单
          </el-button>
          <el-button
            v-if="scope.row.level == 3 && hasPerm('permission.add')"
            type="text"
            size="mini"
            @click="() => {dialogPermissionVisible = true, permission.pid = scope.row.id}">添加功能
          </el-button>
          <el-button
            v-if="scope.row.level == 4 && hasPerm('permission.update')"
            type="text"
            size="mini"
            @click="() => updateFunction(scope.row)">修改功能
          </el-button>
          <el-button
            v-if="scope.row.level != 4 && hasPerm('permission.update')"
            type="text"
            size="mini"
            @click="() => getById(scope.row)">修改
          </el-button>
          <el-button
            v-if="hasPerm('permission.remove')"
            type="text"
            size="mini"
            @click="() => remove(scope.row)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog :visible.sync="dialogFormVisible" :title="dialogFormValue">
      <el-form ref="menu" :model="menu" :rules="menuValidateRules" label-width="120px">
        <el-form-item label="菜单名称" prop="name">
          <el-input v-model="menu.name"/>
        </el-form-item>
        <el-form-item label="访问路径" prop="path">
          <el-input v-model="menu.path"/>
        </el-form-item>
        <el-form-item label="组件路径" prop="component">
          <el-input v-model="menu.component"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="restData()">取 消</el-button>
        <el-button type="primary" @click="append()">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog :visible.sync="dialogPermissionVisible" title="添加功能">
      <el-form ref="permission" :model="permission" :rules="permissionValidateRules" label-width="120px">
        <el-form-item label="功能名称" prop="name">
          <el-input v-model="permission.name"/>
        </el-form-item>
        <el-form-item label="访问路径">
          <el-input v-model="permission.path"/>
        </el-form-item>
        <el-form-item label="组件路径">
          <el-input v-model="permission.component"/>
        </el-form-item>
        <el-form-item label="功能权限值" prop="permissionValue">
          <el-input v-model="permission.permissionValue"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="restData()">取 消</el-button>
        <el-button type="primary" @click="appendPermission()">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import menu from '@/api/acl/menu'
const menuForm = {
  name: '',
  pid: 0,
  path: '',
  component: '',
  type: '1'
}
const perForm = {
  permissionValue: '',
  type: '2',
  name: '',
  path: '',
  component: '',
  pid: 0
}
export default {
  data() {
    return {
      filterText: '',
      menuList: [],
      defaultProps: {
        children: 'children',
        label: 'name'
      },
      dialogFormValue: '添加菜单',
      dialogFormVisible: false,
      dialogPermissionVisible: false,
      menu: menuForm,
      permission: perForm,
      menuValidateRules: {
        name: [{ required: true, trigger: 'blur', message: '菜单名必须输入' }],
        path: [{ required: true, trigger: 'blur', message: '菜单路径必须输入' }],
        component: [{ required: true, trigger: 'blur', message: '组件名称必须输入' }]
      },
      permissionValidateRules: {
        name: [{ required: true, trigger: 'blur', message: '功能名称必须输入' }],
        permissionValue: [{ required: true, trigger: 'blur', message: '功能权限值必须输入' }]
      }
    }
  },
  watch: {
    filterText(val) {
      this.$refs.menuTree.filter(val)
    }
  },
  created() {
    this.fetchNodeList()
  },
  methods: {
    fetchNodeList() {
      menu.getNestedTreeList().then(response => {
        if (response.success === true) {
          this.menuList = response.data.children
        }
      })
    },
    filterNode(value, data) {
      if (!value) return true
      return data.title.toLowerCase().indexOf(value.toLowerCase()) !== -1
    },
    remove(data) {
      this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        return menu.removeById(data.id)
      }).then(() => {
        this.fetchNodeList()
        this.$message({
          type: 'success',
          message: '删除成功!'
        })
      }).catch((response) => {
        if (response === 'cancel') {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        } else {
          this.$message({
            type: 'error',
            message: '删除失败'
          })
        }
      })
    },
    appendPermission() {
      this.$refs.permission.validate(valid => {
        if (valid) {
          if (this.permission.id) {
            this.update(this.permission)
          } else {
            menu.saveLevelOne(this.permission).then(response => {
              this.dialogPermissionVisible = false
              this.$message({
                type: 'success',
                message: '添加功能成功'
              })
              this.fetchNodeList()
              this.menu = { ...menuForm }
              this.permission = { ...perForm }
            })
          }
        }
      })
    },
    appendLevelOne() {
      menu.saveLevelOne(this.menu)
        .then(response => {
          this.dialogFormVisible = false
          this.$message({
            type: 'success',
            message: '添加一级菜单成功'
          })
          this.fetchNodeList()
          this.menu = { ...menuForm }
          this.permission = { ...perForm }
        })
        .catch(response => {
          this.dialogFormVisible = false
          this.$message({
            type: 'error',
            message: '添加一级菜单失败'
          })
          this.menu = { ...menuForm }
          this.permission = { ...perForm }
        })
    },
    append() {
      this.$refs.menu.validate(valid => {
        if (valid) {
          if (!this.menu.id) {
            if (this.menu.pid == 0) {
              this.appendLevelOne()
            } else {
              this.appendLevelTwo()
            }
          } else {
            this.update(this.menu)
          }
        }
      })
    },
    update(obj) {
      menu.update(obj).then(response => {
        this.dialogFormVisible = false
        this.$message({
          type: 'success',
          message: '修改成功'
        })
        this.fetchNodeList()
        this.restData()
      })
    },
    appendLevelTwo() {
      menu.saveLevelOne(this.menu)
        .then(response => {
          this.dialogFormVisible = false
          this.$message({
            type: 'success',
            message: '添加二级分类成功'
          })
          this.fetchNodeList()
          this.menu = { ...menuForm }
          this.permission = { ...perForm }
        })
        .catch(response => {
          this.dialogFormVisible = false
          this.$message({
            type: 'error',
            message: '添加二级分类失败'
          })
          this.menu = { ...menuForm }
          this.permission = { ...perForm }
        })
    },
    getById(data) {
      this.dialogFormVisible = true
      this.menu = data
    },
    updateFunction(data) {
      this.dialogPermissionVisible = true
      this.permission = data
    },
    restData() {
      this.dialogPermissionVisible = false
      this.dialogFormVisible = false
      this.menu = { ...menuForm }
      this.permission = { ...perForm }
    }
  }
}
</script>
