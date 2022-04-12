<template>
  <div>
    <div class="header">
      复旦大学教师系统
      <el-dropdown>
        <span class="el-dropdown-link">
          你好,
          <el-icon class="el-icon--right">
            <arrow-down/>
          </el-icon>
        </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item>修改密码</el-dropdown-item>
                <el-dropdown-item>返回首页</el-dropdown-item>
                <el-dropdown-item divided>登出</el-dropdown-item>
              </el-dropdown-menu>
            </template>
      </el-dropdown>
    </div>
    <div>
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
import { ArrowDown } from '@element-plus/icons-vue'
export default {
  name: "Teacher",
  components:{
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
</style>