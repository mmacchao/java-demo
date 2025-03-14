<script setup>
import { ref } from "vue";
import MyLogin from "./components/Login.vue";
const user = ref({})
function onLogin(userInfo) {
  user.value = userInfo
}
</script>

<template>
  <MyLogin @ok="onLogin" v-if="!user.username"></MyLogin>
  <template v-else>
    <div >
      欢迎您：{{user.username}}
    </div>
    <el-button type="primary" @click="user.username = ''">退出登录</el-button>
  </template>

</template>

<style scoped>
</style>
