<template>
  <div>
    <div class="header">
      复旦大学教师系统
    </div>
    <div class="user">
      <el-dropdown>
        <span class="el-dropdown-link">
          你好, {{user_name}}
          <el-icon class="el-icon--right">
            <arrow-down/>
          </el-icon>
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="change_password">修改密码</el-dropdown-item>
            <el-dropdown-item @click="this.$router.push('/teacher')">返回首页</el-dropdown-item>
            <el-dropdown-item divided @click="logout">登出</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
    <div>
      <el-dialog v-model="dialogVisible" title="Tips" width="30%">
        <el-form :model="new_info" label-width="120px">
          <el-form-item label="邮箱">
            <el-input v-model="new_info.email" />
          </el-form-item>
          <el-form-item label="电话">
            <el-input v-model="new_info.phoneNum" />
          </el-form-item>
        </el-form>
        <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="send_newinfo">确认</el-button>
      </span>
        <template #footer>
        </template>
      </el-dialog>
    </div>
  </div>
  <el-divider />
  <div class="teacher_menu">
    <el-row class="tac" style="width: 400px">
      <el-col :span="12">
        <el-menu
            default-active="2"
            class="el-menu-vertical-demo"
            @open="handleOpen"
            @close="handleClose"
        >
          <el-sub-menu index="1">
            <template #title>
              <el-icon><location /></el-icon>
              <span>信息维护</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="1-1">
                <router-link to="/teacher/teacherinfo">个人信息维护</router-link>
                <router-view></router-view>
              </el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group>
              <el-menu-item index="1-2">
                <router-link to="/teacher/checkcourse">课程信息维护</router-link>
              </el-menu-item>
            </el-menu-item-group>
          </el-sub-menu>
        </el-menu>
      </el-col>
    </el-row>
  </div>

</template>

<script>
import request from "@/utils/request";
import {createRouter, createWebHistory} from "vue-router";
import router, {routes} from "@/router";
export default {
  name: "Teacher",
  data() {
    return {
      user_name: '',
      dialogVisible:false,
    }
  },
  components:{
  },
  mounted() {
    this.user_name = JSON.parse(sessionStorage.getItem("user")).number

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
    change_password: function () {

    },
    input_newinfo: function (){
      this.dialogVisible=true
      this.new_info={}
    }
  }
}
</script>

<style scoped>
.teacher_menu{
  width:400px;
  display: flex;
  position:relative;
  box-sizing: border-box;
  float: left;
}
.header{
  height:60px;
  font-size: 50px;
  color: transparent;
  background-color : black;
  text-shadow : rgba(255,255,255,0.5) 0 5px 6px, rgba(255,255,255,0.2) 1px 3px 3px;
  -webkit-background-clip : text;
}

.user {
  float: right;
}
</style>