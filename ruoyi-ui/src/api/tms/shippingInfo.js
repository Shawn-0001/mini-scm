import request from '@/utils/request'

// 查询运单详细信息列表
export function listShippingInfo(query) {
  return request({
    url: '/tms/shippingInfo/list',
    method: 'get',
    params: query
  })
}

// 查询运单详细信息详细
export function getShippingInfo(id) {
  return request({
    url: '/tms/shippingInfo/' + id,
    method: 'get'
  })
}

// 新增运单详细信息
export function addShippingInfo(data) {
  return request({
    url: '/tms/shippingInfo',
    method: 'post',
    data: data
  })
}

// 修改运单详细信息
export function updateShippingInfo(data) {
  return request({
    url: '/tms/shippingInfo',
    method: 'put',
    data: data
  })
}

// 删除运单详细信息
export function delShippingInfo(id) {
  return request({
    url: '/tms/shippingInfo/' + id,
    method: 'delete'
  })
}
