import { createApp } from 'vue';

import ElementPlus from 'element-plus';
import 'element-plus/dist/index.css'; // 样式必须最先引入


import * as ElementPlusIconsVue from '@element-plus/icons-vue';
import App from './App.vue';
import router from './router';
import store from './store';

const app = createApp(App);

// 1. 先注册 ElementPlus 组件（确保组件全局可用）
app.use(ElementPlus);

// 2. 再注册图标（图标作为全局组件，可在 ElementPlus 之后注册）
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component);
}

// 3. 最后注册路由和状态管理（顺序不影响功能，但逻辑上后置更清晰）
app.use(store).use(router).mount('#app');