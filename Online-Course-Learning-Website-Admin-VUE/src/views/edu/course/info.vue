<template>
  <div class="app-container">
    <h2 style="text-align: center;">发布新课程</h2>
    <el-steps :active="1" process-status="wait" align-center style="margin-bottom: 40px;">
      <el-step title="填写课程基本信息"/>
      <el-step title="创建课程大纲"/>
      <el-step title="最终发布"/>
    </el-steps>
    <el-form label-width="120px">
      <el-form-item label="课程标题">
        <el-input v-model="courseInfo.title" placeholder=" 示例：机器学习项目课：从基础到搭建项目视频课程。专业名称注意大小写"/>
      </el-form-item>
      <el-form-item label="课程分类">
        <el-select
          v-model="courseInfo.subjectParentId"
          placeholder="一级分类"
          @change="subjectLevelOneChanged">
          <el-option
            v-for="subject in subjectOneList"
            :key="subject.id"
            :label="subject.title"
            :value="subject.id"/>
        </el-select>
        <el-select v-model="courseInfo.subjectId" placeholder="二级分类">
          <el-option
            v-for="subject in subjectTwoList"
            :key="subject.id"
            :label="subject.title"
            :value="subject.id"/>
        </el-select>
      </el-form-item>
      <el-form-item label="课程讲师">
        <el-select
          v-model="courseInfo.teacherId"
          placeholder="请选择">
          <el-option
            v-for="teacher in teacherList"
            :key="teacher.id"
            :label="teacher.name"
            :value="teacher.id"/>
        </el-select>
      </el-form-item>
      <el-form-item label="总课时">
        <el-input-number :min="0" v-model="courseInfo.lessonNum" controls-position="right" placeholder="请填写课程的总课时数"/>
      </el-form-item>
      <el-form-item label="课程简介">
        <tinymce :height="300" v-model="courseInfo.description"/>
      </el-form-item>
      <el-form-item label="课程封面">
        <el-upload
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload"
          :action="BASE_API+'/eduoss/fileoss'"
          class="avatar-uploader">
          <img :src="courseInfo.cover">
        </el-upload>
      </el-form-item>
      <el-form-item label="课程价格">
        <el-input-number :min="0" v-model="courseInfo.price" controls-position="right" placeholder="免费课程请设置为0元"/> 元
      </el-form-item>
      <el-form-item>
        <el-button v-if="hasPerm('course.upload')" :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存并下一步</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import course from '@/api/edu/course'
import subject from '@/api/edu/subject'
import Tinymce from '@/components/Tinymce'
export default {
  components: { Tinymce },
  data() {
    return {
      saveBtnDisabled: false,
      courseInfo: {
        title: '',
        subjectId: '',
        subjectParentId: '',
        teacherId: '',
        lessonNum: 0,
        description: '',
        cover: '/static/01.jpg',
        price: 0
      },
      courseId: '',
      BASE_API: process.env.BASE_API,
      OSS_PATH: process.env.OSS_PATH,
      teacherList: [],
      subjectOneList: [],
      subjectTwoList: []
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
    init() {
      if (this.$route.params && this.$route.params.id) {
        this.courseId = this.$route.params.id
        this.getInfo()
      } else {
        this.getListTeacher()
        this.getOneSubject()
        this.courseInfo.title = ''
        this.courseInfo.subjectId = ''
        this.courseInfo.subjectParentId = ''
        this.courseInfo.teacherId = ''
        this.courseInfo.lessonNum = ''
        this.courseInfo.description = ''
        this.courseInfo.cover = this.OSS_PATH + '/pictures/load.png'
        this.courseInfo.price = ''
      }
    },
    getInfo() {
      course.getCourseInfoId(this.courseId)
        .then(response => {
          this.courseInfo = response.data.courseInfoVo
          subject.getSubjectList()
            .then(response => {
              this.subjectOneList = response.data.list
              for (var i = 0; i < this.subjectOneList.length; i++) {
                var oneSubject = this.subjectOneList[i]
                if (this.courseInfo.subjectParentId == oneSubject.id) {
                  this.subjectTwoList = oneSubject.children
                }
              }
            })
          this.getListTeacher()
        })
    },
    handleAvatarSuccess(res, file) {
      this.courseInfo.cover = res.data.url
    },
    beforeAvatarUpload(file) {
      const isLt2M = file.size / 1024 / 1024 < 2
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!')
      }
      return isLt2M
    },
    subjectLevelOneChanged(value) {
      for (var i = 0; i < this.subjectOneList.length; i++) {
        var oneSubject = this.subjectOneList[i]
        if (value === oneSubject.id) {
          this.subjectTwoList = oneSubject.children
          this.courseInfo.subjectId = ''
        }
      }
    },
    getOneSubject() {
      subject.getSubjectList()
        .then(response => {
          this.subjectOneList = response.data.list
        })
    },
    getListTeacher() {
      course.getListTeacher()
        .then(response => {
          this.teacherList = response.data.items
        })
    },
    saveCourse() {
      course.addCourseInfo(this.courseInfo)
        .then(response => {
          this.$message({
            type: 'success',
            message: '添加课程信息成功!'
          })
          this.$router.push({ path: '/course/chapter/' + response.data.courseId })
        })
    },
    updateCourse() {
      course.updateCourseInfo(this.courseInfo)
        .then(response => {
          this.$message({
            type: 'success',
            message: '修改课程信息成功!'
          })
          this.$router.push({ path: '/course/chapter/' + this.courseId })
        })
    },
    saveOrUpdate() {
      if (!this.courseInfo.id) {
        this.saveCourse()
      } else {
        this.updateCourse()
      }
    }
  }
}
</script>
<style scoped>
.tinymce-container {
  line-height: 29px;
}
</style>
