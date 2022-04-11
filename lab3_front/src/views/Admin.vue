<template>
  <div class="header">
    复旦大学管理员系统
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
              <el-menu-item index="1-1">
                <router-link to="/admin/checkteacherinfo">查看老师信息</router-link>
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
                <router-link to="/admin/checkmajor">专业信息维护</router-link>
              </el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group>
              <el-menu-item index="1-2">
                <router-link to="/admin/checkschool">学院信息维护</router-link>
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
export default {
  name: "Admin",
  components:{

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
</style>