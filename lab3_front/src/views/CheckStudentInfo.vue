<template>
  <div class="checkstudent">
    <div>
      <div class="add" style="margin: 10px 0">
        <el-button size="large" @click="add" type="primary">新增</el-button>
        <el-input clearable v-model="search" placeholder="请输入关键字" style="width:50%;margin-left: 100px"></el-input>
        <el-button type="primary" style="margin-left: 5px" @click="load">搜索</el-button>
      </div>
      <el-table :data="tableData" style="width: 100%" border stripe>
        <el-table-column fixed prop="number" label="学号" width="150" sortable/>
        <el-table-column prop="name" label="姓名" width="120" />
        <el-table-column prop="idNum" label="身份证号" width="120" />
        <el-table-column prop="phoneNum" label="电话" width="120" />
        <el-table-column prop="email" label="邮箱" width="120" />
        <el-table-column prop="stuStatus" label="状态" width="120" />
        <el-table-column prop="school" label="院系" width="120" />
        <el-table-column prop="major" label="专业" width="120" />
        <el-table-column prop="password" label="密码" width="120" />
        <el-table-column fixed="right" label="操作" width="120">
          <template #default="scope">
            <el-button type="text" size="small" @click="handleEdit(scope.row.number)">编辑</el-button>
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
          <el-form-item label="身份">
            <el-radio v-model="addStudent.role" label="student">学生</el-radio>
          </el-form-item>
          <el-form-item label="院系/专业" prop="school_major">
            <el-cascader  v-model="school_major" :options="options" @change="school_major_select"/>
          </el-form-item>
          <el-form-item label="学号">
            <el-input v-model="addStudent.number" />
          </el-form-item>
          <el-form-item label="新姓名">
            <el-input v-model="addStudent.name" />
          </el-form-item>
          <el-form-item label="新电话">
            <el-input v-model="addStudent.phoneNum" />
          </el-form-item>
          <el-form-item label="新身份证号">
            <el-input v-model="addStudent.idNum" />
          </el-form-item>
          <el-form-item label="新邮箱">
            <el-input v-model="addStudent.email" />
          </el-form-item>

<!--          <el-form-item label="新院系">-->
<!--            <el-input v-model="addStudent.school" />-->
<!--          </el-form-item>-->
<!--          <el-form-item label="新专业">-->
<!--            <el-input v-model="addStudent.major"/>-->
<!--          </el-form-item>-->
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
            <el-radio v-model="stuStatus" label="Graduated" checked="true">Graduated</el-radio>
            <el-radio v-model="stuStatus" label="Normal" checked="true">Normal</el-radio>
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
      stuNumber:'',
      name:'',
      password:'',
      idNum:'',
      phoneNum:'',
      email:'',
      stuStatus:'',
      major:'',
      school:'',
      school_major:'',
      addStudent:{
        number:'',
        name:'',
        idNum:'',
        phoneNum:'',
        email:'',
        role:'student',
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
          let option = {value: item.school, label: item.school, children: []}
          if (!item.majors) return
          item.majors.forEach (function (item) {
            let child = {value: item, label: item}
            option.children.push(child)
          })
          that.options.push(option)
        })
      })
    },
    school_major_select: function () {
      this.addStudent.school = this.school_major[0]
      this.addStudent.major = this.school_major[1]
      console.log(this.addStudent.major)
    },
    load(){
      console.log(this.pageData)
      request.post("/admin/findStudentPage",{
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
      this.addStudent={}
    },
    save:function (){
      request.post("/admin/register", this.addStudent).then(res => {
        console.log(res)
        this.load() // 刷新表格的数据
        this.dialogVisible = false  // 关闭弹窗
      })
    },
    saveEdit(){
      console.log(this.newSchool)
      request.post("/admin/updateStudentInfo", {
        stuNumber:this.stuNumber,
        name:this.name,
        password:this.password,
        idNum:this.idNum,
        phoneNum: this.phoneNum,
        email:this.email,
        stuStatus:this.stuStatus,
        major:this.major,
        school:this.school
      }).then(res=>{
        console.log(res)
        this.load()
        this.dialogVisible2=false
      })
    },
    handleEdit(number){
      this.stuNumber=number
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
.checkstudent{
  margin-left: 100px;
  display: flex;
}
.add{
  text-align: left;
}
</style>