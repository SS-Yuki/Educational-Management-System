import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import * as ElIconModules from '@element-plus/icons'

import '@/assets/css/global.css'
import locale from 'element-ui/lib/locale/lang/zh-CN'

const app = createApp(App);
app.use(store).use(ElementPlus, { locale, size: 'small' }).use(router).mount('#app')
for(let iconName in ElIconModules){
    app.component(iconName,ElIconModules[iconName])
}