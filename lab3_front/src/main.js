import { createApp } from 'vue'
import App from './App.vue'
import router, {admin_routes, student_routes, teacher_routes} from './router'
import store from './store'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import * as ElIconModules from '@element-plus/icons'

import '@/assets/css/global.css'
import locale from 'element-ui/lib/locale/lang/zh-CN'


const app = createApp(App);
{
    if (!sessionStorage.getItem('routes')) {
        sessionStorage.setItem('routes', JSON.stringify(router.getRoutes()));
    }

    let store_routes = JSON.parse(sessionStorage.getItem('routes'))
    let now_routes = router.getRoutes()
    if (now_routes.length < store_routes.length) {
        let role = JSON.parse(sessionStorage.getItem('user')).role
        if (role === "admin") {router.addRoute(admin_routes)}
        else if (role === "teacher") {router.addRoute(teacher_routes)}
        else if (role === "student") {router.addRoute(student_routes)}
    }
}
app.use(store).use(ElementPlus, { locale, size: 'small' }).use(router).mount('#app')
for(let iconName in ElIconModules){
    app.component(iconName,ElIconModules[iconName])
}