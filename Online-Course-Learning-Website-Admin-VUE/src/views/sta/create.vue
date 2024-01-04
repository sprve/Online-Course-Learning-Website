<template>
  <div class="app-container">
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="日期">
        <el-date-picker
          v-model="day"
          type="date"
          placeholder="选择要统计的日期"
          value-format="yyyy-MM-dd" />
      </el-form-item>
      <el-button
        v-if="hasPerm('daily.list')"
        :disabled="btnDisabled"
        type="primary"
        @click="create()">生成</el-button>
    </el-form>
  </div>
</template>
<script>
import sta from '@/api/sta'
export default {
  data() {
    return {
      day: '',
      btnDisabled: false
    }
  },
  methods: {
    create() {
      sta.createStaData(this.day)
        .then(response => {
          this.$message({
            type: 'success',
            message: '生成数据成功!'
          })
          this.$router.push({ path: '/sta/show' })
        })
    }
  }
}
</script>
