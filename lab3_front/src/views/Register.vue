<template>
  <div>
    <div>Register</div>
    <div>
      <div class="register">
        <el-form :model="register_data" label-width="120px">
          <el-form-item label="身份">
            <el-radio-group v-model="register_data.role">
              <el-radio label="学生" />
              <el-radio label="教师" />
            </el-radio-group>
          </el-form-item>
          <el-form-item label="学号/工号">
            <el-input v-model="register_data.number" />
          </el-form-item>
          <el-form-item label="姓名">
            <el-input v-model="register_data.name" />
          </el-form-item>
          <el-form-item label="身份证号">
            <el-input v-model="register_data.idcard" />
          </el-form-item>
          <el-form-item label="手机号">
            <el-input v-model="register_data.phone" />
          </el-form-item>
          <el-form-item label="电子邮箱">
            <el-input v-model="register_data.email" />
          </el-form-item>
          <el-form-item label="密码">
            <el-input v-model="register_data.password" />
          </el-form-item>
          <el-button style="width: 100%" type="primary" @click="register">
            注册
          </el-button>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Register",
  data() {
    return {
      register_data: {
        role: '',
        number: '',
        name: '',
        idcard: '',
        phone: '',
        email: '',
        password: ''
      }
    }
  },
  methods: {
    register: function () {
      request.post("/user/register", this.register_data).then(res => {
        console.log(res)
      })
    }
  }
}
</script>

<style scoped>
.register {
  width: 300px;
  height: 500px;
  margin: 20px auto;
}
</style>