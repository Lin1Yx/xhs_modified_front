<template>
  <div class="home">
    <h1>home</h1>
    <el-button type='success' @click="goto('/personal')">个人主页</el-button>
      <router-view></router-view>
  </div>
</template>

<script>
// @ is an alias to /src
export default{
  methods:{
      goto(path) {
        this.$router.push(path)
      }
    }
}

</script>
