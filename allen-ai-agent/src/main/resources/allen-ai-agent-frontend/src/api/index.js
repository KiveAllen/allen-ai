import axios from 'axios'

const API_URL = '/api'

/**
 * 创建SSE连接，调用AI恋爱大师应用
 * @param {string} message - 用户消息
 * @param {string} chatId - 聊天ID
 * @returns {EventSource} - SSE事件源
 */
export function chatWithLoveAppSSE(message, chatId) {
  const url = `${API_URL}/ai/love_app/chat/sse?message=${encodeURIComponent(message)}&chatId=${encodeURIComponent(chatId)}`
  return new EventSource(url)
}

/**
 * 创建SSE连接，调用Manus超级智能体
 * @param {string} message - 用户消息
 * @returns {EventSource} - SSE事件源
 */
export function chatWithManus(message) {
  const url = `${API_URL}/ai/manus/chat?message=${encodeURIComponent(message)}`
  return new EventSource(url)
}

/**
 * 生成唯一的聊天ID
 * @returns {string} - 聊天ID
 */
export function generateChatId() {
  return 'chat_' + Date.now() + '_' + Math.random().toString(36).substr(2, 9)
}