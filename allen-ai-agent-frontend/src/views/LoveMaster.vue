<template>
  <div class="chat-container">
    <NavBar />
    <div class="chat-header">
      <h2>AI 恋爱大师</h2>
      <div>聊天ID: {{ chatId }}</div>
    </div>
    <div class="chat-messages" ref="messagesContainer">
      <div 
        v-for="(message, index) in messages" 
        :key="index" 
        :class="['message', message.isUser ? 'user-message' : 'ai-message']"
      >
        {{ message.content }}
      </div>
    </div>
    <div class="chat-input">
      <input 
        type="text" 
        v-model="userInput" 
        @keyup.enter="sendMessage" 
        placeholder="输入您的问题..."
      />
      <button @click="sendMessage">发送</button>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted, nextTick, watch } from 'vue'
import { chatWithLoveAppSSE, generateChatId } from '../api'
import NavBar from '../components/NavBar.vue'

const messages = ref([])
const userInput = ref('')
const messagesContainer = ref(null)
const chatId = ref('')
let eventSource = null

// 初始化聊天ID
onMounted(() => {
  chatId.value = generateChatId()
  // 添加AI欢迎消息
  messages.value.push({
    content: '你好！我是AI恋爱大师，有什么恋爱问题需要咨询吗？',
    isUser: false
  })
})

// 关闭SSE连接
onUnmounted(() => {
  if (eventSource) {
    eventSource.close()
  }
})

// 监听消息变化，自动滚动到底部
watch(messages, () => {
  nextTick(() => {
    if (messagesContainer.value) {
      messagesContainer.value.scrollTop = messagesContainer.value.scrollHeight
    }
  })
}, { deep: true })

// 发送消息
const sendMessage = () => {
  if (!userInput.value.trim()) return
  
  // 添加用户消息
  const userMessage = userInput.value
  messages.value.push({
    content: userMessage,
    isUser: true
  })
  
  // 清空输入框
  userInput.value = ''
  
  // 关闭之前的连接
  if (eventSource) {
    eventSource.close()
  }
  
  // 创建新的SSE连接
  eventSource = chatWithLoveAppSSE(userMessage, chatId.value)
  
  // 创建AI消息占位
  const aiMessageIndex = messages.value.length
  messages.value.push({
    content: '',
    isUser: false
  })
  
  // 监听SSE事件
  eventSource.onmessage = (event) => {
    if (event.data) {
      // 更新AI消息内容
      messages.value[aiMessageIndex].content += event.data
    }
  }
  
  eventSource.onerror = () => {
    eventSource.close()
    // 如果AI消息为空，添加错误提示
    if (!messages.value[aiMessageIndex].content) {
      messages.value[aiMessageIndex].content = '抱歉，连接出现问题，请稍后再试。'
    }
  }
}
</script>