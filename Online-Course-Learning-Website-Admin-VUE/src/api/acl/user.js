import request from '@/utils/request'

const api_name = '/admin/acl/user'

export default {
  //查询用户列表
  getPageList(page, limit, searchObj) {
    return request({
      url: `${api_name}/${page}/${limit}`,
      method: 'get',
      params: searchObj // url查询字符串或表单键值对
    })
  },
  //根据id查询用户信息
  getById(id) {
    return request({
      url: `${api_name}/get/${id}`,
      method: 'get'
    })
  },
  //新增用户
  addUser(user) {
    return request({
      url: `${api_name}/save`,
      method: 'post',
      data: user
    })
  },
  //修改用户
  updateUser(user) {
    return request({
      url: `${api_name}/update`,
      method: 'put',
      data: user
    })
  },
  // 根据用户获取角色数据
  getAssign(userId) {
    return request({
      url: `${api_name}/toAssign/${userId}`,
      method: 'get'
    })
  },
  // 根据用户分配角色
  saveAssign(userId, roleId) {
    return request({
      url: `${api_name}/doAssign`,
      method: 'post',
      params: { userId, roleId }
    })
  },
  // 删除一行数据
  removeById(id) {
    return request({
      url: `${api_name}/remove/${id}`,
      method: 'delete'
    })
  },
  // 批量删除
  removeRows(idList) {
    return request({
      url: `${api_name}/batchRemove`,
      method: 'delete',
      data: idList
    })
  },
  // 修改用户状态
  changeUserStatus(userId, status) {
    const data = {
      userId,
      status
    }
    return request({
      url: '/admin/acl/user/changeStatus',
      method: 'post',
      data: data
    })
  }
}
