<template>
  <div class="main_part">
    <div>
      <div>
        <el-table :data="tableData" border :show-header="status" style="width: 100%" size="large">
          <el-table-column prop="key"  width="180" />
          <el-table-column prop="value"  width="180" />
        </el-table>
      </div>
      <div style="margin-left:300px;margin-right: 300px;margin-bottom: 5px;margin-top: 10px">
        <div>
          <el-button size="large" @click="open" type="primary">开启选课</el-button>
          <el-button size="large" @click="close" type="primary">关闭选课</el-button>
        </div>
      </div>

      <div style="margin-left:300px;margin-right: 300px">
        <div>
          <el-button size="large" @click="next" type="primary">下一轮选课</el-button>
          <el-button size="large" @click="select" type="primary">随机筛选</el-button>
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
        {key:'选课状态',value:''},
        {key:'选课轮数',value:''}
      ]
    }
  },
  mounted() {
    this.load()
    this.getTurn()
  },
  methods:{
    open:function(){
      request.post("/admin/openSelectCourse",)
      this.load()
    },
    close:function(){
      request.post("/admin/closeSelectCourse",)
      this.load()
    },
    next:function (){
      request.post("/admin/nextTurn",)
      this.load()
    },
    select:function (){
      request.post("/admin/randomSelect",)
    },
    load(){
      setTimeout(() => {
        request.post("/admin/isSelectCourseOpen").then(res=>{
          this.$message({
            type:"success",
            message: res.data.msg
          })
          this.tableData[0].value=res.data.data;
        })
      }, 10)
    },
    getTurn:function (){
      request.post("/admin/whichTurn").then(res=>{
        this.$message({
          type:"success",
          message: res.data.msg
        })
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