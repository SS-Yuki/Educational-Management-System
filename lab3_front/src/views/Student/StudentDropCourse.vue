<template>
  <div class="main_part">
    <el-table :data="tableData" style="width: 1200px" border stripe>
      <el-table-column prop="courseId" label="courseId" width="150"  />
      <el-table-column prop="courseName" label="课程名" width="200" />
      <el-table-column prop="courseNumber" label="课程编号" width="200" />
      <el-table-column prop="major" label="开课专业" width="200" />
      <el-table-column prop="school" label="开课院系" width="200" />
      <el-table-column prop="occupyTime" label="时间" width="200"   />
      <el-table-column prop="classroom" label="教室" width="200"  />
      <el-table-column prop="creditHours" label="学时" width="100"  />
      <el-table-column prop="credits" label="学分" width="100"  />
      <el-table-column prop="introduction" label="介绍" width="100"  />
      <el-table-column fixed="right" label="操作" width="70">
        <template #default="scope">
          <el-popconfirm title="确认退课?" @confirm="this.dropCourse(scope.row.courseId)">
            <template #reference>
              <el-button type="text" >退课</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "StudentDropCourse",
  data(){
    return{
      tableData:[],

    }
  },
  mounted() {
    this.getCourse()
  },
  methods:{
    getCourse:function (){
      setTimeout(() => {
        request.post("/student/getMyCanDropCourse",).then(res => {

          if (res.data.code === 200) {
            this.tableData = res.data.data
          } else {
            this.$message({
              type: "error",
              message: res.data.msg
            })
          }
        })
      }, 500)
    },
    dropCourse:function (courseId){
      request.post("/student/dropCourse",courseId).then(res=>{
        if (res.data.code === 200) {
          this.tableData = res.data.data.records
          this.$message({
            type: "success",
            message: res.data.msg
          })

        } else {
          this.$message({
            type: "error",
            message: res.data.msg
          })
        }
      })
      this.getCourse()
    }
  }
}
</script>

<style scoped>

</style>