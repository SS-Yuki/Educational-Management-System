<template>
  <div class="header">
    复旦大学管理员系统
  </div>

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
          <el-dropdown-item @click="this.$router.push('/admin')">返回首页</el-dropdown-item>
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


  <el-divider />
  <div class="admin_menu">
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
              <span>用户信息管理</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="1-1">
                <router-link to="/admin/checkstudentinfo">查看学生信息</router-link>
              </el-menu-item>
              <el-menu-item index="1-2">
                <router-link to="/admin/checkteacherinfo">查看老师信息</router-link>
              </el-menu-item>
            </el-menu-item-group>
          </el-sub-menu>
          <el-sub-menu index="1">
            <template #title>
              <el-icon><location /></el-icon>
              <span>院系信息管理</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="1-1">
                <router-link to="/admin/checkmajor">查看专业</router-link>
              </el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group>
              <el-menu-item index="1-2">
                <router-link to="/admin/checkschool">查看院系</router-link>
              </el-menu-item>
            </el-menu-item-group>
          </el-sub-menu>
          <el-sub-menu index="1">
            <template #title>
              <el-icon><location /></el-icon>
              <span>教务信息管理</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="1-1">
                <router-link to="/admin/checkbuilding">教学楼管理</router-link>
              </el-menu-item>
              <el-menu-item index="1-2">
                <router-link to="/admin/checkclassroom">教室管理</router-link>
              </el-menu-item>
              <el-menu-item index="1-3">
                <router-link to="/admin/checktime">上课时间管理</router-link>
              </el-menu-item>
            </el-menu-item-group>
          </el-sub-menu>

          <el-sub-menu index="1">
            <template #title>
              <el-icon><location /></el-icon>
              <span>课程信息管理</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="1-1">
                <router-link to="/admin/checkcourse">课程管理</router-link>
              </el-menu-item>
              <el-menu-item index="1-2">
                <router-link to="/admin/checkapplication">教师申请管理</router-link>
              </el-menu-item>
              <el-menu-item index="1-3">
                <router-link to="/admin/checkteacherinfo">

                </router-link>
              </el-menu-item>
            </el-menu-item-group>
          </el-sub-menu>

        </el-menu>
      </el-col>
    </el-row>
  </div>

  <div>
    <router-view></router-view>
  </div>

</template>

<script>
import router, {routes} from "@/router";
import store from "@/store";
import {createRouter, createWebHistory} from "vue-router";
import request from "@/utils/request";


export default {
  name: "Admin",

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
  mounted() {
    this.user_num = JSON.parse(sessionStorage.getItem("user")).number
  },

  components:{

  },
  methods: {

  //   logout: function (){
  //     request.post("/user/logout", JSON.parse(sessionStorage["user"]).token).then(res => {
  //       console.log(res)
  //       console.log(res.data.code)
  //       if (res.data.code === 200) {
  //         this.$message({
  //           type: "success",
  //           message: res.data.msg
  //         })
  //       }
  //       router = createRouter({
  //         history: createWebHistory(process.env.BASE_URL),
  //         routes
  //       })
  //       console.log("---")
  //       //sessionStorage.setItem('routes', JSON.stringify(router.getRoutes()));
  //       sessionStorage["routes"] = JSON.stringify(router.getRoutes())
  //       sessionStorage.removeItem("user")
  //       //sessionStorage.clear()
  //       console.log(sessionStorage.getItem("routes"))
  //       console.log("---")
  //       this.$router.push("/login")
  //       console.log("tui chu le")
  //       console.log(router.getRoutes())
  //       console.log("hahaha")
  //     })
  //   }

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
    }

  }
}
</script>

<style scoped>
.admin_menu{
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