<template>
  <div class="main_part">
    <div class="add" style="margin: 10px 0">
      <div style="float: left">
        <el-input clearable v-model="search" placeholder="请输入关键字" style="width:50%;margin-left: 100px"></el-input>
        <el-button type="primary" style="margin-left: 5px" @click="load">搜索</el-button>
      </div>
      <div>
        <el-cascader v-model="select_building_classroom" :options="classroomOptions" placeholder="按教学楼/教室筛选" clearable/>
        <el-button  @click="getTime(); timeShow = true">按上课时间筛选</el-button>
      </div>
      <el-dialog v-model="timeShow" title="进行时间选择" :show-close="false" center>
        <div>
          <el-checkbox-group  style="display: inline-block" disabled >
            <el-checkbox-button v-for="item of length" :key="item" :label=item+1 style="display: block"
                                value=item>
              {{ startTimes[item-1].label + "-" + endTimes[item-1].label }}
            </el-checkbox-button>
          </el-checkbox-group>

          <el-checkbox-group v-model="day1" style="display: inline-block" >
            <el-checkbox-button v-for="item of length" :key="item" :label=item
                                style="display: block"
                                value=item>
              {{ timeNames[item-1].label}}
            </el-checkbox-button>
          </el-checkbox-group>

          <el-checkbox-group v-model="day2" style="display: inline-block" >
            <el-checkbox-button v-for="item of length" :key="item" :label=item
                                style="display: block"
                                value=item>
              {{ timeNames[item-1].label }}
            </el-checkbox-button>
          </el-checkbox-group>

          <el-checkbox-group v-model="day3" style="display: inline-block" >
            <el-checkbox-button v-for="item of length" :key="item" :label=item
                                style="display: block"
                                value=item>
              {{ timeNames[item-1].label }}
            </el-checkbox-button>
          </el-checkbox-group>

          <el-checkbox-group v-model="day4" style="display: inline-block" >
            <el-checkbox-button v-for="item of length" :key="item" :label=item
                                style="display: block"
                                value=item>
              {{ timeNames[item-1].label }}
            </el-checkbox-button>
          </el-checkbox-group>

          <el-checkbox-group v-model="day5" style="display: inline-block" >
            <el-checkbox-button v-for="item of length" :key="item" :label=item
                                style="display: block"
                                value=item >
              {{ timeNames[item-1].label }}
            </el-checkbox-button>
          </el-checkbox-group>

          <el-checkbox-group v-model="day6" style="display: inline-block">
            <el-checkbox-button v-for="item of length" :key="item" :label=item
                                style="display: block"
                                value=item >
              {{ timeNames[item-1].label }}
            </el-checkbox-button>
          </el-checkbox-group>

          <el-checkbox-group v-model="day7" style="display: inline-block" >
            <el-checkbox-button v-for="item of length" :key="item" :label=item
                                style="display: block"
                                value=item >
              {{ timeNames[item-1].label }}
            </el-checkbox-button>
          </el-checkbox-group>
        </div>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="cancelTime">取消</el-button>
            <el-button type="primary" @click="ensureTime">确认</el-button>
          </span>
        </template>
      </el-dialog>
    </div>
    <el-table :data="tableData" style="width: 1200px" border stripe>
      <el-table-column prop="courseId" label="courseId" width="150"  />
      <el-table-column prop="courseName" label="课程名" width="200" />
      <el-table-column prop="courseNumber" label="课程编号" width="200" />
<!--        <el-table-column prop="teacherNum" label="教师工号" width="150" v-if="false"/>-->
      <el-table-column prop="major" label="开课专业" width="200" />
      <el-table-column prop="school" label="开课院系" width="200" />
      <el-table-column prop="occupyTime" label="时间" width="200"   />
      <el-table-column prop="classroom" label="教室" width="200"  />
      <el-table-column prop="creditHours" label="学时" width="100"  />
      <el-table-column prop="credits" label="学分" width="100"  />
      <el-table-column prop="introduction" label="介绍" width="100"  />
      <el-table-column prop="applicant" label="申请人" width="0" v-if="false" />
      <el-table-column fixed="right"  prop="selectNumber" label="选课人数" width="100" />
        <el-table-column fixed="right" prop="capacity" label="容量" width="100"/>
      <el-table-column fixed="right" label="操作" width="70">
        <template #default="scope">
          <el-popconfirm title="确认选课?" @confirm="this.chooseCourse(scope.row.courseId)">
            <template #reference>
              <el-button type="text" >选课</el-button>
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

</template>

<script>
import request from "@/utils/request";

export default {
  name: "StudentChooseCourse",
  data(){
    return{
      select_year_semester:[],
      select_building_classroom:[],
      semesterOptions:[],
      classroomOptions:[],
      total:0,
      pageSize:10,
      currentPage:1,
      search:'',
      tableData:[],
      timeShow: false,
      length: 0,
      day1:[],
      day2:[],
      day3:[],
      day4:[],
      day5:[],
      day6:[],
      day7:[],
      timeNames:[],
      startTimes:[],
      endTimes:[]
    }
  },
  mounted() {
    this.load()
    this.getOptionClassroom()
    this.getOptionSemesters()
  },
  computed: {
    selectJudge() {
      return {
        year: this.select_year_semester[0],
        semester: this.select_year_semester[1],
        classroom: this.select_building_classroom[1]
      }
    },
    day: {
      get() {
        return [this.day1,this.day2,this.day3,this.day4,this.day5,this.day6,this.day7]
      },
      set(newValue) {
        [this.day1,this.day2,this.day3,this.day4,this.day5,this.day6,this.day7] = newValue
      }
    }
  },
  watch: {
    selectJudge: {
      deep: true,
      handler() {
        this.load()
      }
    }
  },
  methods:{
    load(){
      setTimeout(() => {
        request.post("/student/findSelectCoursePage", {
              pageNum: this.currentPage,
              pageSize: this.pageSize,
              search: this.search,
              classroom:this.select_building_classroom[1],
              selectTime:this.day
            }
        ).then(res => {
          if (res.data.code === 200) {
            this.tableData = res.data.data.records
            this.total = res.data.data.total
          } else {
            this.$message({
              type: "error",
              message: res.data.msg
            })
          }
        })
      }, 500)
    },
    getOptionClassroom: function () {
      request.post("/common/allClassrooms").then(res => {
        let that = this
        if (!res.data) return
        res.data.data.buildings.forEach (function (item) {
          let option = {value: item.building, label: item.building, children: []}
          if (!item.classrooms) return
          item.classrooms.forEach (function (item) {
            let child = {value: item, label: item}
            option.children.push(child)
          })
          that.classroomOptions.push(option)
        })
      })
    },
    getOptionSemesters: function (){
      request.post("/common/allSemesters").then(res => {
        let that = this
        if (!res.data) return
        res.data.data.yearAndSemesters.forEach (function (item) {
          let option = {value: item.year, label: item.year, children: []}
          if (!item.semesters) return
          item.semesters.forEach (function (item) {
            let child = {value: item, label: item}
            option.children.push(child)
          })
          that.semesterOptions.push(option)
        })
        this.select_year_semester = [res.data.data.defaultYear, res.data.data.defaultSemester]
      })
    },
    getTime:function (){
      request.post("/common/allTime").then(res=>{
        if (!res.data) return
        this.timeNames.splice(0)
        this.startTimes.splice(0)
        this.endTimes.splice(0)
        res.data.data.times.forEach ((item) => {
          let option1 = {value: item.timeName, label: item.timeName}
          this.timeNames.push(option1)
          let option2 = {value: item.startTime, label: item.startTime}
          this.startTimes.push(option2)
          let option3 = {value: item.endTime, label: item.endTime}
          this.endTimes.push(option3)
        })
        this.length=this.timeNames.length
        console.log(this.timeNames, this.startTimes, this.endTimes)
      })
    },
    cancelTime:function () {
      this.timeShow = false
      if(sessionStorage.getItem('day') !== null) {
        this.day = JSON.parse(sessionStorage.getItem('day'))
      }
      else {
        this.day.forEach(item => {item.splice(0)})
      }
      console.log(this.day)
    },
    ensureTime: function () {
      this.timeShow = false
      sessionStorage.setItem('day', JSON.stringify(this.day))
      this.load()
    },
    handleCurrentChange:function (pageNum){
      this.currentPage = pageNum
      this.load()
    },
    chooseCourse:function (courseId){
      request.post("/student/selectCourse",courseId).then(res => {
        if(res.data.code===200) {
          this.$message({
            type:"success",
            message: res.data.msg
          })
        }
        if(res.data.code!==200) {
          this.$message({
            type:"error",
            message: res.data.msg
          })
        }
        this.load()  // 删除之后重新加载表格的数据
      })
    }

  }
}
</script>

<style scoped>
.add{
  text-align: left;
}
</style>