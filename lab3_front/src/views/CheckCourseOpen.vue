<template>
  <div class="checkschool">
    <div>
      <div>
        <el-table :data="tableData" border :show-header="status" style="width: 100%" size="large">
          <el-table-column prop="key"  width="180" />
          <el-table-column prop="value"  width="180" />
        </el-table>
      </div>
      <div>
        <div style="float: left">
          <el-button size="large" @click="open" type="primary">开启选课</el-button>
        </div>
        <div style="float: left">
          <el-button size="large" @click="close" type="primary">关闭选课</el-button>
        </div>
      </div>
      <div style="margin: 10px 0">
        <el-pagination
            v-model:currentPage="currentPage"
            v-model:page-size="pageSize"
            :page-sizes="[5,10,20]"
            :small="small"
            :disabled="disabled"
            :background="background"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
        />
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
        {key:'选课状态',value:''}
      ]
    }
  },
  mounted() {
    this.load()
  },
  methods:{
    open:function(){
      request.post("/admin/openSelectCourse",)
      this.get_info()
    },
    close:function(){
      request.post("/admin/closeSelectCourse",)
      this.get_info()
    },
    get_info:function (){
      request.post("/admin/isSelectCourseOpen").then(res=>{
        this.tableData[0].value=res.data.data;
      })
    },
    load(){
      console.log(this.pageData)
      request.post("/admin/findSchoolPage",{
            pageNum: this.currentPage,
            pageSize: this.pageSize,
            search: this.search
          }
      ).then(res=>{
        console.log(res)
        if(res.data.code===200){
          this.tableData=res.data.data.records
          this.total=res.data.data.total
        }
        else{
          this.$message({
            type:"fail",
            message: "失败"
          })
        }
      })
    },
  }
}
</script>

<style scoped>
.checkschool{
  margin-left: 100px;
  display: flex;
}
.add{
  text-align: left;
}
</style>