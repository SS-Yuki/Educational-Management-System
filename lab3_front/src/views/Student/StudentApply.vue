<template>
  <div class="main_part">
    <div style="margin-right: auto;margin-left: auto">
      <el-input clearable v-model="courseId" placeholder="请输入课程编号" style="width:20%;" type="number"></el-input>
      <el-button type="primary" @click="search">确认</el-button>
    </div>
    <div style="margin-left: auto;margin-right: auto;margin-top: 10px">
      <el-table :data="tableData1" border :show-header="status" style="width: 360px;margin-left: 250px;float:left"  stripe size="large ">
        <el-table-column prop="key"  width="180" />
        <el-table-column prop="value"  width="180" />
      </el-table>
      <el-table :data="tableData2" border :show-header="status" style="width: 360px;"  stripe size="large">
        <el-table-column prop="key"  width="180" />
        <el-table-column prop="value"  width="180" />
      </el-table>
    </div>

    <div style="margin:10px auto">
      <el-input v-model="applyReason"
                type="textarea"
                :rows="5"
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
      tableData1 : [
        {key: '课程编号', value: ''},
        {key: '课程名', value: ''},
        {key: '教师名', value: ''},
        {key: '开课院系', value: ''},
        {key: '开课专业', value: ''},
        {key: '教学楼', value: ''},
        {key: '教室', value: ''},
        {key: '学时', value: ''}
      ],
      tableData2:[
        {key: '学分', value: ''},
        {key: '容量', value: ''},
        {key: '简介', value: ''},
        {key: '选课类型', value: ''},
        {key: '专业限制', value: ''},
        {key: '开课学年', value: ''},
        {key: '开课学期', value: ''},
        {key: '上课时间', value: ''}
      ]
    }
  },
  methods:{
    search:function (){
      request.post("/common/findOneCourseInfo",this.courseId).then(res=>{
        this.tableData1[0].value = res.data.data.courseNumber
        this.tableData1[1].value = res.data.data.courseName
        this.tableData1[2].value = res.data.data.teacherName
        this.tableData1[3].value = res.data.data.school
        this.tableData1[4].value = res.data.data.major
        this.tableData1[5].value = res.data.data.building
        this.tableData1[6].value = res.data.data.classroom
        this.tableData1[7].value = res.data.data.creditHours

        this.tableData2[0].value = res.data.data.credits
        this.tableData2[1].value = res.data.data.capacity
        this.tableData2[2].value = res.data.data.introduction
        this.tableData2[3].value = res.data.data.selectTypeString
        this.tableData2[4].value = res.data.data.majorLimits
        this.tableData2[5].value = res.data.data.year
        this.tableData2[6].value = res.data.data.semester
        this.tableData2[7].value = res.data.data.occupyTime
      })
    },
    submit:function (){
      request.post("/student/applyForSelectCourse", {
            courseId:this.courseId,
            description:this.applyReason
          }).then(res=>{
        if(res.data.code!==200) {
          this.$message({
            type:"error",
            message: res.data.msg
          })
        }
        if(res.data.code ===200) {
          this.$message({
            type:"success",
            message: res.data.msg
          })
          this.$router.push("/student/studentviewapplication")
        }
      })
    }
  }
}
</script>

<style scoped>

</style>