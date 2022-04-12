<template>
  <div class="checkschool">
    <div>
      <div class="add" style="margin: 10px 0">
        <el-button size="large" @click="add" type="primary">新增</el-button>
        <el-input clearable v-model="search" placeholder="请输入关键字" style="width:50%;margin-left: 100px"></el-input>
        <el-button type="primary" style="margin-left: 5px" @click="load">搜索</el-button>
      </div>
      <el-table :data="tableData" style="width: 100%" border stripe>
        <el-table-column prop="timeName" label="事件" width="200" />
        <el-table-column prop="startTime" label="开始时间" width="200" />
        <el-table-column prop="endTime" label="结束时间" width="200" />
        <el-table-column fixed="right" label="操作" width="200">
          <template #default="scope">
            <el-button type="text" size="small" @click="handleEdit(scope.row.timeName)">编辑</el-button>
            <el-popconfirm title="确认删除?" @confirm="handleDelete(scope.row.timeName)">
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
      <el-dialog v-model="dialogVisible" title="添加新事件" width="30%">
        <el-form :model="addTime" label-width="120px">
          <el-form-item label="新事件">
            <el-input v-model="addTime.timeName" />
          </el-form-item>
          <el-form-item label="新起始时间">
            <el-input v-model="addTime.startTime" />
          </el-form-item>
          <el-form-item label="新结束时间">
            <el-input v-model="addTime.endTime" />
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
    <div>
      <el-dialog v-model="dialogVisible2" title="编辑信息" width="30%">
        <el-form :model="newSchool" label-width="120px">
          <el-form-item label="新开始时间">
            <el-input v-model="startTime" />
          </el-form-item>
          <el-form-item label="新结束时间">
            <el-input v-model="endTime" />
          </el-form-item>
          <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="saveEdit">确认</el-button>
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
  name: "CheckTime",
  data(){
    return{
      total:0,
      pageSize:10,
      currentPage:1,
      search:'',
      dialogVisible:false,
      dialogVisible2:false,
      timeName:'',
      startTime:'',
      endTime:'',
      addTime:{
        timeName:'',
        startTime:'',
        endTime:''
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
      request.post("/admin/findTimePage",{
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
      request.post("/admin/addTime", this.addTime).then(res => {
        console.log(res)
        this.load() // 刷新表格的数据
        this.dialogVisible = false  // 关闭弹窗
      })
    },
    saveEdit(){
      console.log(this.newSchool)
      request.post("/admin/updateTimeInfo", {
        timeName:this.timeName,
        startTime: this.startTime,
        endTime: this.endTime
      }).then(res=>{
        console.log(res)
        this.load()
        this.dialogVisible2=false
      })
    },
    handleEdit(timeName){
      this.timeName=timeName
      this.dialogVisible2 = true
      this.newBuildingName=''
    },
    handleDelete(timeName) {
      request.post("/admin/deleteTime",timeName).then(res => {
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
.checkschool{
  margin-left: 100px;
  display: flex;
}
.add{
  text-align: left;
}
</style>