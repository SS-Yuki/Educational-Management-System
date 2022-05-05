<template>
  <div class="main_part">
    <el-form :model="editCourse" label-width="80px" size="default" class="form">
      <el-form-item label="课程id" v-if="false">
        <el-input v-model="editCourse.id"/>
      </el-form-item>
      <el-form-item label="课程编号">
        <el-input v-model="editCourse.courseNumber" disabled/>
      </el-form-item>
      <el-form-item label="课程名">
        <el-input v-model="editCourse.courseName"/>
      </el-form-item>
      <el-form-item label="教学楼/教室">
        <el-cascader v-model="edit_building_classroom" :options="classroomOptions"/>
      </el-form-item>
      <el-form-item label="上课时间">
        <div>
          <el-checkbox-group  style="display: inline-block" disabled size="small">
            <el-checkbox-button v-for="item in range" :key="item" :label=item+1 style="display: block"
                                value=item>
              {{ startTimes[item].label + "-" + endTimes[item].label }}
            </el-checkbox-button>
          </el-checkbox-group>

          <el-checkbox-group v-model="day1" style="display: inline-block" size="small">
            <el-checkbox-button v-for="item in range" :key="item" :label=item+1 style="display: block"
                                :disabled=spare[0][item]
                                value=item+1
            >
              {{ timeNames[item].label}}
            </el-checkbox-button>
          </el-checkbox-group>

          <el-checkbox-group v-model="day2" style="display: inline-block" size="small">
            <el-checkbox-button v-for="item in range" :key="item" :label=item+1 style="display: block"
                                :disabled=spare[1][item]
                                value=item+1>
              {{ timeNames[item].label }}
            </el-checkbox-button>
          </el-checkbox-group>

          <el-checkbox-group v-model="day3" style="display: inline-block" size="small">
            <el-checkbox-button v-for="item in range" :key="item" :label=item+1 style="display: block"
                                :disabled=spare[2][item]
                                value=item+1 >
              {{ timeNames[item].label }}
            </el-checkbox-button>
          </el-checkbox-group>

          <el-checkbox-group v-model="day4" style="display: inline-block" size="small">
            <el-checkbox-button v-for="item in range" :key="item" :label=item+1 style="display: block"
                                :disabled=spare[3][item]
                                value=item+1  >
              {{ timeNames[item].label }}
            </el-checkbox-button>
          </el-checkbox-group>

          <el-checkbox-group v-model="day5" style="display: inline-block" size="small">
            <el-checkbox-button v-for="item in range" :key="item" :label=item+1 style="display: block"
                                :disabled=spare[4][item]
                                value=item+1 >
              {{ timeNames[item].label }}
            </el-checkbox-button>
          </el-checkbox-group>

          <el-checkbox-group v-model="day6" style="display: inline-block" size="small">
            <el-checkbox-button v-for="item in range" :key="item" :label=item+1 style="display: block"
                                :disabled=spare[5][item]
                                value=item+1 >
              {{ timeNames[item].label }}
            </el-checkbox-button>
          </el-checkbox-group>

          <el-checkbox-group v-model="day7" style="display: inline-block" size="small">
            <el-checkbox-button v-for="item in range" :key="item" :label=item+1 style="display: block"
                                :disabled=spare[6][item]
                                value=item+1 >
              {{ timeNames[item].label }}
            </el-checkbox-button>
          </el-checkbox-group>
        </div>

      </el-form-item>
      <!--        time-->
      <el-form-item label="学时">
        <el-input type="number" v-model="editCourse.creditHours" />
      </el-form-item>
      <el-form-item label="学分">
        <el-input type="number" v-model="editCourse.credits" />
      </el-form-item>
      <el-form-item label="容量">
        <el-input type="number" v-model="editCourse.capacity" />
      </el-form-item>
      <el-form-item label="介绍">
        <el-input v-model="editCourse.introduction" />
      </el-form-item>
      <!--        申请人-->
      <el-form-item label="选课类型">
        <el-select v-model="editCourse.selectTypeString" disabled>
          <el-option v-for="item in typeOptions" :key="item.value" :label="item.label" :value="item.value"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="专业限制">
        <el-input v-model="limit_majors" disabled/>
      </el-form-item>
      <el-form-item label="学年/学期" disabled>
        <el-cascader v-model="edit_year_semester" :options="semesterOptions" disabled/>
      </el-form-item>
      <el-button type="primary" @click="save">确认</el-button>
    </el-form>
  </div>
</template>

<script>
import request from "@/utils/request";

function index(num){
  var arr=[];
  for(var i = 0;i<num;i++){
    arr.push(i);
  }
  return arr;
}

export default {
  name: "TeacherEditCourse",
  data() {
    return {
      editCourse:{
        id:0,
        courseNumber:'',
        courseName:'',
        classroom:'',
        creditHours:'',
        credits:'',
        capacity:'',
        introduction:'',
        selectTypeString:'',
        majorLimits:[],
        year: '',
        semester: '',
        occupyTime: '',
      },
      timeNames:[],
      startTimes:[],
      endTimes:[],
      spare:[],
      occupy:[],
      day1:[],
      day2:[],
      day3:[],
      day4:[],
      day5:[],
      day6:[],
      day7:[],
      length:0,
      range:[],

      edit_school_major:[],
      limit_school_major:[],
      limit_majors:'',
      edit_building_classroom:[],
      edit_year_semester:[],

      majorOptionsChoose:[],
      majorOptionsLimit:[],
      classroomOptions:[],
      semesterOptions:[],
      typeOptions:[
        {
          value: '通识课程',
          label: '通识课程',
        },
        {
          value: '专业限制课程',
          label: '专业限制课程',
        }
      ],
      // school_major_props:{multiple: true},
      // major_limit_show:"请选择专业"
      school_major_props:{},
      major_limit_show:"默认选择所有专业"

    }
  },
  props:["id"],
  mounted() {
    this.getTime()
    this.getOptionMajor()
    this.getOptionClassroom()
    this.getOptionSemesters()
    this.show()
  },
  computed: {
    day: {
      get() {
        return [this.day1,this.day2,this.day3,this.day4,this.day5,this.day6,this.day7]
      },
      set(newValue) {
        [this.day1,this.day2,this.day3,this.day4,this.day5,this.day6,this.day7] = newValue
      }
    },
    spare_occupyJudge() {
      return {
        courseId: this.editCourse.id,
        classroom: this.edit_building_classroom[1],
      }
    }
  },
  watch: {
    editCourse: {
      deep: true,
      handler(new_) {
        if (new_.selectTypeString === '通识课程') {
          this.majorOptionsLimit.forEach((item) => {
            item.disabled = true
          })
          this.school_major_props = {}
          this.major_limit_show = "默认选择所有专业"
          this.limit_school_major = []
        }
        else if (new_.selectTypeString === '专业限制课程') {
          this.majorOptionsLimit.forEach((item) => {
            item.disabled = false
          })
          this.school_major_props = {multiple: true}
          this.major_limit_show = "请选择专业"
        }
      }
    },
    spare_occupyJudge: {
      deep: true,
      handler(new_) {
        for (let key in new_) {
          if (new_[key] === null || new_[key] === undefined) {
            return;
          }
        }
        request.post("/common/getClassroomSpareTimeExceptOneCourse", new_).then(res => {
          this.spare = res.data.data.days
        })
        request.post("/common/getClassroomOccupyByOneCourse", new_).then(res => {
          this.occupy = res.data.data.occupys
          for (let i = 0; i < 7; i++) {
            this.day[i].splice(0)
            for (let j = 0; j < this.length; j++) {
              if (this.occupy[i][j]) {
                this.day[i].push(j+1)
              }
            }
          }
          console.log(this.day)

        })
      }
    }
  },
  methods: {
    show: function () {
      setTimeout(() => {
        request.post("/common/findOneCourseInfo",this.$route.params.id
        ).then(res => {
          if(res.data.code===200){
            let showData = res.data.data
            this.editCourse.id = showData.courseId
            this.editCourse.courseName = showData.courseName
            this.editCourse.courseNumber = showData.courseNumber
            this.editCourse.teacherNum = showData.teacherNum
            this.editCourse.major = showData.major
            this.editCourse.school = showData.school
            this.editCourse.classroom = showData.classroom
            this.editCourse.creditHours = showData.creditHours
            this.editCourse.credits = showData.credits
            this.editCourse.capacity = showData.capacity
            this.editCourse.introduction = showData.introduction
            this.editCourse.selectTypeString = showData.selectTypeString
            //申请人
            // this.editCourse.majorLimits = showData.majorLimits
            this.editCourse.year = showData.year
            this.editCourse.semester = showData.semester
            this.edit_school_major = [showData.school, showData.major]
            this.limit_majors = showData.majorLimits
            this.edit_building_classroom = [showData.building, showData.classroom]
            this.edit_year_semester = [showData.year, showData.semester]
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
    getChecked:function () {
      request.post("/common/getClassroomOccupyByOneCourse").then(res => {
        let that = this
        if (!res.data) return})

    },
    getOptionMajor: function () {
      request.post("/common/allMajors").then(res => {
        let that = this
        if (!res.data) return
        res.data.data.schools.forEach((item) => {
          let option = {value: item.school, label: item.school, disabled: false, children: []}
          if (!item.majors) return
          item.majors.forEach((item) => {
            let child = {value: item, label: item}
            option.children.push(child)
          })
          that.majorOptionsChoose.push(option)
        })
      })
      request.post("/common/allMajors").then(res => {
        let that = this
        if (!res.data) return
        res.data.data.schools.forEach((item) => {
          let option = {value: item.school, label: item.school, disabled: false, children: []}
          if (!item.majors) return
          item.majors.forEach((item) => {
            let child = {value: item, label: item}
            option.children.push(child)
          })
          that.majorOptionsLimit.push(option)
        })
      })
    },
    getOptionClassroom: function () {
      request.post("/common/allClassrooms").then(res => {
        let that = this
        if (!res.data) return
        res.data.data.buildings.forEach(function (item) {
          let option = {value: item.building, label: item.building, children: []}
          if (!item.classrooms) return
          item.classrooms.forEach(function (item) {
            let child = {value: item, label: item}
            option.children.push(child)
          })
          that.classroomOptions.push(option)
        })
      })
    },
    getTime:function (){
      request.post("/common/allTime").then(res=>{
        if (!res.data) return
        res.data.data.times.forEach ((item) => {
          let option1 = {value: item.timeName, label: item.timeName}
          this.timeNames.push(option1)
          let option2 = {value: item.startTime, label: item.startTime}
          this.startTimes.push(option2)
          let option3 = {value: item.endTime, label: item.endTime}
          this.endTimes.push(option3)
        })
        this.length=this.timeNames.length
        this.range=index(this.length)
      })

      let arr = new Array(this.length).fill(true);
      for(let i = 0; i < 7; i++) {
        this.spare.push(arr)
        this.occupy.push(arr)
      }
    },

    //标记
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
        this.edit_year_semester = [res.data.data.defaultYear, res.data.data.defaultSemester]
      })
    },
    save:function (){
      // this.editCourse.school = this.edit_school_major[0]
      // this.editCourse.major = this.edit_school_major[1]
      this.editCourse.classroom = this.edit_building_classroom[1]
      this.editCourse.year = this.edit_year_semester[0]
      this.editCourse.semester = this.edit_year_semester[1]
      this.editCourse.occupyTime = this.day

      if (this.limit_school_major !== []) {
        this.limit_school_major.forEach((item)=>{
          this.editCourse.majorLimits.push(item[1])
        })
      }
      else {
        this.editCourse.majorLimits = []
      }
      request.post("/teacher/updateCourseInfo", this.editCourse).then(res => {
        if(res.data.code!==200) {
          this.$message({
            type:"error",
            message: res.data.msg
          })
        }
        else {
          this.$message({
            type:"success",
            message: res.data.msg
          })
          this.$router.push("/teacher/teachercourse")
        }
      })
    },
  }
}
</script>

<style scoped>
.edit_form {
  width: 800px;
}
.form {
  padding-top: 50px;
  padding-left: 50px;
}
</style>