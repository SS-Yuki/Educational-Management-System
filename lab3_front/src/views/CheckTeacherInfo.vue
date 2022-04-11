<template>
  <div class="checkinfo">
    <div>
      <div class="add" style="margin: 10px 0">
        <el-button size="large" @click="input" type="primary">新增</el-button>
        <el-input v-model="search" placeholder="请输入关键字" style="width:50%;margin-left: 100px"></el-input>
        <el-button type="primary" style="margin-left: 5px">搜索</el-button>
      </div>

      <el-table :data="tableData" style="width: 100%" border stripe>
        <el-table-column fixed prop="number" label="工号" width="150" sortable/>
        <el-table-column prop="name" label="姓名" width="120" />
        <el-table-column prop="phone_num" label="电话" width="120" />
        <el-table-column prop="id_num" label="身份证号" width="120" />
        <el-table-column prop="email" label="邮箱" width="120" />
        <el-table-column prop="password" label="密码" width="120" />
        <el-table-column prop="role" label="身份" width="120" />
        <el-table-column prop="school" label="院系" width="120" />
        <el-table-column prop="major" label="专业" width="120" />
        <el-table-column fixed="right" label="操作" width="120">
          <template #default>
            <el-button type="text" size="small" @click="handleClick">编辑</el-button>
            <el-popconfirm title="确认删除?">
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
  </div>
  <div>
    <el-dialog v-model="dialogVisible" title="添加新用户" width="30%">
      <el-form :model="new_people" label-width="120px">
        <el-form-item label="新工号">
          <el-input v-model="new_people.new_number" />
        </el-form-item>
        <el-form-item label="新姓名">
          <el-input v-model="new_people.new_name" />
        </el-form-item>
        <el-form-item label="新电话">
          <el-input v-model="new_people.new_phonenum" />
        </el-form-item>
        <el-form-item label="新身份证号">
          <el-input v-model="new_people.new_idnum" />
        </el-form-item>
        <el-form-item label="新邮箱">
          <el-input v-model="new_people.new_email" />
        </el-form-item>
        <el-form-item label="新密码">
          <el-input v-model="new_people.new_password" />
        </el-form-item>
        <el-form-item label="新身份">
          <el-radio v-model="new_people.new_role" label="学生">学生</el-radio>
          <el-radio v-model="new_people.new_role" label="老师">老师</el-radio>
        </el-form-item>
        <el-form-item label="新院系">
          <el-input v-model="new_people.new_school" />
        </el-form-item>
        <el-form-item label="新专业">
          <el-input v-model="new_people.new_major"/>
        </el-form-item>
        <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="send_newpeople">确认</el-button>
      </span>
      </el-form>
      <template #footer>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "CheckTeacherInfo",
  data(){
    return{
      total:0,
      pageSize:10,
      currentPage:1,
      search:'',
      dialogVisible:false,
      new_people:{
        new_number:'',
        new_name:'',
        new_phonenum:'',
        new_idnum:'',
        new_email:'',
        new_password:'',
        new_role:'',
        new_school:'',
        new_major:''
      },
      tableData:[]
    }
  },
  created() {
    this.load()
  },
  methods:{
    load(){
      request.get("/api/checkinfo",{
        pageNum:this.currentPage,
        pageSize:this.pageSize, //每页的条目数
        search:this.search
      }).then(res=>{
        console.log(res)
        this.tableData=res.data.records
        this.total=res.data.total
      })
    },
    input:function (){
      this.dialogVisible=true
      this.new_people={}
    },
    send_newpeople:function (){
      this.dialogVisible=false
      request.post("/user/checkinfo",this.new_people).then(res=>{
        console.log(res)
      })
    },
    handleClick:function (){

    },
    handleSizeChange:function (){

    },
    handleCurrentChange:function (){

    }
  }
}
const handleClick = () => {
  console.log('click')
}
</script>

<style scoped>
.checkinfo{
  margin-left: 100px;
  display: flex;
}
.add{
  text-align: left;
}
</style>