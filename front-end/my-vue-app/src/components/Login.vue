<script setup>
import { ref, defineEmits } from "vue";
import axios from "axios";
import { ElMessage } from "element-plus";
const user = ref({username: '', password: ''})
const emit = defineEmits(["ok"]);
const formRef = ref(null)

async function login() {
  await formRef.value.validate()
  const res = await axios({
    method: 'POST',
    url: '/login',
    data: user.value,
  })
  ElMessage.success(res.data.message)
  if(res.data.code == 200) {
    emit('ok', user.value)
  }

}
</script>

<template>
  <el-form :model="user" ref="formRef" label-width="150px">
    <el-form-item label="用户名" prop="username">
      <el-input v-model="user.username"></el-input>
    </el-form-item>
    <el-form-item label="密码" prop="password">
      <el-input type="password" v-model="user.password"></el-input>
    </el-form-item>
    <el-button type="primary" @click="login">登录</el-button>
    <div>未注册将自动注册</div>
  </el-form>
</template>

<style scoped>

</style>
