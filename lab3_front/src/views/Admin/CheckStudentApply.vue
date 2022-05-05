<template>
  <div class="main_part">
    <el-table :data="tableData" style="width: 1200px" border stripe>
      <el-table-column prop="applyId" label="申请ID" v-if="false"/>
      <el-table-column prop="courseId" label="课程ID" width="200" v-if="false"/>
      <el-table-column prop="courseNum" label="课程编号" width="200"/>
      <el-table-column prop="courseName" label="课程名" width="200"/>
      <el-table-column prop="teacherName" label="教师姓名" width="200" v-if="false"/>
      <el-table-column prop="year" label="学年" width="200"/>
      <el-table-column prop="semester" label="学期" width="200"/>
      <el-table-column prop="description" label="申请理由" width="200"/>
      <el-table-column prop="dealStatus" label="处理状态" width="200"/>
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
    <el-dialog v-model="deal" title="审批" width="30%">
      <el-form :model="addSchool" label-width="120px">
        <el-form-item label="审批意见">
          <el-input v-model="apply.advice"
          type="textarea"
          :rows="5"/>
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
      apply:{
        applyId:0,
        advice:""
      }
    }
  },
  mounted() {
    this.load()
  },
  methods:{
    dealApply(applyId){
      this.deal=true
      this.apply.applyId=applyId
    },
    accept:function() {
      this.deal=false
      request.post("/admin/acceptSelectCourseApply",
          this.apply
      ).then(res=>{
        if(res.data.code===200){
          this.$message({
            type:"success",
            message: res.data.msg
          })
        }
        else{
          this.$message({
            type:"error",
            message: res.data.msg
          })
        }
      })
      this.load()
    },
    reject:function() {
      this.deal=false
      request.post("/admin/rejectSelectCourseApply",
          this.apply).then(res=> {
            if (res.data.code === 200) {
              this.$message({
                type: "success",
                message: res.data.msg
              })
            } else {
              this.$message({
                type: "error",
                message: res.data.msg
              })
            }
          }
          )
      this.load()
    },
    load(){
      setTimeout(() => {
        request.post("/admin/findSelectCourseApplyPage",{
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