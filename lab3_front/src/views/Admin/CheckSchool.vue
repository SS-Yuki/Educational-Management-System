<template>
  <div class="main_part">
    <div>
      <div class="add" style="margin: 10px 0">
        <div style="float: left">
          <el-button size="large" @click="add" type="primary">新增</el-button>
        </div>
        <div style="float: left">
          <el-input clearable v-model="search" placeholder="请输入关键字" style="width:50%;margin-left: 100px"></el-input>
          <el-button type="primary" style="margin-left: 5px" @click="load">搜索</el-button>
        </div>
      </div>
      <div>
        <el-table :data="tableData" style="width: 1200px" border stripe>
          <el-table-column prop="schoolName" label="学院" width="200" />
          <el-table-column prop="introduction" label="简介" width="800" />
          <el-table-column fixed="right" label="操作" width="200">
            <template #default="scope">
              <el-button type="text" size="small" @click="handleEdit(scope.row.schoolName, scope.row.introduction)">编辑</el-button>
              <el-popconfirm title="确认删除?" @confirm="handleDelete(scope.row.schoolName)">
                <template #reference>
                  <el-button type="text">删除</el-button>
                </template>
              </el-popconfirm>
            </template>
          </el-table-column>
        </el-table>
      </div>
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
      <el-dialog v-model="dialogVisible" title="添加新院系" width="30%">
        <el-form :model="addSchool" label-width="120px">
          <el-form-item label="院系名">
            <el-input v-model="addSchool.schoolName" />
          </el-form-item>
          <el-form-item label="介绍">
            <el-input v-model="addSchool.introduction"/>
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
      <el-dialog v-model="dialogVisible2" title="编辑院系信息" width="30%">
        <el-form :model="newSchool" label-width="120px">
          <el-form-item label="院系">
            <el-input v-model="newName" />
          </el-form-item>
          <el-form-item label="介绍">
            <el-input v-model="introduction"/>
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
  name: "CheckSchool",
  data(){
    return{
      total:0,
      pageSize:10,
      currentPage:1,
      search:'',
      dialogVisible:false,
      dialogVisible2:false,
      oldName:'',
      newName:'',
      introduction:'',
      addSchool:{
        schoolName:'',
        introduction:''
      },
      newSchool:{},
      tableData:[]
    }
  },
  mounted() {
    this.load()
  },
  methods:{
    load(){
      setTimeout(() => {
        request.post("/admin/findSchoolPage",{
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
      this.addSchool={}
    },
    save:function (){
      request.post("/admin/addSchool", this.addSchool).then(res => {
        if(res.data.code!==200) {
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
      
      request.post("/admin/updateSchoolInfo", {
        oldName:this.oldName,
        newName:this.newName,
        introduction:this.introduction
      }).then(res=>{
        if(res.data.code!==200) {
          this.$message({
            type:"error",
            message: res.data.msg
          })
        }
        this.load()
        this.dialogVisible2=false
      })
    },
    handleEdit(schoolName, introduction){
      
      this.oldName=schoolName
      this.newName=schoolName
      this.introduction=introduction
      
      this.dialogVisible2 = true
      this.newSchool={}
    },
    handleDelete(schoolName) {
      request.post("/admin/deleteSchool",schoolName).then(res => {
        if(res.data.code!==200) {
          this.$message({
            type:"error",
            message: res.data.msg
          })
        }
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