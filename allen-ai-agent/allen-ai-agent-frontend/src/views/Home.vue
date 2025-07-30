<template>
  <div class="home-container">
    <h1>Allen AI Agent 应用平台</h1>
    <div class="app-cards">
      <div class="app-card" @click="navigateTo('love-master')">
        <h2>AI 恋爱大师</h2>
        <p>专业的AI恋爱顾问，为您提供恋爱建议和情感支持</p>
      </div>
      <div class="app-card" @click="navigateTo('super-agent')">
        <h2>AI 超级智能体</h2>
        <p>强大的AI智能助手，可以回答各种问题并提供全方位帮助</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'

const router = useRouter()

const navigateTo = (route) => {
  router.push(`/${route}`)
}
</script>