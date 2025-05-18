# 日志
## 2025/05/17 次提交
1. 新增studentcontorller功能两个：发送验证码和使用验证码登录
2. 新增util包下多个工具类：两个拦截器配置类类（可以去看代码看不懂问我）
3. util：两个regex类负责防御性编程，校验前端数据的合法性
4. util：新增studentholder类负责将学生基础信息缓存在threallocal中方便返回前端
5. config：一个拦截器，负责调用两个拦截器配置类
---
## 2025/05/18 陈永川
主要做了如下事情
1. 新增一个controller类`OnlineResumeController`，该类负责处理在线简历的请求
2. 新增两个实体类`OnlineResume`和`ResumeExperience`,分别用于存储在线简历中的基本信息和工作经历，项目经历等信息
3. 新增两张表 在线简历表`online_resume`和在线简历经历表`resume_experience`对应上述的两个新增实体类，建表语句详见`\src\main\resources\SQL\init.sql`的后两张表
4. 新建了上述两张表对应的Mapper类
5. 完成了获取在线简历的接口，详情见`\src\main\resources\document\api文档.md`里的内容
6. 整理了项目至今用到的数据库表建表语句，一共四张，在上面说的`init.sql`里，后续有建表可以在里面加，方便开发