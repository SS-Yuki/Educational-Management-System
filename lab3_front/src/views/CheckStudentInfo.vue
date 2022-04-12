<template>
  <div class="checkstudent">
    <div class="add">
      <div style="margin: 10px 0">
        <div>
          <div style="float: left">
            <el-button size="large" @click="add" type="primary">新增</el-button>
          </div>
          <div style="float: left">
            <el-upload
                class="upload-demo"
                action=""
                :on-change="handleChange"
                :on-exceed="handleExceed"
                :on-remove="handleRemove"
                :file-list="fileListUpload"
                accept=".csv"
                :auto-upload="false">
              <el-button size="large" type="primary">导入</el-button>
            </el-upload>
          </div>
        </div>

        <div style="float: left">
          <el-input clearable v-model="search" placeholder="请输入关键字" style="width:50%;margin-left: 100px"></el-input>
          <el-button type="primary" style="margin-left: 5px" @click="load">搜索</el-button>
        </div>
      </div>
      <div>
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
              <el-button type="text" size="small" @click="handleEdit(
                scope.row.number,
                scope.row.name,
                scope.row.idNum,
                scope.row.phoneNum,
                scope.row.email,
                scope.row.stuStatus,
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
    <div>
      <el-dialog v-model="dialogVisible" title="添加新学生" width="30%">
        <el-form :model="addStudent" ref="add" label-width="120px" :rules="add_rules">
          <el-form-item label="身份" prop="role">
            <el-radio v-model="addStudent.role" label="student">学生</el-radio>
          </el-form-item>
          <el-form-item label="院系/专业" prop="school_major">
            <el-cascader  v-model="add_school_major" :options="options"/>
          </el-form-item>
          <el-form-item label="姓名" prop="name">
            <el-input v-model="addStudent.name" />
          </el-form-item>
          <el-form-item label="学号" prop="number">
            <el-input v-model="addStudent.number" />
          </el-form-item>
          <el-form-item label="身份证号" prop="idNum">
            <el-input v-model="addStudent.idNum" />
          </el-form-item>
          <el-form-item label="电话" prop="phoneNum">
            <el-input v-model="addStudent.phoneNum" />
          </el-form-item>
          <el-form-item label="邮箱" prop="email">
            <el-input v-model="addStudent.email" />
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
      <el-dialog v-model="dialogVisible2" title="编辑学生信息" width="30%">
        <el-form :model="newSchool" label-width="120px" :rules="edit_rules">
          <el-form-item label="院系/专业">
            <el-cascader  v-model="edit_school_major" :options="options"/>
          </el-form-item>
          <el-form-item label="状态">
            <el-radio v-model="stuStatus" label="Graduated" checked="true">Graduated</el-radio>
            <el-radio v-model="stuStatus" label="Normal" checked="true">Normal</el-radio>
          </el-form-item>
          <el-form-item label="姓名">
            <el-input v-model="name" />
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input v-model="password" />
          </el-form-item>
          <el-form-item label="身份证号">
            <el-input v-model="idNum" />
          </el-form-item>
          <el-form-item label="电话">
            <el-input v-model="phoneNum" />
          </el-form-item>
          <el-form-item label="邮箱">
            <el-input v-model="email" />
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
import Papa from "papaparse";
// const validatorPassword = (rule, value, callback) => {
//   const reg = /^((?=.*\d)(?=.*[a-zA-Z])|(?=.*\d)(?=.*[-_])|(?=.*[a-zA-Z])(?=.*[-_]))[a-zA-Z0-9-_]{6,32}$/
//   if (!value) {
//     return callback(new Error("请输入密码"));
//   } else {
//     if (reg.test(value)) {
//       callback();
//     } else {
//       return callback(new Error('密码格式不正确'))
//     }
//   }
//   console.log(value)
// };

export default {

  name: "CheckStudentInfo",
  data(){
    return{

      fileTemp: null,
      fileListUpload: [],

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
      edit_school_major: [],
      add_school_major: [],
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
      tableData:[],
      add_rules: {
        role: [{required: true, message: '请选择身份', trigger: 'change'}],
        //school_major: [{required: true, message: '请选择院系/专业', trigger: 'blur'}],
        number: [{required: true, message: '请填写学号', trigger: 'blur'},
          {pattern: /^\d{6}$/, message: '学号格式错误'}],
        name: [{required: true, message: '请填写姓名', trigger: 'blur'},
          {pattern: /^[\u4e00-\u9fa5a-zA-Z]+$/, message: '姓名只能为中文或英文'}],
        idNum: [{required: true, message: '请填写身份证号', trigger: 'blur'},
          {
            pattern: /^[1-9]\d{5}(18|19|20)\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/,
            message: '身份证号格式错误'
          }],
        phoneNum: [{pattern: /^1\d{10}$/, message: '手机号码格式错误'}],
        email: [{pattern: /^[\u4e00-\u9fa5a-zA-Z0-9]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/, message: '电子邮箱格式错误'}],
      },
      edit_rules: {
        password: [{
          // required: true,
          pattern: /^((?=.*\d)(?=.*[a-zA-Z])|(?=.*\d)(?=.*[-_])|(?=.*[a-zA-Z])(?=.*[-_]))[a-zA-Z0-9-_]{6,32}$/,
          message: '密码格式错误'
        }]
      }
    }
  },
  mounted() {
    this.load()
    this.getOption()
  },
  methods:{

    handleChange(file, fileList) {
      this.fileTemp = file.raw
      if (this.fileTemp) {
        if (this.fileTemp.type==='text/csv') {
          this.importcsv(file.raw)
        } else {
          this.$message({
            type: 'warning',
            message: '附件格式错误，请删除后重新上传！'
          })
        }
      } else {
        this.$message({
          type: 'warning',
          message: '请上传附件！'
        })
      }
    },

    importcsv (obj) {
      // let _this = this//如果需要点击事件结束之后对DOM进行操作使用)_this.xx=xx进行操作
      Papa.parse(obj, {
        complete (results) {
          console.log(results)//这个是csv文件的数据
          let data = []
          //遍历csv文件中的数据，存放到data中 方法不唯一，可自己更改
          for (let i = 0; i < results.data.length-1; i++) {
            let obj = {}
            obj.number = results.data[i][0]
            obj.name = results.data[i][1]
            obj.idNum = results.data[i][2]
            obj.phoneNum = results.data[i][3]
            obj.email = results.data[i][4]
            obj.role = results.data[i][5]
            obj.school = results.data[i][6]
            obj.major = results.data[i][7]
            data.push(obj)
          }
          data.splice(0, 1)//将数组第一位的表格名称去除
          let num = 0
          console.log('data', data)
          // _this.tableData = data//将数据放入要展示的表格中
        }
      })
    },

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
      this.addStudent.school = this.add_school_major[0]
      this.addStudent.major = this.add_school_major[1]
      this.$refs["add"].validate((valid) => {
        if (valid) {
          request.post("/admin/register", this.addStudent).then(res => {
            console.log(res)
            this.load() // 刷新表格的数据
            this.dialogVisible = false  // 关闭弹窗
          })
        }
        else {
          this.$message({
            type: "error",
            message: "请按格式填写"
          })
        }
      })
    },
    saveEdit(){
      request.post("/admin/updateStudentInfo", {
        stuNumber:this.stuNumber,
        name:this.name,
        password:this.password,
        idNum:this.idNum,
        phoneNum: this.phoneNum,
        email:this.email,
        stuStatus:this.stuStatus,
        major:this.edit_school_major[1],
        school:this.edit_school_major[0]
      }).then(res=>{
        console.log(res)
        this.load()
        this.dialogVisible2=false
      })
    },
    handleEdit(number,name,idNum,phoneNum,email,stuStatus,school,major,password){
      this.stuNumber=number
      this.name = name
      this.idNum = idNum
      this.phoneNum = phoneNum
      this.email = email
      this.stuStatus = stuStatus
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
.checkstudent{
  display: flex;
}
.add{
  margin-left: auto;
  text-align: left;
}

</style>