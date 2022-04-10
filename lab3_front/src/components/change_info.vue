<template>
  <div>
    <div id="change_info">
      <div>
        <input v-model="email">
      </div>
      <div>
        <input v-model="telephone">
      </div>
      <div>
        <input v-model="password">
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "change_info"
}
const change_info={
  data(){
    return{
      email:'',
      telephone:'',
      password:''
    }
  }
}
</script>

<style scoped>

</style>