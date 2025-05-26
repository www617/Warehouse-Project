package com.neuedu.yyzx.service.wsj;


import com.neuedu.yyzx.pojo.wsj.Customers;
import java.util.List;

public interface CustomersService {
    // 查询所有顾客
    List<Customers> getAllCustomers();

    // 根据ID查询顾客
    Customers getCustomerById(Integer id);

    // 新增顾客
    int addCustomer(Customers customers);

    // 更新顾客信息
    int updateCustomer(Customers customers);

    // 根据ID删除顾客
    int deleteCustomer(Integer id);

    // 新增：根据姓名查询顾客
    List<Customers> getCustomersByName(String name);


}