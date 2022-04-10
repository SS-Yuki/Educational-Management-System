<template>
  <div>
    <div>
      <h1>复日大学教务系统</h1>
    </div>
    <div class="login">
      <el-form :model="login_data" size="normal">
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
        if (res.code === '200') {
          this.$message({
            type: "success",
            message: "登录成功"
          })
          // if (res.data.number.length)
        //  根据学号位数进行跳转
        }

        else {
          this.$message({
            type: "error",
            message: res.msg
          })
          // this.$route.push("/register")
        }
      })
    }
  }
}


</script>

<style scoped>
.login {
  width: 300px;
  height: 500px;
  margin: 100px auto;
}

</style>