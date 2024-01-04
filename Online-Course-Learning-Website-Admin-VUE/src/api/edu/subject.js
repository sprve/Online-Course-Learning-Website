import request from '@/utils/request'

export default{
  // 1 课程分类列表
  getSubjectList() {
    return request({
      url: `/eduservice/subject/getAllSubject`,
      method: 'get'
    })
  },
  addOneSubject(form) {
    return request({
      url: `/eduservice/subject/addOneSubject`,
      method: 'post',
      data: form
    })
  },
  deleteOneSubject(form) {
    return request({
      url: `/eduservice/subject/deleteOneSubject`,
      method: 'post',
      data: form
    })
  }
}
