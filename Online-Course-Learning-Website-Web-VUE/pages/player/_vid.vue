<template>
<div class="bg">
    <div class="video">
        <div class="title">
            <div class="course-txt-body-wrap">
                <h6 class="c-i-content c-infor-title">
                    <span><div v-html="this.title"></div></span>
                </h6>
            </div>
        </div>
  <div class="player">
    <link rel="stylesheet" href="https://g.alicdn.com/de/prismplayer/2.8.1/skins/default/aliplayer-min.css" >
    <script charset="utf-8" type="text/javascript" src="https://g.alicdn.com/de/prismplayer/2.8.1/aliplayer-min.js" />
    <div id="J_prismPlayer" class="prism-player" />
  </div>
    <div class="comment">
        <div>
            <p class="c-i-content c-infor-title">
            <span>课程介绍</span>
            </p>
            <div class="course-txt-body-wrap">
            <section class="course-txt-body">
            <div v-html="this.description"></div>
            </section>
            </div>
        </div>
    </div>
  </div>
</div>
</template>
<script>
import vod from '@/api/vod'
export default {
    layout: 'video',
    asyncData({ params, error }) {
       return vod.getPlayAuth(params.vid)
        .then(response => {
            return { 
                playAuth: response.data.data.playAuth,
                vid: params.vid
            }
        })
    },
    data(){
        return{
            title:'1',
            description:'2'
        }
    },
    created(){
        this.title=this.$route.query.title
        this.description=this.$route.query.description
    },
    mounted() {
        new Aliplayer({
            id: 'J_prismPlayer',
            vid: this.vid,
            playauth: this.playAuth,
            encryptType: '1',
            width: '100%',
            height: '500px',
            cover: '',
            qualitySort: 'asc',
            mediaType: 'video',
            autoplay: false,
            isLive: false,
            rePlay: false,
            preload: true,
            controlBarVisibility: 'hover',
            useH5Prism: true,
        }, function(player) {
        })
    }
}
</script>
<style scoped>
    .bg{
        background-image: url("https://api.yimian.xyz/img?type=wallpaper");
    }
    .video{
        width: 1000px;
        height:800px;
        margin:0 auto;
        box-shadow: 0 0 15px 10px;
    }
    .title{
        width: 100%;
        height: 5%;
        background-color: white;
    }
        .player{
        width: 100%;
        height: 62%;
    }
    .comment{
        width: 100%;
        height: 38%;
        background-color: white;
    }
</style>
