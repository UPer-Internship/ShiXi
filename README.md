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
## 2025/7/14
本次 PR 新增了 `IconController` 下的六个用户头像管理接口，具体包括：

- **上传用户头像**
    - 接口：`POST /icon/upload`
    - 功能：用户上传头像图片，图片将存储至 OSS，并返回头像访问 URL，并更新至数据库。

- **删除用户头像**
    - 接口：`DELETE /icon/delete`
    - 功能：根据头像 URL 删除指定的头像文件。

- **获取用户当前头像**
    - 接口：`GET /icon/current`
    - 功能：获取当前登录用户的头像信息，包括头像 URL、用户 ID、昵称。

- **更新用户头像信息**
    - 接口：`PUT /icon/update`
    - 功能：更新当前用户的头像信息（如数据库中的头像 URL 字段）（估计不会用这个接口，因为第一个接口已经有了更新数据库的功能，但先写在这里，以免后面要）。

- **检查头像文件是否存在**
    - 接口：`GET /icon/check`
    - 功能：校验指定头像文件在 OSS 上是否存在。

- **获取头像预览 URL**
    - 接口：`GET /icon/preview`
    - 功能：生成头像的临时预览链接（如 1 小时有效），便于用户临时查看头像效果。