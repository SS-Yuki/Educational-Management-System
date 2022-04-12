<template>
  <div>
    <div class="user">
      <el-dropdown>
        <span class="el-dropdown-link">
          你好, {{user_num}}
          <el-icon class="el-icon--right">
            <arrow-down/>
          </el-icon>
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="pop">修改密码</el-dropdown-item>
            <el-dropdown-item @click="return_home">返回首页</el-dropdown-item>
            <el-dropdown-item divided @click="logout">登出</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>

    <div>
      <el-dialog v-model="dialogVisible" title="Tips" width="30%">
        <el-form :model="change_pass_set" label-width="120px">
          <el-form-item label="旧密码">
            <el-input v-model="change_pass_set.old_pass" />
          </el-form-item>
          <el-form-item label="新密码">
            <el-input v-model="change_pass_set.new_pass" />
          </el-form-item>
        </el-form>
        <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="change_pass">确认</el-button>
      </span>
        <template #footer>
        </template>
      </el-dialog>
    </div>

  </div>
</template>

<script>
import request from "@/utils/request";
import {createRouter, createWebHistory} from "vue-router";
import router, {routes} from "@/router";
export default {
  name: "UserInfo",
  data() {
    return {
      user_num: '',
      dialogVisible:false,
      change_pass_set: {
        old_pass: '',
        new_pass: ''
      }
    }
  },
  components:{
  },
  mounted() {
    this.user_num = JSON.parse(sessionStorage.getItem("user")).number
  },
  methods: {
    logout: function () {
      request.post("/user/logout", JSON.parse(sessionStorage["user"]).token).then(res => {
        console.log(res)
        console.log(res.data.code)
        if (res.data.code === 200) {
          this.$message({
            type: "success",
            message: res.data.msg
          })
        }
        router = createRouter({
          history: createWebHistory(process.env.BASE_URL),
          routes
        })
        sessionStorage["routes"] = JSON.stringify(router.getRoutes())
        sessionStorage.removeItem("user")
        this.$router.push("/login")
      })
    },
    pop: function () {
      this.dialogVisible=true
    },
    change_pass: function () {
      request.post("/user/changePassword", this.change_pass_set).then(res => {
        console.log(res)
        if (res.data.code === 200) {
          this.$message({
            type: "success",
            message: res.data.msg
          })
        }
        else {
          this.$message({
            type: "error",
            message: res.data.msg
          })
        }
      })
    },
    return_home: function () {
      let user = JSON.parse(sessionStorage.getItem("user"))
      if (user.role === "admin") {
        this.$router.push("/admin")
      }
      else if (user.role === "teacher"){
        this.$router.push("/teacher")
      }
      else if (user.role === "student") {
        this.$router.push("/student")
      }
    }
  }
}
</script>

<style scoped>
.user {
  float: right;
}
</style>