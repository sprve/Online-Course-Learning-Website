<template>
  <div class="in-wrap">
    <header id="header">
      <section class="container">
        <h1 id="logo">
          <a href="/" title="在线学习网站">
            <img src="~/assets/img/logo.png" width="100%" alt="在线学习网站">
          </a>
        </h1>
        <div class="h-r-nsl">
          <ul class="nav">
          <div v-html = "headerHtml">{{headerHtml}}</div>
          </ul>
          <ul class="h-r-login">
            <li v-if="!loginInfo.id" id="no-login">
                <a href="/login" title="登录">
                    <em class="icon18 login-icon">&nbsp;</em>
                    <span class="vam ml5">登录</span>
                </a>
                |
                <a href="/register" title="注册">
                    <span class="vam ml5">注册</span>
                </a>
            </li>
            <li v-if="loginInfo.id" id="is-login-one" class="mr10">
                <a id="headerMsgCountId" href="#" title="消息">
                    <em class="icon18 news-icon">&nbsp;</em>
                </a>
                <q class="red-point" style="display: none">&nbsp;</q>
            </li>
            <li v-if="loginInfo.id" id="is-login-two" class="h-r-user">
                <el-dropdown>
                    <span class="el-dropdown-link" ref="echarType">
                        <a href="#" title>
                            <img
                                :src="loginInfo.avatar"
                                width="30"
                                height="30"
                                class="vam picImg"
                                alt
                                >
                            <span id="userName" class="vam disIb">{{ loginInfo.nickname }}</span>
                        </a>
                    </span>
                    <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item ><a href="javascript:void(0);" title @click="updateUser" class="ml5">个人信息</a></el-dropdown-item>
                        <el-dropdown-item ><a href="javascript:void(0);" title @click="updatePassword" class="ml5">修改密码</a></el-dropdown-item>
                        <el-dropdown-item ><a href="javascript:void(0);" title  @click="orderInfo" class="ml5">订单信息</a></el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
                <a href="javascript:void(0);" title="退出" @click="logout()" class="ml5">退出</a>
            </li>
        </ul>
          <aside class="h-r-search">
            <form action="#" method="post">
              <label class="h-r-s-box">
                <input type="text" placeholder="输入你想学的课程" name="queryCourse.courseName" value>
                <button type="submit" class="s-btn">
                  <em class="icon18">&nbsp;</em>
                </button>
              </label>
            </form>
          </aside>
        </div>
        <aside class="mw-nav-btn">
          <div class="mw-nav-icon"></div>
        </aside>
        <div class="clear"></div>
      </section>
      <el-dialog v-if="show1" :visible.sync="dialogFormVisible1">
      <el-form
        :model="updateUserForm"
        label-position="left"
        label-width="90px"
        style="width: 400px; margin-left:50px;"
      >
        <el-form-item label="手机号" prop="mobile">
          <el-input v-model="updateUserForm.mobile" />
        </el-form-item>
                <el-form-item label="昵称" prop="nickname">
           <el-input v-model="updateUserForm.nickname" />
        </el-form-item>
                <el-form-item label="年龄" prop="age">
          <el-input v-model="updateUserForm.age" />
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-radio-group v-model="updateUserForm.sex" style="margin-right:12px;">
                <el-radio :label="1">女</el-radio>
                <el-radio :label="2">男</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible1 = false">取消</el-button>
        <el-button @click="updateUserData() ">确定</el-button>
      </div>
    </el-dialog>
          <el-dialog v-if="show2" :visible.sync="dialogFormVisible2">
      <el-form
        :model="updatePasswordForm"
        label-position="left"
        label-width="90px"
        style="width: 400px; margin-left:50px;"
      >
                <el-form-item label="旧密码" prop="oldpassword">
           <el-input v-model="updatePasswordForm.oldpassword"/>
        </el-form-item>
                <el-form-item label="新密码" prop="newpassword">
          <el-input v-model="updatePasswordForm.newpassword" />
        </el-form-item>
        </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible2 = false">取消</el-button>
        <el-button @click="updatePasswordData() ">确定</el-button>
      </div>
    </el-dialog>
    <el-dialog v-if="show3" :visible.sync="dialogFormVisible3">
      <div>
        <table style="	width: 90%;
	background: #ccc;
	margin: 10px auto;
	border-collapse: collapse;">
          <tr>
            <th >课程</th>
            <th>老师</th>
            <th>价格</th>
          </tr>
            <tr v-for="(infoOrder,index) in data.orderlist" v-bind:key="index">
            <td><a :href="'/course/'+infoOrder.courseId" >{{infoOrder.courseTitle}}</a></td>
            <td><a :href="'/teacher/'+infoOrder.teacherId" >{{infoOrder.teacherName}}</a></td>
            <td><a href=# >{{infoOrder.totalFee}}</a></td>
          </tr>
        </table>      
      </div>                      
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible3 = false">退出</el-button>
      </div>
    </el-dialog>
    </header>
    <nuxt/>
    <footer id="footer">
      <section class="container">
        <div v-html = "footerHtml">{{footerHtml}}</div>
      </section>
    </footer>
  </div>
</template>
<script>
import '~/assets/css/reset.css'
import '~/assets/css/theme.css'
import '~/assets/css/global.css'
import '~/assets/css/web.css'
import '~/assets/css/base.css'
import '~/assets/css/activity_tab.css'
import '~/assets/css/bottom_rec.css'
import '~/assets/css/nice_select.css'
import '~/assets/css/order.css'
import '~/assets/css/swiper-3.3.1.min.css'
import "~/assets/css/pages-weixinpay.css"
import cookie from 'js-cookie'
import userApi from '@/api/user'
import orderApi from '@/api/orders'
import index from '@/api/index'
export default {
  data() {
    return {
      token: '',
      code:"",
      loginInfo: {
        id: '',
        age: '',
        avatar: '',
        mobile: '',
        nickname: '',
        sex: ''
      },
      updateUserForm: {
        id:"",
        mobile: "",
        nickname: "",
        sex: "",
        age: "",
      },
      dialogFormVisible1: false,
      dialogFormVisible2: false,
      dialogFormVisible3: false,
      show1:false,
      show2:false,
      show3:false,
      updatePasswordForm:{
        id:"",
        oldpassword:"",
        newpassword:"",
      },
      infoOrder:{
        courseId:"",
        courseTitle:"",
        teacherId:"",
        teacherName:"",
        totalFee:""
      },
      data:{},
      headerHtml:'',
      footerHtml:''
    }
  },
  created() {
    this.getHtmlInfo()
    this.showInfo() 
  },
  methods: {
    updateUser() {
          this.updateUserForm = {
            mobile: this.loginInfo.mobile,
            nickname: this.loginInfo.nickname,
            sex: this.loginInfo.sex,
            age: this.loginInfo.age,
            id: this.loginInfo.id
          };
          this.show1=true
          this.dialogFormVisible1= true;
        },
        updatePassword() {
          this.updatePasswordForm={
          id:this.loginInfo.id
          };
          this.show2=true;
          this.dialogFormVisible2=true;
        },
    updateUserData(){
      userApi.updateUser(this.updateUserForm).then(response => {
            this.$message({
              type: 'success',
              message: "更新成功"
            })
            this.tokenData();
      })
    },
    updatePasswordData(){
      userApi.passwordUser(this.updatePasswordForm).then(response => {
            var code=response.data.code;
            if(code==20000){
              this.$message({
              type: 'success',
              message: "更新成功"
            })
              this.logout();
            }
        })
    },
    orderInfo(){
      orderApi.queryUserPay().then(response => {
            this.data= response.data.data
            this.show3=true
            this.dialogFormVisible3=true
      })
    },
    tokenData(){
      userApi.tokenUser().then(response => {
            this.loginInfo = response.data.data.userInfo
            cookie.set('sprve_ucenter','')
            cookie.set('sprve_ucenter', this.loginInfo)
            window.location.href = "/";
      })
    },
    showInfo() {
      var userStr = cookie.get('sprve_ucenter')
      if(userStr) {
        this.loginInfo = JSON.parse(userStr)
      }
    },
    logout() {
      cookie.set('sprve_token','')
      cookie.set('sprve_ucenter','')
      window.location.href = "/";
    },
    getHtmlInfo(){
       index.getHtml(1).then(response => {
           this.headerHtml=response.data.data.eduHtml.htmlPage
        })
        index.getHtml(2).then(response => {
           this.footerHtml=response.data.data.eduHtml.htmlPage
        })
    }
  }
}
</script>
<style scoped>
table {
	width: 90%;
	background: #ccc;
	margin: 10px auto;
	border-collapse: collapse;
}
th,
td {
	height: 25px;
	line-height: 25px;
	text-align: center;
	border: 1px solid #ccc;
}
th {
	background: #eee;
	font-weight: normal;
}
tr {
	background: #fff;
}
tr:hover {
	background: #cc0;
}
td a {
	color: #06f;
	text-decoration: none;
}
td a:hover {
	color: #06f;
	text-decoration: underline;
}
</style>