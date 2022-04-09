<template>
  <div class="login">
    <form class="main">
<!--      <br>-->
      <t1><h1>复日大学教务系统</h1></t1>
      <p>学工号：<input type="text" placeholder="请输入学工号" name="numberid" id="numberid"> <br><br></p>
      <p>密&emsp;码：<input type="password" placeholder="请输入密码" name="password" id="password"><br><br></p>
      <p><input type="button" value="登录" id="submit"> <br><br></p>
      <div>还没有账号？<a href="register.html">点击此处注册</a></div>
    </form>
  </div>
</template>

<script>
export default {
  name: "Login"
}
</script>

<style scoped>
t1{
  font-family: "宋体";
  color: blue;
}
.login{
  background-image: url(../assets/background2.jpg);
  background-size: 100%;
}
login{
  width: 150px;
  height:30px
}
submitbtn{
  width:50px;
  height:30px;
  background-color: deepskyblue;
}
.main{

  background-color: white;
  height: 300px;
  width: 600px;
  border-radius: 25px;
  font-family: "微软雅黑";
  opacity: 1;
}

</style>