<template>
  <div class="main">
    <div class="title">
      <a class="active" href="/login">登录</a>
      <span>·</span>
      <a href="/register">注册</a>
    </div>
    <div class="sign-up-container">
      <el-form ref="userForm" :model="user">
        <el-form-item class="input-prepend restyle" prop="mobile"
                      :rules="[{ required: true, message: '请输入手机号码', trigger: 'blur' },{validator: checkPhone, trigger: 'blur'}]">
          <div>
            <el-input type="text" placeholder="手机号" v-model="user.mobile"/>
            <i class="iconfont icon-phone"/>
          </div>
        </el-form-item>
        <el-form-item class="input-prepend" prop="password"
                      :rules="[{ required: true, message: '请输入密码', trigger: 'blur' }]">
          <div>
            <el-input type="password" placeholder="密码" v-model="user.password"/>
            <i class="iconfont icon-password"/>
          </div>
        </el-form-item>
        <div class="btn">
          <input type="button" class="sign-in-button" value="登录" @click="submitLogin()">
        </div>
      </el-form>
    </div>
  </div>
</template>
<script>
import '~/assets/css/sign.css'
import '~/assets/css/iconfont.css'
import cookie from 'js-cookie'
import loginApi from '@/api/login'
export default {
  layout: 'sign',
  data() {
    return {
      user: {
        mobile: '',
        password: ''
      },
      loginInfo: {}
    }
  },
  methods: {
    submitLogin() {
      this.$refs["userForm"].validate(valid => {
        if (valid) {
          loginApi.submitLoginUser(this.user).then(response => {
            cookie.set('sprve_token', response.data.data.token)
            console.log(response.data.data.token);
            loginApi.getLoginUserInfo()
              .then(response => {
                this.loginInfo = response.data.data.userInfo
                cookie.set('sprve_ucenter', this.loginInfo)
                window.location.href = "/";
              })
          }).catch(() => {})
        }
      })
    },
    checkPhone(rule, value, callback) {
      if (!(/^1[34578]\d{9}$/.test(value))) {
        return callback(new Error('手机号码格式不正确'))
      }
      return callback()
    }
  }
}
</script>
<style>
.el-form-item__error {
  z-index: 9999999;
}
</style>
