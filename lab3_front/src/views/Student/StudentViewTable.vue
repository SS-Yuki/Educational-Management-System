<template>
  <div>
    <div>
        <el-cascader v-model="add_year_semester" placeholder="学年/学期" :options="semesterOptions"/>
    </div>
    <div>
      <el-table :data="tableData1" style="width: 80%;margin-right: auto;margin-left: auto" >
        <el-table-column prop="timeName" label="节次" width="120" />
        <el-table-column prop="day1" label="星期一" width="120"/>
        <el-table-column prop="day2" label="星期二" width="120"/>
        <el-table-column prop="day3" label="星期三" width="120"/>
        <el-table-column prop="day4" label="星期四" width="120"/>
        <el-table-column prop="day5" label="星期五" width="120"/>
        <el-table-column prop="day6" label="星期六" width="120"/>
        <el-table-column prop="day7" label="星期日" width="120"/>
      </el-table>
    </div>
    <div>
      <el-table :data="tableData2" style="width:1200px;margin-right: auto;margin-left: auto" >
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
        <el-table-column fixed="right" label="操作" width="200">
          <template #default="scope">
            <el-popconfirm title="确认退课?" @confirm="handleDelete(scope.row.courseId)">
              <template #reference>
                <el-button type="text">退课</el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";
export default {
  name: "StudentViewTable",
  data(){
    return{
      semester:'',
      semesterOptions:[],
      add_year_semester:[],
      tableData1:[],
      tableData2:[]
    }
  },
  methods:{
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
        this.add_year_semester = [res.data.data.defaultYear, res.data.data.defaultSemester]
      })
    },
    getTable1:function (){
      request.post("/commom/courseInfo").then(res=>{
        this.tableData1=res.data.data.courseInfo
      })
    },
    getTable2:function (){
      request.post("/commom/courseInfo").then(res=>{
        this.tableData2=res.data.data.courseInfo
      })
    }
  },
  mounted() {
    this.getOptionSemesters()
    this.getTable1()
    this.getTable2()
  }

}
</script>

<style scoped>

</style>