<template>
<div class="bg">
  <div style="padding: 10px; margin-bottom: 50px;margin-left:200px" >
    <el-row >
      <el-col :span="16" >
        <div style="width: 1000px; margin: 0 auto; background-color: white;
                    border-radius: 5px; box-shadow: 0 0 10px #ccc">
          <div style="text-align: center; line-height: 40px;">
            智能问答
            <el-select v-model="selectedAiId" placeholder="" @change="aiChange" clearable>
              <el-option v-for="ai in ais" :label="ai.name" :value="ai.id" :key="ai.id"></el-option>
            </el-select>
          </div>
          <div style="height: 350px; overflow:auto; border-top: 1px solid #ccc" v-html="content"></div>
          <div style="height: 200px">
            <textarea v-model="text" style="height: 160px; width: 95%; padding: 20px; border: none; border-top: 1px solid #ccc;
             border-bottom: 1px solid #ccc; outline: none"></textarea>
            <div style="text-align: right; padding-right: 10px; font-size:30px">
              <el-button type="primary"  @click="send">发送</el-button>
            </div>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
  </div>
</template>
<script>
import ai from "@/api/ai"
import cookie from 'js-cookie'
let socket;
export default {
  name: "Im",
  data() {
    return {
      role:"扮演Ai",
      text: "",
      question:'',
      content: '',
      data:'',
      loginInfo: [],
      selectedAiId:1,
      ais: [{
        id: 1,
        name: 'AI'
      },
      {
        id: 2,
        name: '猫猫'
      }]
    }
  },
  created() {
  },
  methods: {
    createContent(remoteUser, nowUser, text) {
      let html
      if (nowUser) {
        html = "<div class=\"el-row\" style=\"padding: 5px 0\">\n" +
            "  <div class=\"el-col el-col-22\" style=\"text-align: right; padding-right: 10px\">\n" +
            "    <div class=\"tip left\">" + text + "</div>\n" +
            "  </div>\n" +
            "  <div class=\"el-col el-col-2\">\n" +
            "  <span class=\"el-avatar el-avatar--circle\" style=\"height: 40px; width: 40px; line-height: 40px;\">\n" +
            "    <img src=\""+this.loginInfo.avatar+"\" style=\"object-fit: cover;\">\n" +
            "  </span>\n" +
            "  </div>\n" +
            "</div>";
      } else if (remoteUser) {
        html = "<div class=\"el-row\" style=\"padding: 5px 0\">\n" +
            "  <div class=\"el-col el-col-2\" style=\"text-align: right\">\n" +
            "  <span class=\"el-avatar el-avatar--circle\" style=\"height: 40px; width: 40px; line-height: 40px;\">\n" +
            "    <img src=\"https://sprve.oss-cn-beijing.aliyuncs.com/pictures/ai.png\" style=\"object-fit: cover;\">\n" +
            "  </span>\n" +
            "  </div>\n" +
            "  <div class=\"el-col el-col-22\" style=\"text-align: left; padding-left: 10px\">\n" +
            "    <div class=\"tip right\">" + text + "</div>\n" +
            "  </div>\n" +
            "</div>";
      }
      this.content += html;
    },
    send() {
      this.showInfo()
      if (!this.text) {
        this.$message({type: 'warning', message: "请输入内容"})
      } else {
        this.createContent(null, "用户", this.text)
        this.question=this.text;
        this.text="";
        ai.getAnswer(this.role,this.question).then(response => {
            this.data=response.data.data.answer;
            this.data = this.data.split(/\\n/g).join('<br/>')
             this.createContent("AI", null, this.data);
        })
        }
      },
      showInfo() {
      var userStr = cookie.get('sprve_ucenter')
      if(userStr) {
        this.loginInfo = JSON.parse(userStr)
      }
      else{
        window.location.href = "/login"
      }
    },
    aiChange() {
      if( this.selectedAiId==1){
        this.role="扮演Ai"
      }
      else if(this.selectedAiId==2){
        this.role="扮演猫娘，情景对话"
      }
    }
  }
}
</script>
<style>
.tip {
  color: white;
  text-align: center;
  border-radius: 10px;
  font-family: sans-serif;
  padding: 10px;
  width:auto;
  display:inline-block !important;
  display:inline;
}
.right {
  background-color: deepskyblue;
}
.left {
  background-color: forestgreen;
}
.bg{
border-radius:20px; 
width:100%;
height:100%;
background-image: url("https://api.yimian.xyz/img?type=wallpaper");
}
</style>
