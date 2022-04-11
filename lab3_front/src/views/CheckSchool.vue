<template>
  <div class="checkschool">
    <div>
      <div class="add" style="margin: 10px 0">
        <el-button size="large" @click="add" type="primary">新增</el-button>
      </div>
      <el-table :data="tableData" style="width: 100%" border stripe>
        <el-table-column prop="school" label="学院" width="120" />
        <el-table-column prop="introduction" label="简介" width="600" />
        <el-table-column fixed="right" label="操作" width="120">
          <template #default>
            <el-button type="text" size="small" @click="handleEdit">编辑</el-button>
            <el-popconfirm title="确认删除?">
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
      <el-dialog v-model="dialogVisible" title="添加新用户" width="30%">
        <el-form :model="addSchool" label-width="120px">
          <el-form-item label="新院系">
            <el-input v-model="addSchool.schoolName" />
          </el-form-item>
          <el-form-item label="新介绍">
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
      pageData:{
        pageNum: 1,
        pageSize:2,
        search:''
      },
      addSchool:{
        schoolName:'',
        introduction:''
      },
      new_school:{
        oldName:'',
        newName:'',
        introduction:''
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
      request.post("/admin/findSchoolPage",this.pageData).then(res=>{
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
      this.addSchool={}
    },
    save:function (){
      request.post("/admin/addSchool", this.addSchool).then(res => {
        console.log(res)
        this.load() // 刷新表格的数据
        this.dialogVisible = false  // 关闭弹窗
      })
    },
    handleEdit(){
      this.dialogVisible = true
      request.post("/admin/updatesSchool",this.new_school).then(res=>{
        console.log(res)
        this.dialogVisible=false
      })
    },
    handleClick:function (){

    },
    handleSizeChange:function (){

    },
    handleCurrentChange:function (){

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