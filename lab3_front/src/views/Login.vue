<template>
  <div>
    <div>
      <h1>复日大学教务系统</h1>
    </div>
    <div class="login" @keyup.enter="login">
      <el-form :model="login_data" size="normal" :rules="rules">
        <el-form-item prop="number">
          <el-input prefix-icon="User" v-model="login_data.number"/>
        </el-form-item>
        <el-form-item prop="pass
        word">
          <el-input show-password prefix-icon="Lock" v-model="login_data.password" />
        </el-form-item>
        <el-button style="width: 100%" type="primary" @click="login">
          登录
        </el-button>
      </el-form>
    </div>
  </div>

</template>


<script>
import request from "@/utils/request";
import {admin_routes} from "@/router";
import {student_routes} from "@/router";
import {teacher_routes} from "@/router";
import router from "@/router";

function add_routes () {
  var cache = [];
  sessionStorage['routes'] = JSON.stringify(router.getRoutes(), function(key, value) {
      if (typeof value === 'object' && value !== null) {
          if (cache.indexOf(value) !== -1) {
              return;
          }
          cache.push(value);
      }
      return value;
  });
  cache = null;
}

export default {
  name: "Login",
  data() {
    return {
      login_data: {
        number: '',
        password: ''
      },
      rules: {
        number: [
          { required: true, message: '请输入学/工号', trigger: 'blur' },
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    login: function (){
      request.post("/user/login", this.login_data).then(res => {

        if (res.data.code === 200) {
          this.$message({
            type: "success",
            message: res.data.msg
          })
          sessionStorage.setItem("user", JSON.stringify(res.data.data))

          if (res.data.data.role === "admin") {
            router.addRoute(admin_routes)
            add_routes()
            router.push('/admin')
            setTimeout(() => {
              router.go(0)
            }, 10);

          }
          else if (res.data.data.role === "student") {
            router.addRoute(student_routes)
            add_routes()
            router.push('/student')
            setTimeout(() => {
              router.go(0)
            }, 10);

          }
          else if (res.data.data.role === "teacher") {
            router.addRoute(teacher_routes)
            add_routes()
            router.push('/teacher')
            setTimeout(() => {
              router.go(0)
            }, 10);
          }
        }
        else {

          this.$message({
            type: "error",
            message: res.data.msg
          })
        }

      })
    }
  },
}


</script>

<style scoped>
.login {
  width: 300px;
  height: 500px;
  margin: 100px auto;
}

</style>