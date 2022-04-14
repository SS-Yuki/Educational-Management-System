<template>
  <div>
    <div class="header">
      复旦大学教师系统
    </div>
  <UserInfo/>
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
                <router-link to="/teacher/teachercourse">课程信息维护</router-link>
              </el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group>
              <el-menu-item index="1-2">
                <router-link to="/teacher/teacherapplication">查看已提交申请</router-link>
              </el-menu-item>
            </el-menu-item-group>
          </el-sub-menu>
        </el-menu>
      </el-col>
    </el-row>
  </div>

</template>

<script>
import UserInfo from "@/components/UserInfo";
export default {
  name: "Teacher",

  components:{
    UserInfo
  },
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
a {
  text-decoration: none;
}

.router-link-active {
  text-decoration: none;
}
</style>