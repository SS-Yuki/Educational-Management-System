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
      this.load()
    },
    close:function(){
      request.post("/admin/closeSelectCourse",)
      this.load()
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

    }
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