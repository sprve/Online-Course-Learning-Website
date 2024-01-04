import request from '@/utils/request'

export default{
  // 删除评论
  deleteCommentInfo(id) {
    return request({
      url: `/eduservice/comment/removeComment/${id}`,
      method: 'post'
    })
  },
  // 评论列表（条件查询分页）
  // current当前页 limit每页记录数 courseQuery条件对象
  pageCommentCondition(current, limit, commentQuery) {
    return request({
      url: `/eduservice/comment/pageComment/${current}/${limit}`,
      method: 'post',
      data: commentQuery
    })
  }
}
