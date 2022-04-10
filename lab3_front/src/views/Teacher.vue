<template>
  <header msg="复旦大学教师系统"/>
  <div class="teacher_menu">
    <el-row class="tac">
      <el-col :span="12">
        <h5 class="mb-2">Default colors</h5>
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
              <el-menu-item index="1-1">个人信息维护</el-menu-item>
            </el-menu-item-group>
            <el-sub-menu index="1-4">
              <template #title>课程信息维护</template>
              <el-menu-item index="1-4-1">新增课程</el-menu-item>
              <el-menu-item index="1-4-1">修改课程信息</el-menu-item>
            </el-sub-menu>
          </el-sub-menu>
        </el-menu>
      </el-col>
    </el-row>
  </div>
  <div>
    <change_info/>
  </div>
</template>

<script>
import change_info from "@/components/change_info";
import header from "@/components/header";
export default {
  name: "Teacher",
  components:{
    change_info,
    header
  }
}
</script>

<style scoped>

</style>