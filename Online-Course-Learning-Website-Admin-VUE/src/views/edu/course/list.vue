<template>
  <div class="app-container">
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="课程名称" prop="title">
        <el-input v-model="courseQuery.title" placeholder="请输入课程名称"/>
      </el-form-item>
      <el-form-item label="课程状态" prop="status">
        <el-select v-model="courseQuery.status" clearable placeholder="请选择课程状态">
          <el-option value="Normal" label="已发布"/>
          <el-option value="Draft" label="未发布"/>
        </el-select>
      </el-form-item>
      <el-button type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
      <el-button type="default" @click="resetData()">清空</el-button>
    </el-form>
    <el-table
      :data="list"
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
      <el-table-column prop="title" label="课程名称"/>
      <el-table-column prop="cover" label="课程封面" width="250px">
        <template slot-scope="scope">
          <img :src="scope.row.cover" :alt="scope.row.title" width="230">
        </template>
      </el-table-column>
      <el-table-column prop="lessonNum" label="课时数"/>
      <el-table-column prop="price" label="课程价格"/>
      <el-table-column label="课程状态">
        <template slot-scope="scope">
          <el-tag type="success">{{ scope.row.status === 'Normal' ? '已发布' : '未发布' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="gmtCreate" label="添加时间" />
      <el-table-column prop="viewCount" label="浏览数量" />
      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <router-link :to="'/course/info/'+scope.row.id">
            <el-button v-if="hasPerm('course.update')" type="primary" size="mini" icon="el-icon-edit">编辑课程基本信息</el-button>
          </router-link>
          <router-link :to="'/course/chapter/'+scope.row.id">
            <el-button v-if="hasPerm('chapter.update')" type="primary" size="mini" icon="el-icon-edit">编辑课程大纲息</el-button>
          </router-link>
          <el-button v-if="hasPerm('course.remove')" type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)">删除课程信息
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      :current-page="page"
      :page-size="limit"
      :total="total"
      style="padding: 30px 0; text-align: center;"
      layout="total, prev, pager, next, jumper"
      @current-change="getList"
    />
  </div>
</template>
<script>
import course from '@/api/edu/course'
export default {
  data() {
    return {
      list: null,
      page: 1,
      limit: 10,
      total: 0,
      courseQuery: {}
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList(page = 1) {
      this.page = page
      course.pageCourseCondition(this.page, this.limit, this.courseQuery)
        .then(response => {
          this.list = response.data.rows
          this.total = response.data.total
        })
    },
    removeDataById(id) {
      this.$confirm('此操作将永久删除课程, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        course.deleteCourse(id)
          .then(response => {
            this.$message({
              type: 'success',
              message: '删除课程成功!'
            })
            this.getList()
          })
      })
    },
    resetData() {
      this.courseQuery = {}
      this.getList()
    }
  }
}
</script>
