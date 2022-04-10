<template>
  <div>
    <div id="personalinfo" class="personalinfo">
      <div>
        你的邮箱为：
        <el-button size="small" @click="input_newemail">
          编辑
        </el-button>
      </div>
      <div>
        你的电话为：
        <el-button size="small" @click="input_newtelephone">
          编辑
        </el-button>
      </div>
      <div>
        你的密码为：
        <el-button size="small" @click="input_newpassword">
          编辑
        </el-button>
      </div>
    </div>
    <el-dialog v-model="email_dialogVisible" title="Tips" width="30%">
      <el-form :model="new_email" label-width="120px">
        <el-form-item label="新邮箱">
          <el-input v-model="new_email" />
        </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="email_dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="send_newemail">确认</el-button>
      </span>
      </template>
    </el-dialog>
    <el-dialog v-model="telephone_dialogVisible" title="Tips" width="30%">
      <el-form :model="new_telephone" label-width="120px">
        <el-form-item label="新电话">
          <el-input v-model="new_telephone" />
        </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="telephone_dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="send_newtelephone">确认</el-button>
      </span>
      </template>
    </el-dialog>
    <el-dialog v-model="password_dialogVisible" title="Tips" width="30%">
      <el-form :model="new_password" label-width="120px">
        <el-form-item label="新密码">
          <el-input v-model="new_password" />
        </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="password_dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="send_newpassword">确认</el-button>
      </span>
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
      email_dialogVisible:false,
      telephone_dialogVisible:false,
      password_dialogVisible:false,
      new_email:'',
      new_telephone:'',
      new_password:''
    }
  },
  methods:{
    send_newemail:function (){
      request.post("/user/personalinfo",this.new_email).then(res=>{
        console.log(res)
      })
    },
    send_newtelephone:function (){
      request.post("/user/personalinfo",this.new_email).then(res=>{
        console.log(res)
      })
    },
    send_newpassword:function (){
      request.post("/user/personalinfo",this.new_email).then(res=>{
        console.log(res)
      })
    },
    input_newemail:function (){
      this.email_dialogVisible=true
      this.new_email=''
    },
    input_newtelephone:function (){
      this.telephone_dialogVisible=true
      this.new_telephone=''
    },
    input_newpassword:function (){
      this.password_dialogVisible=true
      this.new_password=''
    },
  }
}
</script>

<style scoped>
.personalinfo{
  margin-left: 400px;
  margin-right: 400px;
}
</style>