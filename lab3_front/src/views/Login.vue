<template>
  <div>
    <div>
      <h1>复日大学教务系统</h1>
    </div>
    <div class="login">
      <el-form :model="login_data" size="normal" :rules="rules">
        <el-form-item prop="number">
          <el-input prefix-icon="User" v-model="login_data.number"/>
        </el-form-item>
        <el-form-item prop="password">
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
    test:function () {
      alert(sessionStorage.length)
    },
    login: function (){
      request.post("/user/login", this.login_data).then(res => {
        if (res.code === 200) {
          this.$message({
            type: "success",
            message: "登录成功"
          })
          sessionStorage.setItem("user", JSON.stringify(res.data))
          if (res.data.number.length === 6) {
            this.$router.push('/student')
          }
          else {
            this.$router.push('/teacher')
          }
        }
        else {
          this.$message({
            type: "error",
            message: res.msg
          })
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