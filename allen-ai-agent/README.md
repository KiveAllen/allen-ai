# Allen-AI-Agent

[![构建状态](https://img.shields.io/badge/build-passing-brightgreen)](https://github.com/KiveAllen/allen-ai-agent/actions)
[![许可证](https://img.shields.io/badge/license-MIT-blue)](./LICENSE)

> 基于 Spring Boot 与 AI 技术的智能对话代理系统，支持多种 AI 调用、聊天记忆、文档增强检索（RAG）等能力，适合个性化对话机器人开发。

---

## 目录
- [项目简介](#项目简介)
- [核心功能](#核心功能)
- [项目结构](#项目结构)
- [快速开始](#快速开始)
- [优化建议](#优化建议)
- [监控与可观测性](#监控与可观测性)
- [许可证](#许可证)

---

## 项目简介
Allen-AI-Agent 致力于打造高可用、可扩展的智能对话系统，集成本地缓存、AI 多模型调用、文档增强检索（RAG）等技术，适用于多场景的对话机器人。

## 核心功能
- 🤖 **多模型 AI 对话**：支持 HTTP、SDK、LangChain、Ollama 等多种 AI 调用方式，轻松扩展新模型。
- 💾 **聊天记忆持久化**：支持文件存储聊天记录，便于轻量级部署，可扩展为 Redis 或数据库。
- 📄 **文档增强检索（RAG）**：加载本地文档，结合向量数据库实现语义检索，提升问答准确性。
- 📝 **AOP 方法日志**：基于 Spring AOP 实现方法级日志，便于调试与性能分析。
- 🧩 **模块化设计**：分层清晰，易于扩展和维护。

## 项目结构
```
allen-ai-agent/
├── allen-ai-agent-mcp/                  # AI 工具服务（Java）
│   ├── src/main/java/com/allen/mcpService/
│   │   ├── tools/                        # AI 工具类（如图片搜索）
│   │   └── AllenAiMcpServiceApplication.java
│   └── src/main/resources/               # 配置文件
│
├── src/main/java/com/allen/agent/         # 后端主服务
│   ├── advisor/                          # AOP 切面
│   ├── agent/                            # 智能体核心
│   ├── app/                              # 业务应用（如 LoveApp）
│   ├── chatmemory/                       # 聊天记忆模块
│   ├── config/                           # 配置类
│   ├── constant/                         # 常量定义
│   ├── controller/                       # REST API 控制器
│   ├── demo/                             # AI 调用与 RAG 示例
│   ├── rag/                              # 文档加载与向量存储
│   ├── tools/                            # 工具类
│   └── AllenAiAgentApplication.java      # 启动类
│
├── src/main/resources/
│   ├── application.yml                   # 主配置文件
│   ├── application-local.yml             # 本地配置
│   ├── document/                         # RAG 文档
│   └── allen-ai-agent-frontend/          # 前端项目（Vue3 + Vite，嵌套于此）
│       ├── src/
│       │   ├── api/                      # 前端 API 封装
│       │   ├── components/               # 公共组件
│       │   ├── router/                   # 路由配置
│       │   ├── views/                    # 页面视图
│       │   ├── App.vue                   # 入口组件
│       │   └── main.js                   # 入口文件
│       └── public/                       # 静态资源
│
├── pom.xml                               # 主 Maven 配置
├── Dockerfile                            # Docker 构建文件
├── README.md                             # 项目说明
└── ...                                   # 其他文件
```

## 快速开始
1. 安装 JDK 17+、Maven 3.8+，建议使用 IntelliJ IDEA。
2. 克隆项目并安装依赖：
   ```bash
   git clone https://github.com/KiveAllen/allen-ai.git
   cd allen-ai-agent
   mvn clean install
   ```
3. 配置 `src/main/resources/application.yml`，填写 AI Key、RAG 文档路径等参数。
4. 启动后端服务：
   ```bash
   mvn spring-boot:run
   ```
5. 访问健康检查接口：`http://localhost:8080/health`
6. 前端目录位于 `src/main/resources/allen-ai-agent-frontend`，可独立启动 Vue3 前端（如需）。

## 优化建议
- 引入 Redis 缓存高频 AI 回答
- 使用异步调用提升并发能力
- 聊天记忆可扩展为数据库或内存映射文件
- RAG 查询结果可做缓存，提升检索效率

## 监控与可观测性
- 可集成 Prometheus + Grafana 实现指标监控
- 日志可输出至 ELK 等日志中心
- 异常处理机制可持续完善

## 许可证
MIT License 