import request from '@/utils/request'

// 查询物料主数据列表
export function listMaterial(query) {
  return request({
    url: '/masterdata/material/list',
    method: 'get',
    params: query
  })
}

// 查询物料主数据详细
export function getMaterial(id) {
  return request({
    url: '/masterdata/material/' + id,
    method: 'get'
  })
}

// 查询物料主数据详细 by code
export function getMaterialByCode(code) {
  return request({
    url: '/masterdata/material/code/' + code,
    method: 'get'
  })
}

// 新增物料主数据
export function addMaterial(data) {
  return request({
    url: '/masterdata/material',
    method: 'post',
    data: data
  })
}

// 修改物料主数据
export function updateMaterial(data) {
  return request({
    url: '/masterdata/material',
    method: 'put',
    data: data
  })
}

// 删除物料主数据
export function delMaterial(id) {
  return request({
    url: '/masterdata/material/' + id,
    method: 'delete'
  })
}
