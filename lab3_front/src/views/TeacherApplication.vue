<template>
  <div class="checkschool">
    <div>
      <div class="add" style="margin: 10px 0">
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
        <el-table-column prop="applyType" label="申请类型" width="0"/>
        <el-table-column fixed="right" label="操作" width="200">
          <template #default="scope">
            <el-button type="text" size="small" @click="handleCheck(
                scope.row.applyId,
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
                scope.row.applicant,
                scope.row.applyType
                )">查看详情</el-button>
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
            <el-input v-model="applyId" disabled/>
          </el-form-item>
          <el-form-item label="课程id">
            <el-input v-model="courseId" disabled/>
          </el-form-item>
          <el-form-item label="课程名">
            <el-input v-model="courseName" disabled/>
          </el-form-item>
          <el-form-item label="课程编号">
            <el-input v-model="courseNumber" disabled/>
          </el-form-item>
          <el-form-item label="教师工号">
            <el-input v-model="teacherNum" disabled/>
          </el-form-item>
          <el-form-item label="教师姓名">
            <el-input v-model="teacherName" disabled/>
          </el-form-item>
          <el-form-item label="开课专业">
            <el-input v-model="major" disabled/>
          </el-form-item>
          <el-form-item label="开课院系">
            <el-input v-model="school" disabled/>
          </el-form-item>
          <el-form-item label="上课时间">
            <el-input v-model="classPeriod" disabled/>
          </el-form-item>
          <el-form-item label="教室">
            <el-input v-model="classroom" disabled/>
          </el-form-item>
          <el-form-item label="学时">
            <el-input v-model="creditHours" disabled/>
          </el-form-item>
          <el-form-item label="学分">
            <el-input v-model="credits" disabled/>
          </el-form-item>
          <el-form-item label="容量">
            <el-input v-model="capacity" disabled />
          </el-form-item>
          <el-form-item label="介绍">
            <el-input v-model="introduction" disabled/>
          </el-form-item>
          <el-form-item label="申请人">
            <el-input v-model="applicant" disabled/>
          </el-form-item>
          <el-form-item label="申请类型">
            <el-input v-model="applyType" disabled />
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
  name: "TeacherApplication",
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
      applyType:'',
      tableData:[]
    }
  },
  mounted() {
    this.load()
  },
  methods:{
    load(){
      
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
            message: "失败"
          })
        }
      })
    },
    add:function (){
      this.dialogVisible=true
      this.buildingName=''
    },
    handleCheck(applyId,courseId,courseName,courseNumber,teacherNum,teacherName,
                major,school,classPeriod,classroom,
                creditHours,credits,capacity,introduction,applicant,applyType){
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
      this.applyType=applyType
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