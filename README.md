# Allen-AI-Agent - 智能对话代理系统

## 项目介绍

Allen-AI-Agent 是一个基于 Spring Boot 和 AI 技术的智能对话代理系统，专注于提供高可用、可扩展的聊天记忆与响应能力。该项目结合了本地缓存、AI 调用接口以及文档增强检索（RAG）等技术，适用于构建个性化的对话机器人解决方案。

## 技术栈

- **核心框架**：Spring Boot + Java 17/21
- **AI 支持**：支持多种 AI 调用方式（HTTP、SDK、LangChain、Ollama 等）
- **缓存**：Caffeine（本地缓存）、Redis（可选）
- **数据存储**：文档文件系统 + 向量数据库（如 FAISS 或其他 RAG 存储）
- **AOP 日志**：基于 Spring AOP 的方法级日志记录
- **配置管理**：YAML 格式配置文件
- **测试框架**：JUnit 5

## 核心特性

### 🧠 **AI 对话集成**
- 多种 AI 调用实现类，便于扩展不同模型服务
- 支持本地大语言模型（如 Ollama）与远程 API（如 OpenAI）

### 💬 **聊天记忆持久化**
- 文件存储聊天记录，适合轻量级场景
- 可扩展为 Redis 或数据库存储

### 🔍 **文档增强检索 (RAG)**
- 支持加载文档并构建知识库
- 结合向量存储进行语义搜索

### 📝 **方法日志切面**
- 基于 AOP 的方法调用日志记录
- 易于调试与性能分析

### 🚀 **模块化设计**
- 分层清晰：Controller、Service、Model、Advisor、Demo 等
- 易于扩展新功能模块

## 项目结构

```
allen-ai-agent/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/allen/agent/
│   │   │       ├── advisor/          # AOP 切面相关（如日志）
│   │   │       ├── app/              # 主应用逻辑（如 LoveApp）
│   │   │       ├── chatmemory/       # 聊天记忆模块（如 FileBasedChatMemory）
│   │   │       ├── consts/           # 常量定义（如 API Key）
│   │   │       ├── controller/       # 控制器（如 HealthController）
│   │   │       ├── demo/             # 示例代码（各种 AI 调用实现）
│   │   │       ├── rag/              # RAG 相关模块（文档加载、向量存储配置）
│   │   │       └── AllenAiAgentApplication.java  # 启动类
│   │   │
│   │   └── resources/
│   │       ├── document/             # 存放用于 RAG 的文档
│   │       ├── application.yml       # 主配置文件
│   │       └── application-local.yml # 本地开发配置
│   │
├── test/                             # 单元测试
│   └── java/
│       └── com/allen/agent/
│           ├── app/
│           └── AllenAiAgentApplicationTests.java
│
├── README.md                         # 项目说明
└── pom.xml                           # Maven 项目配置
```


## 快速开始

### 环境要求

- JDK 17 或 JDK 21
- Maven 3.8+
- IDE（如 IntelliJ IDEA）
- Python 环境（如使用 Ollama）
- 可选：Docker（用于部署 AI 模型）

### 启动步骤

1. 克隆项目：
   ```bash
   git clone https://github.com/KiveAllen/allen-ai-agent.git
   ```


2. 进入目录并安装依赖：
   ```bash
   cd allen-ai-agent
   mvn clean install
   ```


3. 修改配置文件 `src/main/resources/application.yml` 中的 AI Key、RAG 文档路径等参数。

4. 启动应用：
   ```bash
   mvn spring-boot:run
   ```


5. 访问健康检查接口（默认端口 8080）：
   ```
   GET http://localhost:8080/health
   ```


6. 使用 `/chat` 接口与 AI 进行对话（详见 Controller 定义）。

## 功能模块说明

| 模块 | 说明 |
|------|------|
| `advisor` | 包含 AOP 切面，如方法执行日志记录 |
| `app` | 主业务逻辑入口，包含对话处理核心类 [LoveApp](file://E:\code\allen-ai-agent\src\main\java\com\allen\agent\app\LoveApp.java#L20-L83) |
| `chatmemory` | 聊天记录保存与读取模块，当前实现为文件存储 |
| `demo/invoke` | 多种 AI 调用方式的示例实现 |
| `rag` | 文档加载、向量存储配置与检索逻辑 |
| `controller` | 提供 RESTful API 接口 |
| `resources/document` | 存放 RAG 所需的 Markdown 文档 |

## 性能优化方向（建议）

- 引入 Redis 缓存频繁访问的 AI 回答
- 使用异步调用提升并发响应能力
- 将 [FileBasedChatMemory](file://E:\code\allen-ai-agent\src\main\java\com\allen\agent\chatmemory\FileBasedChatMemory.java#L19-L88) 替换为数据库或内存映射文件以提高效率
- 对 RAG 查询结果做缓存，避免重复计算相似问题

## 监控与可观测性（待完善）

- 可接入 Prometheus + Grafana 实现指标监控
- 日志可通过 AOP 输出至日志中心（如 ELK）
- 异常处理机制可进一步增强

---