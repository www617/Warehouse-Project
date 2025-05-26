// 引入封装好的axios @等价于/src
import http from '@/request/request.js';

// 获取所有顾客信息
function getAllCustomers() {
    return http.get('/api/wsj/customers/all');
}

// 根据ID获取顾客信息
function getCustomerById(id) {
    return http.get(`/api/wsj/customers/${id}`);
}

// 新增顾客
function addCustomer(data) {
    return http.post('/api/wsj/customers/add', data);
}

// 更新顾客信息
function updateCustomer(data) {
    return http.put('/api/wsj/customers/update', data);
}

// 根据ID删除顾客
function deleteCustomer(id) {
    return http.delete(`/api/wsj/customers/delete/${id}`);
}

// src/api/wsj/customer.js
function searchCustomersByName(params) { // 接收包含name、page、size的参数对象
  return http.get('/api/wsj/customers/search', {
    params: {
      name: params.name, // 单独传递name参数
      page: params.page, // 传递page参数
      size: params.size // 传递size参数
    }
  });
}

export {
    getAllCustomers,
    getCustomerById,
    addCustomer,
    updateCustomer,
    deleteCustomer,
    searchCustomersByName
}