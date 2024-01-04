import request from '@/utils/request'

export default {
  getPageList(page, limit, courseId) {
    return request({
      url: `/eduservice/comment/getCommentList/${page}/${limit}`,
      method: 'get',
      params: {courseId}
    })
  },
  addComment(comment) {
    return request({
      url: `/eduservice/comment/saveComment`,
      method: 'post',
      data: comment
    })
  }
}