<template>
  <div id="aCoursesList" class="bg-fa of">
    <section class="container">
      <section class="path-wrap txtOf hLh30">
        <a href="#" title class="c-999 fsize14">首页</a>
        \
        <a href="#" title class="c-999 fsize14">{{courseWebVo.subjectLevelOne}}</a>
        \
        <span class="c-333 fsize14">{{courseWebVo.subjectLevelTwo}}</span>
      </section>
      <div>
        <article class="c-v-pic-wrap" style="height: 357px;">
          <section class="p-h-video-box" id="videoPlay">
            <img height="357px" :src="courseWebVo.cover" :alt="courseWebVo.title" class="dis c-v-pic">
          </section>
        </article>
        <aside class="c-attr-wrap">
          <section class="ml20 mr15">
            <h2 class="hLh30 txtOf mt15">
              <span class="c-fff fsize24">{{courseWebVo.title}}</span>
            </h2>
            <section class="c-attr-jg">
              <span class="c-fff">价格：</span>
              <b class="c-yellow" style="font-size:24px;">￥{{courseWebVo.price}}</b>
            </section>
            <section class="c-attr-mt c-attr-undis">
              <span class="c-fff fsize14">主讲： {{courseWebVo.teacherName}}&nbsp;&nbsp;&nbsp;</span>
            </section>
            <section v-if="isbuy || Number(courseWebVo.price) === 0" class="c-attr-mt">
              <a href="#" title="立即观看" class="comm-btn c-btn-3">立即观看</a>
            </section>
            <section v-else class="c-attr-mt">
              <a @click="createOrders()" href="#" title="立即购买" class="comm-btn c-btn-3">立即购买</a>
            </section>
          </section>
        </aside>
        <aside class="thr-attr-box">
          <ol class="thr-attr-ol">
            <li>
              <p>&nbsp;</p>
              <aside>
                <span class="c-fff f-fM">购买数</span>
                <br>
                <h6 class="c-fff f-fM mt10">{{courseWebVo.buyCount}}</h6>
              </aside>
            </li>
            <li>
              <p>&nbsp;</p>
              <aside>
                <span class="c-fff f-fM">课时数</span>
                <br>
                <h6 class="c-fff f-fM mt10">{{courseWebVo.lessonNum}}</h6>
              </aside>
            </li>
            <li>
              <p>&nbsp;</p>
              <aside>
                <span class="c-fff f-fM">学习数</span>
                <br>
                <h6 class="c-fff f-fM mt10">{{courseWebVo.viewCount}}</h6>
              </aside>
            </li>
          </ol>
        </aside>
        <div class="clear"></div>
      </div>
      <div class="mt20 c-infor-box">
        <article class="fl col-7">
          <section class="mr30">
            <div class="i-box">
              <div>
                <section id="c-i-tabTitle" class="c-infor-tabTitle c-tab-title">
                  <a name="c-i" class="current" title="课程详情">课程详情</a>
                </section>
              </div>
              <article class="ml10 mr10 pt20">
                <div>
                  <h6 class="c-i-content c-infor-title">
                    <span>课程介绍</span>
                  </h6>
                  <div class="course-txt-body-wrap">
                    <section class="course-txt-body">
                      <p v-html="courseWebVo.description">{{courseWebVo.description}}</p>
                    </section>
                  </div>
                </div>
                <div class="mt50">
                  <h6 class="c-g-content c-infor-title">
                    <span>课程大纲</span>
                  </h6>
                  <section class="mt20">
                    <div class="lh-menu-wrap">
                      <menu id="lh-menu" class="lh-menu mt10 mr10">
                        <ul>
                          <li class="lh-menu-stair" v-for="chapter in chapterVideoList" :key="chapter.id">
                            <a href="javascript: void(0)" :title="chapter.title" class="current-1">
                              <em class="lh-menu-i-1 icon18 mr10"></em>{{chapter.title}}
                            </a>
                            <ol class="lh-menu-ol" style="display: block;">
                              <li class="lh-menu-second ml30" v-for="video in chapter.children" :key="video.id">
                                 <a v-if="video.videoSourceId" :href="'/player/'+video.videoSourceId+'?title='+video.title+'&description='+ decodeURIComponent(courseWebVo.description)" :title="video.title" target="_blank">
                                  <span class="fr">
                                    <i class="free-icon vam mr10">可观看</i>
                                  </span>
                                  <em class="lh-menu-i-2 icon16 mr5">&nbsp;</em>{{video.title}}
                                </a>
                                 <a v-else href=# :title="video.title" target="_blank">
                                  <span class="fr">
                                    <i class="free-icon vam mr10">VIP观看</i>
                                  </span>
                                  <em class="lh-menu-i-2 icon16 mr5">&nbsp;</em>{{video.title}}
                                </a>
                              </li>
                            </ol>
                          </li>
                        </ul>
                      </menu>
                    </div>
                  </section>
                </div>
                <div class="mt50 commentHtml"><div>
                <h6 class="c-c-content c-infor-title" id="i-art-comment">
                  <span class="commentTitle">课程评论</span>
                </h6>
                <section class="lh-bj-list pr mt20 replyhtml">
                  <ul>
                    <li class="unBr">
                      <aside class="noter-pic">
                        <img width="50" height="50" class="picImg" :src="loginInfo.avatar">
                        </aside>
                      <div class="of">
                        <section class="n-reply-wrap">
                          <fieldset>
                            <textarea name="" v-model="comment.content" placeholder="输入您要评论的文字" id="commentContent"></textarea>
                          </fieldset>
                          <p class="of mt5 tar pl10 pr10">
                            <span class="fl "><tt class="c-red commentContentmeg" style="display: none;"></tt></span>
                            <input type="button" @click="addComment()" value="回复" class="lh-reply-btn">
                          </p>
                        </section>
                      </div>
                    </li>
                  </ul>
                </section>
                <section class="">
                    <section class="question-list lh-bj-list pr">
                      <ul class="pr10">
                        <li v-for="(comment,index) in data.items" v-bind:key="index">
                            <aside class="noter-pic">
                              <img width="50" height="50" class="picImg" :src="comment.avatar">
                              </aside>
                            <div class="of">
                              <span class="fl"> 
                              <font class="fsize12 c-blue"> 
                                {{comment.nickname}}</font>
                              <font class="fsize12 c-999 ml5">评论：</font></span>
                            </div>
                            <div class="noter-txt mt5">
                              <p>{{comment.content}}</p>
                            </div>
                            <div class="of mt5">
                              <span class="fr"><font class="fsize12 c-999 ml5">{{comment.gmtCreate}}</font></span>
                            </div>
                          </li>
                        </ul>
                    </section>
                  </section>
                  <div class="paging">
                      <a
                      :class="{undisable: !data.hasPrevious}"
                      href="#"
                      title="首页"
                      @click.prevent="gotoPage(1)">首</a>
                      <a
                      :class="{undisable: !data.hasPrevious}"
                      href="#"
                      title="前一页"
                      @click.prevent="gotoPage(data.current-1)">&lt;</a>
                      <a
                      v-for="page in data.pages"
                      :key="page"
                      :class="{current: data.current == page, undisable: data.current == page}"
                      :title="'第'+page+'页'"
                      href="#"
                      @click.prevent="gotoPage(page)">{{ page }}</a>
                      <a
                      :class="{undisable: !data.hasNext}"
                      href="#"
                      title="后一页"
                      @click.prevent="gotoPage(data.current+1)">&gt;</a>
                      <a
                      :class="{undisable: !data.hasNext}"
                      href="#"
                      title="末页"
                      @click.prevent="gotoPage(data.pages)">末</a>
                      <div class="clear"/>
                  </div>
                </div>
                </div>
              </article>
            </div>
          </section>
        </article>
        <aside class="fl col-3">
          <div class="i-box">
            <div>
              <section class="c-infor-tabTitle c-tab-title">
                <a title href="javascript:void(0)">主讲讲师</a>
              </section>
              <section class="stud-act-list">
                <ul style="height: auto;">
                  <li>
                    <div class="u-face">
                      <a href="#">
                        <img :src="courseWebVo.avatar" width="50" height="50" alt>
                      </a>
                    </div>
                    <section class="hLh30 txtOf">
                      <a class="c-333 fsize16 fl" href="#">{{courseWebVo.teacherName}}</a>
                    </section>
                    <section class="hLh20 txtOf">
                      <span class="c-999">{{courseWebVo.intro}}</span>
                    </section>
                  </li>
                </ul>
              </section>
            </div>
          </div>
        </aside>
        <div class="clear"></div>
      </div>
    </section>
  </div>
</template>
<script>
import courseApi from '@/api/course'
import commentApi from '@/api/comment'
import ordersApi from '@/api/orders'
import cookie from 'js-cookie'
export default {
  asyncData({ params, error }) {
    return {
      courseId: params.id
    }
  },
  data() {
    return {
      data:{},
      page:1,
      limit:4,
      total:10,
      comment:{
        content:'',
        courseId:''
      },
      loginInfo: [],
      courseWebVo:{},
      chapterVideoList:[],
      isbuy:false
    }
  },
  created() {
    this.initCourseInfo()
    this.initComment()
    this.showInfo()
  },
  methods:{
    showInfo() {
      var userStr = cookie.get('sprve_ucenter')
      if(userStr) {
        this.loginInfo = JSON.parse(userStr)
      }
    },
    initCourseInfo() {
      courseApi.getCourseInfo(this.courseId).then(response => {
        this.courseWebVo=response.data.data.courseWebVo
        this.chapterVideoList=response.data.data.chapterVideoList
         if (cookie.get('sprve_token')){
            this.isbuy=response.data.data.isBuy
        }
      })
    },
    initComment(){
       commentApi.getPageList(this.page, this.limit, this.courseId).then(response => {
           this.data = response.data.data
       })
    },
    addComment(){
        this.comment.courseId = this.courseId
        this.comment.teacherId = this.courseWebVo.teacherId
        commentApi.addComment(this.comment).then(response => {
            if(response.data.success){
                this.comment.content = ''
                this.initComment()
            }
        })
    },
    gotoPage(page){
          commentApi.getPageList(page, this.limit,this.courseId).then(response => {
              this.data = response.data.data
          })
    },
    createOrders() {
      ordersApi.createOrders(this.courseId).then(response =>{
        this.$router.push({path:'/orders/'+response.data.data.orderId})
      })
    }
  }
};
</script>