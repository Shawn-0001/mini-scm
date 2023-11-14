import request from '@/utils/request'

// 查询库区信息列表
export function listWarehouseSection(query) {
  return request({
    url: '/masterdata/warehouse/section/list',
    method: 'get',
    params: query
  })
}

// 查询库区信息详细
export function getWarehouseSection(warehouseCode, code) {
  return request({
    url: '/masterdata/warehouse/section/' + warehouseCode + '/' + code,
    method: 'get'
  })
}

// 新增库区信息
export function addWarehouseSection(data) {
  return request({
    url: '/masterdata/warehouse/section',
    method: 'post',
    data: data
  })
}

// 修改库区信息
export function updateWarehouseSection(data) {
  return request({
    url: '/masterdata/warehouse/section',
    method: 'put',
    data: data
  })
}

// 删除库区信息
export function delWarehouseSection(warehouseCode, code) {
  return request({
    url: '/masterdata/warehouse/section/' + warehouseCode + '/' + code,
    method: 'delete'
  })
}
