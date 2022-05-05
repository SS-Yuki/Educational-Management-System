<template>
  <div class="main_part">
    <div class="add" style="margin: 10px 0">
      <el-button size="large" @click="add" type="primary">申请新增课程</el-button>
      <el-input clearable v-model="search" placeholder="请输入关键字" style="width:50%;margin-left: 100px"></el-input>
      <el-button type="primary" style="margin-left: 5px" @click="load">搜索</el-button>
    </div>
    <el-table :data="tableData" style="width: 1200px" border stripe>
      <el-table-column prop="courseId" label="courseId" width="200" v-if="false"  />
      <el-table-column prop="courseName" label="课程名" width="200" />
      <el-table-column prop="courseNumber" label="课程编号" width="200" />
      <el-table-column prop="occupyTime" label="时间" width="150"   />
      <el-table-column prop="classroom" label="教室" width="150" />
      <el-table-column prop="creditHours" label="学时"  width="70" />
      <el-table-column prop="credits" label="学分" width="60"  />
      <el-table-column prop="capacity" label="容量" width="70" />
      <el-table-column prop="selectNumber" fixed="right" label="选课人数" width="70" />
      <el-table-column prop="introduction" label="介绍" width="0" v-if="false" />
      <el-table-column fixed="right" label="操作" width="300">
        <template #default="scope">
          <el-button type="text" size="small" @click="checkList(scope.row.courseId)">查看选课名单</el-button>
          <el-button type="text" size="small" @click="edit(scope.row.courseId)">申请修改信息</el-button>
          <el-popconfirm title="确认删除?" @confirm="delete(scope.row.courseId)">
            <template #reference>
              <el-button type="text">申请删除</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <div>
      <el-dialog v-model="check" title="选课名单" width="650px">

        <el-table :data="studentList" style="width: 600px" height="400" border stripe>
          <el-table-column prop="stuNumber" label="学号" width="150"  />
          <el-table-column prop="name" label="姓名" width="150" />
          <el-table-column prop="grade" label="年级" width="150" />
          <el-table-column prop="major" label="专业" width="150"  />
        </el-table>

        <template #footer>
        </template>
      </el-dialog>

    </div>

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
</template>

<script>
import request from "@/utils/request";

export default {
  name: "TeacherCourse",
  data(){
    return{
      check:false,
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
    checkList:function (courseId){
      request.post("common/getStudentListOfOneCourse",courseId).then(res=>{
        if(!res.data) return
        this.studentList=res.data.data.records,
            this.check=true
      })
    },

    load(){
      setTimeout(() => {
        request.post("/teacher/findCoursePage",{
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
              type:"error",
              message: res.data.msg
            })
          }
        })
      }, 500)
    },
    add:function (){
      this.$router.push("/teacher/teacheraddcourse")
    },
    edit:function (courseId) {
      this.$router.push({ name: 'TeacherEditCourse', params: { id: courseId} })
      console.log(courseId)
    },
    delete(courseId) {
      request.post("/teacher/deleteCourse", {courseId: courseId}).then(res => {
        this.load()
      })
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