<template>
  <div style="margin: 20px 20px">
    <el-tree
      ref="tree"
      :data="data"
      :props="defaultProps"
      show-checkbox
      default-expand-all
      node-key="id"
      highlight-current
      check-strictly="true"/>
    <el-button :disabled="saveBtnDisabled" type="primary" @click="save">保存</el-button>
  </div>
</template>
<script>
import menu from '@/api/acl/menu'
export default {
  data() {
    return {
      saveBtnDisabled: false,
      data: [],
      defaultProps: {
        children: 'children',
        label: 'name'
      },
      roleId: ''
    }
  },
  watch: {
    $route(to, from) {
      console.log('路由变化......')
      console.log(to)
      console.log(from)
      this.init()
    }
  },
  created() {
    this.init()
  },
  methods: {
    init() {
      if (this.$route.params && this.$route.params.id) {
        this.roleId = this.$route.params.id
        this.fetchDataById(this.roleId)
      }
    },
    fetchDataById(roleId) {
      menu.toAssign(roleId).then(response => {
        this.data = response.data.children
        var jsonList = JSON.parse(JSON.stringify(this.data))
        var list = []
        this.getJsonToList(list, jsonList)
        console.log(list, '最终集合')
        this.setCheckedKeys(list)
      })
    },
    getJsonToList(list, jsonList) {
      for (var i = 0; i < jsonList.length; i++) {
        if (jsonList[i]['select'] == true) {
          list.push(jsonList[i]['id'])
        }
        if (jsonList[i]['children'] != null) {
          this.getJsonToList(list, jsonList[i]['children'])
        }
      }
    },
    getCheckedNodes() {
      console.log(this.$refs.tree.getCheckedNodes())
    },
    getCheckedKeys() {
      console.log(this.$refs.tree.getCheckedKeys())
    },
    setCheckedKeys(list) {
      this.$refs.tree.setCheckedKeys(list)
    },
    save() {
      this.saveBtnDisabled = true
      var ids = this.$refs.tree.getCheckedKeys().join(',')
      menu.doAssign(this.roleId, ids).then(response => {
        if (response.success) {
          this.$message({
            type: 'success',
            message: '保存成功'
          })
          this.$router.push({ path: '/acl/role/list' })
        }
      })
    }
  }
}
</script>
