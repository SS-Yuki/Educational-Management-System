<template>
<div>Student</div>
  <router-link to="/student/viewcourse">hhh</router-link>
  <router-view></router-view>
<!--  <div>-->
<!--    <el-form-->
<!--        ref="formRef"-->
<!--        :model="numberValidateForm"-->
<!--        label-width="100px"-->
<!--        class="demo-ruleForm"-->
<!--    >-->
<!--      <el-form-item-->
<!--          label="age"-->
<!--          prop="age"-->
<!--          :rules="[-->
<!--        { required: true, message: 'age is required' },-->
<!--        { type: 'number', message: 'age must be a number' },-->
<!--      ]"-->
<!--      >-->
<!--        <el-input-->
<!--            v-model.number="numberValidateForm.age"-->
<!--            type="text"-->
<!--            autocomplete="off"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item>-->
<!--        <el-button type="primary" @click="submitForm(formRef)">Submit</el-button>-->
<!--        <el-button @click="resetForm(formRef)">Reset</el-button>-->
<!--      </el-form-item>-->
<!--    </el-form>-->
<!--  </div>-->
</template>

<script>
export default {
  name: "Student"
}
</script>

<style scoped>

</style>