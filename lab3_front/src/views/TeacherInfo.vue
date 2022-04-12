<template>
  <div>
    <div id="personalinfo" class="personalinfo">
      <div class="display">
        <el-table :data="tableData" border :show-header="status" style="width: 100%">
          <el-table-column prop="key"  width="180" />
          <el-table-column prop="value"  width="180" />
        </el-table>
        <div>学工号为:{{info.number}}</div>
        <div>姓名为:{{info.name}}</div>
        <div>身份为:{{info.role}}</div>
        <div>身份证号为:{{info.idNum}}</div>
        <div>电话号为:{{info.phoneNum}}</div>
        <div>邮箱为:{{info.email}}</div>
        <div>院系为:{{info.school}}</div>
        <div>专业为:{{info.major}}</div>
        <el-button size="small" @click="input_newinfo">
          编辑
        </el-button>
      </div>
    </div>
    <el-dialog v-model="dialogVisible" title="Tips" width="30%">
      <el-form :model="new_info" label-width="120px">
        <el-form-item label="邮箱">
          <el-input v-model="new_info.email" />
        </el-form-item>
        <el-form-item label="电话">
          <el-input v-model="new_info.phoneNum" />
        </el-form-item>
      </el-form>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="send_newinfo">确认</el-button>
      </span>
      <template #footer>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "TeacherInfo",
  data(){
    return{
      dialogVisible:false,
      info:{
        number:'',
        name:'',
        role:'',
        idNum:'',
        phoneNum:'',
        email:'',
        school:'',
        major:''
      },
      new_info:{
        number:'this.info.number',
        email:'this.info.email',
        phoneNum:'this.info.phoneNum'
      },
      status: false,
      tableData : [
        {
          key: '姓名',
          value: ''
        },
        {
          key: '工号',
          value: ''
        },
        {
          key: '身份',
          value: ''
        },
        {
          key: '院系',
          value: ''
        },
        {
          key: '专业',
          value: ''
        },
        {
          key: '身份证号',
          value: ''
        },
        {
          key: '电话',
          value: ''
        },
        {
          key: '邮箱',
          value: ''
        }
      ]
    }
  },
  methods:{
    send_newinfo:function (){
      this.dialogVisible=false
      request.post("/teacher/maintaininfo",this.new_info).then(res=>{
        console.log(res)
      })
    },
    input_newinfo:function (){
      this.dialogVisible=true
      this.new_info={}
    },
    get_info:function (){
      let user = JSON.parse(sessionStorage.getItem("user"))
      request.post("teacher/information", user.number).then(res=>{
        console.log(res)
        this.info = res.data.data
        this.tableData[0].value = this.info.name
        this.tableData[1].value = this.info.idNum
        this.tableData[2].value = this.info.role
        this.tableData[3].value = this.info.school
        this.tableData[4].value = this.info.major
        this.tableData[5].value = this.info.idNum
        this.tableData[6].value = this.info.phoneNum
        this.tableData[7].value = this.info.email
        // this.info.number=res.data.data.number
        // this.info.name=res.data.data.name
        // this.info.role=res.data.data.role
        // this.info.idNum=res.data.data.idNum
        // this.info.phoneNum=res.data.data.phoneNum
        // this.info.email=res.data.data.email
        // this.info.school=res.data.data.school
        // this.info.major=res.data.data.major
      })
    }
  },
  mounted(){
    this.get_info()
  }
}
</script>

<style scoped>
.personalinfo{
  margin-left: 400px;
  margin-right: 400px;
}
.display{
  margin-top: 300px;
}
</style>