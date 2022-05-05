<template>
  <div class="main_part">
    <el-table :data="tableData" style="width: 1200px" border stripe>
      <el-table-column prop="applyId" label="申请ID" v-if="false"/>
      <el-table-column prop="courseId" label="课程ID" width="100" v-if="false"/>
      <el-table-column prop="courseNum" label="课程编号" width="100"/>
      <el-table-column prop="courseName" label="课程名" width="100"/>
      <el-table-column prop="teacherName" label="教师姓名" width="100" v-if="false"/>
      <el-table-column prop="year" label="学年" width="100"/>
      <el-table-column prop="semester" label="学期" width="100"/>
      <el-table-column prop="description" label="学期" width="100"/>
      <el-table-column prop="dealStatus" label="学期" width="100"/>
      <el-table-column fixed="right" label="操作" width="200">
        <template #default="scope">
          <el-button size="small" @click="dealApply(scope.row.applyId)">
            处理
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <div style="margin: 10px 0">
      <el-pagination
          v-model:currentPage="currentPage"
          v-model:page-size="pageSize"
          :page-sizes="[5,10,20]"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          @current-change="handleCurrentChange"
      />
    </div>
  </div>
  <div>
    <el-dialog v-model="deal" title="添加新院系" width="30%">
      <el-form :model="addSchool" label-width="120px">
        <el-form-item label="审批意见">
          <el-input v-model="advice" />
        </el-form-item>
        <span class="dialog-footer">
        <el-button type="primary" @click="accept" >通过</el-button>
        <el-button type="primary" @click="reject">不通过</el-button>
      </span>
      </el-form>
      <template #footer>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "CheckStudentApply",
  data(){
    return{
      advice:'',
      deal:false,
      total:0,
      pageSize:10,
      currentPage:1,
      search:'',
      tableData:[],
      applyId:0
    }
  },
  mounted() {
    this.load()
  },
  methods:{
    dealApply(applyId){
      this.deal=true
      this.applyId=applyId
    },
    accept:function() {
      this.deal=false
      request.post("/admin/acceptSelectCourseApply",
          {applyId:this.applyId,advice:this.advice}
      )
      this.load()
    },
    reject:function() {
      this.deal=false
      request.post("/admin/rejectSelectCourseApply",
          {applyId:this.applyId,advice:this.advice})
      this.load()
    },
    load(){
      setTimeout(() => {
        request.post("/admin/findSelectCoursePage",{
              pageNum: this.currentPage,
              pageSize: this.pageSize,
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
      }, 500);
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