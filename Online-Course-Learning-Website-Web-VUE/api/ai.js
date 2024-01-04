import request from '@/utils/request'

export default {
    
  getAnswer(role,question) {
    return request({
        url: `/eduai/ai/question/${role}/${question}`,
        method: 'get',
        timeout:40000,
      })
  }
}