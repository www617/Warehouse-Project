<template>
  <div class="customer-management">
    <div class="header">
      <h2>顾客管理</h2>
      <div class="search-bar">
        <el-input v-model="condition.customerName" placeholder="请输入姓名" clearable @clear="handleSearch" />
        <el-button type="primary" @click="handleSearch">搜索</el-button>
        <el-button type="success" @click="handleAdd">新增顾客</el-button>
      </div>
    </div>

    <el-table :data="customerList" stripe border fit highlight-current-row @row-click="handleRowClick">
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="name" label="姓名">
        <template #default="scope">
          <span>{{ scope.row.name || '未填写' }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="sex" label="性别" width="80">
        <template #default="scope">
          <span>{{ scope.row.sex === 0 ? '女' : '男' }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="phoneNumber" label="电话">
        <template #default="scope">
          <span>{{ scope.row.phoneNumber || '未填写' }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="status" label="状态" width="100">
        <template #default="scope">
          <el-tag :type="getStatusType(scope.row.status)">{{ getStatusText(scope.row.status) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" width="180">
        <template #default="scope">
          <span>{{ scope.row.createTime ? formatDate(scope.row.createTime) : '未记录' }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="180">
        <template #default="scope">
          <el-button type="primary" size="small" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button type="danger" size="small" @click="handleDelete(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-empty v-if="customerList.length === 0" description="暂无顾客数据" />

    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="page.currentPage"
      :page-sizes="[10, 20, 30]"
      :page-size="page.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="page.total"
    />

    <!-- 使用 v-model:visible 绑定对话框显示状态 -->
    <el-dialog v-model="dialog.dialogVisible" title="顾客信息" @close="resetForm" :style="{ width: '600px' }">
      <el-form :model="dialog.item" ref="formRef" label-width="100px">
        <el-form-item label="姓名" prop="name">
          <el-input v-model="dialog.item.name" required />
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-radio-group v-model="dialog.item.sex">
            <el-radio :label="1">男</el-radio>
            <el-radio :label="0">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="电话" prop="phoneNumber">
          <el-input v-model="dialog.item.phoneNumber" />
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="dialog.item.email" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="dialog.item.status" placeholder="请选择状态"  :style="{ width: '200px' }">
            <el-option label="未登记" value="0" />
            <el-option label="入住" value="1" />
            <el-option label="退住" value="2" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialog.dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="saveCustomer">保存</el-button>
        </span>
      </template>
    </el-dialog>

    <el-dialog v-model="dialog.deleteDialogVisible" title="确认删除" width="300px">
      <template #content>
        <p>确定要删除该顾客信息吗？</p>
      </template>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialog.deleteDialogVisible = false">取消</el-button>
          <el-button type="danger" @click="confirmDelete">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import { ElMessage } from 'element-plus';
import { 
  getAllCustomers, 
  getCustomerById, 
  addCustomer, 
  updateCustomer, 
  deleteCustomer,
  searchCustomersByName // ✅ 必须包含这一行
} from '@/api/wsj/customer.js';
import { getCurDate } from '@/utils/common.js';

export default {
  data() {
    return {
      // 分页属性
      page: {
        total: 0,
        pageSize: 10,
        currentPage: 1, // 修正拼写错误：currentPag -> currentPage
        pagCount: 0
      },
      // 查询条件
      condition: {
        customerName: '',
        pageSize: 10 // 修正：pageSize应该是每页数量
      },
      // 顾客列表
      customerList: [],
      // 对话框数据
      dialog: {
        dialogVisible: false,  // 顾客信息对话框
        deleteDialogVisible: false,  // 删除确认对话框
        tops: '',  // 对话框标题
        item: {
          id: null,
          name: '',
          sex: 1,
          phoneNumber: '',
          email: '',
          status: 0
        }
      }
    };
  },
  computed: {
    // 分页序号不重置
    indexMethod() {
      return (this.page.currentPage - 1) * this.page.pageSize + 1;
    }
  },
  methods: {
// 获取顾客列表
async fetchCustomerList() {
  try {
    const { customerName } = this.condition;
    const params = {
      page: this.page.currentPage,
      size: this.page.pageSize
    };

    let response;
    if (customerName) {
      // 有搜索词时，调用搜索接口（带name参数）
      response = await searchCustomersByName({ ...params, name: customerName });
      console.log('请求参数:', params); // 检查page和size是否为数字
    } else {
      // 无搜索词时，调用原接口获取所有顾客
      response = await getAllCustomers(params);
    }

    // 统一处理响应数据
    if (Array.isArray(response)) {
      this.customerList = response || [];
      this.page.total = response.length;
    } else if (response.data && Array.isArray(response.data.records)) {
      this.customerList = response.data.records || [];
      this.page.total = response.data.total || 0;
    } else {
      this.customerList = [];
      this.page.total = 0;
      ElMessage.warning('获取顾客列表格式异常');
    }
  } catch (error) {
    console.error('获取顾客列表失败:', error);
    ElMessage.error('获取顾客列表失败，请稍后重试');
    this.customerList = [];
    this.page.total = 0;
  }
},
    
    // 搜索功能
    handleSearch() {
      this.page.currentPage = 1;
      this.fetchCustomerList();
    },
    
    // 分页功能
    handleSizeChange(size) {
      this.page.pageSize = size;
      this.fetchCustomerList();
    },
    
    handleCurrentChange(page) {
      this.page.currentPage = page;
      this.fetchCustomerList();
    },
    
    // 格式化日期
    formatDate(timestamp) {
      if (!timestamp) return '未记录';
      
      // 处理不同格式的时间戳
      if (typeof timestamp === 'string') {
        // 处理类似 "2023-10-01" 的字符串格式
        return new Date(timestamp).toLocaleString();
      }
      
      // 处理时间戳数字
      return new Date(timestamp).toLocaleString();
    },
    
    // 状态文本和样式
    getStatusText(status) {
      const statusMap = { 0: '未登记', 1: '入住', 2: '退住' };
      return statusMap[status] || '未知';
    },
    
    getStatusType(status) {
      const typeMap = { 0: 'info', 1: 'success', 2: 'warning' };
      return typeMap[status] || 'default';
    },
    
    // 行点击事件
    handleRowClick(row) {
      Object.assign(this.dialog.item, row);
    },
    
    // 新增顾客
    handleAdd() {
      this.resetForm();
      this.dialog.tops = '新增顾客';
      this.dialog.dialogVisible = true;
    },
    
    // 编辑顾客
    async handleEdit(row) {
      try {
        const customer = await getCustomerById(row.id);
        
        if (!customer) {
          ElMessage.error('顾客信息不存在');
          return;
        }
        
        Object.assign(this.dialog.item, {
          ...customer,
          name: customer.name || '',
          email: customer.email || '',
          phoneNumber: customer.phoneNumber || ''
        });
        
        this.dialog.tops = '编辑顾客';
        this.dialog.dialogVisible = true;
      } catch (error) {
        console.error('获取顾客信息失败:', error);
        ElMessage.error('获取顾客信息失败，请稍后重试');
      }
    },
    
    // 重置表单
    resetForm() {
      this.dialog.item = {
        id: null,
        name: '',
        sex: 1,
        phoneNumber: '',
        email: '',
        status: 0
      };
      
      // 重置表单验证状态
      this.$nextTick(() => {
        if (this.$refs.formRef) {
          this.$refs.formRef.resetFields();
        }
      });
    },
    
    // 关闭对话框
    handleClose() {
      this.dialog.dialogVisible = false;
      this.resetForm();
    },
    
    // 保存顾客信息
    saveCustomer() {
      if (!this.$refs.formRef) return;
      
      this.$refs.formRef.validate(valid => {
        if (valid) {
          this.submitForm();
        } else {
          console.log('表单验证失败');
          return false;
        }
      });
    },
    
// 提交表单
async submitForm() {
  try {
    const response = this.dialog.item.id 
      ? await updateCustomer({ ...this.dialog.item }) 
      : await addCustomer({ ...this.dialog.item, createTime: getCurDate() });

    // 后端返回 1 表示成功，其他值表示失败
    if (response === 1) {
      this.dialog.dialogVisible = false;
      this.fetchCustomerList();
      ElMessage.success(this.dialog.item.id ? '更新成功' : '新增成功');
    } else {
      // 可根据后端返回的具体错误码添加不同提示（若有）
      ElMessage.error('操作失败，可能输入有误或数据重复');
    }
  } catch (error) {
    // 处理网络异常（如超时、404 等）
    if (error.response) {
      ElMessage.error(`请求失败，状态码：${error.response.status}`);
    } else {
      ElMessage.error('操作失败，请重试');
    }
    console.error('保存失败:', error);
  }
},
    
    // 删除顾客
    handleDelete(id) {
      this.dialog.item.id = id;
      this.dialog.deleteDialogVisible = true;
    },
    
// 确认删除
async confirmDelete() {
  try {
    const response = await deleteCustomer(this.dialog.item.id);
    
    if (response === 1) {
      this.dialog.deleteDialogVisible = false;
      this.fetchCustomerList();
      ElMessage.success('删除成功');
    } else {
      ElMessage.error('删除失败，该顾客可能已关联其他数据');
    }
  } catch (error) {
    ElMessage.error('删除操作网络请求失败');
    console.error('删除失败:', error);
  }
}
  },
  mounted() {
    this.fetchCustomerList();
  }
};
</script>

<style scoped>
.customer-management {
  padding: 20px;
  background: #fff;
  border-radius: 8px;
  margin-top: 20px;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.search-bar {
  display: flex;
  gap: 10px;
}

.el-input {
  width: 200px;
}

.el-table {
  margin-bottom: 20px;
}

.el-pagination {
  display: flex;
  justify-content: flex-end;
}
</style>