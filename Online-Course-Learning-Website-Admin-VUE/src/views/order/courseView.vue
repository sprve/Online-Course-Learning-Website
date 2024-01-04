<template>
  <el-dialog
    :title="title"
    :visible.sync="visible"
    :before-close="handleClose"
    width="60%">
    <el-form :inline="true">
      <el-card >
        <el-divider style="font-weight:bold; background-color:#f4f4f5">课程信息</el-divider>
        <el-row>
          <el-col :span="8"><div>
            <el-form-item label="课程封面:">
              <div class="demo-image">
                <div class="block">
                  <el-image :src="courseData.cover" style="width: 100px; height: 100px"/>
                </div>
              </div>
            </el-form-item>
          </div></el-col>
          <el-col :span="8"><div>
            <el-form-item label="课程编号:">
              <span>{{ courseData.id }}</span>
            </el-form-item>
            <br>
            <el-form-item label="课程名称:">
              <span>{{ courseData.title }}</span>
            </el-form-item>
          </div></el-col>
          <br><br><br><br>
          <el-col :span="8"><div>
            <el-form-item label="课时数:">
              <span>{{ courseData.lessonNum }}</span>
            </el-form-item>
          </div></el-col>
        </el-row>
        <el-row>
          <el-col :span="8"><div>
            <el-form-item label="课程价格:">
              <span >{{ courseData.price }}</span>
            </el-form-item>
          </div></el-col>
          <el-col :span="8"><div>
            <el-form-item label="浏览数量:">
              <span >{{ courseData.viewCount }}</span>
            </el-form-item>
          </div></el-col>
          <el-col :span="8"><div>
            <el-form-item label="添加时间:">
              <span >{{ courseData.gmtCreate }}</span>
            </el-form-item>
          </div></el-col>
        </el-row>
      </el-card>
      <br>
      <el-card >
        <el-divider style="font-weight:bold; background-color:#f4f4f5">简介</el-divider>
        <el-form-item v-html="courseData.description"/>
      </el-card>
    </el-form>
    <el-row style="margin-top:17px; ">
      <el-button style="float:right;margin-left:6px;" size="mini" plain @click="handleClose">取 消</el-button>
    </el-row>
  </el-dialog>
</template>
<script>
export default {
  props: {
    courseData: {
      type: Object,
      default: () => {}
    },
    visible: {
      type: Boolean,
      default: false
    },
    title: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
    }
  },
  methods: {
    handleClose(done) {
      this.$emit('close-callback')
    }
  }
}
</script>
