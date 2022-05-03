<template>
  <div class="main_part">
    <div style="margin-right: auto;margin-left: auto">
      <el-input clearable v-model="courseId" placeholder="请输入课程编号" style="width:20%;"></el-input>
      <el-button type="primary" @click="search">确认</el-button>
    </div>
    <div>
      <el-table :data="tableData" border :show-header="status" style="width: 30%;margin-left: auto;margin-right: auto"  stripe size="large">
        <el-table-column prop="key"  width="180" />
        <el-table-column prop="value"  width="180" />
      </el-table>
    </div>
    <div style="margin-left: auto;margin-right: auto">
      <el-input v-model="applyReason"
                :rows="12"
                placeholder="请输入申请理由"
                style="width:40%"
                clearable ></el-input>
    </div>
    <div>
      <el-button type="primary" @click="submit">提交申请</el-button>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";
export default {
  name: "StudentApply",
  data(){
    return{
      status:false,
      courseId:0,
      applyReason:"",
      tableData : [
        {key: '课程id', value: ''},
        {key: '课程编号', value: ''},
        {key: '课程名', value: ''},
        {key: '教师工号', value: ''},
        {key: '教师名', value: ''},
        {key: '开课院系', value: ''},
        {key: '开课专业', value: ''},
        {key: '教学楼', value: ''},
        {key: '教室', value: ''},
        {key: '学时', value: ''},
        {key: '学分', value: ''},
        {key: '容量', value: ''},
        {key: '简介', value: ''},
        {key: '选课类型', value: ''},
        {key: '专业限制', value: ''},
        {key: '开课学年', value: ''},
        {key: '开课学期', value: ''},
        {key: '上课时间', value: ''},
      ]
    }
  },
  methods:{
    search:function (){
      request.post("/common/findOneCourseInfo",this.courseId).then(res=>{
        this.tableData[0].value = res.data.data.courseId
        this.tableData[1].value = res.data.data.courseNumber
        this.tableData[2].value = res.data.data.courseName
        this.tableData[3].value = res.data.data.teacherNum
        this.tableData[4].value = res.data.data.teacherName
        this.tableData[5].value = res.data.data.school
        this.tableData[6].value = res.data.data.major
        this.tableData[7].value = res.data.data.building
        this.tableData[8].value = res.data.data.classroom
        this.tableData[9].value = res.data.data.creditHours
        this.tableData[10].value = res.data.data.credits
        this.tableData[11].value = res.data.data.capacity
        this.tableData[12].value = res.data.data.introduction
        this.tableData[13].value = res.data.data.selectTypeString
        this.tableData[14].value = res.data.data.majorLimits
        this.tableData[15].value = res.data.data.year
        this.tableData[16].value = res.data.data.semester
        this.tableData[17].value = res.data.data.occupyTime
      })
    },
    submit:function (){
      request.post("/student/selectCourseApply",
          {courseId:this.courseId,applyReason:this.applyReason}).then(res=>{

      })
    }
  }
}
</script>

<style scoped>

</style>