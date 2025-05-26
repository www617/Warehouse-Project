package com.neuedu.yyzx.service.impl.wsj;


import com.neuedu.yyzx.mapper.wsj.CustomersMapper;
import com.neuedu.yyzx.pojo.wsj.Customers;
import com.neuedu.yyzx.service.wsj.CustomersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CustomersServiceImpl implements CustomersService {

    @Autowired
    private CustomersMapper customersMapper;

    @Override
    public List<Customers> getAllCustomers() {
        return customersMapper.selectAll();
    }

    @Override
    public Customers getCustomerById(Integer id) {
        return customersMapper.selectById(id);
    }

    @Override
    public int addCustomer(Customers customers) {
        // 设置创建时间和更新时间
        Date now = new Date();
        customers.setCreateTime(now);
        customers.setUpdateTime(now);
        return customersMapper.insert(customers);
    }

    @Override
    public int updateCustomer(Customers customers) {
        // 设置更新时间
        customers.setUpdateTime(new Date());
        return customersMapper.update(customers);
    }

    @Override
    public int deleteCustomer(Integer id) {
        return customersMapper.deleteById(id);
    }
    // 新增：根据姓名查询顾客
    public List<Customers> getCustomersByName(String name) {
        return customersMapper.selectByName(name);
    }
}