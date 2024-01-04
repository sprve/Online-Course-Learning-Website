<template>
  <div class="app-container">
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-select v-model="searchObj.type" clearable placeholder="请选择">
          <el-option label="用户注册数统计" value="register_num"/>
          <el-option label="课程播放数统计" value="video_view_num"/>
          <el-option label="购买课程数统计" value="buy_num"/>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-date-picker
          v-model="searchObj.begin"
          type="date"
          placeholder="选择开始日期"
          value-format="yyyy-MM-dd"/>
      </el-form-item>
      <el-form-item>
        <el-date-picker
          v-model="searchObj.end"
          type="date"
          placeholder="选择截止日期"
          value-format="yyyy-MM-dd"/>
      </el-form-item>
      <el-button
        v-if="hasPerm('daily.add')"
        :disabled="btnDisabled"
        type="primary"
        icon="el-icon-search"
        @click="showChart()">查询
      </el-button>
    </el-form>
    <div class="chart-container">
      <div id="chart" class="chart" style="height:500px;width:100%"/>
    </div>
  </div>
</template>
<script>
import echarts from 'echarts'
import staApi from '@/api/sta'
export default {
  data() {
    return {
      searchObj: {},
      btnDisabled: false,
      xData: [],
      yData: []
    }
  },
  methods: {
    showChart() {
      staApi.getDataSta(this.searchObj)
        .then(response => {
          this.yData = response.data.numDataList
          this.xData = response.data.date_calculatedList
          this.setChart()
        })
    },
    setChart() {
      this.chart = echarts.init(document.getElementById('chart'))
      var option = {
        title: {
          text: '数据统计'
        },
        tooltip: {
          trigger: 'axis'
        },
        dataZoom: [{
          show: true,
          height: 30,
          xAxisIndex: [
            0
          ],
          bottom: 30,
          start: 10,
          end: 80,
          handleIcon: 'path://M306.1,413c0,2.2-1.8,4-4,4h-59.8c-2.2,0-4-1.8-4-4V200.8c0-2.2,1.8-4,4-4h59.8c2.2,0,4,1.8,4,4V413z',
          handleSize: '110%',
          handleStyle: {
            color: '#d3dee5'
          },
          textStyle: {
            color: '#fff'
          },
          borderColor: '#90979c'
        },
        {
          type: 'inside',
          show: true,
          height: 15,
          start: 1,
          end: 35
        }],
        xAxis: {
          type: 'category',
          data: this.xData
        },
        yAxis: {
          type: 'value'
        },
        series: [{
          data: this.yData,
          type: 'line'
        }]
      }
      this.chart.setOption(option)
    }
  }
}
</script>
