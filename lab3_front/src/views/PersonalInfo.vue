<template>
  <div>
    <div id="personalinfo" class="personalinfo">
      <div class="display">
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
        <el-form-item label="新邮箱">
          <el-input v-model="new_info.email" />
        </el-form-item>
        <el-form-item label="新电话">
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
  name: "PersonalInfo",
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
        email:'',
        phoneNum:''
      }
    }
  },
  methods:{
    send_newinfo:function (){
      this.dialogVisible=false
      request.post("/student/maintaininfo",this.new_info).then(res=>{
        console.log(res)
      })
    },
    input_newinfo:function (){
      this.dialogVisible=true
      this.new_info={}
    },
    get_info:function (){
      request.post("student/information").then(res=>{
        console.log(res)
        this.info.number=res.number
        this.info.name=res.name
        this.info.role=res.role
        this.info.idNum=res.idNum
        this.info.phoneNum=res.phoneNum
        this.info.email=res.email
        this.info.school=res.school
        this.info.major=res.major
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