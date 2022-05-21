<template>
  <div>
    <el-table :data="tableData" style="width:1200px;margin-right: auto;margin-left: auto" >
      <el-table-column prop="courseId" label="courseId" width="200" v-if="false" />
      <el-table-column prop="courseName" label="课程名" width="200" />
      <el-table-column prop="courseNumber" label="课程编号" width="200" />
      <el-table-column prop="teacherNum" label="教师工号" width="200" v-if="false"/>
      <el-table-column prop="major" label="开课专业" width="200" />
      <el-table-column prop="school" label="开课院系" width="200" />
      <el-table-column prop="classroom" label="教室" width="200"  />
      <el-table-column prop="creditHours" label="学时" width="200"  />
      <el-table-column prop="credits" label="学分" width="200"  />
      <el-table-column prop="capacity" label="容量" width="200"  />
      <el-table-column prop="introduction" label="介绍" width="200"  />
      <el-table-column prop="applicant" label="申请人" width="200" v-if="false" />
    </el-table>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "StudentFinishedCourse",
  data() {
    return {
      tableData: [],
    }
  },
  methods:{
    load(){
      setTimeout(() => {
        console.log(this.select_year_semester)
        request.post("/student/allFinishedCourse",{}).then(res=>{
          if(res.data.code===200){
            this.tableData=res.data.data
          }
          else{
            this.$message({
              type:"error",
              message: res.data.msg
            })
          }
        })
      }, 500)
    }
  }
}
</script>

<style scoped>

</style>