<template>
  <div class="main_part">
    <div>
      <div class="add" style="margin: 10px 0">
        <div style="float: left">
          <el-button size="large" @click="add" type="primary">新增</el-button>
        </div>
        <div style="float: left">
          <el-button size="large" @click="deleteTime" type="primary">删除</el-button>
        </div>
        <div style="float: left">
          <el-input clearable v-model="search" placeholder="请输入关键字" style="width:50%;margin-left: 100px"></el-input>
          <el-button type="primary" style="margin-left: 5px" @click="load">搜索</el-button>
        </div>
      </div>
      <el-table :data="tableData" style="width: 1200px" border stripe>
        <el-table-column prop="timeName" label="事件" width="400" />
        <el-table-column prop="startTime" label="开始时间" width="400" />
        <el-table-column prop="endTime" label="结束时间" width="400" />
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
      <el-dialog v-model="dialogVisible" title="添加新事件" width="30%">
        <el-form :model="addTime" label-width="120px">
<!--          <el-form-item label="事件">-->
<!--            <el-input v-model="addTime.timeName" />-->
<!--          </el-form-item>-->
<!--          <el-form-item label="开始时间">-->
<!--            <el-input v-model="addTime.startTime" />-->
<!--          </el-form-item>-->
<!--          <el-form-item label="结束时间">-->
<!--            <el-input v-model="addTime.endTime" />-->
<!--          </el-form-item>-->
<!--          <el-form-item label="时间选择">-->
<!--          </el-form-item>-->
          <div class="demo-time-range">
            <el-time-select
                v-model="addTime.startTime"
                :max-time="addTime.endTime"
                class="mr-4"
                placeholder="开始时间"
                start="08:00"
                step="00:05"
                end="21:05"
            />
            <el-time-select
                v-model="addTime.endTime"
                :min-time="addTime.startTime"
                placeholder="结束时间"
                start="08:00"
                step="00:05"
                end="21:05"
            />
          </div>
          <br><br>
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
      <el-dialog v-model="dialogVisible2" title="编辑事件信息" width="30%">
        <el-form :model="newSchool" label-width="120px">
          <el-form-item label="开始时间">
            <el-input v-model="startTime" />
          </el-form-item>
          <el-form-item label="结束时间">
            <el-input v-model="endTime" />
          </el-form-item>
          <span class="dialog-footer">
        <el-button @click="dialogVisible2 = false">取消</el-button>
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
      value:'',
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
        startTime:'',
        endTime:'',
      },
      tableData:[],

    }
  },
  mounted() {
    this.load()

  },
  methods:{
    load(){
      setTimeout(() => {
        request.post("/admin/findTimePage",{
              pageNum: this.currentPage,
              pageSize: this.pageSize,
              search: this.search
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
      }, 500)
    },
    add:function (){
      this.dialogVisible=true
      this.buildingName=''
    },
    deleteTime:function(){
      request.post("/admin/deleteTime",)
      this.load()
    },
    save:function (){
      request.post("/admin/addTime", this.addTime).then(res => {
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
        this.load() // 刷新表格的数据
        this.dialogVisible = false  // 关闭弹窗
      })
    },
    saveEdit(){
      
      request.post("/admin/updateTimeInfo", {
        timeName:this.timeName,
        startTime: this.startTime,
        endTime: this.endTime
      }).then(res=>{
        
        this.load()
        this.dialogVisible2=false
      })
    },
    handleEdit(timeName,startTime,endTime){
      this.timeName=timeName
      this.startTime=startTime
      this.endTime=endTime
      this.dialogVisible2 = true
      this.newBuildingName=''
    },
    handleDelete(timeName) {
      request.post("/admin/deleteTime",timeName).then(res => {
        this.load()  // 删除之后重新加载表格的数据
      })
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