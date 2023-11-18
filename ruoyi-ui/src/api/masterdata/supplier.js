import request from '@/utils/request'

// 查询供应商主数据列表
export function listSupplier(query) {
  return request({
    url: '/masterdata/supplier/list',
    method: 'get',
    params: query
  })
}

// 查询供应商 by code
// export function getSupplierByCode(code) {
//   return request({
//     url: '/masterdata/supplier/code/' + code,
//     method: 'get'
//   })
// }

// 查询供应商主数据详细
export function getSupplier(id) {
  return request({
    url: '/masterdata/supplier/' + id,
    method: 'get'
  })
}

// 新增供应商主数据
export function addSupplier(data) {
  return request({
    url: '/masterdata/supplier',
    method: 'post',
    data: data
  })
}

// 修改供应商主数据
export function updateSupplier(data) {
  return request({
    url: '/masterdata/supplier',
    method: 'put',
    data: data
  })
}

// 删除供应商主数据
export function delSupplier(id) {
  return request({
    url: '/masterdata/supplier/' + id,
    method: 'delete'
  })
}
