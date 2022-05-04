<template>
  <div>
    <el-container>
      <el-header height="60px">
        <span class="title">复旦大学管理员系统</span>
        <UserInfo/>
      </el-header>
      <el-divider/>
      <el-container>
        <el-aside width="200px">
          <el-row class="tac" style="width: 400px">
            <el-col :span="12">
              <el-menu default-active="2" class="el-menu-vertical-demo">
                <el-sub-menu index="1">
                  <template #title>
                    <!--              <el-icon><location /></el-icon>-->
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
                <el-sub-menu index="2">
                  <template #title>
                    <!--              <el-icon><location /></el-icon>-->
                    <span>院系信息管理</span>
                  </template>
                  <el-menu-item-group>
                    <el-menu-item index="2-1">
                      <router-link to="/admin/checkmajor">查看专业</router-link>
                    </el-menu-item>
                  </el-menu-item-group>
                  <el-menu-item-group>
                    <el-menu-item index="2-2">
                      <router-link to="/admin/checkschool">查看院系</router-link>
                    </el-menu-item>
                  </el-menu-item-group>
                </el-sub-menu>
                <el-sub-menu index="3">
                  <template #title>
                    <!--              <el-icon><location /></el-icon>-->
                    <span>教务信息管理</span>
                  </template>
                  <el-menu-item-group>
                    <el-menu-item index="3-1">
                      <router-link to="/admin/checkbuilding">教学楼管理</router-link>
                    </el-menu-item>
                    <el-menu-item index="3-2">
                      <router-link to="/admin/checkclassroom">教室管理</router-link>
                    </el-menu-item>
                    <el-menu-item index="3-3">
                      <router-link to="/admin/checktime">上课时间管理</router-link>
                    </el-menu-item>
                  </el-menu-item-group>
                </el-sub-menu>

                <el-sub-menu index="4">
                  <template #title>
                    <!--              <el-icon><location /></el-icon>-->
                    <span>课程信息管理</span>
                  </template>
                  <el-menu-item-group>
                    <el-menu-item index="4-1">
                      <router-link to="/admin/checkcourse">课程管理</router-link>
                    </el-menu-item>
                    <el-menu-item index="4-2">
                      <router-link to="/admin/checkapplication">教师申请管理</router-link>
                    </el-menu-item>
                    <el-menu-item index="4-3">
                      <router-link to="/admin/checkstudentapply">学生申请管理</router-link>
                    </el-menu-item>
                    <el-menu-item index="4-4">
                      <router-link to="/admin/checkcourseopen">选课开放管理</router-link>
                    </el-menu-item>
                  </el-menu-item-group>
                </el-sub-menu>

              </el-menu>
            </el-col>
          </el-row>
        </el-aside>
        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>

import UserInfo from "@/components/UserInfo";

export default {
  name: "Admin",

  components:{
    UserInfo
  },

}
</script>

<style scoped>

</style>