<template>
  <div class="main_part">
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
        <el-table :data="tableData" style="width: 1210px" border stripe>
          <el-table-column fixed prop="number" label="学号" width="110" sortable/>
          <el-table-column prop="name" label="姓名" width="110" />
          <el-table-column prop="idNum" label="身份证号" width="110" />
          <el-table-column prop="phoneNum" label="电话" width="110" />
          <el-table-column prop="email" label="邮箱" width="110" />
          <el-table-column prop="stuStatus" label="状态" width="110" />
          <el-table-column prop="school" label="院系" width="110" />
          <el-table-column prop="major" label="专业" width="110" />
          <el-table-column prop="grade" label="年级" width="110" />
          <el-table-column prop="registerTime" label="注册时间" width="110" />
          <el-table-column prop="password" label="密码" width="110" v-if="false"/>
          <el-table-column fixed="right" label="操作" width="110">
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
                scope.row.grade,
                scope.row.password
                )">编辑</el-button>
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
      <el-dialog v-model="dialogVisible2" title="编辑学生信息" width="30%" >
        <el-form :model="newSchool" label-width="120px">
          <el-form-item label="院系/专业">
            <el-cascader  v-if="flag" v-model="edit_school_major" :options="options"/>
          </el-form-item>
          <el-form-item label="年级">
            <el-select v-model="grade" class="m-2" placeholder="Select" size="small">
              <el-option
                  v-for="item in grades"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="状态">
            <el-radio v-model="stuStatus" label="Graduated" checked="true">Graduated</el-radio>
            <el-radio v-model="stuStatus" label="Normal" checked="true">Normal</el-radio>
          </el-form-item>
          <el-form-item label="姓名">
            <el-input v-model="name" />
          </el-form-item>
          <el-form-item label="密码">
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
import Papa from "papaparse";
export default {

  name: "CheckStudentInfo",
  data(){
    return{
      grades:[
        {
          value: '2019',
          label: '2019',
        },
        {
          value: '2020',
          label: '2020',
        },
        {
          value: '2021',
          label: '2021',
        },
        {
          value: '2022',
          label: '2022',
        }
      ],
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
      flag:false,
      password:'',
      idNum:'',
      phoneNum:'',
      email:'',
      stuStatus:'',
      major:'',
      school:'',
      grade:'',
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
      // edit_rules: {
      //   password: [{required: true, message: '请填写新密码', trigger: 'blur'}, {
      //     pattern: /^((?=.*\d)(?=.*[a-zA-Z])|(?=.*\d)(?=.*[-_])|(?=.*[a-zA-Z])(?=.*[-_]))[a-zA-Z0-9-_]{6,32}$/,
      //     message: '密码格式错误'
      //   }]
      // }
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
      let that = this//如果需要点击事件结束之后对DOM进行操作使用)_this.xx=xx进行操作
      Papa.parse(obj, {
        complete (results) {
          
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
          
          // _this.tableData = data//将数据放入要展示的表格中
          request.post("/admin/csvRegister", data).then(res => {
            that.load()
            that.$router.go(0)
            if(res.data.code!==200) {
              this.$message({
                type:"error",
                message: res.data.msg
              })
            }
          })
        }
      })

    },

    getOption: function () {
      request.post("/common/allMajors").then(res => {
        
        let that = this
        if (!res.data) return
        res.data.data.schools.forEach (function (item) {
          
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
      setTimeout(() => {
        request.post("/admin/findStudentPage",{
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
      this.addStudent={}
    },
    save:function (){
      this.addStudent.school = this.add_school_major[0]
      this.addStudent.major = this.add_school_major[1]
      this.$refs["add"].validate((valid) => {
        if (valid) {
          request.post("/admin/register", this.addStudent).then(res => {
            if(res.data.code!==200) {
              this.$message({
                type:"error",
                message: res.data.msg
              })
            }
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
        school:this.edit_school_major[0],
        grade:this.grade

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
    handleEdit(number,name,idNum,phoneNum,email,stuStatus,school,major,grade,password){
      this.stuNumber=number
      this.name = name
      this.idNum = idNum
      this.phoneNum = phoneNum
      this.email = email
      this.stuStatus = stuStatus
      this.school = school
      this.major = major
      this.password = password
      this.grade = grade
      this.edit_school_major = [school, major]
      this.dialogVisible2 = true
      this.flag=true
      
    },
    handleDelete(number) {
      request.post("/admin/delete",number).then(res => {
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
  margin-left: auto;
  text-align: left;
}

</style>