<template>
  <!-- 首页内容将显示在布局组件的 <router-view> 中 -->
  <div class="home-page">
    <h2>欢迎来到东软颐养中心</h2>
    <p>这是顾客登记表</p>
    <p>当前时间：{{currentTime}}</p>
  </div>
</template>

<script>
export default {
  data() {
    return {
      currentTime: new Date().toLocaleString()
    }
  },
  mounted() {
    // 实时更新时间（示例）
    setInterval(() => {
      this.currentTime = new Date().toLocaleString()
    }, 1000)
  }
}
</script>

<style scoped>
.home-page {
  padding: 30px;
  background: #fff;
  border-radius: 8px;
  margin-top: 20px;
}
</style>