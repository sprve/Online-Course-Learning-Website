<template>
  <div class="app-container">
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="课程ID" prop="courseId">
        <el-input v-model="commentQuery.courseId" placeholder="请输入课程Id"/>
      </el-form-item>
      <el-form-item label="用户昵称" prop="nickName">
        <el-input v-model="commentQuery.nickName" placeholder="请输入用户昵称"/>
      </el-form-item>
      <el-button type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
      <el-button type="default" @click="resetData()">清空</el-button>
    </el-form>
    <el-table
      :data="list"
      border
      fit
      highlight-current-row>
      <el-table-column prop="courseId" label="课程Id"/>
      <el-table-column prop="nickname" label="用户名"/>
      <el-table-column prop="content" label="评论"/>
      <el-table-column prop="gmtCreate" label="添加时间" />
      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <el-button v-if="hasPerm('comment.remove')" type="danger" size="mini" icon="el-icon-delete" @click="removeComment(scope.row.id)">删除</el-button>
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
import comment from '@/api/edu/comment'
export default {
  data() {
    return {
      list: null,
      page: 1,
      limit: 10,
      total: 0,
      commentQuery: {}
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList(page = 1) {
      this.page = page
      comment.pageCommentCondition(this.page, this.limit, this.commentQuery)
        .then(response => {
          this.list = response.data.items
          this.total = response.data.total
        })
    },
    removeComment(id) {
      this.$confirm('此操作将永久删除评论, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        comment.deleteCommentInfo(id)
          .then(response => {
            this.$message({
              type: 'success',
              message: '删除评论成功!'
            })
            this.getList()
          })
      })
    },
    resetData() {
      this.commentQuery = {}
      this.getList()
    }
  }
}
</script>
