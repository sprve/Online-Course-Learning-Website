import request from '@/utils/request'

const api_name = '/admin/acl/role'

export default {
  // 查询角色列表
  getPageList(page, limit, searchObj) {
    return request({
      url: `${api_name}/${page}/${limit}`,
      method: 'get',
      params: searchObj // url查询字符串或表单键值对
    })
  },
  //根据id查询角色信息
  getById(id) {
    return request({
      url: `${api_name}/get/${id}`,
      method: 'get'
    })
  },
  // 新增角色
  addRole(role) {
    return request({
      url: `${api_name}/save`,
      method: 'post',
      data: role
    })
  },
  // 修改角色
  updateRole(role) {
    return request({
      url: `${api_name}/update`,
      method: 'put',
      data: role
    })
  },
  // 检查角色是否被分配出去
  getCheckRole(roleId) {
    return request({
      url: `${api_name}/getCheckRole/${roleId}`,
      method: 'get'
    })
  },
  // 根据id删除角色
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
  // 修改角色状态
  changeUserStatus(roleId, status) {
    const data = {
      roleId,
      status
    }
    return request({
      url: '/admin/acl/role/changeStatus',
      method: 'post',
      data: data
    })
  }
}
