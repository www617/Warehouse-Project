package com.neuedu.yyzx.mapper.wsj;


import com.neuedu.yyzx.pojo.wsj.Customers;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CustomersMapper {
    // 查询所有顾客
    List<Customers> selectAll();

    // 根据ID查询顾客
    Customers selectById(Integer id);

    // 新增顾客
    int insert(Customers customers);

    // 更新顾客信息
    int update(Customers customers);

    // 根据ID删除顾客
    int deleteById(Integer id);

    // 新增：根据姓名模糊查询顾客（支持模糊匹配）
    List<Customers> selectByName(String name);
}