<template>
  <div class="checkclassroom">
    <div>
      <div class="add" style="margin: 10px 0">
        <el-button size="large" @click="add" type="primary">新增</el-button>
        <el-input clearable v-model="search" placeholder="请输入关键字" style="width:50%;margin-left: 100px"></el-input>
        <el-button type="primary" style="margin-left: 5px" @click="load">搜索</el-button>
      </div>
      <el-table :data="tableData" style="width: 100%" border stripe>
        <el-table-column prop="classroomName" label="教室" width="300" />
        <el-table-column prop="buildingName" label="教学楼" width="300" />
        <el-table-column fixed="right" label="操作" width="300">
          <template #default="scope">
            <el-popconfirm title="确认删除?" @confirm="handleDelete(scope.row.buildingName,scope.row.classroomName)">
              <template #reference>
                <el-button type="text">删除</el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
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
    <div>
      <el-dialog v-model="dialogVisible" title="添加新教学楼" width="30%">
        <el-form :model="addClassroom" label-width="120px">
          <el-form-item label="新教室">
            <el-input v-model="addClassroom.classroomName" />
          </el-form-item>
          <el-form-item label="新教学楼">
            <el-input v-model="addClassroom.buildingName" />
          </el-form-item>
          <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="save">确认</el-button>
      </span>
        </el-form>
        <template #footer>
        </template>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "CheckClassroom",
  data(){
    return{
      total:0,
      pageSize:10,
      currentPage:1,
      search:'',
      dialogVisible:false,
      dialogVisible2:false,
      addClassroom:{
        classroomName:'',
        buildingName:''
      },
      tableData:[]
    }
  },
  mounted() {
    this.load()
  },
  methods:{
    load(){
      console.log(this.pageData)
      request.post("/admin/findClassroomPage",{
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
    add:function (){
      this.dialogVisible=true
      this.buildingName=''
    },
    save:function (){
      request.post("/admin/addClassroom", this.addClassroom).then(res => {
        console.log(res)
        this.load() // 刷新表格的数据
        this.dialogVisible = false  // 关闭弹窗
      })
    },
    handleDelete(buildingName,classroomName) {
      request.post("/admin/deleteClassroom", {
        buildingName:buildingName,
        classroomName:classroomName
      }).then(res => {
        this.load()  // 删除之后重新加载表格的数据
      })
    },
    handleClick:function (){

    },
    handleSizeChange:function (){

    },
    handleCurrentChange:function (pageNum){
      this.currentPage = pageNum
      this.load()
    }
  }
}
</script>

<style scoped>
.checkclassroom{
  margin-left: 100px;
  display: flex;
}
.add{
  text-align: left;
}
</style>