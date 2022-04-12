<template>
  <div class="checkschool">
    <div>
      <div class="add" style="margin: 10px 0">
        <div style="float: left">
          <el-button size="large" @click="add" type="primary">新增</el-button>
        </div>
        <div style="float: left">
          <el-upload
              class="upload-demo"
              action=""
              :on-change="handleChange"
              :on-exceed="handleExceed"
              :on-remove="handleRemove"
              :file-list="fileListUpload"
              accept=".csv"
              :auto-upload="false">
            <el-button size="large" type="primary">导入</el-button>
          </el-upload>
        </div>
        <div style="float: left">
          <el-input clearable v-model="search" placeholder="请输入关键字" style="width:50%;margin-left: 100px"></el-input>
          <el-button type="primary" style="margin-left: 5px" @click="load">搜索</el-button>
        </div>

      </div>
      <el-table :data="tableData" style="width: 100%" border stripe>
        <el-table-column prop="applyId" label="applyId" width="150" />
        <el-table-column prop="courseId" label="courseId" width="0" v-if="false" />
        <el-table-column prop="courseName" label="课程名" width="150" />
        <el-table-column prop="courseNumber" label="课程编号" width="150" />
        <el-table-column prop="teacherNum" label="教师工号" width="150" v-if="false" />
        <el-table-column prop="teacherName" label="教师姓名" width="150" />
        <el-table-column prop="major" label="开课专业" width="150" v-if="false"/>
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
            <el-button type="text" size="small" @click="handleCheck(
                scope.applyId,
                scope.row.courseId,
                scope.row.courseName,
                scope.row.courseNumber,
                scope.row.teacherNum,
                scope.row.teacherName,
                scope.row.major,
                scope.row.school,
                scope.row.classPeriod,
                scope.row.classroom,
                scope.row.creditHours,
                scope.row.credits,
                scope.row.capacity,
                scope.row.introduction,
                scope.row.applicant
                )">查看详情</el-button>
            <el-button size="small" @click="accept(scope.row.applyId)">
              通过
            </el-button>
            <el-button size="small" @click="reject(scope.row.applyId)">
              不通过
            </el-button>
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
      <el-dialog v-model="dialogVisible2" title="查看申请信息" width="30%">
        <el-form :model="editCourse" label-width="120px">
          <el-form-item label="applyId">
            <el-input v-model="applyIdId" />
          </el-form-item>
          <el-form-item label="id">
            <el-input v-model="courseId" />
          </el-form-item>
          <el-form-item label="课程名">
            <el-input v-model="courseName" />
          </el-form-item>
          <el-form-item label="课程编号">
            <el-input v-model="courseNumber"/>
          </el-form-item>
          <el-form-item label="教师工号">
            <el-input v-model="teacherNum" readonly="readonly"/>
          </el-form-item>
          <el-form-item label="教师姓名">
            <el-input v-model="teacherName" readonly="readonly"/>
          </el-form-item>
          <el-form-item label="开课专业">
            <el-input v-model="major" readonly="readonly"/>
          </el-form-item>
          <el-form-item label="开课院系">
            <el-input v-model="school" readonly="readonly"/>
          </el-form-item>
          <el-form-item label="上课时间">
            <el-input v-model="classPeriod" />
          </el-form-item>
          <el-form-item label="教室">
            <el-input v-model="classroom" />
          </el-form-item>
          <el-form-item label="学时">
            <el-input v-model="creditHours" />
          </el-form-item>
          <el-form-item label="学分">
            <el-input v-model="credits" />
          </el-form-item>
          <el-form-item label="容量">
            <el-input v-model="capacity" />
          </el-form-item>
          <el-form-item label="介绍">
            <el-input v-model="introduction" />
          </el-form-item>
          <el-form-item label="申请人">
            <el-input v-model="applicant" />
          </el-form-item>
          <span class="dialog-footer">
        <el-button @click="dialogVisible2 = false">关闭</el-button>
      </span>
        </el-form>
        <template #footer>
        </template>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";
import Papa from "papaparse";

export default {
  name: "CheckApplication",
  data(){
    return{
      total:0,
      pageSize:10,
      currentPage:1,
      search:'',
      dialogVisible:false,
      dialogVisible2:false,
      applyId:0,
      courseId:'',
      courseName:'',
      courseNumber:'',
      teacherNum:'',
      teacherName:'',
      major:'',
      school:'',
      classPeriod:'',
      classroom:'',
      creditHours:'',
      credits:'',
      capacity:'',
      introduction:'',
      applicant:'',
      tableData:[]
    }
  },
  mounted() {
    this.load()
  },
  methods:{
    accept:function(applyId) {
      request.post("/admin/acceptApply",applyId)
    },
    reject:function(applyId) {
      request.post("/admin/rejectApply",applyId)
    },
    load(){
      console.log(this.pageData)
      request.post("/admin/findCoursePage",{
            pageNum: this.currentPage,
            pageSize: this.pageSize,
            search: this.search
          }
      ).then(res=>{
        console.log(res)
        if(res.data.code===200){
          this.tableData=res.data.data.records
          this.total=res.data.data.total
        }
        else{
          this.$message({
            type:"fail",
            message: "失败"
          })
        }
      })
    },
    add:function (){
      this.dialogVisible=true
      this.buildingName=''
    },
    save:function (){
      request.post("/admin/addCourse", this.addCourse).then(res => {
        console.log(res)
        this.load() // 刷新表格的数据
        this.dialogVisible = false  // 关闭弹窗
      })
    },
    saveEdit(){
      console.log(this.newSchool)
      request.post("/admin/updateCourseInfo",this.editCourse).then(res=>{
        console.log(res)
        this.load()
        this.dialogVisible2=false
      })
    },
    handleCheck(applyId,courseId,courseName,courseNumber,teacherNum,teacherName,
                major,school,classPeriod,classroom,
               creditHours,credits,capacity,introduction,applicant){
      this.dialogVisible2=true
      this.applyId=applyId
      this.courseId=courseId
      this.courseName=courseName
      this.courseNumber=courseNumber
      this.teacherName=teacherName
      this.teacherNum=teacherNum
      this.major=major
      this.school=school
      this.classPeriod=classPeriod
      this.classroom=classroom
      this.creditHours=creditHours
      this.credits=credits
      this.capacity=capacity
      this.introduction=introduction
      this.applicant=applicant
    },
    handleDelete(courseId) {
      this.id.id=courseId
      request.post("/admin/deleteCourse",this.id).then(res => {
        this.load()  // 删除之后重新加载表格的数据
      })
    },
    handleClick:function (){

    },
    handleSizeChange:function (){

    },
    handleCurrentChange:function (pageNum){
      this.currentPage = pageNum
      this.load()
    }
  }
}
</script>

<style scoped>
.checkschool{
  margin-left: 100px;
  display: flex;
}
.add{
  text-align: left;
}
</style>