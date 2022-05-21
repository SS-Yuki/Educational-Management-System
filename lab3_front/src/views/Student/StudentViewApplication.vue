<template>
  <div class="main_part">
    <el-table :data="tableData" style="width: 1200px;margin-right: auto;margin-left: auto" >
      <el-table-column prop="courseId" label="courseId" width="180" />
      <el-table-column prop="courseNum" label="课程编号" width="180"/>
      <el-table-column prop="courseName" label="课程名" width="180"/>
      <el-table-column prop="teacherName" label="任课教师" width="180"/>
      <el-table-column prop="year" label="学年" width="180"/>
      <el-table-column prop="semester" label="学期" width="180"/>
      <el-table-column prop="description" label="申请理由" width="180"/>
      <el-table-column  fixed="right" prop="dealStatus" label="审批状态" width="180"/>
      <el-table-column prop="advice" label="审批意见" width="180"/>
    </el-table>
  </div>
</template>

<script>
import request from "@/utils/request";
export default {
  name: "StudentViewApplication",
  data(){
    return{
      tableData : []
    }
  },
  methods:{
    getApplication:function (){
      request.post("/student/findMySelectCourseApply").then(res=>{
        this.tableData=res.data.data
      })
    }
  },
  mounted() {
    this.getApplication()
  }
}
</script>

<style scoped>

</style>