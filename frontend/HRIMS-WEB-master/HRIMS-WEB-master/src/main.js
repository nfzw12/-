import { createApp } from 'vue'
import App from './App.vue'
import store from './store';
// 自定义路由
import router from './router'

// element-plus相关
import 'element-plus/dist/index.css'
import ElementPlus from 'element-plus'
import zhCn from 'element-plus/es/locale/lang/zh-cn'


// 创建实例
const app = createApp(App)

// 全局引入
app.use(ElementPlus, {locale: zhCn})
app.use(router)
app.use(store)

app.mount('#app')
