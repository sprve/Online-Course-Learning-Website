<template>
  <el-dialog
    :title="title"
    :visible.sync="visible"
    :before-close="handleClose"
    width="60%">
    <el-form :inline="true">
      <el-card >
        <el-divider style="font-weight:bold; background-color:#f4f4f5">讲师个人信息</el-divider>
        <el-row>
          <el-col :span="8"><div>
            <el-form-item label="头像:">
              <div class="demo-image">
                <div class="block">
                  <el-image :src="tearcherData.avatar" style="width: 100px; height: 100px"/>
                </div>
              </div>
            </el-form-item>
          </div></el-col>
          <el-col :span="8"><div>
            <el-form-item label="讲师编号:">
              <span >{{ tearcherData.id }}</span>
            </el-form-item>
            <br>
            <el-form-item label="讲师名称:">
              <span >{{ tearcherData.name }}</span>
            </el-form-item>
          </div></el-col>
          <br><br><br><br>
        </el-row>
        <el-row>
          <el-col :span="8"><div>
            <el-form-item label="手机号:">
              <span >{{ tearcherData.phonenumber }}</span>
            </el-form-item>
          </div></el-col>
          <el-col :span="8"><div>
            <el-form-item label="邮箱:">
              <span >{{ tearcherData.email }}</span>
            </el-form-item>
          </div></el-col>
          <el-col :span="8"><div>
            <el-form-item label="职位:">
              <template slot-scope="scope">
                <span :class="textClass(tearcherData.level)">{{ textLevel[tearcherData.level] }}</span>
              </template>
            </el-form-item>
          </div></el-col>
        </el-row>
        <el-row>
          <el-col :span="8"><div>
            <el-form-item label="注册时间:">
              <span >{{ tearcherData.gmtCreate }}</span>
            </el-form-item>
          </div></el-col>
        </el-row>
      </el-card>
      <br>
      <el-card >
        <el-divider style="font-weight:bold; background-color:#f4f4f5">简介</el-divider>
        <el-form-item v-html="tearcherData.intro"/>
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
    tearcherData: {
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
      textLevel: {
        1: '高级讲师',
        2: '首席讲师'
      }
    }
  },
  methods: {
    textClass(isFree) {
      return {
        c_red: isFree === 0,
        c_blue: isFree === 1
      }
    },
    handleClose(done) {
      this.$emit('close-callback')
    }
  }
}
</script>
