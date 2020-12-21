# 在线教育
## 项目概述
（1）
在线教育系统，分为前台网站系统和后台运营平台。
前台用户系统包括课程、问答、文章三大部分，使用了微服务技术架构，前后端分离开发。
后端的主要技术架构是：SpringBoot + SpringCloud + MyBatis-Plus + HttpClient + MySQL +  Docker + Maven，
前端的架构是：Node.js + Vue.js 

其他涉及到的中间件包括Redis、ActiveMQ、阿里云OSS、视频点播
业务中使用了ECharts做图表展示，使用POI完成用户信息批量上传、注册
分布式单点登录使用了JWT

（2）
项目前后端分离开发，后端采用SpringCloud微服务架构，持久层用的是MyBatis-Plus，微服务分库设计，使用Swagger生成接口文档
接入了阿里云视频点播、阿里云OSS。
系统分为前台用户系统和后台管理系统两部分。
前台用户系统包括课程、问答、文章。
后台管理系统包括：系统管理、内容管理、社群问答、课程管理、消息中心、用户中心、统计分析、系统配置等功能。

### 准备重构成SpringCloudAlibaba微服务...
