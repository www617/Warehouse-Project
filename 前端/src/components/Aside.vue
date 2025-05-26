<template>
  <el-aside id="asideNav" width="200px">
    <div class="logo-name">
      <p>东软颐养中心</p>
      <img src="../assets/logo.png" class="logo-png"/>
    </div>

    <el-menu
      active-text-color="#ffd04b"
      background-color="#3CA2E0"
      :default-active="$route.path"
      :router="true"
      unique-opened
      text-color="#fff">

      <el-menu-item index="/">
        <el-icon><HomeFilled /></el-icon>
        <span>首页</span>
      </el-menu-item>

      <el-sub-menu index="customer"> 
        <template #title>
          <el-icon><Location /></el-icon>
          <span>客户管理</span>
        </template>
          <el-menu-item index="/customer/customer">
          <el-icon><DArrowRight /></el-icon>
          <span>顾客信息</span>
        </el-menu-item>
        <el-menu-item index="/customer/checkIn">
          <el-icon><DArrowRight /></el-icon>
          <span>入住登记</span>
        </el-menu-item>
        <el-menu-item index="/customer/checkOut">
          <el-icon><DArrowLeft /></el-icon>
          <span>退住登记</span>
        </el-menu-item>
      </el-sub-menu>

      <el-sub-menu index="nurse"> 
        <template #title>
          <el-icon><SuitcaseLine /></el-icon>
          <span>护理管理</span>
        </template>
        <el-menu-item index="/nurse/nurseLevel">
          <el-icon><Sort /></el-icon>
          <span>护理级别</span>
        </el-menu-item>
        <el-menu-item index="/nurse/nurseRecords">
          <el-icon><ScaleToOriginal /></el-icon>
          <span>护理记录</span>
        </el-menu-item>
      </el-sub-menu>



    </el-menu>
  </el-aside>
</template>

<script>
export default {
  name: 'Aside',
  watch: {
    "$route.path": {
      handler: function (newVal, oldVal) {
        if (newVal === "/login") return;
        const tabs = this.$store.getters.tabs;
        const hasTab = tabs.some(tab => tab.path === newVal);
        if (!hasTab) {
          const menuName = this.getMenuNameByUrl(newVal);
          this.$store.commit("addTab", { title: menuName, path: newVal });
        }
      },
      immediate: true,
    },
  },
  methods: {
    getMenuNameByUrl(path) {
      const menuMap = {
        "/customer/customer": "顾客信息",
        "/customer/checkIn": "入住登记",
        "/customer/checkOut": "退住登记",
        "/nurse/nurseLevel": "护理级别",
        "/nurse/nurseRecords": "护理记录",
        "/": "首页",
      };
      return menuMap[path] || "未知页面";
    },
  },
};
</script>

<style scoped>
#asideNav {
  display: flex;
  flex-direction: column;
}

#asideNav .logo-name {
  width: 100%;
  height: 150px;
  background-color:#3CA2E0;
}
#asideNav .logo-name .logo-png{
  width: 165px;
  height: 165px;
  margin: -30px 0 0 10px;
}
#asideNav .logo-name p {
  font-weight: 800;
  height: 50px;
  line-height: 50px;
  text-align: center;
  font-size: 25px;
  margin: 0px;
  color: #fff;
}

#asideNav .el-menu {
  flex: 1;
  border-right: none;
}
</style>