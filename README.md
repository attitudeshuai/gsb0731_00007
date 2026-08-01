# 🛒 超市管理系统 (Supermarket Management System)

一个功能完整、开箱即用的现代化超市管理系统，基于 Spring Boot + Vue 3 + MySQL 构建，支持 Docker 一键部署。

[![Tech Stack](https://img.shields.io/badge/Stack-Spring%20Boot%20%2B%20Vue%203-blue)]()
[![Docker](https://img.shields.io/badge/Docker-Ready-brightgreen)]()
[![License](https://img.shields.io/badge/License-MIT-yellow)]()

---

## 📸 系统预览

### 登录页面
- 渐变背景设计
- 支持登录和注册
- 表单验证和错误提示

### 管理后台
- 现代化侧边栏导航
- 响应式布局设计
- 用户信息展示和退出登录

### 核心功能
- **商品管理**：完整的商品 CRUD，支持搜索、分类关联、库存管理
- **分类管理**：商品分类维护
- **供应商管理**：供应商信息管理

---

## 🛠 技术栈

### 前端
- **框架**: Vue 3 (Composition API)
- **构建工具**: Vite 5
- **UI 组件库**: Ant Design Vue 4
- **路由**: Vue Router 4
- **HTTP 客户端**: Axios
- **图标**: Ant Design Icons

### 后端
- **语言**: Java 17
- **框架**: Spring Boot 3.2
- **ORM**: Spring Data JPA
- **数据库**: MySQL 8.0
- **构建工具**: Maven 3.9

### 容器化
- **Docker**: 多阶段构建优化镜像大小
- **Docker Compose**: 服务编排和依赖管理

---

## 🚀 快速开始

### 前置要求
- Docker Desktop (推荐) 或 Docker Engine + Docker Compose
- 或者本地环境：Node.js 20+, Java 17+, MySQL 8.0+

### 方式一：Docker 一键启动（推荐）

1. **启动所有服务**
   ```bash
   docker compose up --build
   ```

2. **访问系统**
   - 前端：http://localhost:3000
   - 后端 API：http://localhost:8080/api

3. **停止服务**
   ```bash
   docker compose down
   ```

### 方式二：本地开发模式

#### 启动数据库
```bash
docker compose up -d db
```

#### 启动后端
```bash
cd backend
./mvnw spring-boot:run
```

#### 启动前端
```bash
cd frontend
npm install
npm run dev
```

---

## 🔗 服务地址

| 服务 | 地址 | 说明 |
|------|------|------|
| 前端应用 | http://localhost:3000 | Vue 3 管理后台 |
| 后端 API | http://localhost:8080/api | RESTful API |
| 数据库 | localhost:3309 | MySQL (root/root) |

---

## 🧪 测试账号

系统首次启动会自动初始化以下测试账号：

| 用户名 | 密码 | 角色 | 说明 |
|--------|------|------|------|
| admin | 123456 | ADMIN | 管理员账号 |
| user | 123456 | USER | 普通用户账号 |

---

## 📋 功能列表

### ✅ 用户认证
- [x] 用户登录（用户名/密码）
- [x] 用户注册（自动验证）
- [x] 路由守卫（未登录自动跳转）
- [x] 退出登录
- [x] 用户信息展示

### ✅ 商品管理
- [x] 商品列表展示
- [x] 商品搜索（按名称）
- [x] 添加商品（名称、价格、库存、分类、条形码）
- [x] 编辑商品
- [x] 删除商品（二次确认）
- [x] 表单验证（必填项校验）

### ✅ 分类管理
- [x] 分类列表展示
- [x] 添加分类（名称、描述）
- [x] 删除分类（二次确认）

### ✅ 供应商管理
- [x] 供应商列表展示
- [x] 添加供应商（名称、联系人、电话、地址）
- [x] 编辑供应商
- [x] 删除供应商（二次确认）

### 🎨 UI/UX 特性
- [x] 响应式布局
- [x] 侧边栏收缩/展开
- [x] 渐变 Logo 设计
- [x] 加载状态提示
- [x] 操作成功/失败反馈
- [x] 表单实时验证
- [x] 禁用浏览器自动填充

---

## 🏗 项目结构

```
194/
├── backend/                    # Spring Boot 后端
│   ├── src/
│   │   └── main/
│   │       ├── java/com/supermarket/
│   │       │   ├── model/              # 实体类
│   │       │   │   ├── User.java
│   │       │   │   ├── Product.java
│   │       │   │   ├── Category.java
│   │       │   │   ├── Supplier.java
│   │       │   │   └── PurchaseOrder.java
│   │       │   ├── repository/         # 数据访问层
│   │       │   ├── controller/         # REST 控制器
│   │       │   └── config/             # 配置类
│   │       └── resources/
│   │           └── application.properties
│   ├── pom.xml
│   └── Dockerfile
├── frontend/                   # Vue 3 前端
│   ├── src/
│   │   ├── views/              # 页面组件
│   │   │   ├── Login.vue
│   │   │   ├── ProductList.vue
│   │   │   ├── CategoryManager.vue
│   │   │   └── SupplierManager.vue
│   │   ├── router/             # 路由配置
│   │   ├── api/                # API 封装
│   │   └── App.vue             # 主布局
│   ├── package.json
│   ├── vite.config.js
│   ├── nginx.conf
│   └── Dockerfile
├── docker-compose.yml          # Docker 编排文件
└── README.md
```

---

## 🛡️ 工程特性

### 后端特性
- **分层架构**: Model - Repository - Controller 清晰分层
- **ORM 管理**: 使用 Spring Data JPA，避免原生 SQL
- **自动建表**: JPA `ddl-auto=update` 自动创建/更新表结构
- **数据初始化**: CommandLineRunner 自动初始化演示数据
- **跨域支持**: `@CrossOrigin` 支持前后端分离开发
- **RESTful API**: 标准的 REST 接口设计

### 前端特性
- **Composition API**: Vue 3 最新语法
- **组件化开发**: 页面组件独立，可复用
- **路由守卫**: 自动拦截未登录访问
- **统一错误处理**: Axios 拦截器全局处理 API 错误
- **表单验证**: Ant Design 表单验证规则
- **响应式设计**: 适配不同屏幕尺寸

### 容器化特性
- **多阶段构建**: 优化 Docker 镜像大小
- **服务编排**: Docker Compose 管理服务依赖
- **数据持久化**: MySQL 数据卷持久化
- **健康检查**: 数据库健康检查确保启动顺序

---

## 📊 数据库设计

### 核心表结构

#### users (用户表)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| username | VARCHAR(255) | 用户名（唯一） |
| password | VARCHAR(255) | 密码 |
| role | VARCHAR(50) | 角色（ADMIN/USER） |
| created_at | DATETIME | 创建时间 |

#### products (商品表)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(255) | 商品名称 |
| barcode | VARCHAR(255) | 条形码 |
| price | DECIMAL(10,2) | 价格 |
| stock | INT | 库存 |
| category_id | BIGINT | 分类 ID（外键） |

#### categories (分类表)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(255) | 分类名称 |
| description | TEXT | 描述 |

#### suppliers (供应商表)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(255) | 供应商名称 |
| contact | VARCHAR(255) | 联系人 |
| phone | VARCHAR(50) | 联系电话 |
| address | TEXT | 地址 |

---

## 🔧 开发指南

### 添加新的业务模块

1. **创建实体类** (`backend/src/main/java/com/supermarket/model/`)
2. **创建 Repository** (`backend/src/main/java/com/supermarket/repository/`)
3. **创建 Controller** (`backend/src/main/java/com/supermarket/controller/`)
4. **创建前端页面** (`frontend/src/views/`)
5. **添加路由** (`frontend/src/router/index.js`)
6. **添加菜单项** (`frontend/src/App.vue`)

### 环境变量配置

可以通过环境变量覆盖默认配置：

```yaml
# docker-compose.yml
environment:
  SPRING_DATASOURCE_URL: jdbc:mysql://db:3306/supermarket
  SPRING_DATASOURCE_USERNAME: root
  SPRING_DATASOURCE_PASSWORD: your_password
```

---

## 🐛 常见问题

### 1. Docker 启动失败
**问题**: 端口被占用
**解决**: 检查 3000、8080、3309 端口是否被占用，修改 `docker-compose.yml` 中的端口映射

### 2. 前端无法访问后端 API
**问题**: CORS 跨域错误
**解决**: 确保后端 Controller 添加了 `@CrossOrigin(origins = "*")`

### 3. 数据库连接失败
**问题**: 后端无法连接数据库
**解决**: 等待数据库完全启动（约 10-20 秒），或检查 `application.properties` 配置

### 4. 浏览器自动填充表单
**问题**: 登录表单被浏览器自动填充
**解决**: 已添加 `autocomplete="off"` 属性，清除浏览器缓存即可

---

## 📝 开发日志

### v1.0.0 (2026-01-21)
- ✅ 完成用户认证系统（登录/注册/退出）
- ✅ 完成商品管理模块（CRUD + 搜索）
- ✅ 完成分类管理模块
- ✅ 完成供应商管理模块
- ✅ 优化 Logo 设计和 UI 交互
- ✅ 添加表单验证和错误处理
- ✅ 实现 Docker 一键部署
- ✅ 完善 README 文档

---

## 🤝 贡献指南

欢迎提交 Issue 和 Pull Request！

1. Fork 本项目
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 开启 Pull Request

---

## 📄 许可证

本项目采用 MIT 许可证 - 详见 [LICENSE](LICENSE) 文件

---

## 👨‍💻 作者

开发者：AI Assistant  
项目类型：全栈超市管理系统  
技术栈：Spring Boot + Vue 3 + MySQL + Docker

---

## 🙏 致谢

- [Spring Boot](https://spring.io/projects/spring-boot)
- [Vue.js](https://vuejs.org/)
- [Ant Design Vue](https://antdv.com/)
- [Docker](https://www.docker.com/)
