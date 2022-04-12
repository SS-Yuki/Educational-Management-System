<template>
  <div class="checkmajor">
    <div>
      <div class="add" style="margin: 10px 0">
        <el-button size="large" @click="add" type="primary">新增</el-button>
        <el-input clearable v-model="search" placeholder="请输入关键字" style="width:50%;margin-left: 100px"></el-input>
        <el-button type="primary" style="margin-left: 5px" @click="load">搜索</el-button>
      </div>
      <el-table :data="tableData" style="width: 100%" border stripe>
        <el-table-column prop="majorName" label="专业" width="120" />
        <el-table-column prop="schoolName" label="学院" width="120" />
        <el-table-column prop="introduction" label="简介" width="600" />
        <el-table-column fixed="right" label="操作" width="120">
          <template #default="scope">
            <el-button type="text" size="small" @click="handleEdit(scope.row.majorName,scope.row.schoolName)">编辑</el-button>
            <el-popconfirm title="确认删除?" @confirm="handleDelete(scope.row.majorName,scope.row.schoolName)">
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
      <el-dialog v-model="dialogVisible" title="添加新专业" width="30%">
        <el-form :model="addSchool" label-width="120px">
          <el-form-item label="院系" prop="school_major">
            <el-cascader  v-model="addMajor.schoolName" :options="options" @change="school_major_select"/>
          </el-form-item>
          <el-form-item label="专业">
            <el-input v-model="addMajor.majorName" />
          </el-form-item>
          <el-form-item label="介绍">
            <el-input v-model="addMajor.introduction"/>
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
          <el-form-item label="院系" prop="school_major">
            <el-cascader  v-model="addMajor.schoolName" :options="options"/>
          </el-form-item>
          <el-form-item label="专业">
            <el-input v-model="majorNewName" />
          </el-form-item>
          <el-form-item label="介绍">
            <el-input v-model="introduction"/>
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
  name: "CheckMajor",
  data(){
    return{
      options:[],
      total:0,
      pageSize:10,
      currentPage:1,
      search:'',
      dialogVisible:false,
      dialogVisible2:false,
      majorOldName:'',
      majorNewName:'',
      introduction:'',
      majorOldSchool:'',
      majorNewSchool:'',
      addMajor:{
        majorName:'',
        schoolName:'',
        introduction:''
      },
      // newSchool:{
      //   oldName:'',
      //   newName:'',
      //   introduction:''
      // },
      tableData:[]
    }
  },
  mounted() {
    this.load()
    this.getOption()
  },
  methods:{
    getOption: function () {
      request.post("/admin/allMajors").then(res => {
        console.log(res)
        let that = this
        if (!res.data) return
        res.data.data.schools.forEach (function (item) {
          console.log(item);
          let option = {value: item.school, label: item.school}
          that.options.push(option)
        })
      })
    },
    load(){
      console.log(this.pageData)
      request.post("/admin/findMajorPage",{
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
      this.addMajor={}
    },
    save:function (){
      request.post("/admin/addMajor", this.addMajor).then(res => {
        console.log(res)
        this.load() // 刷新表格的数据
        this.dialogVisible = false  // 关闭弹窗
      })
    },
    saveEdit(){
      console.log(this.newSchool)
      request.post("/admin/updateMajorInfo", {
        majorOldName:this.majorOldName,
        majorNewName:this.majorNewName,
        introduction:this.introduction,
        majorOldSchool:this.majorOldSchool,
        majorNewSchool:this.majorNewSchool
      }).then(res=>{
        console.log(res)
        this.load()
        this.dialogVisible2=false
      })
    },
    handleEdit(majorName,schoolName){
      this.majorOldName=majorName
      this.majorNewName=majorName
      this.majorOldSchool=schoolName
      this.majorNewSchool=schoolName
      this.dialogVisible2 = true
      this.newMajor={}
    },
    handleDelete(majorName,schoolName) {
      request.post("/admin/deleteMajor", {
        majorName:majorName,
        schoolName:schoolName
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
.checkmajor{
  margin-left: 100px;
  display: flex;
}
.add{
  text-align: left;
}
</style>