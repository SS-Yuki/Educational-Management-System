<template>
  <div class="add_form">
      <el-form :model="addCourse" label-width="80px" size="default" class="form">
        <el-form-item label="课程id">
          <el-input v-model="addCourse.id" disabled/>
        </el-form-item>
        <el-form-item label="课程名">
          <el-input v-model="addCourse.courseName"/>
        </el-form-item>
        <el-form-item label="课程编号">
          <el-input v-model="addCourse.courseNumber" />
        </el-form-item>
        <el-form-item label="教师工号">
          <el-input v-model="addCourse.teacherNum" />
        </el-form-item>
        <el-form-item label="院系/专业" prop="school_major">
          <el-cascader  v-model="add_school_major" :options="majorOptions"/>
        </el-form-item>
        <el-form-item label="上课时间">
          <el-input v-model="addCourse.classPeriod" />
        </el-form-item>
        <el-form-item label="教学楼/教室" prop="building_classroom">
          <el-cascader  v-model="add_building_classroom" :options="classroomOptions"/>
        </el-form-item>
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
        <el-button type="primary" @click="save">确认</el-button>
      </el-form>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "AddCourse",
  data() {
    return {
      addCourse:{
        id:0,
        courseName:'',
        courseNumber:'',
        teacherNum:'',
        major:'',
        school:'',
        classPeriod:'',
        building:'',
        classroom:'',
        creditHours:'',
        credits:'',
        capacity:'',
        introduction:'',
        applicant:''
      },
      add_school_major:'',
      add_building_classroom:'',
      majorOptions:[],
      classroomOptions:[],
    }
  },
  mounted() {
    this.getOptionMajor()
    this.getOptionClassroom()
  },
  methods: {
    getOptionMajor: function () {
      request.post("/admin/allMajors").then(res => {
        let that = this
        if (!res.data) return
        res.data.data.schools.forEach(function (item) {
          let option = {value: item.school, label: item.school, children: []}
          if (!item.majors) return
          item.majors.forEach(function (item) {
            let child = {value: item, label: item}
            option.children.push(child)
          })
          that.majorOptions.push(option)
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
    save:function (){
      this.addCourse.school = this.add_school_major[0]
      this.addCourse.major = this.add_school_major[1]
      this.addCourse.building = this.add_building_classroom[0]
      this.addCourse.classroom = this.add_building_classroom[1]
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