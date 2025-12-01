<template>
  <div class="chat-container">
    <NavBar />
    <div class="chat-header">
      <h2>AI 超级智能体</h2>
    </div>
    <div class="chat-messages" ref="messagesContainer">
      <div 
        v-for="(message, index) in messages" 
        :key="index" 
        :class="['message', message.isUser ? 'user-message' : 'ai-message']"
      >
        <!-- 处理不同类型的消息内容 -->
        <div v-if="isJsonMessage(message.content)" class="json-message">
          <div v-if="getMessageType(message.content) === 'image'" class="image-message">
            <div class="message-content">
              <p>{{ getImageMessageText(message.content) }}</p>
              <img 
                :src="'file:///' + getImagePath(message.content)" 
                alt="Downloaded image" 
                class="downloaded-image"
                @load="scrollToBottom"
              />
            </div>
          </div>
          
          <div v-else-if="getMessageType(message.content) === 'search'" class="search-message">
            <div class="message-content">
              <h3>搜索结果: {{ getSearchQuery(message.content) }}</h3>
              <div 
                v-for="(result, idx) in getSearchResults(message.content)" 
                :key="idx" 
                class="search-result-item"
              >
                <h4>
                  <a :href="result.link" target="_blank">{{ result.title }}</a>
                </h4>
                <p v-if="result.snippet">{{ result.snippet }}</p>
                <img 
                  v-if="result.thumbnail" 
                  :src="result.thumbnail" 
                  :alt="result.title" 
                  class="search-result-thumbnail"
                />
              </div>
            </div>
          </div>
          
          <div v-else-if="getMessageType(message.content) === 'scrape'" class="scrape-message">
            <div class="message-content">
              <h3>网页内容: {{ getScrapeTitle(message.content) }}</h3>
              <p>{{ getScrapeContent(message.content) }}</p>
              <a :href="getScrapeUrl(message.content)" target="_blank" class="scrape-url">
                查看完整页面
              </a>
            </div>
          </div>
          
          <div v-else-if="getMessageType(message.content) === 'error'" class="error-message">
            <div class="message-content">
              <p class="error-text">错误: {{ getErrorMessage(message.content) }}</p>
            </div>
          </div>
          
          <div v-else class="unknown-json-message">
            <div class="message-content">
              <pre>{{ message.content }}</pre>
            </div>
          </div>
        </div>
        
        <div v-else class="text-message">
          <div class="message-content">
            {{ message.content }}
          </div>
        </div>
      </div>
    </div>
    <div class="chat-input">
      <input 
        type="text" 
        v-model="userInput" 
        @keyup.enter="sendMessage" 
        placeholder="输入您的问题..."
        :disabled="isLoading"
      />
      <button @click="sendMessage" :disabled="isLoading || !userInput.trim()">
        <span v-if="!isLoading">发送</span>
        <span v-else>...</span>
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted, nextTick, watch } from 'vue'
import { chatWithManus } from '../api/index.js'
import NavBar from '../components/NavBar.vue'

const messages = ref([])
const userInput = ref('')
const messagesContainer = ref(null)
const isLoading = ref(false)
let eventSource = null

// 初始化欢迎消息
onMounted(() => {
  messages.value.push({
    content: '你好！我是AI超级智能体，有什么我可以帮助你的吗？',
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
    scrollToBottom()
  })
}, { deep: true })

const scrollToBottom = () => {
  nextTick(() => {
    if (messagesContainer.value) {
      messagesContainer.value.scrollTop = messagesContainer.value.scrollHeight
    }
  })
}

// 检查消息是否为JSON格式
const isJsonMessage = (content) => {
  try {
    JSON.parse(content);
    return true;
  } catch (e) {
    return false;
  }
}

// 获取消息类型
const getMessageType = (content) => {
  try {
    const parsed = JSON.parse(content);
    return parsed.type || 'unknown';
  } catch (e) {
    return 'unknown';
  }
}

// 图片消息相关方法
const isImageMessage = (content) => {
  return getMessageType(content) === 'image';
}

const getImagePath = (content) => {
  try {
    const parsed = JSON.parse(content);
    return parsed.path;
  } catch (e) {
    return '';
  }
}

const getImageMessageText = (content) => {
  try {
    const parsed = JSON.parse(content);
    if (parsed.path) {
      const fileName = parsed.path.split('/').pop();
      return `图片文件 "${fileName}" 下载成功:`;
    }
    return '图片下载成功:';
  } catch (e) {
    return '图片下载成功:';
  }
}

// 搜索消息相关方法
const getSearchQuery = (content) => {
  try {
    const parsed = JSON.parse(content);
    return parsed.query || '';
  } catch (e) {
    return '';
  }
}

const getSearchResults = (content) => {
  try {
    const parsed = JSON.parse(content);
    return parsed.results || [];
  } catch (e) {
    return [];
  }
}

// 抓取消息相关方法
const getScrapeTitle = (content) => {
  try {
    const parsed = JSON.parse(content);
    return parsed.title || '';
  } catch (e) {
    return '';
  }
}

const getScrapeContent = (content) => {
  try {
    const parsed = JSON.parse(content);
    return parsed.content || '';
  } catch (e) {
    return '';
  }
}

const getScrapeUrl = (content) => {
  try {
    const parsed = JSON.parse(content);
    return parsed.url || '';
  } catch (e) {
    return '';
  }
}

// 错误消息相关方法
const getErrorMessage = (content) => {
  try {
    const parsed = JSON.parse(content);
    return parsed.message || '未知错误';
  } catch (e) {
    return '未知错误';
  }
}

// 发送消息
const sendMessage = () => {
  if (!userInput.value.trim() || isLoading.value) return
  
  // 添加用户消息
  const userMessage = userInput.value
  messages.value.push({
    content: userMessage,
    isUser: true
  })
  
  // 设置加载状态
  isLoading.value = true
  
  // 清空输入框
  userInput.value = ''
  
  // 关闭之前的连接
  if (eventSource) {
    eventSource.close()
  }
  
  // 创建新的SSE连接
  eventSource = chatWithManus(userMessage)
  
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
      messages.value[aiMessageIndex].content = '{"type":"error","message":"抱歉，连接出现问题，请稍后再试。"}';
    }
    isLoading.value = false
  }
  
  eventSource.addEventListener('end', () => {
    isLoading.value = false
    eventSource.close()
  })
}
</script>

<style scoped>
.chat-container {
  display: flex;
  flex-direction: column;
  height: calc(100vh - 60px);
  background-color: #f5f5f5;
}

.chat-header {
  background-color: #4a6fa5;
  color: white;
  padding: 15px 20px;
  box-shadow: 0 2px 5px rgba(0,0,0,0.1);
}

.chat-header h2 {
  margin: 0;
  font-size: 1.5rem;
  font-weight: 500;
}

.chat-messages {
  flex: 1;
  overflow-y: auto;
  padding: 20px;
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.message {
  display: flex;
  max-width: 80%;
  animation: fadeIn 0.3s ease-out;
}

@keyframes fadeIn {
  from { opacity: 0; transform: translateY(10px); }
  to { opacity: 1; transform: translateY(0); }
}

.user-message {
  align-self: flex-end;
}

.ai-message {
  align-self: flex-start;
}

.message-content {
  padding: 12px 16px;
  border-radius: 18px;
  line-height: 1.5;
  box-shadow: 0 1px 3px rgba(0,0,0,0.1);
  word-wrap: break-word;
  position: relative;
}

.user-message .message-content {
  background-color: #4a6fa5;
  color: white;
  border-bottom-right-radius: 4px;
}

.ai-message .message-content {
  background-color: #ffffff;
  color: #333;
  border-bottom-left-radius: 4px;
}

/* 图片消息样式 */
.image-message .message-content {
  background-color: #ffffff;
  padding: 15px;
}

.image-message .message-content p {
  margin: 0 0 10px 0;
  color: #333;
  font-size: 0.9rem;
}

.downloaded-image {
  max-width: 100%;
  max-height: 400px;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
  display: block;
  margin-top: 10px;
  border: 1px solid #eee;
}

/* 搜索消息样式 */
.search-message .message-content {
  background-color: #ffffff;
  padding: 15px;
  max-width: 100%;
}

.search-message .message-content h3 {
  margin-top: 0;
  color: #333;
  border-bottom: 1px solid #eee;
  padding-bottom: 8px;
}

.search-result-item {
  margin-bottom: 15px;
  padding-bottom: 15px;
  border-bottom: 1px solid #f0f0f0;
}

.search-result-item:last-child {
  border-bottom: none;
  margin-bottom: 0;
  padding-bottom: 0;
}

.search-result-item h4 {
  margin: 0 0 5px 0;
  font-size: 1rem;
}

.search-result-item h4 a {
  color: #4a6fa5;
  text-decoration: none;
}

.search-result-item h4 a:hover {
  text-decoration: underline;
}

.search-result-item p {
  margin: 5px 0;
  font-size: 0.9rem;
  color: #666;
  line-height: 1.4;
}

.search-result-thumbnail {
  max-width: 100px;
  max-height: 100px;
  margin-top: 5px;
  border-radius: 4px;
}

/* 抓取消息样式 */
.scrape-message .message-content {
  background-color: #ffffff;
  padding: 15px;
}

.scrape-message .message-content h3 {
  margin-top: 0;
  color: #333;
}

.scrape-message .message-content p {
  white-space: pre-wrap;
  line-height: 1.6;
}

.scrape-url {
  display: inline-block;
  margin-top: 10px;
  color: #4a6fa5;
  text-decoration: none;
  font-weight: 500;
}

.scrape-url:hover {
  text-decoration: underline;
}

/* 错误消息样式 */
.error-message .message-content {
  background-color: #ffebee;
  padding: 15px;
}

.error-text {
  color: #c62828;
  margin: 0;
}

/* 未知JSON消息样式 */
.unknown-json-message .message-content {
  background-color: #f5f5f5;
  padding: 15px;
}

.unknown-json-message pre {
  margin: 0;
  white-space: pre-wrap;
  font-family: monospace;
  font-size: 0.9rem;
  color: #333;
}

.chat-input {
  display: flex;
  padding: 15px;
  background-color: white;
  border-top: 1px solid #eee;
  gap: 10px;
}

.chat-input input {
  flex: 1;
  padding: 12px 15px;
  border: 1px solid #ddd;
  border-radius: 24px;
  outline: none;
  font-size: 1rem;
  transition: border-color 0.3s;
}

.chat-input input:focus {
  border-color: #4a6fa5;
}

.chat-input input:disabled {
  background-color: #f5f5f5;
}

.chat-input button {
  padding: 12px 24px;
  background-color: #4a6fa5;
  color: white;
  border: none;
  border-radius: 24px;
  cursor: pointer;
  font-size: 1rem;
  transition: background-color 0.3s;
}

.chat-input button:hover:not(:disabled) {
  background-color: #3a5a80;
}

.chat-input button:disabled {
  background-color: #cccccc;
  cursor: not-allowed;
}

/* 滚动条样式 */
.chat-messages::-webkit-scrollbar {
  width: 6px;
}

.chat-messages::-webkit-scrollbar-track {
  background: #f1f1f1;
}

.chat-messages::-webkit-scrollbar-thumb {
  background: #c1c1c1;
  border-radius: 3px;
}

.chat-messages::-webkit-scrollbar-thumb:hover {
  background: #a1a1a1;
}
</style>