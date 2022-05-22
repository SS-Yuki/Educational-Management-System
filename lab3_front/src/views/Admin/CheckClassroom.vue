<template>
  <div class="main_part">
    <div>
      <div class="add" style="margin-right: auto;margin-left: auto">
        <div style="float: left;margin-left: 20px;margin-bottom: 15px">
          <el-button size="large" @click="add" type="primary">新增</el-button>
        </div>
        <div style="float: left;margin-right: auto;margin-left: auto">
          <el-input clearable v-model="search" placeholder="请输入关键字" style="width:400px;margin-left: 100px"></el-input>
          <el-button type="primary" style="margin-left: 5px" @click="load">搜索</el-button>
        </div>
      </div>
      <el-table :data="tableData" style="width:1200px;margin-left: auto;margin-right: auto" border stripe>
        <el-table-column prop="classroomName" label="教室" width="300" />
        <el-table-column prop="buildingName" label="教学楼" width="300" />
        <el-table-column prop="capacity" label="教室容量" width="300" />
        <el-table-column fixed="right" label="操作" width="300">
          <template #default="scope">
            <el-button type="text" size="small" @click="handleEdit(scope.row.classroomName, scope.row.teachingBuildingName, scope.row.capacity)">
              编辑
            </el-button>
            <el-popconfirm title="确认删除?" @confirm="handleDelete(scope.row.buildingName,scope.row.classroomName,scope.row.capacity)">
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
            layout="total, sizes, prev, pager, next, jumper"
            :total="total"
            @current-change="handleCurrentChange"
        />
      </div>
    </div>
    <div>
      <el-dialog v-model="dialogVisible" title="新教室信息" width="30%">
        <el-form :model="addClassroom" label-width="120px">
          <el-form-item label="所属教学楼" prop="school_major">
            <el-select v-model="addClassroom.teachingBuildingName" class="m-2" placeholder="请选择" size="small">
              <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="新教室名称">
            <el-input v-model="addClassroom.classroomName" />
          </el-form-item>
          <el-form-item label="新教室容量">
            <el-input v-model="addClassroom.capacity" />
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
      <el-dialog v-model="dialogVisible2" title="编辑教室信息" width="30%">
        <el-form :model="editClassroom" label-width="120px">
          <el-form-item label="教室名称">
            <el-input v-model="editClassroom.newClassroomName" />
          </el-form-item>
          <el-form-item label="教室容量">
            <el-input v-model="editClassroom.capacity" />
          </el-form-item>
          <span class="dialog-footer">
            <el-button @click="dialogVisible2 = false">取消</el-button>
            <el-button type="primary" @click="saveEdit">确认</el-button>
          </span>
        </el-form>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "CheckClassroom",
  data(){
    return{
      options:[],
      total:0,
      pageSize:10,
      currentPage:1,
      search:'',
      dialogVisible:false,
      dialogVisible2:false,
      addClassroom:{
        classroomName:'',
        teachingBuildingName:'',
        capacity:0
      },
      editClassroom:{
        oldClassroomName: '',
        newClassroomName: '',
        TeachingBuilding: '',
        capacity: 0
        // classroomName:'',
        // teachingBuildingName:'',
        // capacity:0
      },
      tableData:[]
    }
  },
  mounted() {
    this.load()
    this.getOption()
  },
  methods:{
    getOption: function () {
      request.post("/admin/allBuildings").then(res => {
        let that = this
        if (!res.data) return
        res.data.data.buildings.forEach (function (item) {
          
          let option = {value: item, label: item}
          that.options.push(option)
        })
      })
    },
    load(){
      setTimeout(() => {
        request.post("/admin/findClassroomPage",{
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
      this.teachingBuildingName=''
    },
    save:function (){
      request.post("/admin/addClassroom", this.addClassroom).then(res => {
        if(res.data.code!==200) {
          this.$message({
            type:"error",
            message: res.data.msg
          })
        }
        this.load() // 刷新表格的数据
        this.dialogVisible = false  // 关闭弹窗
      })
    },
    saveEdit(){
      request.post("/admin/updateClassroomInfo",this.editClassroom).then(res=>{
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
    handleEdit(classroomName, teachingBuildingName, capacity){
      this.dialogVisible2=true
      this.editClassroom.oldClassroomName=classroomName
      this.editClassroom.newClassroomName=classroomName
      this.editClassroom.TeachingBuilding=teachingBuildingName
      this.editClassroom.capacity=capacity
    },
    handleDelete(buildingName,classroomName,capacity) {
      request.post("/admin/deleteClassroom", {
        buildingName:buildingName,
        classroomName:classroomName,
        capacity:capacity
      }).then(res => {
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
  text-align: left;
}
</style>