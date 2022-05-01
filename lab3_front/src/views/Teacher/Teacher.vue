<template>
  <div>
    <el-container>
      <el-header height="60px">
        <span class="title">复旦大学教师系统</span>
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
                    <el-icon><location /></el-icon>
                    <span>信息维护</span>
                  </template>
                  <el-menu-item-group>
                    <el-menu-item index="1-1">
                      <router-link to="/teacher/teacherinfo">个人信息维护</router-link>
                    </el-menu-item>
                  </el-menu-item-group>
                  <el-menu-item-group>
                    <el-menu-item index="1-2">
                      <router-link to="/teacher/teachercourse">课程信息维护</router-link>
                    </el-menu-item>
                  </el-menu-item-group>
                  <el-menu-item-group>
                    <el-menu-item index="1-3">
                      <router-link to="/teacher/teacherapplication">查看已提交申请</router-link>
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
  name: "Teacher",
  components:{
    UserInfo
  },
}
</script>

<style scoped>
.title{
  font-size: 50px;
  color: transparent;
  background-color : black;
  text-shadow : rgba(255,255,255,0.5) 0 5px 6px, rgba(255,255,255,0.2) 1px 3px 3px;
  -webkit-background-clip : text;
}
</style>