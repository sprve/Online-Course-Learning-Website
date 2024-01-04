import request from '@/utils/request'

export default {
  // 学员列表（条件查询分页）
  listStudent(current, limit, student) {
    return request({
      url: `/educenter/member/pageStudentCondition/${current}/${limit}`,
      method: 'post',
      data: student
    })
  },
  // 添加学员
  addStudent(student) {
    return request({
      url: `/educenter/member/addStudent`,
      method: 'post',
      data: student
    })
  },
  // 根据id查询学员，回显数据
  getStudent(id) {
    return request({
      url: `/educenter/member/getStudent/${id}`,
      method: 'get'
    })
  },
  // 修改学员
  updateStudent(student) {
    return request({
      url: `/educenter/member/updateStudent`,
      method: 'post',
      data: student
    })
  },
  // 删除学员
  delStudent(id) {
    return request({
      url: `/educenter/member/${id}`,
      method: 'delete'
    })
  }
}
