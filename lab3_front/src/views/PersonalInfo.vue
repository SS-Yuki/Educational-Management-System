<template>
  <div>
    <div id="personalinfo" class="personalinfo">
      <div>
        <div>邮箱为</div>
        <div>电话为</div>
        <div>密码为</div>
        <el-button size="small" @click="input_newinfo">
          编辑
        </el-button>
      </div>
    </div>
    <el-dialog v-model="dialogVisible" title="Tips" width="30%">
      <el-form :model="new_info" label-width="120px">
        <el-form-item label="新邮箱">
          <el-input v-model="new_info.new_email" />
        </el-form-item>
        <el-form-item label="新电话">
          <el-input v-model="new_info.new_telephone" />
        </el-form-item>
        <el-form-item label="新电话">
          <el-input v-model="new_info.new_telephone" />
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
      new_info:{
        new_email:'',
        new_telephone:'',
        new_password:''
      }
    }
  },
  methods:{
    send_newinfo:function (){
      this.dialogVisible=false
      request.post("/user/personalinfo",this.new_info).then(res=>{
        console.log(res)
      })
    },
    input_newinfo:function (){
      this.dialogVisible=true
      this.new_info={}
    }
  }
}
</script>

<style scoped>
.personalinfo{
  margin-left: 400px;
  margin-right: 400px;
}
</style>