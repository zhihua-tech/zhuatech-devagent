# DevAgent API

版权所有 © 2026 上海如静知华信息科技有限公司。

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| POST | `/api/auth/login` | 获取 JWT |
| GET | `/api/admin/dashboard` | 研发智能体效能态势 |
| GET | `/api/admin/work-orders` | Agent 研发任务 |
| GET | `/api/shopfloor/dashboard` | 工程师任务工作台 |
| POST | `/api/shopfloor/work-orders/{id}/reports` | 提交评审反馈 |
| POST | `/api/shopfloor/agent-preview` | 运行本地沙箱演示智能体 |
| POST | `/api/shopfloor/change-risk` | 检查测试、安全、数据库变更与回滚证据并执行质量门禁 |
