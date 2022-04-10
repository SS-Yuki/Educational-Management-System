<template>
  <div>
    <div>
      <h1>复日大学教务系统</h1>
    </div>
<!--    <div>-->
<!--      <span>学工号：</span>-->
<!--      <input type="text" placeholder="请输入学工号" name="numberid" v-model="login_data.number">-->
<!--      <span>密码：</span>-->
<!--      <input type="password" placeholder="请输入密码" name="password" v-model="login_data.password">-->
<!--      <input type="button" value="登录" @click="login">-->
<!--      <div>还没有账号？<a href="register.html">点击此处注册</a></div>-->
<!--    </div>-->
    <div class="login">
      <el-form :model="login_data">
        <el-form-item>
          <el-input prefix-icon="User" v-model="login_data.number" />
        </el-form-item>
        <el-form-item>
          <el-input prefix-icon="Lock" v-model="login_data.password" />
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

export default {
  name: "Login",
  data() {
    return {
      login_data: {
        number: '',
        password: ''
      }
    }
  },
  methods: {
    login: function (){
      request.post("/user/login", this.login_data).then(res => {
        console.log(res)
      })
    }
  }
}


</script>

<style scoped>
.login {
  width: 300px;
  height: 500px;
  margin: 20px auto;
}

</style>