<template>
  <div class="app-container">
    <el-form label-width="120px">
      <el-form-item label="信息描述">
        <el-tag type="info">excel模版说明</el-tag>
        <el-tag>
          <i class="el-icon-download"/>
          <a :href="OSS_PATH + '/resource/%E8%AF%BE%E7%A8%8B%E5%88%86%E7%B1%BB%E6%A8%A1%E6%9D%BF.xls'">点击下载模版</a>
        </el-tag>
      </el-form-item>
      <el-form-item label="选择Excel">
        <el-upload
          ref="upload"
          :auto-upload="false"
          :on-success="fileUploadSuccess"
          :on-error="fileUploadError"
          :disabled="importBtnDisabled"
          :limit="1"
          :action="BASE_API+'/eduservice/subject/addSubject'"
          name="file"
          accept="application/vnd.ms-excel">
          <el-button v-if="hasPerm('subject.upload')" slot="trigger" size="small" type="primary">选取文件</el-button>
          <el-button
            v-if="hasPerm('subject.upload')"
            :loading="loading"
            style="margin-left: 10px;"
            size="small"
            type="success"
            @click="submitUpload">{{ fileUploadBtnText }}
          </el-button>
        </el-upload>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
export default {
  data() {
    return {
      BASE_API: process.env.BASE_API,
      OSS_PATH: process.env.OSS_PATH,
      fileUploadBtnText: '上传到服务器',
      importBtnDisabled: false,
      loading: false
    }
  },
  methods: {
    submitUpload() {
      this.importBtnDisabled = true
      this.loading = true
      this.$refs.upload.submit()
    },
    fileUploadSuccess(response) {
      this.loading = false
      this.$message({
        type: 'success',
        message: '添加课程分类成功'
      })
      this.$router.push({ path: '/course/subject/list' })
    },
    fileUploadError() {
      this.loading = false
      this.$message({
        type: 'error',
        message: '添加课程分类失败'
      })
    }
  }
}
</script>
