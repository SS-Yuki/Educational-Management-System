<template>
  <div>
    <el-table :data="tableData" style="width: 50%;margin-right: auto;margin-left: auto" >
      <el-table-column prop="courseId" label="课程编号" width="180" />
      <el-table-column prop="Notes" label="审批意见" width="180"/>
      <el-table-column prop="Status" label="审批状态" width="180"/>
    </el-table>
  </div>
</template>

<script>
import request from "@/utils/request";
export default {
  name: "StudentViewApplication",
  data(){
    return{
      tableData : []
    }
  },
  methods:{
    getApplication:function (){
      request.post("/commom/courseInfo").then(res=>{
        this.tableData=res.data.data.courseInfo
      })
    }
  },
  mounted() {
    this.getApplication()
  }
}
</script>

<style scoped>

</style>