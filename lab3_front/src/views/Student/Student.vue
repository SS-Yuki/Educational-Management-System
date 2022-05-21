<template>
  <div>
    <el-container>
      <el-header height="60px">
        <span class="title">复旦大学学生系统</span>
        <UserInfo/>
      </el-header>
      <el-divider/>
      <el-container>
        <el-aside width="200px">
          <el-row class="tac" style="width: 400px">
            <el-col :span="12">
              <el-menu default-active="2" class="el-menu-vertical-demo"  >
                <el-sub-menu index="1">
                  <template #title>
                    <el-icon><location /></el-icon>
                    <span>信息维护</span>
                  </template>
                  <el-menu-item-group>
                    <el-menu-item index="1-1">
                      <router-link to="/student/studentinfo">个人信息维护</router-link>
                    </el-menu-item>
                  </el-menu-item-group>
                </el-sub-menu>

                <el-sub-menu index="2">
                  <template #title>
                    <el-icon><location /></el-icon>
                    <span>选课相关</span>
                  </template>

                  <el-menu-item-group>
                    <el-menu-item index="2-1">
                      <router-link to="/student/studentchoosecourse">选课</router-link>
                    </el-menu-item>
                  </el-menu-item-group>
                  <el-menu-item-group>
                    <el-menu-item index="2-2">
                      <router-link to="/student/studentdropcourse">退课</router-link>
                    </el-menu-item>
                  <el-menu-item-group>
                    <el-menu-item index="2-3">
                      <router-link to="/student/studentapply">提交选课申请</router-link>
                    </el-menu-item>
                  </el-menu-item-group>
                  <el-menu-item-group>
                    <el-menu-item index="2-4">
                      <router-link to="/student/studentviewapplication">查看选课申请</router-link>
                    </el-menu-item>
                  </el-menu-item-group>

                  </el-menu-item-group>
                </el-sub-menu>

                <el-sub-menu index="3">
                  <template #title>
                    <el-icon><location /></el-icon>
                    <span>查看课程</span>
                  </template>
                  <el-menu-item-group>
                    <el-menu-item index="3-1">
                      <router-link to="/student/studentviewtable">查看课表</router-link>
                    </el-menu-item>
                    <el-menu-item index="3-2">
                      <router-link to="/student/studentfinishedcourse">查看已修课程</router-link>
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
  name: "Student",
  components:{
    UserInfo
  },
}
</script>

<style scoped>

</style>