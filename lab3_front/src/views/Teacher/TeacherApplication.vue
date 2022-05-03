<template>
  <div class="main_part">
      <div class="add" style="margin: 10px 0">
        <div style="margin-bottom: 20px">
          <el-input clearable v-model="search" placeholder="请输入关键字" style="width:50%;margin-left: 100px"></el-input>
          <el-button type="primary" style="margin-left: 5px" @click="load">搜索</el-button>
        </div>
      <el-table :data="tableData" style="width: 1200px" border stripe>
        <el-table-column prop="applyId" label="申请ID" v-if="false"/>
        <el-table-column prop="courseId" label="课程ID" width="100" v-if="false"/>
        <el-table-column prop="courseNumber" label="课程编号" width="100"/>
        <el-table-column prop="courseName" label="课程名" width="100"/>
        <el-table-column prop="teacherNum" label="教师工号" width="100" v-if="false"/>
        <el-table-column prop="teacherName" label="教师姓名" width="100" v-if="false"/>
        <el-table-column prop="major" label="开课专业" width="100" v-if="false"/>
        <el-table-column prop="school" label="开课院系" width="100" v-if="false"/>
        <el-table-column prop="classroom" label="教室" width="100"/>
        <el-table-column prop="creditHours" label="学时" width="100"/>
        <el-table-column prop="credits" label="学分" width="100"/>
        <el-table-column prop="capacity" label="容量" width="100"/>
        <el-table-column prop="occupyTime" label="时间" width="100"/>
        <el-table-column prop="introduction" label="介绍" width="100"/>
        <el-table-column prop="applyType" label="申请类型" width="100"/>
        <el-table-column prop="applicant" label="申请人" width="100" v-if="false"/>
        <el-table-column prop="selectTypeString" label="选课类型" width="100"/>
        <el-table-column prop="majorLimits" label="专业限制" width="100"/>
        <el-table-column prop="year" label="学年" width="100"/>
        <el-table-column prop="semester" label="学期" width="100"/>
      </el-table>
      <div style="margin: 10px 0">
        <el-pagination
            v-model:currentPage="currentPage"
            v-model:page-size="pageSize"
            :page-sizes="[5,10,20]"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total"
            @current-change="handleCurrentChange"
        />
      </div>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "TeacherApplication",
  data(){
    return{
      total:0,
      pageSize:10,
      currentPage:1,
      search:'',
      tableData:[]
    }
  },
  mounted() {
    this.load()
  },
  methods:{
    load(){
      setTimeout(() => {
        request.post("/teacher/findApplyPage",{
              pageNum: this.currentPage,
              pageSize: this.pageSize,
              search: this.search
            }
        ).then(res=>{
          
          if(res.data.code===200){
            this.tableData=res.data.data.records
            this.total=res.data.data.total
          }
          else{
            this.$message({
              type:"fail",
              message: res.data.msg
            })
          }
        })
      }, 500)
    },
    handleCurrentChange:function (pageNum){
      this.currentPage = pageNum
      this.load()
    }
  }
}
</script>

<style scoped>
.add{
  text-align: left;
}
</style>