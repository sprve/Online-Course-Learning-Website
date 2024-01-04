<template>
  <div class="app-container">
    <el-col :span="7">
      <el-input
        v-model="filterText"
        placeholder="支持模糊查询"
        clearable
        size="small"
        prefix-icon="el-icon-search"
        style="margin-bottom: 30px"
      />
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button
            v-if="hasPerm('subject.add')"
            type="primary"
            plain
            icon="el-icon-plus"
            size="mini"
            @click="append()"
          >新增</el-button>
        </el-col>
      </el-row>
      <el-tree
        ref="tree2"
        :data="data2"
        :props="defaultProps"
        :expand-on-click-node="false"
        :filter-node-method="filterNode"
        node-key="id"
        default-expand-all
        @node-click="handleNodeClick"
      >
        <span slot-scope="{ node, data }" class="custom-tree-node">
          <span>{{ node.label }}</span>
          <span>
            <el-button v-if="hasPerm('subject.add') && data.children!=null" type="text" size="mini" @click="append(data.id) ">
              添加
            </el-button>
            <el-button
              v-if="hasPerm('subject.remove')"
              type="text"
              size="mini"
              @click="removeForm(data.id,data.children.id)"
            >
              删除
            </el-button>
          </span>
        </span>
      </el-tree>
    </el-col>
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="菜单名" prop="id">
          <el-input v-model="form.data" placeholder="请输入菜单名" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="addForm()">确 定</el-button>
        <el-button @click="cancel()">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import subject from '@/api/edu/subject'
export default {
  data() {
    return {
      filterText: '',
      data2: [],
      defaultProps: {
        children: 'children',
        label: 'title'
      },
      open: false,
      form: {
        id: '',
        data: ''
      }
    }
  },
  watch: {
    filterText(val) {
      this.$refs.tree2.filter(val)
    }
  },
  created() {
    this.getAllSubjectList()
  },
  methods: {
    getAllSubjectList() {
      subject.getSubjectList().then((response) => {
        this.data2 = response.data.list
      })
    },
    filterNode(value, data) {
      if (!value) return true
      return data.title.toLowerCase().indexOf(value.toLowerCase()) !== -1
    },
    handleNodeClick(data) {
    },
    append(data) {
      this.open = true
      this.form.id = data
      this.form.data = ''
    },
    cancel() {
      this.open = false
      this.form.id = ''
      this.form.data = ''
    },
    addForm() {
      if (this.form.id == null) {
        this.form.id = ''
      }
      subject.addOneSubject(this.form).then((response) => {
        this.$message({
          type: 'success',
          message: '修改成功!'
        })
        this.getAllSubjectList()
      })
      this.open = false
    },
    removeForm(id, childrenId) {
      this.form.id = id
      if (childrenId == null) {
        this.form.data = ''
      }
      this.form.data = childrenId
      subject.deleteOneSubject(this.form).then((response) => {
        this.$message({
          type: 'success',
          message: '删除成功!'
        })
        this.getAllSubjectList()
      })
    }
  }
}
</script>
<style>
.custom-tree-node {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 14px;
  padding-right: 8px;
}
</style>
