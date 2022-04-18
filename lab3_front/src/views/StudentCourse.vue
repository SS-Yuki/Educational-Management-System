
<template>
  <div style="margin: auto 200px ">
      <div style="margin-bottom: 20px">
        <el-input clearable v-model="search" placeholder="请输入关键字" style="width:50%;margin-left: 100px"></el-input>
        <el-button type="primary" style="margin-left: 5px" @click="load">搜索</el-button>
      </div>
      <el-table :data="tableData" style="width: 100%" border stripe>
        <el-table-column prop="courseId" label="courseId" width="0" v-if="false" />
        <el-table-column prop="courseName" label="课程名" width="150" />
        <el-table-column prop="courseNumber" label="课程编号" width="150" />
<!--        <el-table-column prop="teacherNum" label="教师工号" width="150" v-if="false"/>-->
        <el-table-column prop="major" label="开课专业" width="150" />
        <el-table-column prop="school" label="开课院系" width="150" />
        <el-table-column prop="classPeriod" label="时间" width="0" v-if="false"  />
        <el-table-column prop="classroom" label="教室" width="0" v-if="false" />
        <el-table-column prop="creditHours" label="学时" width="0" v-if="false" />
        <el-table-column prop="credits" label="学分" width="0" v-if="false" />
        <el-table-column prop="capacity" label="容量" width="0" v-if="false" />
        <el-table-column prop="introduction" label="介绍" width="0" v-if="false" />
        <el-table-column prop="applicant" label="申请人" width="0" v-if="false" />
        <el-table-column fixed="right" label="操作" width="200">
          <template #default="scope">
            <el-button type="text" size="small" @click="handleEdit(
                scope.row.courseId,
                scope.row.courseName,
                scope.row.courseNumber,
                scope.row.teacherNum,
                scope.row.major,
                scope.row.school,
                scope.row.classPeriod,
                scope.row.classroom,
                scope.row.creditHours,
                scope.row.credits,
                scope.row.capacity,
                scope.row.introduction,
                scope.row.applicant
                )">查看</el-button>
            <el-popconfirm title="确认选课?" @confirm="this.load()">
              <template #reference>
                <el-button type="text">选课</el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
      <div style="margin: 10px 0">
        <el-pagination
            v-model:currentPage="currentPage"
            v-model:page-size="pageSize"
            :page-sizes="[5,10,20]"
            :small="small"
            :disabled="disabled"
            :background="background"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
        />
      </div>
    </div>
    <div>
      <el-dialog v-model="dialogVisible" title="查看课程信息" width="30%">
        <el-form :model="editCourse" label-width="120px">
          <el-form-item label="id" >
            <el-input v-model="editCourse.id" disabled/>
          </el-form-item>
          <el-form-item label="课程名">
            <el-input v-model="editCourse.courseName" disabled/>
          </el-form-item>
          <el-form-item label="课程编号">
            <el-input v-model="editCourse.courseNumber" disabled/>
          </el-form-item>
          <el-form-item label="教师工号">
            <el-input v-model="editCourse.teacherNum" disabled/>
          </el-form-item>
          <el-form-item label="开课专业">
            <el-input v-model="editCourse.major" disabled/>
          </el-form-item>
          <el-form-item label="开课院系">
            <el-input v-model="editCourse.school" disabled/>
          </el-form-item>
          <el-form-item label="上课时间">
            <el-input v-model="editCourse.classPeriod" disabled/>
          </el-form-item>
          <el-form-item label="教室">
            <el-input v-model="editCourse.classroom" disabled/>
          </el-form-item>
          <el-form-item label="学时">
            <el-input v-model="editCourse.creditHours" disabled/>
          </el-form-item>
          <el-form-item label="学分">
            <el-input v-model="editCourse.credits" disabled/>
          </el-form-item>
          <el-form-item label="容量">
            <el-input v-model="editCourse.capacity" disabled/>
          </el-form-item>
          <el-form-item label="介绍">
            <el-input v-model="editCourse.introduction" disabled/>
          </el-form-item>
          <span class="dialog-footer">
        <el-button type="primary" @click="dialogVisible = false">确认</el-button>
      </span>
        </el-form>
        <template #footer>
        </template>
      </el-dialog>
    </div>

</template>

<script>
import request from "@/utils/request";

export default {
  name: "CheckCourse",
  data(){
    return{
      total:0,
      pageSize:10,
      currentPage:1,
      search:'',

      dialogVisible:false,
      editCourse:{
        id:0,
        courseName:'',
        courseNumber:'',
        teacherNum:'',
        major:'',
        school:'',
        classPeriod:'',
        classroom:'',
        creditHours:0,
        credits:0,
        capacity:0,
        introduction:'',
        applicant:''
      },
      tableData:[]
    }
  },
  mounted() {
    request.post("/student/findCoursePage", {
      pageNum: this.currentPage,
      pageSize: this.pageSize,
      search: this.search
    }).then(res=>{
      if(res.data.code===200){
        this.$message({
          type:"success",
          message: res.data.msg
        })
        this.load()
      }
      else {
        this.$message({
          type:"error",
          message: res.data.msg
        })
      }
    })
  },
  methods:{
    load(){
      setTimeout(() => {
        request.post("/student/findCoursePage", {
              pageNum: this.currentPage,
              pageSize: this.pageSize,
              search: this.search
            }
        ).then(res => {

          if (res.data.code === 200) {
            this.tableData = res.data.data.records
            this.total = res.data.data.total
          } else {
            this.$message({
              type: "error",
              message: res.data.msg
            })
          }
        })
      }, 10)
    },
    handleEdit(courseId,courseName,courseNumber,teacherNum,major,school,classPeriod,classroom,
               creditHours,credits,capacity,introduction,applicant){
      this.dialogVisible=true
      this.editCourse.id=courseId
      this.editCourse.courseName=courseName
      this.editCourse.courseNumber=courseNumber
      this.editCourse.teacherNum=teacherNum
      this.editCourse.major=major
      this.editCourse.school=school
      this.editCourse.classPeriod=classPeriod
      this.editCourse.classroom=classroom
      this.editCourse.creditHours=creditHours
      this.editCourse.credits=credits
      this.editCourse.capacity=capacity
      this.editCourse.introduction=introduction
    },
    handleCurrentChange:function (pageNum){
      this.currentPage = pageNum
      this.load()
    }
  }
}
</script>

<style scoped>

</style>