<template>
  <div class="checkschool">
    <div>
      <div class="add" style="margin: 10px 0">
        <el-button size="large" @click="add" type="primary">新增</el-button>
        <el-input clearable v-model="search" placeholder="请输入关键字" style="width:50%;margin-left: 100px"></el-input>
        <el-button type="primary" style="margin-left: 5px" @click="load">搜索</el-button>
      </div>
      <el-table :data="tableData" style="width: 100%" border stripe>
        <el-table-column prop="id" label="id" width="0" />
        <el-table-column prop="courseName" label="课程名" width="150" />
        <el-table-column prop="courseNumber" label="课程编号" width="150" />
        <el-table-column prop="teacherNum" label="教师工号" width="150" />
        <el-table-column prop="major" label="开课专业" width="150" />
        <el-table-column prop="school" label="开课院系" width="150" />
        <el-table-column prop="classPeriod" label="时间" width="0" />
        <el-table-column prop="classroom" label="教室" width="0" />
        <el-table-column prop="creditHours" label="学时" width="0" />
        <el-table-column prop="credits" label="学分" width="0" />
        <el-table-column prop="capacity" label="容量" width="0" />
        <el-table-column prop="introduction" label="介绍" width="0" />
        <el-table-column prop="applicant" label="申请人" width="0" />
        <el-table-column fixed="right" label="操作" width="200">
          <template #default="scope">
            <el-button type="text" size="small" @click="handleEdit(
                scope.row.id,
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
                )">查看/编辑</el-button>
            <el-popconfirm title="确认删除?" @confirm="handleDelete(scope.row.id)">
              <template #reference>
                <el-button type="text">删除</el-button>
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
      <el-dialog v-model="dialogVisible" title="添加新课程" width="30%">
        <el-form :model="addCourse" label-width="120px">
          <el-form-item label="新课程名">
            <el-input v-model="addCourse.courseName" />
          </el-form-item>
          <el-form-item label="新课程编号">
            <el-input v-model="addCourse.courseNumber" />
          </el-form-item>
          <el-form-item label="新教师工号">
            <el-input v-model="addCourse.teacherNum" />
          </el-form-item>
          <el-form-item label="新开课专业">
            <el-input v-model="addCourse.major" />
          </el-form-item>
          <el-form-item label="新开课院系">
            <el-input v-model="addCourse.school" />
          </el-form-item>
          <el-form-item label="新上课时间">
            <el-input v-model="addCourse.classPeriod" />
          </el-form-item>
          <el-form-item label="新教室">
            <el-input v-model="addCourse.classroom" />
          </el-form-item>
          <el-form-item label="新学时">
            <el-input v-model="addCourse.creditHours" />
          </el-form-item>
          <el-form-item label="新学分">
            <el-input v-model="addCourse.credits" />
          </el-form-item>
          <el-form-item label="新容量">
            <el-input v-model="addCourse.capacity" />
          </el-form-item>
          <el-form-item label="新介绍">
            <el-input v-model="addCourse.introduction" />
          </el-form-item>
          <el-form-item label="新申请人">
            <el-input v-model="addCourse.applicant" />
          </el-form-item>
          <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="save">确认</el-button>
      </span>
        </el-form>
        <template #footer>
        </template>
      </el-dialog>
    </div>
    <div>
      <el-dialog v-model="dialogVisible2" title="查看编辑信息" width="30%">
        <el-form :model="editCourse" label-width="120px">
          <el-form-item label="新id">
            <el-input v-model="editCourse.id" />
          </el-form-item>
          <el-form-item label="新课程名">
            <el-input v-model="editCourse.courseName" />
          </el-form-item>
          <el-form-item label="新课程编号">
            <el-input v-model="editCourse.courseNumber" />
          </el-form-item>
          <el-form-item label="新教师工号">
            <el-input v-model="editCourse.teacherNum" />
          </el-form-item>
          <el-form-item label="新开课专业">
            <el-input v-model="editCourse.major" />
          </el-form-item>
          <el-form-item label="新开课院系">
            <el-input v-model="editCourse.school" />
          </el-form-item>
          <el-form-item label="新上课时间">
            <el-input v-model="editCourse.classPeriod" />
          </el-form-item>
          <el-form-item label="新教室">
            <el-input v-model="editCourse.classroom" />
          </el-form-item>
          <el-form-item label="新学时">
            <el-input v-model="editCourse.creditHours" />
          </el-form-item>
          <el-form-item label="新学分">
            <el-input v-model="editCourse.credits" />
          </el-form-item>
          <el-form-item label="新容量">
            <el-input v-model="editCourse.capacity" />
          </el-form-item>
          <el-form-item label="新介绍">
            <el-input v-model="editCourse.introduction" />
          </el-form-item>
          <el-form-item label="新申请人">
            <el-input v-model="editCourse.applicant" />
          </el-form-item>
          <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="saveEdit">确认</el-button>
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

export default {
  name: "CheckCourse",
  data(){
    return{
      total:0,
      pageSize:10,
      currentPage:1,
      search:'',
      dialogVisible:false,
      dialogVisible2:false,
      id:0,
      addCourse:{
        courseName:'',
        courseNumber:'',
        teacherNum:'',
        major:'',
        school:'',
        classPeriod:'',
        classroom:'',
        creditHours:'',
        credits:'',
        capacity:'',
        introduction:'',
        applicant:''
      },
      editCourse:{
        id:'',
        courseName:'',
        courseNumber:'',
        teacherNum:'',
        major:'',
        school:'',
        classPeriod:'',
        classroom:'',
        creditHours:'',
        credits:'',
        capacity:'',
        introduction:'',
        applicant:''
      },
      tableData:[]
    }
  },
  mounted() {
    this.load()
  },
  methods:{
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
      request.post("/admin/addCourse", this.id+this.addCourse).then(res => {
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
    handleEdit(id,courseName,courseNumber,teacherNum,major,school,classPeriod,classroom,
    creditHours,credits,capacity,introduction,applicant){
      this.editCourse.id=id
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
      this.editCourse.applicant=applicant
    },
    handleDelete(id) {
      request.post("/admin/deleteCourse",id).then(res => {
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