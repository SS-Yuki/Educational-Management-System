<template>
  <div class="checkteacher">
    <div>
      <div class="add" style="margin: 10px 0">
        <el-button size="large" @click="add" type="primary">新增</el-button>
        <el-input clearable v-model="search" placeholder="请输入关键字" style="width:50%;margin-left: 100px"></el-input>
        <el-button type="primary" style="margin-left: 5px" @click="load">搜索</el-button>
      </div>
      <el-table :data="tableData" style="width: 100%" border stripe>
        <el-table-column fixed prop="number" label="工号" width="150" sortable/>
        <el-table-column prop="name" label="姓名" width="120" />
        <el-table-column prop="idNum" label="身份证号" width="120" />
        <el-table-column prop="phoneNum" label="电话" width="120" />
        <el-table-column prop="email" label="邮箱" width="120" />
        <el-table-column prop="teaStatus" label="状态" width="120" />
        <el-table-column prop="school" label="院系" width="120" />
        <el-table-column prop="major" label="专业" width="120" />
        <el-table-column prop="password" label="密码" width="120" />
        <el-table-column fixed="right" label="操作" width="120">
          <template #default="scope">
            <el-button type="text" size="small" @click="handleEdit(
                scope.row.number,
                scope.row.name,
                scope.row.idNum,
                scope.row.phoneNum,
                scope.row.email,
                scope.row.teaStatus,
                scope.row.school,
                scope.row.major,
                scope.row.password
            )">编辑</el-button>
            <el-popconfirm title="确认删除?" @confirm="handleDelete(scope.row.number)">
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
        <el-form :model="addStudent" label-width="120px">
          <el-form-item label="新工号">
            <el-input v-model="addTeacher.number" />
          </el-form-item>
          <el-form-item label="新姓名">
            <el-input v-model="addTeacher.name" />
          </el-form-item>
          <el-form-item label="新电话">
            <el-input v-model="addTeacher.phoneNum" />
          </el-form-item>
          <el-form-item label="新身份证号">
            <el-input v-model="addTeacher.idNum" />
          </el-form-item>
          <el-form-item label="新邮箱">
            <el-input v-model="addTeacher.email" />
          </el-form-item>
          <el-form-item label="新身份">
            <el-radio v-model="addTeacher.role" label="teacher" checked="true">teacher</el-radio>
          </el-form-item>
          <el-form-item label="新院系">
            <el-input v-model="addTeacher.school" />
          </el-form-item>
          <el-form-item label="新专业">
            <el-input v-model="addTeacher.major"/>
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
          <el-form-item label="新名字">
            <el-input v-model="name" />
          </el-form-item>
          <el-form-item label="新密码">
            <el-input v-model="password" />
          </el-form-item>
          <el-form-item label="新身份证号">
            <el-input v-model="idNum" />
          </el-form-item>
          <el-form-item label="新电话号">
            <el-input v-model="phoneNum" />
          </el-form-item>
          <el-form-item label="新邮箱">
            <el-input v-model="email" />
          </el-form-item>
          <el-form-item label="新状态">
            <el-radio v-model="teaStatus" label="Dimission" checked="true">Dimisson</el-radio>
            <el-radio v-model="teaStatus" label="Normal" checked="true">Normal</el-radio>
          </el-form-item>
          <el-form-item label="新专业">
            <el-input v-model="major" />
          </el-form-item>
          <el-form-item label="新学院">
            <el-input v-model="school" />
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
  name: "CheckTeacherInfo",
  data(){
    return{
      options:[],
      total:0,
      pageSize:10,
      currentPage:1,
      search:'',
      dialogVisible:false,
      dialogVisible2:false,
      //edit
      jobNumber:'',
      name:'',
      password:'',
      idNum:'',
      phoneNum:'',
      email:'',
      teaStatus:'',
      major:'',
      school:'',
      addTeacher:{
        number:'',
        name:'',
        idNum:'',
        phoneNum:'',
        email:'',
        role:'teacher',
        school:'',
        major:''
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
  },
  methods:{
    school_major_select(){

    },
    load(){
      console.log(this.pageData)
      request.post("/admin/findTeacherPage",{
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
      this.addTeacher={}
    },
    save:function (){
      request.post("/admin/register", this.addTeacher).then(res => {
        console.log(res)
        this.load() // 刷新表格的数据
        this.dialogVisible = false  // 关闭弹窗
      })
    },
    saveEdit(){
      request.post("/admin/updateTeacherInfo", {
        jobNumber:this.jobNumber,
        name:this.name,
        password:this.password,
        idNum:this.idNum,
        phoneNum: this.phoneNum,
        email:this.email,
        teaStatus:this.teaStatus,
        major:this.major,
        school:this.school
      }).then(res=>{
        console.log(res)
        this.load()
        this.dialogVisible2=false
      })
    },
    handleEdit(number,name,idNum,phoneNum,email,teaStatus,school,major,password){
      this.jobNumber=number
      this.name = name
      this.idNum = idNum
      this.phoneNum = phoneNum
      this.email = email
      this.teaStatus = teaStatus
      this.school = school
      this.major = major
      this.password = password
      this.dialogVisible2 = true
    },
    handleDelete(number) {
      request.post("/admin/delete",number).then(res => {
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
.checkteacher{
  margin-left: 100px;
  display: flex;
}
.add{
  text-align: left;
}
</style>