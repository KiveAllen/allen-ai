# Allen-AI 简介

Allen-AI 是一个多模块智能对话系统项目，包含：
- **allen-ai-agent**：后端主服务（Spring Boot，含前端源码）[→ 说明](./allen-ai-agent/README.md)
- **allen-ai-mcp-service**：AI 工具服务（Java，独立 Spring Boot 应用）

---

## 目录结构

```
allen-ai/
├── allen-ai-agent/                  # 后端主服务（Spring Boot，含前端源码）
│   ├── src/main/java/com/allen/agent/
│   ├── src/main/resources/allen-ai-agent-frontend/
│   ├── src/main/resources/document/
│   ├── application.yml
│   └── ...
│
├── allen-ai-mcp-service/            # AI 工具服务（Java，独立 Spring Boot 应用）
│   ├── src/main/java/com/allen/mcpService/
│   ├── src/main/resources/
│   └── ...
│
├── pom.xml                          # 项目聚合 Maven 配置
└── README.md                        # 目录说明（本文件）
```