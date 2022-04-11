<template>
  <div>
    <div>Register</div>
    <div class="register">
      <el-form :model="register_data" label-width="120px" :rules="rules">
        <el-form-item label="身份" prop="role">
          <el-radio-group v-model="register_data.role">
            <el-radio label="student">学生</el-radio>
            <el-radio label="teacher">教师</el-radio>
          </el-radio-group>
        </el-form-item>
          <el-form-item label="院系/专业" prop="school_major">
            <el-cascader  v-model="school_major" :options="options" @change="school_major_select"/>
          </el-form-item>
        <el-form-item label="学号/工号" prop="number">
          <el-input v-model="register_data.number" />
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="register_data.name" />
        </el-form-item>
        <el-form-item label="身份证号" prop="idNum">
          <el-input v-model="register_data.idNum" />
        </el-form-item>
        <el-form-item label="手机号" prop="phoneNum">
          <el-input v-model="register_data.phoneNum"/>
        </el-form-item>
        <el-form-item label="电子邮箱" prop="email">
          <el-input v-model="register_data.email" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="register_data.password" />
        </el-form-item>
        <el-button style="width: 100%" type="primary" @click="register">注册</el-button>
      </el-form>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";
export default {
  name: "AddUser",
  data() {
    return {
      options: [],
      school_major: '',
      register_data: {
        school: '',
        major: '',
        role: '',
        number: '',
        name: '',
        idNum: '',
        phoneNum: '',
        email: '',
        password: ''
      },
      rules: {
        role: [{required: true, message: '请选择身份', trigger: 'change'}],
        school_major: [{required: true, message: '请选择院系/专业', trigger: 'blur'}],
        number: [{required: true, message: '请填写学号', trigger: 'blur'}],
        name: [{required: true, message: '请填写姓名', trigger: 'blur'},
          {pattern: /^[\u4e00-\u9fa5a-zA-Z]+$/, message: '姓名只能为中文或英文'}],
        idNum: [{required: true, message: '请填写身份证号', trigger: 'blur'},
          {
            pattern: /^[1-9]\d{5}(18|19|20)\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/,
            message: '身份证号格式错误'
          }],
        phoneNum: [{pattern: /^1\d{10}$/, message: '手机号码格式错误'}],
        email: [{pattern: /^[\u4e00-\u9fa5a-zA-Z0-9]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/, message: '电子邮箱格式错误'}],
        password: [{required: true, message: '请填写密码', trigger: 'blur'},
          {
            pattern: /^((?=.*\d)(?=.*[a-zA-Z])|(?=.*\d)(?=.*[-_])|(?=.*[a-zA-Z])(?=.*[-_]))[a-zA-Z0-9-_]{6,32}$/,
            message: '长度6-32,至少包含字母、数字或者特殊字符(-_)中的两种'
          }]
      }
    }
  },
  methods: {
    register: function () {
      request.post("/user/register", this.register_data).then(res => {
        console.log(res)
        //this.$router.push('/login')
      })
    },
    school_major_select: function () {
      this.register_data.school = this.school_major[0]
      this.register_data.major = this.school_major[1]
      console.log(this.register_data.major)
    },
    getOption: function () {
      request.post("/admin/allMajors").then(res => {
        console.log(res)
        let that = this
        if (!res.data) return
        res.data.forEach (function (item) {
          console.log(item);
          let option = {value: item.school, label: item.school, children: []}
          if (!item.major) return
          item.major.forEach (function (item) {
            let child = {value: item, label: item}
            option.children.push(child)
          })
          that.options.push(option)
        })
      })
    }
  },
  mounted() {
    this.getOption()
  }
}

</script>

<style scoped>
.register {
  width: 300px;
  height: 500px;
  margin: 20px auto;
}
</style>

