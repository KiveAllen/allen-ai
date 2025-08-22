<template>
  <div class="no-auth-page">
    <a-result status="403" title="403" sub-title="抱歉，您没有权限访问此页面。">
      <template #extra>
        <a-button type="primary" @click="goHome">返回首页</a-button>
        <a-button @click="goBack">返回上一页</a-button>
      </template>
    </a-result>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'

const router = useRouter()

const goHome = () => {
  router.push('/')
}

const goBack = () => {
  router.go(-1)
}
</script>

<style scoped>
.no-auth-page {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  overflow: hidden;
}

.ant-result {
  padding: 0;
}
</style>
