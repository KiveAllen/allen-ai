<template>
  <a-layout-header class="global-header">
    <div class="header-container">
      <!-- 左侧：Logo和网站标题 -->
      <div class="header-left">
        <div class="logo-container">
          <img src="/logo.png" alt="Logo" class="logo" />
          <h1 class="site-title">Allen AI CodeGen</h1>
        </div>
      </div>

      <!-- 中间：导航菜单 -->
      <div class="header-center">
        <a-menu
          v-model:selectedKeys="selectedKeys"
          mode="horizontal"
          :items="menuItems"
          class="header-menu"
          @click="handleMenuClick"
        />
      </div>

      <!-- 右侧：用户信息 -->
      <div class="header-right">
        <a-button type="primary" @click="handleLogin">
          <template #icon>
            <UserOutlined />
          </template>
          登录
        </a-button>
      </div>
    </div>
  </a-layout-header>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { UserOutlined } from '@ant-design/icons-vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const selectedKeys = ref<string[]>(['home'])

// 菜单配置
const menuItems = [
  {
    key: '/',
    label: '首页',
  },
  {
    key: '/about',
    label: '关于',
  },
  {
    key: '/projects',
    label: '项目',
  },
  {
    key: '/contact',
    label: '联系',
  },
]

// 处理菜单点击
const handleMenuClick: MenuProps['onClick'] = (e) => {
  const key = e.key as string
  selectedKeys.value = [key]
  // 跳转到对应页面
  if (key.startsWith('/')) {
    router.push(key)
  }
}

// 处理登录
const handleLogin = () => {
  console.log('登录功能待实现')
}
</script>

<style scoped>
.global-header {
  background: #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  z-index: 1000;
  height: 64px;
  line-height: 64px;
}

.header-container {
  display: flex;
  align-items: center;
  justify-content: space-between;
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 24px;
  height: 100%;
}

.header-left {
  display: flex;
  align-items: center;
}

.logo-container {
  display: flex;
  align-items: center;
  gap: 12px;
}

.logo {
  width: 32px;
  height: 32px;
  object-fit: contain;
}

.site-title {
  margin: 0;
  font-size: 18px;
  font-weight: 600;
  color: #1890ff;
}

.header-center {
  flex: 1;
  display: flex;
  justify-content: center;
}

.header-menu {
  border: none;
  background: transparent;
}

.header-right {
  display: flex;
  align-items: center;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .header-container {
    padding: 0 16px;
  }

  .site-title {
    font-size: 16px;
  }

  .header-center {
    display: none;
  }
}

@media (max-width: 480px) {
  .logo-container {
    gap: 8px;
  }

  .logo {
    width: 28px;
    height: 28px;
  }

  .site-title {
    font-size: 14px;
  }
}
</style>
