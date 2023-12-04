import request from '@/utils/request'

// 查询销售订单列表
export function listSo(query) {
  return request({
    url: '/oms/so/list',
    method: 'get',
    params: query
  })
}

// 查询销售订单详细
export function getSo(id) {
  return request({
    url: '/oms/so/' + id,
    method: 'get'
  })
}

// 新增销售订单
export function addSo(data) {
  return request({
    url: '/oms/so',
    method: 'post',
    data: data
  })
}

// 修改销售订单
export function updateSo(data) {
  return request({
    url: '/oms/so',
    method: 'put',
    data: data
  })
}

// 删除销售订单
export function delSo(id) {
  return request({
    url: '/oms/so/' + id,
    method: 'delete'
  })
}
