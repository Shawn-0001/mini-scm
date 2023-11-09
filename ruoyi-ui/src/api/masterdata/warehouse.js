import request from '@/utils/request'

// 查询仓库主数据列表
export function listWarehouse(query) {
  return request({
    url: '/masterdata/warehouse/list',
    method: 'get',
    params: query
  })
}

// 查询仓库主数据详细
export function getWarehouse(id) {
  return request({
    url: '/masterdata/warehouse/' + id,
    method: 'get'
  })
}

// 查询仓库主数据详细 by code
export function getWarehouseByCode(code) {
  return request({
    url: '/masterdata/warehouse/code/' + code,
    method: 'get'
  })
}

// 新增仓库主数据
export function addWarehouse(data) {
  return request({
    url: '/masterdata/warehouse',
    method: 'post',
    data: data
  })
}

// 修改仓库主数据
export function updateWarehouse(data) {
  return request({
    url: '/masterdata/warehouse',
    method: 'put',
    data: data
  })
}

// 删除仓库主数据
export function delWarehouse(id) {
  return request({
    url: '/masterdata/warehouse/' + id,
    method: 'delete'
  })
}
