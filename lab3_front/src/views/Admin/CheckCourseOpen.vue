<template>
  <div class="main_part">
    <div>
      <div>
        <el-table :data="tableData" border :show-header="status" style="width: 100%" size="large">
          <el-table-column prop="key"  width="180" />
          <el-table-column prop="value"  width="180" />
        </el-table>
      </div>
      <div style="margin:10px 300px">
        <el-button size="large" @click="openPermission" type="primary" :round="true">开放学生选课权限</el-button>
        <el-button size="large" @click="closePermission" type="primary" :round="true">关闭学生选课权限</el-button>
      </div>
      <div style="margin:10px 300px">
        <el-button size="large" @click="nextSelect" type="primary" :round="true">下一轮选课</el-button>
        <el-button size="large" @click="randomSelect" type="primary" :round="true">随机筛选</el-button>
      </div>
      <div style="margin:10px 300px">
        <el-button size="large" @click="startSelect" type="primary" :round="true">开始本学期选课</el-button>
        <el-button size="large" @click="endSelect" type="primary" :round="true">结束本学期选课</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "CheckCourseOpen",
  data(){
    return{
      status:false,
      tableData:[
        {key:'学生选课权限',value:''},
        {key:'选课轮数',value:''}
      ]
    }
  },
  mounted() {
    this.getIfSelect()
    this.getTurn()
  },
  methods:{
    openPermission:function(){
      request.post("/admin/openSelectCourse",)
      if (res.data.code !== 200) {
        this.$message({
          type:"error",
          message: res.data.msg
        })
      }
      this.getIfSelect()
    },
    closePermission:function(){
      request.post("/admin/closeSelectCourse",)
      if (res.data.code !== 200) {
        this.$message({
          type:"error",
          message: res.data.msg
        })
      }
      this.getIfSelect()
    },
    nextSelect:function (){
      request.post("/admin/nextTurn",)
      if (res.data.code !== 200) {
        this.$message({
          type:"error",
          message: res.data.msg
        })
      }
      this.getTurn()
    },
    randomSelect:function (){
      request.post("/admin/randomSelect",)
      if (res.data.code !== 200) {
        this.$message({
          type:"error",
          message: res.data.msg
        })
      }
    },
    startSelect:function (){
      request.post("/admin/startThisSemesterSelectCourse",)
      if (res.data.code !== 200) {
        this.$message({
          type:"error",
          message: res.data.msg
        })
      }
      this.getIfSelect()
    },
    endSelect:function (){
      request.post("/admin/endThisSemesterSelectCourse",)
      if (res.data.code !== 200) {
        this.$message({
          type:"error",
          message: res.data.msg
        })
      }
      this.getIfSelect()
    },
    getIfSelect(){
      setTimeout(() => {
        request.post("/admin/isSelectCourseOpen").then(res=>{
          if (res.data.code !== 200) {
            this.$message({
              type:"error",
              message: res.data.msg
            })
          }
          this.tableData[0].value=res.data.data;
        })
      }, 500)
    },
    getTurn:function (){
      request.post("/admin/whichTurn").then(res=>{
        if (res.data.code !== 200) {
          this.$message({
            type:"error",
            message: res.data.msg
          })
        }
        this.tableData[1].value=res.data.data;
      })
    }
  }
}
</script>

<style scoped>
.add{
  text-align: left;
}
</style>