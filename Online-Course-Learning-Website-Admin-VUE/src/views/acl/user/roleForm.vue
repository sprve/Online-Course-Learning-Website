<template>
  <div class="app-container">
    <el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox>
    <div style="margin: 15px 0;"/>
    <el-checkbox-group v-model="checkedCities" @change="handleCheckedCitiesChange">
      <el-checkbox v-for="city in cities" :label="city.roleId" :key="city.roleId">{{ city.roleName }}</el-checkbox>
    </el-checkbox-group>
    </br>
    <el-button :disabled="saveBtnDisabled" type="primary" @click="update">保存</el-button>
  </div>
</template>
<script>
import userApi from '@/api/acl/user'
export default {
  data() {
    return {
      checkAll: false,
      checkedCities: [],
      cities: [],
      isIndeterminate: true,
      userId: '',
      saveBtnDisabled: false
    }
  },
  created() {
    this.init()
  },
  methods: {
    init() {
      if (this.$route.params && this.$route.params.id) {
        this.userId = this.$route.params.id
        this.getById(this.userId)
      }
    },
    getById(userId) {
      userApi.getAssign(userId).then(response => {
        var jsonObj = response.data.assignRoles
        this.checkedCities = this.getJsonToList(jsonObj, 'roleId')
        this.cities = response.data.allRolesList
      })
    },
    getJsonToList(json, key) {
      var list = JSON.parse(JSON.stringify(json))
      var strText = []
      for (var i = 0; i < list.length; i++) {
        strText.push(list[i][key])
      }
      return strText
    },
    handleCheckAllChange(val) {
      this.checkedCities = this.getJsonToList((val ? this.cities : []), 'roleId')
      this.isIndeterminate = false
    },
    handleCheckedCitiesChange(value) {
      const checkedCount = value.length
      this.checkAll = checkedCount === this.cities.length
      this.isIndeterminate = checkedCount > 0 && checkedCount < this.cities.length
    },
    update() {
      this.saveBtnDisabled = true
      var ids = this.checkedCities.join(',')
      userApi.saveAssign(this.userId, ids).then(response => {
        if (response.success) {
          this.$message({
            type: 'success',
            message: '保存成功'
          })
          this.$router.push({ path: '/acl/user/list' })
        }
      })
    }
  }
}
</script>
