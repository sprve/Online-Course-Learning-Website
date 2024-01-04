import request from '@/utils/request'

export default{
  // 1 添加Banner信息
  addBannerInfo(bannerInfo) {
    return request({
      url: `/eduservice/banneradmin/addBanner`,
      method: 'post',
      data: bannerInfo
    })
  },
  getBannerInfo(id) {
    return request({
      url: `/eduservice/banneradmin/get/${id}`,
      method: 'get'
    })
  },
  // 修改Banner信息
  updateBannerInfo(bannerInfo) {
    return request({
      url: `/eduservice/banneradmin/updateBanner`,
      method: 'post',
      data: bannerInfo
    })
  },
  // 删除Banner
  deleteBannerInfo(bannerId) {
    return request({
      url: `/eduservice/banneradmin/removeBanner/${bannerId}`,
      method: 'post'
    })
  },
  // Banner列表（条件查询分页）
  // current当前页 limit每页记录数 courseQuery条件对象
  pageBannerCondition(current, limit) {
    return request({
      url: `/eduservice/banneradmin/pageBanner/${current}/${limit}`,
      method: 'get'
    })
  },
  saveHtml(Html) {
    return request({
      url: `/eduservice/Html/saveHtml/`,
      method: 'post',
      data: Html
    })
  },
  getHtmlInfo(id) {
    return request({
      url: `/eduservice/Html/getHtmlInfo/${id}`,
      method: `get`
    })
  }
}
