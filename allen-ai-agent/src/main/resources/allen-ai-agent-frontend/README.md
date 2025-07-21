# Allen AI Agent 前端项目

## 项目介绍

这是一个基于Vue3开发的前端项目，提供了两个AI应用界面：

1. **AI 恋爱大师**：提供恋爱咨询和情感支持的AI聊天应用
2. **AI 超级智能体**：功能强大的AI助手，可以回答各种问题

## 技术栈

- Vue 3
- Vue Router
- Axios
- Vite
- SSE (Server-Sent Events)

## 项目结构

```
allen-ai-agent-frontend/
├── public/
├── src/
│   ├── api/            # API接口
│   ├── components/     # 公共组件
│   ├── router/         # 路由配置
│   ├── views/          # 页面组件
│   ├── App.vue         # 根组件
│   ├── main.js         # 入口文件
│   └── style.css       # 全局样式
├── index.html
├── package.json
├── vite.config.js
└── README.md
```

## 功能特点

- 主页：用于切换不同的应用
- AI 恋爱大师：聊天室风格，支持实时对话，自动生成聊天室ID
- AI 超级智能体：聊天室风格，支持实时对话
- 使用SSE技术实现实时消息流式显示

## 安装与运行

### 安装依赖

```bash
npm install
```

### 开发模式运行

```bash
npm run dev
```

### 构建生产版本

```bash
npm run build
```

### 预览生产版本

```bash
npm run preview
```

## 后端接口

项目默认连接到 `http://localhost:8123/api` 作为后端接口地址。

- AI 恋爱大师接口：`/api/ai/love_app/chat/sse`
- AI 超级智能体接口：`/api/ai/manus/chat`

## 注意事项

- 确保后端服务已启动并正常运行
- 项目使用Node.js v18.20版本开发