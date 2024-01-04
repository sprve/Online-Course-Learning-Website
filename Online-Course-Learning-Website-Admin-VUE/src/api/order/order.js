import request from '@/utils/request'

export default{
  // 订单列表（条件查询分页）
  listOrder(current, limit, order) {
    return request({
      url: `/eduorder/order/pageOrder/${current}/${limit}`,
      method: 'post',
      data: order
    })
  }
}
