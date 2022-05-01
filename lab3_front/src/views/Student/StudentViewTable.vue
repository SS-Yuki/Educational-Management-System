<template>
  <div>
    <el-select v-model="semester" placeholder="请选择学期">
      <el-option
          v-for="item in semesterOptions"
          :key="item.value"
          :label="item.label"
          :value="item.value">
      </el-option>
    </el-select>
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
    <el-table :data="tableData2" style="width: 50%;margin-right: auto;margin-left: auto" >
      <el-table-column prop="courseId" label="编号" width="120" />
      <el-table-column prop="teacher" label="任课教师" width="120"/>
      <el-table-column prop="teacher" label="任课时间" width="120"/>
      <el-table-column prop="teacher" label="任课时间" width="120"/>
      <el-table-column prop="teacher" label="任课时间" width="120"/>
    </el-table>
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
      tableData1:[],
      tableData2:[]
    }
  },
  methods:{
    getOptionSemesters: function (){
      request.post("/admin/allSemesters").then(res => {
        if (!res.data) return
        this.semester = res.data.data.defaultSemester
        res.data.data.semesters.forEach ((item) => {
          let option = {value: item, label: item}
          this.semesterOptions.push(option)
        })
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