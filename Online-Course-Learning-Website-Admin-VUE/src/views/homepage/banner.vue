<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          v-if="hasPerm('banner.add')"
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="addBanner"
        >新增</el-button>
      </el-col>
    </el-row>
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
      <el-table-column prop="title" label="轮播图名称"/>
      <el-table-column prop="imageUrl" label="轮播图" width="250px">
        <template slot-scope="scope">
          <img :src="scope.row.imageUrl" :alt="scope.row.title" width="230">
        </template>
      </el-table-column>
      <el-table-column prop="gmtCreate" label="添加时间" />
      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <el-button v-if="hasPerm('banner.update')" type="primary" size="mini" icon="el-icon-edit" @click="updateBanner(scope.row.id)">修改</el-button>
          <el-button v-if="hasPerm('banner.remove')" type="danger" size="mini" icon="el-icon-delete" @click="removeBanner(scope.row.id)">删除</el-button>
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
    <el-dialog :visible.sync="dialogVideoFormVisible" title="添加Banner">
      <el-form :model="data" label-width="120px">
        <el-form-item label="Banner名称">
          <el-input v-model="data.title"/>
        </el-form-item>
        <el-form-item label="Banner图片">
          <el-upload
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload"
            :action="BASE_API+'/eduoss/fileoss'"
            class="avatar-uploader">
            <img :src="data.imageUrl" width="140px">
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVideoFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveOrUpdateBanner">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import banner from '@/api/edu/banner'
export default {
  data() {
    return {
      list: null,
      page: 1,
      limit: 10,
      total: 0,
      data: {
        title: '',
        id: '',
        imageUrl: ''
      },
      BASE_API: process.env.BASE_API,
      OSS_PATH: process.env.OSS_PATH,
      dialogVideoFormVisible: false
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList(page = 1) {
      this.page = page
      banner.pageBannerCondition(this.page, this.limit)
        .then(response => {
          this.list = response.data.items
          this.total = response.data.total
        })
    },
    removeBanner(id) {
      this.$confirm('此操作将永久删除Banner, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        banner.deleteBannerInfo(id)
          .then(response => {
            this.$message({
              type: 'success',
              message: '删除Banner成功!'
            })
            this.getList()
          })
      })
    },
    handleAvatarSuccess(res, file) {
      this.data.imageUrl = res.data.url
    },
    beforeAvatarUpload(file) {
      const isLt2M = file.size / 1024 / 1024 < 2
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!')
      }
      return isLt2M
    },
    addBanner() {
      this.data.id = ''
      this.data.title = ''
      this.data.imageUrl = this.OSS_PATH + '/pictures/load.png'
      this.dialogVideoFormVisible = true
    },
    updateBanner(id) {
      banner.getBannerInfo(id)
        .then(response => {
          this.data.title = response.data.item.title
          this.data.imageUrl = response.data.item.imageUrl
          this.data.id = response.data.item.id
          this.dialogVideoFormVisible = true
        })
    },
    saveOrUpdateBanner() {
      if (this.data.id.length == 0) {
        banner.addBannerInfo(this.data)
          .then(response => {
            this.$message({
              type: 'success',
              message: '添加Banner成功!'
            })
            this.getList()
          })
      } else {
        banner.updateBannerInfo(this.data)
          .then(response => {
            this.$message({
              type: 'success',
              message: '更新Banner成功!'
            })
            this.getList()
          })
      }
      this.dialogVideoFormVisible = false
    }
  }
}
</script>
