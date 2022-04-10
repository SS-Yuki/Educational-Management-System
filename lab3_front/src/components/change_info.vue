<template>
  <div>
    <div id="change_info" class="change_info">
      <div>
        <input type="text" v-model="new_info.new_email">
        <el-button size="small" @click="handleEdit(scope.$index, scope.row)">
          编辑
        </el-button>
      </div>
      <div>
        <input type="text" v-model="new_info.new_telephone">
        <el-button size="small" @click="handleEdit(scope.$index, scope.row)">
          编辑
        </el-button>
      </div>
      <div>
        <input type="text" v-model="new_info.new_password">
        <el-button size="small" @click="handleEdit(scope.$index, scope.row)">
          编辑
        </el-button>
      </div>
      <div>
        <el-button type="primary" round @click="new_info">提交</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "change_info",
  data(){
    return{
      new_info:{
        new_email:'',
        new_telephone:'',
        new_password:''
      }
    }
  },
  methods:{
    new_info:function (){
      request.post("/user/changeinfo",this.new_info).then(res=>{
        console.log(res)
      })
    }
  }
}
</script>

<style scoped>
.change_info{
  margin-left: 400px;
  margin-right: 400px;
}
</style>