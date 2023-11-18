import request from '@/utils/request'

// 查询采购订单列表
export function listPo(query) {
  return request({
    url: '/oms/po/list',
    method: 'get',
    params: query
  })
}

// 查询采购订单详细
export function getPo(id) {
  return request({
    url: '/oms/po/' + id,
    method: 'get'
  })
}

// 新增采购订单
export function addPo(data) {
  return request({
    url: '/oms/po',
    method: 'post',
    data: data
  })
}

// 修改采购订单
export function updatePo(data) {
  return request({
    url: '/oms/po',
    method: 'put',
    data: data
  })
}

// 删除采购订单
export function delPo(id) {
  return request({
    url: '/oms/po/' + id,
    method: 'delete'
  })
}
