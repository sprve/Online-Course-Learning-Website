<template>
  <div class="app-container">
    <el-form ref="teacher" :model="teacher" :rules="rules" label-width="120px">
      <el-alert :closable="false" title="提示：不上传则使用系统默认头像" type="warning"/><br>
      <el-row>
        <el-col :span="12">
          <el-form-item label="讲师名称" prop="name">
            <el-input v-model="teacher.name"/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="手机号" prop="phonenumber">
            <el-input v-model="teacher.phonenumber"/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="邮箱" prop="email">
            <el-input v-model="teacher.email"/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item label="讲师头衔">
        <el-select v-model="teacher.level" clearable placeholder="请选择">
          <el-option :value="1" label="高级讲师"/>
          <el-option :value="2" label="首席讲师"/>
        </el-select>
      </el-form-item>
      <el-row>
        <el-col :span="12">
          <el-form-item label="讲师资历">
            <el-input v-model="teacher.career"/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="讲师简介">
            <el-input v-model="teacher.intro" :rows="10" type="textarea"/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item label="讲师头像">
        <pan-thumb :image="String(teacher.avatar)"/>
        <el-button type="primary" icon="el-icon-upload" @click="imagecropperShow=true">更换头像</el-button>
        <image-cropper
          v-show="imagecropperShow"
          :width="300"
          :height="300"
          :key="imagecropperKey"
          :url="BASE_API+'/eduoss/fileoss'"
          field="file"
          @close="close"
          @crop-upload-success="cropSuccess"/>
      </el-form-item>
      <el-form-item>
        <el-button v-if="hasPerm('teacher.add')" :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import teacherApi from '@/api/edu/teacher'
import ImageCropper from '@/components/ImageCropper'
import PanThumb from '@/components/PanThumb'
export default {
  components: { ImageCropper, PanThumb },
  data() {
    return {
      OSS_PATH: process.env.OSS_PATH,
      teacher: {
        name: '',
        level: '',
        career: '',
        intro: '',
        phonenumber: '',
        email: '',
        avatar: this.OSS_PATH + 'pictures/teacher.png'
      },
      imagecropperShow: false,
      imagecropperKey: 0,
      BASE_API: process.env.BASE_API,
      saveBtnDisabled: false,
      rules: {
        name: [
          { required: true, message: '用户名称不能为空', trigger: 'blur' },
          {
            min: 2,
            max: 20,
            message: '用户名称长度必须介于 2 和 20 之间',
            trigger: 'blur'
          }
        ],
        phonenumber: [
          { required: true, message: '用户昵称不能为空', trigger: 'blur' }
        ],
        email: [
          {
            type: 'email',
            message: '请输入正确的邮箱地址',
            trigger: ['blur', 'change']
          }
        ],
        phonenumber: [
          {
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: '请输入正确的手机号码',
            trigger: 'blur'
          }
        ]
      }
    }
  },
  watch: {
    $route(to, from) {
      this.init()
    }
  },
  created() {
    this.init()
  },
  methods: {
    close() {
      this.imagecropperShow = false
      this.imagecropperKey = this.imagecropperKey + 1
    },
    cropSuccess(data) {
      this.imagecropperShow = false
      this.teacher.avatar = data.url
      this.imagecropperKey = this.imagecropperKey + 1
    },
    init() {
      if (this.$route.params && this.$route.params.id) {
        const id = this.$route.params.id
        this.getInfo(id)
      } else {
        this.reset()
      }
    },
    reset() {
      this.teacher = {
        name: '',
        level: '',
        career: '',
        intro: '',
        phonenumber: '',
        email: '',
        avatar: this.OSS_PATH + '/pictures/teacher.png'
      }
    },
    getInfo(id) {
      teacherApi.getTeacherInfo(id).then(response => {
        this.teacher = response.data.teacher
      })
    },
    saveOrUpdate() {
      this.$refs['teacher'].validate((valid) => {
        if (valid) {
          if (!this.teacher.id) {
            this.saveTeacher()
          } else {
            this.updateTeacher()
          }
        }
      })
    },
    updateTeacher() {
      teacherApi.updateTeacherInfo(this.teacher).then(response => {
        this.$message({
          type: 'success',
          message: '修改成功!'
        })
        this.$router.push({ path: '/teacher/audit' })
      })
    },
    saveTeacher() {
      teacherApi.addTeacher(this.teacher).then(response => {
        this.$message({
          type: 'success',
          message: '添加成功!'
        })
        this.$router.push({ path: '/teacher/audit' })
      }).catch(error => {})
    }
  }
}
</script>
