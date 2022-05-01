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
        {key: '课程名', value: ''},
        {key: '任课教师', value: ''},
        {key: '上课时间', value: ''}
      ]
    }
  },
  methods:{
    search:function (){
      request.post("/common/CourseInfo",this.courseId).then(res=>{

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