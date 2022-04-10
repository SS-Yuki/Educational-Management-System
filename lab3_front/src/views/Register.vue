<template>
  <div>
    <div>Register</div>
    <div class="register">
      <el-form :model="register_data" label-width="120px" :rules="rules">
        <el-form-item label="身份" prop="role">
          <el-radio-group v-model="register_data.role">
            <el-radio label="学生" value="student"/>
            <el-radio label="教师" value="teacher"/>
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
  name: "Register",
  data() {
    return {
      options : [
        {
          value: '计院', label: '计院',
          children: [
            {value: '计科', label: '计科',},
            {value: '软工', label: '软工',},
          ],
        },
        {
          value: '院院', label: '院院',
          children: [
            {value: '1专', label: '1专',},
            {value: '2专', label: '2专',},
          ],
        }
      ],
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
        role:[{ required: true, message: '请选择身份', trigger: 'blur' }],
        school_major:[{ required: true, message: '请选择院系和专业', trigger: 'blur' }],
        number: [{ required: true, message: '请选择职称', trigger: 'blur' }],
        name: [{ required: true, message: '请选择性别', trigger: 'blur' }],
        idNum: [{ required: true, message: '请选择医院', trigger: 'blur' }],
        phoneNum: [{ required: true, message: '请选择职称', trigger: 'blur' }],
          // { pattern: /^(13[0-9]|14[1|4|5|6|7|8|9]|15[0|1|2|3|5|6|7|8|9]|17[2|3|5|6|7|8]|18[0-9]|19[1|8|9])\d{8}$/, message: '手机号格式不正确', trigger: 'blur'}],
        email: [{ required: true, message: '请选择性别', trigger: 'blur' }],
        password: [{ required: true, message: '请选择医院', trigger: 'blur' }]
      }
    }
  },
  methods: {
    register: function () {
      request.post("/user/register", this.register_data).then(res => {
        console.log(res)
        this.$router.push('/login')
      })
    },
    school_major_select: function () {
      this.register_data.school = this.school_major[0]
      this.register_data.major = this.school_major[1]
    }
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