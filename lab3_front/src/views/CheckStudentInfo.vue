<template>
  <div class="checkinfo">
    <div>
      <div class="add" style="margin: 10px 0">
        <el-button size="large" @click="input" type="primary">新增</el-button>
        <el-input clearable v-model="search" placeholder="请输入关键字" style="width:50%;margin-left: 100px"></el-input>
        <el-button type="primary" style="margin-left: 5px" @click="load">搜索</el-button>
      </div>

      <el-table :data="tableData" style="width: 100%" border stripe v-loading="loading">
        <el-table-column fixed prop="number" label="学号" width="150" sortable/>
        <el-table-column prop="name" label="姓名" width="120" />
        <el-table-column prop="phone_num" label="电话" width="120" />
        <el-table-column prop="id_num" label="身份证号" width="120" />
        <el-table-column prop="email" label="邮箱" width="120" />
        <el-table-column prop="password" label="密码" width="120" />
        <el-table-column prop="role" label="身份" width="120" />
        <el-table-column prop="school" label="院系" width="120" />
        <el-table-column prop="major" label="专业" width="120" />
        <el-table-column fixed="right" label="操作" width="120">
          <template #default="scope">
            <el-button type="text" size="small" @click="handleEdit(scope.row)">编辑</el-button>
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
  </div>
  <div>
    <el-dialog v-model="dialogVisible" title="添加新用户" width="30%">
      <el-form :model="form" label-width="120px">
        <el-form-item label="新学号">
          <el-input v-model="form.new_number" />
        </el-form-item>
        <el-form-item label="新姓名">
          <el-input v-model="form.new_name" />
        </el-form-item>
        <el-form-item label="新电话">
          <el-input v-model="form.new_phonenum" />
        </el-form-item>
        <el-form-item label="新身份证号">
          <el-input v-model="form.new_idnum" />
        </el-form-item>
        <el-form-item label="新邮箱">
          <el-input v-model="form.new_email" />
        </el-form-item>
        <el-form-item label="新密码">
          <el-input v-model="form.new_password" />
        </el-form-item>
        <el-form-item label="新身份">
          <el-radio v-model="form.new_role" label="学生">学生</el-radio>
          <el-radio v-model="form.new_role" label="老师">老师</el-radio>
        </el-form-item>
        <el-form-item label="新院系">
          <el-input v-model="form.new_school" />
        </el-form-item>
        <el-form-item label="新专业">
          <el-input v-model="form.new_major"/>
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
</template>

<script>
import request from "@/utils/request";

export default {
  name: "CheckStudentInfo",
  data(){
    return{
      total:0,
      pageSize:10,
      currentPage:1,
      search:'',
      dialogVisible:false,
      loading:true,
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
      form:{},
      tableData:[]
    }
  },
  created() {
    this.load()
  },
  methods:{
    load(){
      this.loading=true
      request.get("/api/checkinfo",{
        params:{
          pageNum:this.currentPage,
          pageSize:this.pageSize, //每页的条目数
          search:this.search
        }
      }).then(res=>{
        console.log(res)
        this.loading=false
        this.tableData=res.data.records
        this.total=res.data.total
      })
    },
    input:function (){
      this.dialogVisible=true
      this.form={}
    },
    save:function (){
      if (this.form.number) {  // 更新
        request.put("/user/checkinfo", this.form).then(res => {
          console.log(res)
          this.load() // 刷新表格的数据
          this.dialogVisible = false  // 关闭弹窗
        })
      }  else {  // 新增
        request.post("/user/checkinfo", this.form).then(res => {
          console.log(res)
          this.load() // 刷新表格的数据
          this.dialogVisible = false  // 关闭弹窗
        })
      }
    },
    handleEdit(){
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogVisible = true
      this.$nextTick(() => {
        if (this.$refs['upload']) {
          this.$refs['upload'].clearFiles()  // 清除历史文件列表
        }
      })
    },
    handleDelete(number) {
      console.log(number)
      request.delete("/user/checkinfo/" + number).then(res => {
        this.load()  // 删除之后重新加载表格的数据
      })
    },
    handleSizeChange:function (){
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange:function (){
      this.currentPage = pageNum
      this.load()
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