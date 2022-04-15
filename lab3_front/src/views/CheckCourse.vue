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
        <el-table-column prop="courseId" label="courseId" width="0" v-if="false" />
        <el-table-column prop="courseName" label="课程名" width="150" />
        <el-table-column prop="courseNumber" label="课程编号" width="150" />
        <el-table-column prop="teacherNum" label="教师工号" width="150" />
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
                )">查看/编辑</el-button>
            <el-popconfirm title="确认删除?" @confirm="handleDelete(scope.row.courseId)">
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
          <el-form-item label="课程id">
            <el-input v-model="addCourse.id" disabled/>
          </el-form-item>
          <el-form-item label="课程名">
            <el-input v-model="addCourse.courseName"/>
          </el-form-item>
          <el-form-item label="课程编号">
            <el-input v-model="addCourse.courseNumber" />
          </el-form-item>
          <el-form-item label="教师工号">
            <el-input v-model="addCourse.teacherNum" />
          </el-form-item>
<!--          <el-form-item label="开课专业">-->
<!--            <el-input v-model="addCourse.major" />-->
<!--          </el-form-item>-->
<!--          <el-form-item label="开课院系">-->
<!--            <el-input v-model="addCourse.school" />-->
<!--          </el-form-item>-->
          <el-form-item label="院系/专业" prop="school_major">
            <el-cascader  v-model="add_school_major" :options="options"/>
          </el-form-item>
          <el-form-item label="上课时间">
            <el-input v-model="addCourse.classPeriod" />
          </el-form-item>
          <el-form-item label="教室">
            <el-input v-model="addCourse.classroom" />
          </el-form-item>
          <el-form-item label="学时">
            <el-input v-model="addCourse.creditHours" />
          </el-form-item>
          <el-form-item label="学分">
            <el-input v-model="addCourse.credits" />
          </el-form-item>
          <el-form-item label="容量">
            <el-input v-model="addCourse.capacity" />
          </el-form-item>
          <el-form-item label="介绍">
            <el-input v-model="addCourse.introduction" />
          </el-form-item>
<!--          <el-form-item label="申请人">-->
<!--            <el-input v-model="addCourse.applicant" />-->
<!--          </el-form-item>-->
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
      <el-dialog v-model="dialogVisible2" title="编辑课程信息" width="30%">
        <el-form :model="editCourse" label-width="120px">
          <el-form-item label="id">
            <el-input v-model="editCourse.id" />
          </el-form-item>
          <el-form-item label="课程名">
            <el-input v-model="editCourse.courseName" />
          </el-form-item>
          <el-form-item label="课程编号">
            <el-input v-model="editCourse.courseNumber" />
          </el-form-item>
          <el-form-item label="教师工号">
            <el-input v-model="editCourse.teacherNum" disabled/>
          </el-form-item>
<!--          <el-form-item label="开课专业">-->
<!--            <el-input v-model="editCourse.major" disabled/>-->
<!--          </el-form-item>-->
<!--          <el-form-item label="开课院系">-->
<!--            <el-input v-model="editCourse.school" disabled/>-->
<!--          </el-form-item>-->
          <el-form-item label="院系/专业" prop="school_major">
            <el-cascader  v-model="edit_school_major" :options="options"/>
          </el-form-item>
          <el-form-item label="上课时间">
            <el-input v-model="editCourse.classPeriod" />
          </el-form-item>
          <el-form-item label="教室">
            <el-input v-model="editCourse.classroom" />
          </el-form-item>
          <el-form-item label="学时">
            <el-input v-model="editCourse.creditHours" />
          </el-form-item>
          <el-form-item label="学分">
            <el-input v-model="editCourse.credits" />
          </el-form-item>
          <el-form-item label="容量">
            <el-input v-model="editCourse.capacity" />
          </el-form-item>
          <el-form-item label="介绍">
            <el-input v-model="editCourse.introduction" />
          </el-form-item>
<!--          <el-form-item label="申请人">-->
<!--            <el-input v-model="editCourse.applicant" />-->
<!--          </el-form-item>-->
          <span class="dialog-footer">
        <el-button @click="dialogVisible2 = false">取消</el-button>
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
import Papa from "papaparse";

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
      add_school_major:'',
      edit_school_major:'',
      options:[],
      id:{
        id:0
      },
      addCourse:{
        id:0,
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
    this.load()
    this.getOption()
  },
  methods:{
    getOption: function () {
      request.post("/admin/allMajors").then(res => {
        
        let that = this
        if (!res.data) return
        res.data.data.schools.forEach (function (item) {
          
          let option = {value: item.school, label: item.school, children: []}
          if (!item.majors) return
          item.majors.forEach (function (item) {
            let child = {value: item, label: item}
            option.children.push(child)
          })
          that.options.push(option)
        })
      })
    },
    handleChange(file, fileList) {
      this.fileTemp = file.raw
      if (this.fileTemp) {
        if (this.fileTemp.type==='text/csv') {
          this.importcsv(file.raw)
        } else {
          this.$message({
            type: 'warning',
            message: '附件格式错误，请删除后重新上传！'
          })
        }
      } else {
        this.$message({
          type: 'warning',
          message: '请上传附件！'
        })
      }
    },

    importcsv (obj) {
      let that = this//如果需要点击事件结束之后对DOM进行操作使用)_this.xx=xx进行操作
      Papa.parse(obj, {
        complete (results) {
          
          let data = []
          //遍历csv文件中的数据，存放到data中 方法不唯一，可自己更改
          for (let i = 0; i < results.data.length-1; i++) {
            let obj = {}
            obj.id = results.data[i][0]
            obj.courseName = results.data[i][1]
            obj.courseNumber = results.data[i][2]
            obj.teacherNum=results.data[i][3]
            obj.major = results.data[i][4]
            obj.school = results.data[i][5]
            obj.classPeriod = results.data[i][6]
            obj.classroom = results.data[i][7]
            obj.creditHours = results.data[i][8]
            obj.credits = results.data[i][9]
            obj.capacity = results.data[i][10]
            obj.introduction = results.data[i][11]
            obj.applicant = results.data[i][12]
            data.push(obj)
          }
          data.splice(0, 1)//将数组第一位的表格名称去除
          let num = 0
          
          // _this.tableData = data//将数据放入要展示的表格中
          request.post("/admin/csvRegister", data).then(res => {
            if(res.data.code!==200) {
              this.$message({
                type:"fail",
                message: res.data.msg
              })
            }
            that.load()
            that.$router.go(0)
          })
        }
      })
    },
    load(){
      setTimeout(() => {
        request.post("/admin/findCoursePage",{
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
      }, 10)
    },
    add:function (){
      this.dialogVisible=true
      this.buildingName=''
    },
    save:function (){
      this.addCourse.school = this.add_school_major[0]
      this.addCourse.major = this.add_school_major[1]
      request.post("/admin/addCourse", this.addCourse).then(res => {
        if(res.data.code!==200) {
          this.$message({
            type:"fail",
            message: res.data.msg
          })
        }
        this.load() // 刷新表格的数据
        this.dialogVisible = false  // 关闭弹窗
      })
    },
    saveEdit(){
      this.editCourse.school = this.edit_school_major[0]
      this.editCourse.major = this.edit_school_major[1]
      
      request.post("/admin/updateCourseInfo",this.editCourse).then(res=>{
        if(res.data.code!==200) {
          this.$message({
            type:"fail",
            message: res.data.msg
          })
        }
        this.load()
        this.dialogVisible2=false
      })
    },
    handleEdit(courseId,courseName,courseNumber,teacherNum,major,school,classPeriod,classroom,
    creditHours,credits,capacity,introduction,applicant){
      this.addCourse={}
      this.dialogVisible2=true
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
      this.editCourse.applicant=applicant
      this.edit_school_major = [school, major]
    },
    handleDelete(courseId) {
      this.id.id=courseId
      request.post("/admin/deleteCourse",this.id).then(res => {
        if(res.data.code!==200) {
          this.$message({
            type:"fail",
            message: res.data.msg
          })
        }
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