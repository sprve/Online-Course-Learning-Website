<template>
  <div class="app-container">
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="讲师名" prop="name">
        <el-input v-model="teacherQuery.name" placeholder="请输入讲师名"/>
      </el-form-item>
      <el-form-item label="讲师头衔" prop="level">
        <el-select v-model="teacherQuery.level" clearable placeholder="请选择讲师头衔">
          <el-option :value="1" label="高级讲师"/>
          <el-option :value="2" label="首席讲师"/>
        </el-select>
      </el-form-item>
      <el-form-item label="注册时间">
        <el-date-picker
          v-model="teacherQuery.begin"
          type="datetime"
          placeholder="选择开始时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>
      <el-form-item>
        <el-date-picker
          v-model="teacherQuery.end"
          type="datetime"
          placeholder="选择截止时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
        <el-button type="default" @click="resetData()">清空</el-button>
      </el-form-item>
    </el-form>
    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="数据加载中"
      border
      fit
      highlight-current-row>
      <el-table-column
        label="序号"
        width="70"
        align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column prop="name" label="名称" width="120">
        <template slot-scope="scope">
          <el-button type="text" @click="handleView(scope.row.id)">{{ scope.row.name }}</el-button>
        </template>
      </el-table-column>
      <el-table-column prop="avatar" label="头像" width="150px">
        <template slot-scope="scope">
          <img :src="scope.row.avatar" :alt="scope.row.name" width="120">
        </template>
      </el-table-column>
      <el-table-column prop="career" label="资历" />
      <el-table-column label="头衔" width="120" align="center">
        <template slot-scope="scope">
          <el-tag type="success">
            {{ scope.row.level===1?'高级讲师':'首席讲师' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="gmtCreate" label="添加时间" width="160"/>
      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <el-button v-if="hasPerm('teacher.remove')" type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)">删除</el-button>
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
    <view-lecturer :visible="ctrl.viewVisible" :form-data="formData" :title="ctrl.dialogTitle" @close-callback="closeCllback"/>
  </div>
</template>
<script>
import teacher from '@/api/edu/teacher'
import ViewLecturer from './view'
export default {
  components: { ViewLecturer },
  data() {
    return {
      listLoading: false,
      list: null,
      page: 1,
      limit: 10,
      total: 0,
      teacherQuery: {},
      formData: {},
      ctrl: {
        loading: false,
        viewVisible: false
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    closeCllback() {
      this.ctrl.viewVisible = false
    },
    handleView(id) {
      teacher.getTeacherInfo(id).then(response => {
        this.formData = response.data.teacher
        this.teacher = response.data.teacher
        this.ctrl.dialogTitle = response.data.teacher.name + ' —— 查看详情'
        this.ctrl.viewVisible = true
      })
    },
    getList(page = 1) {
      this.page = page
      teacher.pageTeacherList(this.page, this.limit, this.teacherQuery).then(response => {
        this.list = response.data.rows
        this.total = response.data.total
      })
        .catch(error => {})
    },
    resetData() {
      this.teacherQuery = {}
      this.getList()
    },
    removeDataById(id) {
      this.$confirm('此操作将永久删除该讲师, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        teacher.deleteTeacher(id)
          .then(response => {
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
