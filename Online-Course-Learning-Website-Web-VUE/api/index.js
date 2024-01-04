import request from '@/utils/request'

export default {
    //查询热门课程和讲师
  getListBanner() {
    return request({
        url: `/eduservice/indexfront/index`,
        method: 'get'
      })
  },
  getHtml(id){
    return request({
      url: `/eduservice/Html/getHtmlInfo/${id}`,
      method: 'get'
    })
  }
}