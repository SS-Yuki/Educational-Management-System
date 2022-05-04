<template>
  <div>
    <div>
        <el-cascader v-model="select_year_semester" placeholder="学年/学期" :options="semesterOptions" clearable/>
    </div>
<!--    <div>-->
<!--      <el-table :data="tableData1" style="width: 80%;margin-right: auto;margin-left: auto" >-->
<!--        <el-table-column prop="timeName" label="节次" width="120" />-->
<!--        <el-table-column prop="day1" label="星期一" width="120"/>-->
<!--        <el-table-column prop="day2" label="星期二" width="120"/>-->
<!--        <el-table-column prop="day3" label="星期三" width="120"/>-->
<!--        <el-table-column prop="day4" label="星期四" width="120"/>-->
<!--        <el-table-column prop="day5" label="星期五" width="120"/>-->
<!--        <el-table-column prop="day6" label="星期六" width="120"/>-->
<!--        <el-table-column prop="day7" label="星期日" width="120"/>-->
<!--      </el-table>-->
<!--    </div>-->

    <div>
      <el-table :data="timeTable" style="width: 210px;display: inline-block" border stripe>
        <el-table-column fixed prop="timeName" label="节次" width="70" />
        <el-table-column fixed prop="startTime" label="开始" width="70" />
        <el-table-column fixed prop="endTime" label="结束" width="70" />
      </el-table>
      <el-table :data="courseData[0]" style="width: 120px;display: inline-block" border stripe >
        <el-table-column fixed prop="courseName" label="星期一" width="120" />
      </el-table>
      <el-table :data="courseData[1]" style="width: 120px;display: inline-block" border stripe >
        <el-table-column fixed prop="courseName" label="星期二" width="120"/>
      </el-table>
      <el-table :data="courseData[2]" style="width: 120px;display: inline-block" border stripe >
        <el-table-column fixed prop="courseName" label="星期三" width="120"/>
      </el-table>
      <el-table :data="courseData[3]" style="width: 120px;display: inline-block" border stripe >
        <el-table-column fixed prop="courseName" label="星期四" width="120"/>
      </el-table>
      <el-table :data="courseData[4]" style="width: 120px;display: inline-block" border stripe >
        <el-table-column fixed prop="courseName" label="星期五" width="120" />
      </el-table>
      <el-table :data="courseData[5]" style="width: 120px;display: inline-block" border stripe >
        <el-table-column fixed prop="courseName" label="星期六" width="120" />
      </el-table>
      <el-table :data="courseData[6]" style="width: 120px;display: inline-block" border stripe >
        <el-table-column fixed prop="courseName" label="星期日" width="120" />
      </el-table>
    </div>
    <div>
      <el-table :data="tableData" style="width:1200px;margin-right: auto;margin-left: auto" >
        <el-table-column prop="courseId" label="courseId" width="200" v-if="false" />
        <el-table-column prop="courseName" label="课程名" width="200" />
        <el-table-column prop="courseNumber" label="课程编号" width="200" />
        <el-table-column prop="teacherNum" label="教师工号" width="200" v-if="false"/>
        <el-table-column prop="major" label="开课专业" width="200" />
        <el-table-column prop="school" label="开课院系" width="200" />
        <el-table-column prop="classroom" label="教室" width="200"  />
        <el-table-column prop="creditHours" label="学时" width="200"  />
        <el-table-column prop="credits" label="学分" width="200"  />
        <el-table-column prop="capacity" label="容量" width="200"  />
        <el-table-column prop="introduction" label="介绍" width="200"  />
        <el-table-column prop="applicant" label="申请人" width="200" v-if="false" />
      </el-table>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

function index(num){
  var arr=[];
  for(var i = 0;i<num;i++){
    arr.push(i);
  }
  return arr;
}

export default {
  name: "StudentViewTable",
  data(){
    return{
      semester:'',
      semesterOptions:[],
      select_year_semester:[],
      courseData:[[],[],[],[],[],[],[]],
      tableData:[],
      timeTable:[],
      length:0
    }
  },
  methods:{
    load(){
      setTimeout(() => {
        console.log(this.select_year_semester)
        request.post("/student/getMyCourseInSemester",{
              year: this.select_year_semester[0],
              semester: this.select_year_semester[1],
            }
        ).then(res=>{
          if(res.data.code===200){
            this.tableData=res.data.data
          }
          else{
            this.$message({
              type:"error",
              message: res.data.msg
            })
          }
        })
        request.post("/student/getMyCourseTableInSemester ",{
              year: this.select_year_semester[0],
              semester: this.select_year_semester[1],
            }
        ).then(res=>{
          if(res.data.code===200){
            this.courseData=res.data.data
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
    handleDelete(courseId) {
      this.courseId=courseId
      request.post("/admin/deleteCourse",this.courseId).then(res => {
        if(res.data.code!==200) {
          this.$message({
            type:"error",
            message: res.data.msg
          })
        }
        this.load()  // 删除之后重新加载表格的数据
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
        console.log(this.select_year_semester)
      })
    },
    getTime:function (){
      request.post("/common/allTime").then(res=>{
        if (!res.data) return
        this.length=res.data.data.length;

        let arr = new Array(this.length).fill("");
        for(let i = 0; i < this.length; i++) {
          this.timeTable.push(arr)
        }

        for(let i = 0; i < 7; i++) {
          this.courseData.push(arr)
        }

        res.data.data.times.forEach ((item) => {
          this.timeTable.push(item);
        })

      })
    },
    getCourse:function (){
      request.post("/student/allTime").then(res=>{
        if (!res.data) return

        res.data.data.times.forEach ((item) => {
          this.courseData.push(item);
        })

      })
    },
  },
  watch: {
    select_year_semester: {
      deep: true,
      handler() {
        this.load()
      }
    }
  },
  mounted() {
    this.getOptionSemesters()
    this.getTime()
  }

}
</script>

<style scoped>

</style>