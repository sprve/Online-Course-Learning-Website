<template>
  <el-dialog
    :title="title"
    :visible.sync="visible"
    :before-close="handleClose"
    width="60%"
  >
    <el-form :inline="true">
      <el-card>
        <el-divider
          style="font-weight: bold; background-color: #f4f4f5"
        >用户个人信息</el-divider
        >
        <el-row>
          <el-col
            :span="8"
          ><div>
            <el-form-item label="头像:">
              <div class="demo-image">
                <div class="block">
                  <el-image
                    :src="studentData.avatar"
                    style="width: 100px; height: 100px"
                  />
                </div>
              </div>
          </el-form-item></div
          ></el-col>
          <el-col
            :span="8"
          ><div>
            <el-form-item label="用户编号:">
              <span>{{ studentData.id }}</span>
            </el-form-item>
            <br >
            <el-form-item label="用户昵称:">
              <span>{{ studentData.nickname }}</span>
            </el-form-item>
          </div></el-col
          >
          <br ><br ><br ><br >
          <el-col
            :span="8"
          ><div>
            <el-form-item label="手机号:">
              <span>{{ studentData.mobile }}</span>
            </el-form-item>
          </div></el-col
          >
        </el-row>
        <el-row>
          <el-col
            :span="8"
          ><div>
            <el-form-item label="年龄:">
              <span>{{ studentData.age }}</span>
            </el-form-item>
          </div></el-col
          >
          <el-col
            :span="8"
          ><div>
            <el-form-item label="性别:">
              <span>{{ studentData.sex }}</span>
            </el-form-item>
          </div></el-col
          >
        </el-row>
        <el-row>
          <el-col
            :span="8"
          ><div>
            <el-form-item label="注册时间:">
              <span>{{ studentData.gmtCreate }}</span>
            </el-form-item>
          </div></el-col
          >
        </el-row>
      </el-card>
    </el-form>
    <el-row style="margin-top: 17px">
      <el-button
        style="float: right; margin-left: 6px"
        size="mini"
        plain
        @click="handleClose"
      >取 消</el-button
      >
    </el-row>
  </el-dialog>
</template>
<script>
export default {
  props: {
    studentData: {
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
