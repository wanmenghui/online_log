import Vue from 'vue'
import App from './App.vue'
import axios from 'axios'  //全局引入axios
import VueRouter from 'vue-router' //引入路由
import VueSession from 'vue-session' //引入vue-session
import routes from './router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

Vue.use(VueRouter)
Vue.use(VueSession)
Vue.use(ElementUI);

Vue.config.productionTip = false
Vue.prototype.axios = axios  //别的vue文件中需要发请求时，只需要this.axios({})

const router = new VueRouter({
  mode: 'history',
  routes: routes
})

//配置跨域
axios.defaults.baseURL = '/api'

new Vue({
  el: '#app',
  router,
  render: h => h(App)
}).$mount('#app')