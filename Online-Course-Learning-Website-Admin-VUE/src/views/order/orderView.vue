<template>
  <el-dialog
    :title="title"
    :visible.sync="visible"
    :before-close="handleClose"
    width="50%">
    <el-form :inline="true">
      <el-alert :closable="false" class="title" title="订单信息" type="info" />
      <el-row>
        <el-col :span="12">
          <el-form-item label="用户编号:">
            <span >{{ orderData.memberId }}</span>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="用户手机号:">
            <span >{{ orderData.mobile }}</span>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="课程编号:">
            <span >{{ orderData.courseId }}</span>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="课程名称:">
            <span >{{ orderData.courseTitle }}</span>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="讲师编号:">
            <span >{{ orderData.teacherId }}</span>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="讲师名称:">
            <span >{{ orderData.teacherName }}</span>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="订单号:">
            <span >{{ orderData.orderNo }}</span>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="支付时间:">
            <span >{{ orderData.gmtCreate }}</span>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="支付方式:">
            <span>{{ textPayType[orderData.payType] }}</span>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="支付状态:">
            <span >{{ textOrderStatus[orderData.status] }}</span>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="备注:">
            <span >{{ orderData.remark }}</span>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
  </el-dialog>
</template>
<script>
export default {
  name: 'OrderView',
  props: {
    orderData: {
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
      textPayType: {
        1: '微信支付',
        2: '支付宝支付'
      },
      textOrderStatus: {
        0: '待支付',
        1: '支付成功',
        2: '支付失败',
        3: '已关闭'
      }
    }
  },
  methods: {
    handleClose(done) {
      this.$emit('close-callback')
    }
  }
}
</script>
