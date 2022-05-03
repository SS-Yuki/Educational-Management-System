<template>
  <div class="main_part">
    <div>
      <div class="add" style="margin: 10px 0">
        <div style="float: left">
          <el-button size="large" @click="add" type="primary">
            新增
          </el-button>
          <router-view></router-view>
        </div>
        <div style="float: left">
          <el-upload
              class="upload-demo"
              action=""
              :on-change="handleChange"
              accept=".csv"
              :auto-upload="false">
            <el-button size="large" type="primary">导入</el-button>
          </el-upload>
        </div>
        <div style="float: left">
          <el-input clearable v-model="search" placeholder="请输入关键字" style="width:50%;margin-left: 100px"></el-input>
          <el-button type="primary" style="margin-left: 5px" @click="load">搜索</el-button>
        </div>
        <div>
          <el-cascader  v-model="select_year_semester" :options="semesterOptions" placeholder="请选择学期"/>
        </div>

      </div>
      <el-table :data="tableData" style="width: 1200px" border stripe>
        <el-table-column prop="courseId" label="courseId" width="0" v-if="false" />
        <el-table-column prop="courseName" label="课程名" width="200" />
        <el-table-column prop="courseNumber" label="课程编号" width="200" />
        <el-table-column prop="teacherNum" label="教师工号" width="200" />
        <el-table-column prop="major" label="开课专业" width="200" />
        <el-table-column prop="school" label="开课院系" width="200" />
<!--        <el-table-column prop="classPeriod" label="时间" width="0" v-if="false"  />-->
        <el-table-column prop="classroom" label="教室" width="0" v-if="false" />
        <el-table-column prop="creditHours" label="学时" width="0" v-if="false" />
        <el-table-column prop="credits" label="学分" width="0" v-if="false" />
        <el-table-column prop="capacity" label="容量" width="0" v-if="false" />
        <el-table-column prop="introduction" label="介绍" width="0" v-if="false" />
        <el-table-column prop="applicant" label="申请人" width="0" v-if="false" />
        <el-table-column fixed="right" label="操作" width="200">
          <template #default="scope">
              <el-button type="text" size="small" @click="edit(scope.row.courseId)">查看/编辑</el-button>
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
            @current-change="handleCurrentChange"
        />
      </div>
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
      select_year_semester:[],
      semesterOptions:[],
      total:0,
      pageSize:10,
      currentPage:1,
      search:'',
      dialogVisible:false,
      dialogVisible2:false,
      add_school_major:'',
      add_building_classroom:'',
      edit_school_major:'',
      edit_building_classroom:'',
      majorOptions:[],
      classroomOptions:[],
      id:{
        id:0
      },
      tableData:[]
    }
  },
  mounted() {
    this.load()
    this.getOptionMajor()
    this.getOptionClassroom()
    this.getOptionSemesters()
  },
  watch: {
    select_year_semester: {
      deep: true,
      handler(new_) {
        this.load()
      }
    }
  },
  methods:{
    getOptionMajor: function () {
      request.post("/common/allMajors").then(res => {
        let that = this
        if (!res.data) return
        res.data.data.schools.forEach (function (item) {
          let option = {value: item.school, label: item.school, children: []}
          if (!item.majors) return
          item.majors.forEach (function (item) {
            let child = {value: item, label: item}
            option.children.push(child)
          })
          that.majorOptions.push(option)
        })
      })
    },
    getOptionClassroom: function () {
      request.post("/common/allClassrooms").then(res => {
        let that = this
        if (!res.data) return
        res.data.data.buildings.forEach (function (item) {
          let option = {value: item.building, label: item.building, children: []}
          if (!item.classrooms) return
          item.classrooms.forEach (function (item) {
            let child = {value: item, label: item}
            option.children.push(child)
          })
          that.classroomOptions.push(option)
        })
      })
    },
    getOptionSemesters: function (){
      request.post("/common/allSemesters").then(res => {
        let that = this
        if (!res.data) return
        res.data.data.yearAndSemesters.forEach (function (item) {
          let option = {value: item.year, label: item.year, children: []}
          if (!item.semesters) return
          item.semesters.forEach (function (item) {
            let child = {value: item, label: item}
            option.children.push(child)
          })
          that.semesterOptions.push(option)
        })
        this.select_year_semester = [res.data.data.defaultYear, res.data.data.defaultSemester]
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
          request.post("/admin/csvAddCourse", data).then(res => {
            if(res.data.code!==200) {
              this.$message({
                type:"error",
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
              search: this.search,
              year: this.select_year_semester[0],
              semester: this.select_year_semester[1]
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
      this.$router.push("/admin/addcourse")
    },
    edit:function (courseId) {
      this.$router.push({ name: 'EditCourse', params: { id: courseId} })
    },
    handleDelete(courseId) {
      this.id.id=courseId
      request.post("/admin/deleteCourse",this.id).then(res => {
        if(res.data.code!==200) {
          this.$message({
            type:"error",
            message: res.data.msg
          })
        }
        this.load()  // 删除之后重新加载表格的数据
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