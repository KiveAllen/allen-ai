<template>
  <div class="navbar">
    <button @click="goBack" class="back-button">
      返回主页
    </button>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'

const router = useRouter()

const goBack = () => {
  router.push('/')
}
</script>

<style scoped>
.navbar {
  position: absolute;
  top: 10px;
  left: 10px;
  z-index: 100;
}

.back-button {
  padding: 8px 12px;
  background-color: rgba(255, 255, 255, 0.8);
  border: 1px solid #ccc;
  border-radius: 4px;
  cursor: pointer;
  font-size: 14px;
}

.back-button:hover {
  background-color: rgba(255, 255, 255, 1);
}
</style>