# Position模块接口文档

## 1. 岗位收藏相关接口

### 1.1 添加岗位收藏
- **接口路径**: `POST /position/favorite`
- **接口作用**: 用户收藏指定岗位
- **请求参数**:
  - `positionId` (Long): 岗位ID，必填
  - `type` (String): 岗位类型，必填，可选值：正职、兼职、实习
- **请求示例**:
```json
{
  "positionId": 123,
  "type": "正职"
}
```
- **返回示例**:
```json
{
  "code": 200,
  "message": "收藏成功",
  "data": null
}
```

### 1.2 取消岗位收藏
- **接口路径**: `DELETE /position/favorite`
- **接口作用**: 用户取消收藏指定岗位
- **请求参数**:
  - `positionId` (Long): 岗位ID，必填
  - `type` (String): 岗位类型，必填
- **请求示例**:
```json
{
  "positionId": 123,
  "type": "正职"
}
```
- **返回示例**:
```json
{
  "code": 200,
  "message": "取消收藏成功",
  "data": null
}
```

### 1.3 判断是否收藏
- **接口路径**: `GET /position/favorite/isFavorite`
- **接口作用**: 检查用户是否已收藏指定岗位
- **请求参数**:
  - `positionId` (Long): 岗位ID
  - `type` (String): 岗位类型
- **返回示例**:
```json
{
  "code": 200,
  "message": "操作成功",
  "data": true
}
```

### 1.4 分页查询我的收藏
- **接口路径**: `GET /position/favorite/myFavorites`
- **接口作用**: 分页查询当前用户收藏的岗位列表
- **请求参数**:
  - `page` (Integer): 页码，默认1
  - `pageSize` (Integer): 每页大小，默认10
  - `type` (String): 岗位类型，可选
- **返回示例**:
```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "total": 50,
    "records": [
      {
        "id": 123,
        "title": "Java开发工程师",
        "salaryMin": 15000.0,
        "salaryMax": 25000.0,
        "type": "正职",
        "province": "北京市",
        "city": "北京市",
        "category": "后端开发",
        "createTime": "2024-01-01T10:00:00"
      }
    ]
  }
}
```

## 2. 岗位查询相关接口

### 2.1 根据ID和类型查询岗位
- **接口路径**: `GET /position/query/getJobByIdAndType`
- **接口作用**: 根据岗位ID和类型查询岗位详细信息
- **请求参数**:
  - `id` (Long): 岗位ID，必填
  - `type` (String): 岗位类型，必填
- **返回示例**:
```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "id": 123,
    "publisherId": 456,
    "companyId": 789,
    "title": "Java开发工程师",
    "salaryMin": 15000.0,
    "salaryMax": 25000.0,
    "salaryRound": "12薪",
    "mainText": "负责后端系统开发...",
    "province": "北京市",
    "city": "北京市",
    "district": "朝阳区",
    "type": "正职",
    "tag": ["Java", "Spring", "MySQL"],
    "status": 1,
    "category": "后端开发",
    "financingProgress": "B轮",
    "enterpriseScale": "100-500人",
    "industry": "互联网",
    "experienceRequirement": "3-5年工作经验",
    "educationRequirement": "本科及以上学历",
    "createTime": "2024-01-01T10:00:00",
    "updateTime": "2024-01-01T10:00:00"
  }
}
```

### 2.2 分页查询我发布的岗位
- **接口路径**: `GET /position/query/myPublishedJobs`
- **接口作用**: 分页查询当前用户发布的岗位列表
- **请求参数**:
  - `page` (Integer): 页码，默认1
  - `pageSize` (Integer): 每页大小，默认10，最大100
  - `type` (String): 岗位类型，默认"正职"
- **返回示例**:
```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "total": 20,
    "records": [
      {
        "id": 123,
        "title": "Java开发工程师",
        "salaryMin": 15000.0,
        "salaryMax": 25000.0,
        "type": "正职",
        "province": "北京市",
        "city": "北京市",
        "category": "后端开发",
        "status": 1,
        "createTime": "2024-01-01T10:00:00"
      }
    ]
  }
}
```

### 2.3 岗位搜索推荐
- **接口路径**: `GET /position/query/searchJobs`
- **接口作用**: 根据关键词和条件搜索岗位
- **请求参数**:
  - `keyword` (String): 搜索关键词
  - `page` (Integer): 页码，默认1
  - `pageSize` (Integer): 每页大小，默认10
  - `type` (String): 岗位类型，可选
  - `province` (String): 省份，可选
  - `city` (String): 城市，可选
  - `category` (String): 职位分类，可选
  - `salaryMin` (Double): 最低薪资，可选
  - `salaryMax` (Double): 最高薪资，可选
- **返回示例**:
```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "total": 100,
    "records": [
      {
        "id": 123,
        "title": "Java开发工程师",
        "salaryMin": 15000.0,
        "salaryMax": 25000.0,
        "type": "正职",
        "province": "北京市",
        "city": "北京市",
        "category": "后端开发",
        "tag": ["Java", "Spring"]
      }
    ]
  }
}
```

## 3. 全职岗位管理接口

### 3.1 创建全职岗位
- **接口路径**: `POST /position/fulltime/create`
- **接口作用**: 创建新的全职岗位
- **请求参数**:
  - `companyId` (Long): 公司ID，必填
  - `title` (String): 岗位标题，必填，最大100字符
  - `mainText` (String): 岗位描述，必填，最大5000字符
  - `salaryMin` (Double): 最低薪资，可选，单位元
  - `salaryMax` (Double): 最高薪资，可选，单位元
  - `salaryRound` (String): 年发薪次数，可选
  - `province` (String): 省份，必填
  - `city` (String): 城市，必填
  - `district` (String): 区县，可选
  - `type` (String): 工作类型，必填，固定值"正职"
  - `category` (String): 职位分类，必填
  - `tag` (List<String>): 标签列表，可选
  - `experienceRequirement` (String): 经验要求，可选，最大200字符
  - `educationRequirement` (String): 学历要求，可选，最大100字符
- **请求示例**:
```json
{
  "companyId": 789,
  "title": "Java开发工程师",
  "mainText": "负责后端系统开发，要求熟悉Java、Spring框架...",
  "salaryMin": 15000.0,
  "salaryMax": 25000.0,
  "salaryRound": "12薪",
  "province": "北京市",
  "city": "北京市",
  "district": "朝阳区",
  "type": "正职",
  "category": "后端开发",
  "tag": ["Java", "Spring", "MySQL"]
}
```
- **返回示例**:
```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "id": 123,
    "publisherId": 456,
    "companyId": 789,
    "title": "Java开发工程师",
    "salaryMin": 15000.0,
    "salaryMax": 25000.0,
    "salaryRound": "12薪",
    "mainText": "负责后端系统开发...",
    "province": "北京市",
    "city": "北京市",
    "district": "朝阳区",
    "type": "正职",
    "tag": ["Java", "Spring", "MySQL"],
    "status": 1,
    "category": "后端开发",
    "experienceRequirement": "3-5年Java开发经验，熟悉Spring框架",
    "educationRequirement": "本科及以上学历，计算机相关专业优先",
    "createTime": "2024-01-01T10:00:00",
    "updateTime": "2024-01-01T10:00:00"
  }
}
```

### 3.2 更新全职岗位
- **接口路径**: `PUT /position/fulltime/update`
- **接口作用**: 更新已存在的全职岗位信息
- **请求参数**:
  - `id` (Long): 岗位ID，必填
  - 其他字段同创建接口，均为可选
- **请求示例**:
```json
{
  "id": 123,
  "title": "高级Java开发工程师",
  "salaryMin": 20000.0,
  "salaryMax": 30000.0,
  "experienceRequirement": "5-8年Java开发经验",
  "educationRequirement": "本科及以上学历"
}
```
- **返回示例**:
```json
{
  "code": 200,
  "message": "更新成功",
  "data": {
    "id": 123,
    "title": "高级Java开发工程师",
    "salaryMin": 20000.0,
    "salaryMax": 30000.0,
    "updateTime": "2024-01-02T10:00:00"
  }
}
```

### 3.3 删除全职岗位
- **接口路径**: `DELETE /position/fulltime/delete`
- **接口作用**: 删除指定的全职岗位（逻辑删除）
- **请求参数**:
  - `id` (Long): 岗位ID，路径参数
- **返回示例**:
```json
{
  "code": 200,
  "message": "删除成功",
  "data": null
}
```

## 4. 实习岗位管理接口

### 4.1 创建实习岗位
- **接口路径**: `POST /position/internship/create`
- **接口作用**: 创建新的实习岗位
- **请求参数**: 同全职岗位，但type固定为"实习"
- **请求示例**:
```json
{
  "companyId": 789,
  "title": "Java实习生",
  "mainText": "协助开发团队进行后端开发工作...",
  "salaryMin": 3000.0,
  "salaryMax": 5000.0,
  "province": "北京市",
  "city": "北京市",
  "type": "实习",
  "category": "后端开发",
  "tag": ["Java", "实习"],
  "experienceRequirement": "无工作经验要求，有相关项目经验优先",
  "educationRequirement": "大专及以上学历，在校学生优先"
}
```
- **返回示例**: 同全职岗位返回格式

### 4.2 更新实习岗位
- **接口路径**: `PUT /position/internship/update`
- **接口作用**: 更新已存在的实习岗位信息
- **请求参数**: 同全职岗位更新接口

### 4.3 删除实习岗位
- **接口路径**: `DELETE /position/internship/delete`
- **接口作用**: 删除指定的实习岗位（逻辑删除）

## 5. 兼职岗位管理接口

### 5.1 创建兼职岗位
- **接口路径**: `POST /position/parttime/create`
- **接口作用**: 创建新的兼职岗位
- **请求参数**: 同全职岗位，但type可为"兼职"或"实习"
- **请求示例**:
```json
{
  "companyId": 789,
  "title": "前端兼职开发",
  "mainText": "负责移动端H5页面开发...",
  "salaryMin": 200.0,
  "salaryMax": 500.0,
  "province": "上海市",
  "city": "上海市",
  "type": "兼职",
  "category": "前端开发",
  "tag": ["Vue", "H5", "兼职"],
  "experienceRequirement": "1-3年前端开发经验",
  "educationRequirement": "大专及以上学历"
}
```
- **返回示例**: 同全职岗位返回格式

### 5.2 更新兼职岗位
- **接口路径**: `PUT /position/parttime/update`
- **接口作用**: 更新已存在的兼职岗位信息

### 5.3 删除兼职岗位
- **接口路径**: `DELETE /position/parttime/delete`
- **接口作用**: 删除指定的兼职岗位（逻辑删除）

## 6. 通用返回格式说明

### 6.1 成功响应
```json
{
  "code": 200,
  "message": "操作成功",
  "data": {}
}
```

### 6.2 失败响应
```json
{
  "code": 400,
  "message": "参数错误",
  "data": null
}
```

### 6.3 分页响应
```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "total": 100,
    "records": []
  }
}
```

## 7. 字段说明

- **id**: 岗位唯一标识
- **publisherId**: 发布者用户ID（即当前登录用户的id）
- **companyId**: 关联公司ID
- **title**: 岗位标题，最大100字符
- **salaryMin/salaryMax**: 薪资范围，单位元，无上限限制
- **salaryRound**: 年发薪次数（如12薪、13薪）
- **mainText**: 岗位详细描述，最大5000字符
- **province/city/district**: 工作地点（省、市、区）
- **type**: 岗位类型（正职/兼职/实习）
- **category**: 职位分类（如后端开发、前端开发等）
- **tag**: 技能标签列表
- **status**: 岗位状态（0-不可见，1-可见）
- **financingProgress**: 公司融资进度
- **enterpriseScale**: 企业规模
- **industry**: 所属行业
- **experienceRequirement**: 经验要求，最大200字符
- **educationRequirement**: 学历要求，最大100字符
- **createTime/updateTime**: 创建/更新时间