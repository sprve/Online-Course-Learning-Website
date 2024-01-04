<template>
  <div class="app-container">
    <el-form :model="data" label-width="120px">
      <el-form-item label="Banner名称">
        <el-input v-model="data.htmlName"/>
      </el-form-item>
      <el-form-item label="内容">
        <el-input
          :rows="23"
          v-model="data.htmlPage"
          type="textarea"
          show-word-limit
          style="width: 100%" />
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer" style="float:right">
      <el-button v-if="hasPerm('admin.update')" type="primary" @click="updateHtml">保存</el-button>
      <el-button v-if="hasPerm('admin.update')" type="primary" @click="defaultHtml">重置</el-button>
    </div>
  </div>
</template>
<script>
import html from '@/api/edu/banner'
export default {
  data() {
    return {
      data: {
        id: '',
        htmlName: '',
        htmlPage: ''
      }
    }
  },
  created() {
    this.defaultHtml()
  },
  methods: {
    defaultHtml() {
      html.getHtmlInfo(3)
        .then(response => {
          this.data.id = response.data.eduHtml.id
          this.data.htmlName = response.data.eduHtml.htmlName
          this.data.htmlPage = response.data.eduHtml.htmlPage
        })
    },
    updateHtml() {
      this.data.htmlPage = decodeURIComponent(this.data.htmlPage)
      html.saveHtml(this.data)
        .then(response => {
          this.$message({
            type: 'success',
            message: '更新Html成功!'
          })
        })
    }
  }
}
</script>
