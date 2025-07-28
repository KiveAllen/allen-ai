# allen-ai-codeGen

## 项目简介
allen-ai-codeGen 是一个基于 Spring Boot 的代码生成服务，旨在为开发者提供便捷的代码生成与管理能力。项目支持 RESTful API，并集成了 Knife4j 和 Springdoc-OpenAPI 以便于接口文档的查看和调试。

## 主要功能
- 健康检查接口
- 代码生成相关接口（可扩展）
- 接口文档自动生成（Knife4j、OpenAPI）

## 快速启动

### 1. 克隆项目
```bash
git clone <your-repo-url>
cd allen-ai/allen-ai-codeGen
```

### 2. 配置环境
如需连接数据库，请在 `src/main/resources/application.yml` 中配置 datasource 信息（默认已注释）。

### 3. 启动服务
```bash
mvn spring-boot:run
```
或
```bash
java -jar target/allen-ai-codeGen-*.jar
```

### 4. 访问接口
- 默认端口：8080（如需修改请在 application.yml 中配置）
- 健康检查接口：`GET /api/health`
- Knife4j 文档地址：`http://localhost:8080/api/doc.html`
- OpenAPI 文档地址：`http://localhost:8080/api/v3/api-docs`

## 依赖环境
- JDK 8+
- Maven 3.6+
- Spring Boot 2.x

## 目录结构
```
allen-ai-codeGen/
  ├── src/
  │   ├── main/
  │   │   ├── java/com/allen/codeGen/
  │   │   └── resources/
  │   └── test/
  ├── pom.xml
  └── README.md
```

## 贡献与反馈
欢迎提交 issue 或 PR 参与项目改进！ 