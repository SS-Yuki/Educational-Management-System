<template>
  <div class="add_form">
      <el-form :model="addCourse" label-width="80px" size="default" class="form">
        <el-form-item label="课程id" v-if="false">
          <el-input v-model="addCourse.id"/>
        </el-form-item>
        <el-form-item label="课程编号">
          <el-input v-model="addCourse.courseNumber" />
        </el-form-item>
        <el-form-item label="课程名">
          <el-input v-model="addCourse.courseName"/>
        </el-form-item>
        <el-form-item label="教师工号">
          <el-input v-model="addCourse.teacherNum" />
        </el-form-item>
        <el-form-item label="院系/专业">
          <el-cascader v-model="add_school_major" :options="majorOptionsChoose"/>
        </el-form-item>
        <el-form-item label="教学楼/教室">
          <el-cascader  v-model="add_building_classroom" :options="classroomOptions"/>
        </el-form-item>
<!--        time-->
        <el-form-item label="上课时间">
          <div>
            <el-checkbox-group  style="display: inline-block" disabled size="small">
              <el-checkbox-button v-for="item in range" :key="item" :label=item+1 style="display: block"
                                  value=item  >
                {{ startTimes[item].label + "-" + endTimes[item].label }}
              </el-checkbox-button>
            </el-checkbox-group>

            <el-checkbox-group v-model="day1" style="display: inline-block" size="small">
              <el-checkbox-button v-for="item in range" :key="item" :label=item+1
                                  :disabled=spare[0][item]
                                  style="display: block"
                                  value=item+1
              >
                {{ timeNames[item].label}}
              </el-checkbox-button>
            </el-checkbox-group>

            <el-checkbox-group v-model="day2" style="display: inline-block" size="small">
              <el-checkbox-button v-for="item in range" :key="item" :label=item+1
                                  :disabled=spare[1][item]
                                  style="display: block"
                                  value=item+1>
                {{ timeNames[item].label }}
              </el-checkbox-button>
            </el-checkbox-group>

            <el-checkbox-group v-model="day3" style="display: inline-block" size="small">
              <el-checkbox-button v-for="item in range" :key="item" :label=item+1
                                  :disabled=false
                                  style="display: block"
                                  value=item+1 >
                {{ timeNames[item].label }}
              </el-checkbox-button>
            </el-checkbox-group>

            <el-checkbox-group v-model="day4" style="display: inline-block" size="small">
              <el-checkbox-button v-for="item in range" :key="item" :label=item+1
                                  :disabled=spare[3][item]
                                  style="display: block"
                                  value=item+1  >
                {{ timeNames[item].label }}
              </el-checkbox-button>
            </el-checkbox-group>

            <el-checkbox-group v-model="day5" style="display: inline-block" size="small">
              <el-checkbox-button v-for="item in range" :key="item" :label=item+1
                                  :disabled=spare[4][item]
                                  style="display: block"
                                  value=item+1 >
                {{ timeNames[item].label }}
              </el-checkbox-button>
            </el-checkbox-group>

            <el-checkbox-group v-model="day6" style="display: inline-block" size="small">
              <el-checkbox-button v-for="item in range" :key="item" :label=item+1
                                  :disabled=spare[5][item]
                                  style="display: block"
                                  value=item+1 >
                {{ timeNames[item].label }}
              </el-checkbox-button>
            </el-checkbox-group>

            <el-checkbox-group v-model="day7" style="display: inline-block" size="small">
              <el-checkbox-button v-for="item in range" :key="item" :label=item+1
                                  :disabled=spare[6][item]
                                  style="display: block"
                                  value=item+1 >
                {{ timeNames[item].label }}
              </el-checkbox-button>
            </el-checkbox-group>
          </div>

        </el-form-item>
<!--        time-->

        <el-form-item label="学时">
          <el-input v-model="addCourse.creditHours" />
        </el-form-item>
        <el-form-item label="学分">
          <el-input v-model="addCourse.credits" />
        </el-form-item>
        <el-form-item label="容量">
          <el-input v-model="addCourse.capacity" />
        </el-form-item>
        <el-form-item label="介绍">
          <el-input v-model="addCourse.introduction" />
        </el-form-item>
<!--        申请人-->
        <el-form-item label="选课类型">
          <el-select v-model="addCourse.selectTypeString">
            <el-option v-for="item in typeOptions" :key="item.value" :label="item.label" :value="item.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="专业限制">
          <el-cascader :placeholder="major_limit_show" v-model="limit_school_major" :options="majorOptionsLimit" :props="school_major_props" collapse-tags/>
        </el-form-item>
          <el-form-item label="学年/学期">
            <el-cascader v-model="add_year_semester" :options="semesterOptions"/>
          </el-form-item>
<!--时间-->
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
  name: "AddCourse",
  data() {
    return {
      addCourse:{
        id:0,
        courseNumber:'',
        courseName:'',
        teacherNum:'',
        major:'',
        school:'',
        classPeriod:'',
        classroom:'',
        creditHours:'',
        credits:'',
        capacity:'',
        introduction:'',
        applicant:'',
        selectTypeString:'',

        //一维数组赋值
        majorLimits:[],

        year: '',
        semester: '',

        //时间！！！！
        occupyTime: '',
      },
      timeNames:[],
      startTimes:[],
      endTimes:[],
      spare:[],
      day1:[],
      day2:[],
      day3:[],
      day4:[],
      day5:[],
      day6:[],
      day7:[],
      length:0,
      range:[],

      add_school_major:[],
      limit_school_major:[],
      add_building_classroom:[],
      add_year_semester:[],

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
  mounted() {
    this.getOptionMajor()
    this.getOptionClassroom()

    //标记
    this.getOptionSemesters()
    this.getTime()
    for(let i = 0; i < 7; i++) {
      let arr = []
      for (let i = 0; i < this.length; i++) {
        arr.push(false)
      }
      this.spare.push(arr)
    }
  },
  computed: {
    day(){
      return [this.day1,this.day2,this.day3,this.day4,this.day5,this.day6,this.day7]
    }
  },
  watch: {
    addCourse: {
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
    add_building_classroom:{
      deep: true,
      handler(new_) {
        console.log("111111")
        request.post("/admin/getClassroomSpareTime",new_[1]).then(res=>{
          this.spare=res.data.data.days

        })

        console.log(this.spare[0][0])
        console.log(this.spare[1][1])
        console.log(this.spare[2][2])
      }
    }
  },
  methods: {
    getOptionMajor: function () {
      request.post("/admin/allMajors").then(res => {
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
      request.post("/admin/allMajors").then(res => {
        let that = this
        if (!res.data) return
        res.data.data.schools.forEach((item) => {
          let option = {value: item.school, label: item.school, disabled: true, children: []}
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
      request.post("/admin/allClassrooms").then(res => {
        let that = this
        if (!res.data) return
        res.data.data.buildings.forEach(function (item) {
          let option = {value: item.buiding, label: item.building, children: []}
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
        this.add_year_semester = [res.data.data.defaultYear, res.data.data.defaultSemester]
      })
    },
    save:function (){
      this.addCourse.school = this.add_school_major[0]
      this.addCourse.major = this.add_school_major[1]
      this.addCourse.classroom = this.add_building_classroom[1]
      this.addCourse.year = this.add_year_semester[0]
      this.addCourse.semester = this.add_year_semester[1]
      this.addCourse.occupyTime = this.day

      if (this.limit_school_major !== []) {
        this.limit_school_major.forEach((item)=>{
          this.addCourse.majorLimits.push(item[1])
        })
      }
      else {
        this.addCourse.majorLimits = []
      }
      request.post("/admin/addCourse", this.addCourse).then(res => {
        if(res.data.code!==200) {
          this.$message({
            type:"error",
            message: res.data.msg
          })
        }
        this.$router.push("/admin/checkcourse")
      })
    },
  }
}
</script>

<style scoped>
.add_form {
  width: 600px;
  height: 600px;
  position:relative;
}
.form {
  position:absolute;
  left:400px;
  width: 600px;
}
</style>