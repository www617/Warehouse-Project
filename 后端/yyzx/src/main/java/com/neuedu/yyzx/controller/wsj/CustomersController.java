package com.neuedu.yyzx.controller.wsj;


import com.neuedu.yyzx.pojo.wsj.Customers;
import com.neuedu.yyzx.service.wsj.CustomersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wsj/customers")

@Api(tags = "疗养中心-顾客管理")
@CrossOrigin(origins = "http://localhost:8080")
public class CustomersController {

    @Autowired
    private CustomersService customersService;

    @GetMapping("/all")
    @ApiOperation("获取所有顾客信息")
    public List<Customers> getAllCustomers() {
        return customersService.getAllCustomers();
    }

    //@PathVariable 假设您有一个 URL 路径：/api/users/{id}，其中 {id} 是一个动态变量。使用 @PathVariable 可以将这个变量值绑定到方法参数上：
    @GetMapping("/{id}")
    @ApiOperation("根据ID获取顾客信息")
    public Customers getCustomerById(@PathVariable Integer id) {
        return customersService.getCustomerById(id);
    }

    //@RequestBody获取请求路径里面的数据
    @PostMapping("/add")
    @ApiOperation("新增顾客")
    public int addCustomer(@RequestBody Customers customers) {
            // 调用服务层方法，根据返回结果判断是否成功（假设返回1表示成功，0表示失败）
            int result = customersService.addCustomer(customers);
            return result > 0 ? 1 : 0; // 新增成功返回1，失败返回0
        }

        @PutMapping("/update")
        @ApiOperation("更新顾客信息")
        public int updateCustomer(@RequestBody Customers customers) {
            // 调用服务层更新方法，根据影响行数判断是否成功
            int updateResult = customersService.updateCustomer(customers);
            return updateResult > 0 ? 1 : 0; // 更新成功返回1，失败返回0
        }

        @DeleteMapping("/delete/{id}")
        @ApiOperation("根据ID删除顾客")
        public int deleteCustomer(@PathVariable Integer id) {
            // 调用服务层删除方法，根据影响行数判断是否成功
            int deleteResult = customersService.deleteCustomer(id);
            return deleteResult > 0 ? 1 : 0; // 删除成功返回1，失败返回0
        }

    /**
     * 根据姓名模糊查询顾客
     * @param name 姓名（支持模糊查询）
     * @return 顾客列表
     */
    //@RequestParam 是 Spring MVC 中用于获取 HTTP 请求参数的注解，主要用于从 URL 查询字符串或表单数据中提取参数-单独获取name
    @GetMapping("/search") // 定义查询接口路径
    @ApiOperation("根据姓名模糊查询顾客")
    public List<Customers> searchCustomersByName(@RequestParam String name) {
        return customersService.getCustomersByName(name);
    }

}