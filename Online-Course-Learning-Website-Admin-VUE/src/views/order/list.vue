<template>
  <div class="app-container">
    <el-form v-show="showSearch" ref="queryForm" :model="queryParams" :inline="true" size="small" label-width="68px">
      <el-form-item label="订单号" prop="orderNo">
        <el-input v-model="queryParams.orderNo" placeholder="请输入订单号" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-table v-loading="loading" :data="orderList">
      <el-table-column type="index" label="序号" width="50" />
      <el-table-column label="订单号" align="center" prop="orderNo">
        <template slot-scope="scope">
          <el-button type="text" @click="handleOrderView(scope.row)">{{ scope.row.orderNo }}</el-button>
        </template>
      </el-table-column>
      <el-table-column label="课程信息" align="center" prop="courseTitle">
        <template slot-scope="scope">
          <el-button type="text" @click="handleCourseView(scope.row.courseId)">{{ scope.row.courseTitle }}</el-button>
        </template>
      </el-table-column>
      <el-table-column label="讲师信息" align="center" prop="teacherName">
        <template slot-scope="scope">
          <el-button type="text" @click="handleTeacherView(scope.row.teacherId)">{{ scope.row.teacherName }}</el-button>
        </template>
      </el-table-column>
      <el-table-column label="用户信息" align="center" prop="nickname">
        <template slot-scope="scope">
          <el-button type="text" @click="handleStudentView(scope.row.memberId)">{{ scope.row.nickname }}</el-button>
        </template>
      </el-table-column>
      <el-table-column label="用户手机号" align="center" prop="mobile" />
      <el-table-column label="支付方式" align="center" prop="payType">
        <template slot-scope="scope">
          <el-tag type="success">
            <el-row v-if="scope.row.payType === 1" type="success">微信</el-row>
            <el-row v-if="scope.row.payType === 2" type="brandColor">支付宝</el-row>
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="支付价格" align="center" prop="totalFee" />
      <el-table-column label="支付状态" align="center" prop="status">
        <template slot-scope="scope">
          <el-tag>
            <el-row v-if="scope.row.status === 0" type="success">未支付</el-row>
            <el-row v-if="scope.row.status === 1" type="success">已支付</el-row>
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="订单时间" align="center" prop="gmtCreate" />
    </el-table>
    <el-pagination
      :current-page="page"
      :page-size="limit"
      :total="total"
      style="padding: 30px 0; text-align: right;"
      layout="total, prev, pager, next, jumper"
      @current-change="getList" />
    <order-view :visible="ctrl.orderViewVisible" :order-data="orderData" :title="ctrl.dialogTitle" @close-callback="closeOrder"/>
    <course-view :visible="ctrl.courseViewVisible" :course-data="courseData" :title="ctrl.dialogTitle" @close-callback="closeOrder"/>
    <teacher-view :visible="ctrl.teacherViewVisible" :tearcher-data="tearcherData" :title="ctrl.dialogTitle" @close-callback="closeOrder"/>
    <student-view :visible="ctrl.studentViewVisible" :student-data="studentData" :title="ctrl.dialogTitle" @close-callback="closeOrder"/>
  </div>
</template>
<script>
import orderApi from '@/api/order/order'
import courseApi from '@/api/edu/course'
import teacherApi from '@/api/edu/teacher'
import studentApi from '@/api/edu/student'
import OrderView from './orderView'
import courseView from './courseView'
import teacherView from './teacherView'
import studentView from './studentView'
export default {
  name: 'Order',
  components: { OrderView, courseView, teacherView, studentView },
  data() {
    return {
      loading: false,
      showSearch: true,
      page: 1,
      limit: 10,
      total: 0,
      orderList: [],
      title: '',
      open: false,
      queryParams: {
        orderNo: ''
      },
      form: {},
      orderData: {},
      courseData: {},
      tearcherData: {},
      studentData: {},
      ctrl: {
        orderViewVisible: false,
        dialogTitle: '',
        courseViewVisible: false,
        teacherViewVisible: false,
        studentViewVisible: false
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList(page = 1) {
      this.page = page
      this.loading = true
      orderApi.listOrder(this.page, this.limit, this.queryParams).then(response => {
        this.orderList = response.data.rows
        this.total = response.data.total
        this.loading = false
      })
    },
    reset() {
      this.queryParams = {}
    },
    handleQuery() {
      this.getList()
    },
    resetQuery() {
      this.handleQuery()
    },
    handleOrderView(row) {
      this.orderData = row
      this.ctrl.dialogTitle = row.orderNo + ' ---- 订单详情'
      this.ctrl.orderViewVisible = true
    },
    closeOrder() {
      this.ctrl.orderViewVisible = false
      this.ctrl.courseViewVisible = false
      this.ctrl.teacherViewVisible = false
      this.ctrl.studentViewVisible = false
    },
    handleCourseView(courseId) {
      courseApi.getCourseInfoId(courseId).then(response => {
        this.courseData = response.data.courseInfoVo
        this.ctrl.dialogTitle = response.data.courseInfoVo.title + ' ---- 课程详情'
        this.ctrl.courseViewVisible = true
      })
    },
    handleTeacherView(teacherId) {
      teacherApi.getTeacherInfo(teacherId).then(response => {
        this.tearcherData = response.data.teacher
        this.ctrl.dialogTitle = response.data.teacher.name + ' ---- 讲师详情'
        this.ctrl.teacherViewVisible = true
      })
    },
    handleStudentView(memberId) {
      studentApi.getStudent(memberId).then(response => {
        this.studentData = response.data.data
        this.ctrl.dialogTitle = response.data.data.nickname + ' ---- 用户详情'
        this.ctrl.studentViewVisible = true
      })
    }
  }
}
</script>
