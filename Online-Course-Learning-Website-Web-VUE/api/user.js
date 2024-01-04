import request from '@/utils/request'

export default {

  updateUser(ucenterMember) {
    return request({
        url: `/educenter/member/updateStudent/`,
        method: 'post',
        data: ucenterMember
      })
  },
  tokenUser() {
    return request({
        url: `/educenter/member/getMemberInfo/`,
        method: 'get',
      })
  },
  passwordUser(userPassword){
    return request({
      url: `/educenter/member/reset/`,
      method: 'post',
      data: userPassword
    })
  }
}