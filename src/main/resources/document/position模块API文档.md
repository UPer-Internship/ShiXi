# Position模块API文档

**简介**: 岗位管理模块API文档，包含岗位查询、收藏、全职岗位、兼职岗位、实习岗位相关接口

**HOST**: localhost:8081

**Version**: 1.0

[TOC]

# 岗位查询相关接口

## 根据id和type查询Job

**接口地址**: `/position/query/getJobByIdAndType`

**请求方式**: `GET`

**请求数据类型**: `application/x-www-form-urlencoded`

**响应数据类型**: `application/json`

**接口描述**: 根据id和type查询Job，主要用于查询岗位收藏时的详情

**请求参数**:

| 参数名称 | 参数说明 | 请求类型 | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
| id | 岗位ID | query | true | integer(int64) | |
| type | 岗位类型：正职、兼职、实习 | query | true | string | |

**响应状态**:

| 状态码 | 说明 | schema |
| -------- | -------- | ----- |
| 200 | OK | Result |
| 401 | Unauthorized | |
| 403 | Forbidden | |
| 404 | Not Found | |

**响应参数**:

| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- | ----- |
| data | 岗位详情数据 | JobVO | JobVO |
| errorMsg | 错误信息 | string | |
| success | 是否成功 | boolean | |
| total | 总数 | integer(int64) | integer(int64) |

**响应示例**:
```javascript
{
  "data": {
    "id": 1,
    "publisherId": 123,
    "companyId": 456,
    "title": "Java开发工程师",
    "salaryMin": 8.0,
    "salaryMax": 15.0,
    "salaryRound": "12薪",
    "mainText": "岗位详细描述...",
    "province": "北京市",
    "city": "北京市",
    "district": "朝阳区",
    "type": "正职",
    "tag": ["Java", "Spring", "MySQL"],
    "status": 1,
    "category": "技术",
    "label": "热门",
    "financingProgress": "A轮",
    "enterpriseScale": "100-500人",
    "industry": "互联网",
    "createTime": "2024-01-01T10:00:00",
    "updateTime": "2024-01-01T10:00:00"
  },
  "errorMsg": "",
  "success": true,
  "total": 1
}
```

## 分类分页查询我发布的岗位

**接口地址**: `/position/query/myPublishedJobs`

**请求方式**: `GET`

**请求数据类型**: `application/x-www-form-urlencoded`

**响应数据类型**: `application/json`

**接口描述**: 分类分页查询当前用户发布的岗位

**请求参数**:

| 参数名称 | 参数说明 | 请求类型 | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
| page | 页码 | query | false | integer | 默认值：1 |
| pageSize | 每页大小 | query | false | integer | 默认值：10 |
| type | 岗位类型：正职、兼职、实习 | query | false | string | 默认值：正职 |

**响应状态**:

| 状态码 | 说明 | schema |
| -------- | -------- | ----- |
| 200 | OK | Result |
| 401 | Unauthorized | |
| 403 | Forbidden | |
| 404 | Not Found | |

**响应参数**:

| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- | ----- |
| data | 分页数据 | PageResult | PageResult |
| &emsp;&emsp;total | 总记录数 | integer(int64) | |
| &emsp;&emsp;records | 岗位列表 | array[JobVO] | JobVO |
| errorMsg | 错误信息 | string | |
| success | 是否成功 | boolean | |

**响应示例**:
```javascript
{
  "data": {
    "total": 25,
    "records": [
      {
        "id": 1,
        "publisherId": 123,
        "companyId": 456,
        "title": "Java开发工程师",
        "salaryMin": 8.0,
        "salaryMax": 15.0,
        "type": "正职",
        "province": "北京市",
        "city": "北京市",
        "category": "技术",
        "createTime": "2024-01-01T10:00:00"
      }
    ]
  },
  "errorMsg": "",
  "success": true
}
```

## 分类分页模糊搜索岗位推荐

**接口地址**: `/position/query/searchJobs`

**请求方式**: `GET`

**请求数据类型**: `application/x-www-form-urlencoded`

**响应数据类型**: `application/json`

**接口描述**: 分类分页模糊搜索岗位推荐，支持多条件筛选

**请求参数**:

| 参数名称 | 参数说明 | 请求类型 | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
| keyword | 搜索关键词 | query | false | string | |
| page | 页码 | query | false | integer | 默认值：1 |
| pageSize | 每页大小 | query | false | integer | 默认值：10 |
| type | 岗位类型：正职、兼职、实习 | query | false | string | |
| province | 省份筛选 | query | false | string | |
| city | 城市筛选 | query | false | string | |
| category | 职位分类筛选 | query | false | string | |
| salaryMin | 最低薪资 | query | false | number(double) | |
| salaryMax | 最高薪资 | query | false | number(double) | |

**响应状态**:

| 状态码 | 说明 | schema |
| -------- | -------- | ----- |
| 200 | OK | Result |
| 401 | Unauthorized | |
| 403 | Forbidden | |
| 404 | Not Found | |

**响应参数**:

| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- | ----- |
| data | 分页数据 | PageResult | PageResult |
| &emsp;&emsp;total | 总记录数 | integer(int64) | |
| &emsp;&emsp;records | 岗位列表 | array[JobVO] | JobVO |
| errorMsg | 错误信息 | string | |
| success | 是否成功 | boolean | |

**响应示例**:
```javascript
{
  "data": {
    "total": 100,
    "records": [
      {
        "id": 1,
        "title": "Java开发工程师",
        "salaryMin": 8.0,
        "salaryMax": 15.0,
        "type": "正职",
        "province": "北京市",
        "city": "北京市",
        "category": "技术",
        "tag": ["Java", "Spring"]
      }
    ]
  },
  "errorMsg": "",
  "success": true
}
```

# 岗位收藏相关接口

## 添加岗位收藏

**接口地址**: `/position/favorite`

**请求方式**: `POST`

**请求数据类型**: `application/x-www-form-urlencoded`

**响应数据类型**: `application/json`

**接口描述**: 添加岗位收藏

**请求参数**:

| 参数名称 | 参数说明 | 请求类型 | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
| positionId | 岗位ID | query | true | integer(int64) | |
| type | 岗位类型：正职、兼职、实习 | query | true | string | |

**响应状态**:

| 状态码 | 说明 | schema |
| -------- | -------- | ----- |
| 200 | OK | Result |
| 401 | Unauthorized | |
| 403 | Forbidden | |
| 404 | Not Found | |

**响应参数**:

| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- | ----- |
| data | 返回数据 | object | |
| errorMsg | 错误信息 | string | |
| success | 是否成功 | boolean | |
| total | 总数 | integer(int64) | integer(int64) |

**响应示例**:
```javascript
{
  "data": null,
  "errorMsg": "",
  "success": true,
  "total": 0
}
```

## 取消岗位收藏

**接口地址**: `/position/favorite`

**请求方式**: `DELETE`

**请求数据类型**: `application/x-www-form-urlencoded`

**响应数据类型**: `application/json`

**接口描述**: 取消岗位收藏

**请求参数**:

| 参数名称 | 参数说明 | 请求类型 | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
| positionId | 岗位ID | query | true | integer(int64) | |
| type | 岗位类型：正职、兼职、实习 | query | true | string | |

**响应状态**:

| 状态码 | 说明 | schema |
| -------- | -------- | ----- |
| 200 | OK | Result |
| 401 | Unauthorized | |
| 403 | Forbidden | |
| 404 | Not Found | |

**响应参数**:

| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- | ----- |
| data | 返回数据 | object | |
| errorMsg | 错误信息 | string | |
| success | 是否成功 | boolean | |
| total | 总数 | integer(int64) | integer(int64) |

**响应示例**:
```javascript
{
  "data": null,
  "errorMsg": "",
  "success": true,
  "total": 0
}
```

## 判断是否已收藏

**接口地址**: `/position/favorite/isFavorite`

**请求方式**: `GET`

**请求数据类型**: `application/x-www-form-urlencoded`

**响应数据类型**: `application/json`

**接口描述**: 判断是否已收藏指定岗位

**请求参数**:

| 参数名称 | 参数说明 | 请求类型 | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
| positionId | 岗位ID | query | true | integer(int64) | |
| type | 岗位类型：正职、兼职、实习 | query | true | string | |

**响应状态**:

| 状态码 | 说明 | schema |
| -------- | -------- | ----- |
| 200 | OK | Result |
| 401 | Unauthorized | |
| 403 | Forbidden | |
| 404 | Not Found | |

**响应参数**:

| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- | ----- |
| data | 是否收藏 | boolean | |
| errorMsg | 错误信息 | string | |
| success | 是否成功 | boolean | |
| total | 总数 | integer(int64) | integer(int64) |

**响应示例**:
```javascript
{
  "data": true,
  "errorMsg": "",
  "success": true,
  "total": 0
}
```

## 分页查询当前用户收藏的岗位

**接口地址**: `/position/favorite/myFavorites`

**请求方式**: `GET`

**请求数据类型**: `application/x-www-form-urlencoded`

**响应数据类型**: `application/json`

**接口描述**: 分页查询当前用户收藏的岗位

**请求参数**:

| 参数名称 | 参数说明 | 请求类型 | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
| page | 页码 | query | false | integer | 默认值：1 |
| pageSize | 每页大小 | query | false | integer | 默认值：10 |
| type | 岗位类型：正职、兼职、实习 | query | false | string | |

**响应状态**:

| 状态码 | 说明 | schema |
| -------- | -------- | ----- |
| 200 | OK | Result |
| 401 | Unauthorized | |
| 403 | Forbidden | |
| 404 | Not Found | |

**响应参数**:

| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- | ----- |
| data | 分页数据 | PageResult | PageResult |
| &emsp;&emsp;total | 总记录数 | integer(int64) | |
| &emsp;&emsp;records | 岗位列表 | array[JobVO] | JobVO |
| errorMsg | 错误信息 | string | |
| success | 是否成功 | boolean | |

**响应示例**:
```javascript
{
  "data": {
    "total": 5,
    "records": [
      {
        "id": 1,
        "title": "Java开发工程师",
        "salaryMin": 8.0,
        "salaryMax": 15.0,
        "type": "正职",
        "province": "北京市",
        "city": "北京市",
        "category": "技术"
      }
    ]
  },
  "errorMsg": "",
  "success": true
}
```

# 全职岗位相关接口

## 创建全职岗位

**接口地址**: `/position/fulltime/create`

**请求方式**: `POST`

**请求数据类型**: `application/json`

**响应数据类型**: `application/json`

**接口描述**: 创建全职岗位

**请求示例**:
```javascript
{
  "companyId": 456,
  "title": "Java开发工程师",
  "salaryMin": 8.0,
  "salaryMax": 15.0,
  "salaryRound": "12薪",
  "mainText": "岗位详细描述...",
  "province": "北京市",
  "city": "北京市",
  "district": "朝阳区",
  "type": "正职",
  "tag": ["Java", "Spring", "MySQL"],
  "category": "技术",
  "financingProgress": "A轮",
  "enterpriseScale": "100-500人",
  "industry": "互联网"
}
```

**请求参数**:

| 参数名称 | 参数说明 | 请求类型 | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
| createDTO | 创建全职岗位请求体 | body | true | JobFullTimeCreateDTO | JobFullTimeCreateDTO |
| &emsp;&emsp;companyId | 关联的公司id | | true | integer(int64) | |
| &emsp;&emsp;title | 标题 | | true | string | |
| &emsp;&emsp;salaryMin | 薪水下限（单位K） | | false | number(double) | |
| &emsp;&emsp;salaryMax | 薪水上限（单位K） | | false | number(double) | |
| &emsp;&emsp;salaryRound | 年发薪次数 | | false | string | |
| &emsp;&emsp;mainText | 正文 | | true | string | |
| &emsp;&emsp;province | 公司所在省份 | | true | string | |
| &emsp;&emsp;city | 城市 | | true | string | |
| &emsp;&emsp;district | 区县 | | false | string | |
| &emsp;&emsp;type | 正职/兼职/实习 | | true | string | |
| &emsp;&emsp;tag | 标签列表 | | false | array[string] | |
| &emsp;&emsp;category | 职位 | | true | string | |
| &emsp;&emsp;financingProgress | 融资进度 | | false | string | |
| &emsp;&emsp;enterpriseScale | 企业规模 | | false | string | |
| &emsp;&emsp;industry | 企业所在行业 | | false | string | |

**响应状态**:

| 状态码 | 说明 | schema |
| -------- | -------- | ----- |
| 200 | OK | Result |
| 401 | Unauthorized | |
| 403 | Forbidden | |
| 404 | Not Found | |

**响应参数**:

| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- | ----- |
| data | 返回数据 | object | |
| errorMsg | 错误信息 | string | |
| success | 是否成功 | boolean | |
| total | 总数 | integer(int64) | integer(int64) |

**响应示例**:
```javascript
{
  "data": null,
  "errorMsg": "",
  "success": true,
  "total": 0
}
```

## 更新全职岗位

**接口地址**: `/position/fulltime/update`

**请求方式**: `PUT`

**请求数据类型**: `application/json`

**响应数据类型**: `application/json`

**接口描述**: 更新全职岗位

**请求示例**:
```javascript
{
  "id": 1,
  "companyId": 456,
  "title": "高级Java开发工程师",
  "salaryMin": 10.0,
  "salaryMax": 20.0,
  "salaryRound": "13薪",
  "mainText": "更新后的岗位详细描述...",
  "province": "北京市",
  "city": "北京市",
  "district": "朝阳区",
  "type": "正职",
  "tag": ["Java", "Spring Boot", "Redis"],
  "status": 1,
  "category": "技术",
  "financingProgress": "B轮",
  "enterpriseScale": "500-1000人",
  "industry": "互联网"
}
```

**请求参数**:

| 参数名称 | 参数说明 | 请求类型 | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
| updateDTO | 更新全职岗位请求体 | body | true | JobFullTimeUpdateDTO | JobFullTimeUpdateDTO |
| &emsp;&emsp;id | 岗位id | | true | integer(int64) | |
| &emsp;&emsp;companyId | 关联的公司id | | false | integer(int64) | |
| &emsp;&emsp;title | 标题 | | false | string | |
| &emsp;&emsp;salaryMin | 薪水下限（单位K） | | false | number(double) | |
| &emsp;&emsp;salaryMax | 薪水上限（单位K） | | false | number(double) | |
| &emsp;&emsp;salaryRound | 年发薪次数 | | false | string | |
| &emsp;&emsp;mainText | 正文 | | false | string | |
| &emsp;&emsp;province | 公司所在省份 | | false | string | |
| &emsp;&emsp;city | 城市 | | false | string | |
| &emsp;&emsp;district | 区县 | | false | string | |
| &emsp;&emsp;type | 正职/兼职/实习 | | false | string | |
| &emsp;&emsp;tag | 标签列表 | | false | array[string] | |
| &emsp;&emsp;status | 状态 0/1 可见/不可见 | | false | integer | |
| &emsp;&emsp;category | 职位 | | false | string | |
| &emsp;&emsp;financingProgress | 融资进度 | | false | string | |
| &emsp;&emsp;enterpriseScale | 企业规模 | | false | string | |
| &emsp;&emsp;industry | 企业所在行业 | | false | string | |

**响应状态**:

| 状态码 | 说明 | schema |
| -------- | -------- | ----- |
| 200 | OK | Result |
| 401 | Unauthorized | |
| 403 | Forbidden | |
| 404 | Not Found | |

**响应参数**:

| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- | ----- |
| data | 返回数据 | object | |
| errorMsg | 错误信息 | string | |
| success | 是否成功 | boolean | |
| total | 总数 | integer(int64) | integer(int64) |

**响应示例**:
```javascript
{
  "data": null,
  "errorMsg": "",
  "success": true,
  "total": 0
}
```

## 删除全职岗位

**接口地址**: `/position/fulltime/delete`

**请求方式**: `DELETE`

**请求数据类型**: `application/x-www-form-urlencoded`

**响应数据类型**: `application/json`

**接口描述**: 删除全职岗位

**请求参数**:

| 参数名称 | 参数说明 | 请求类型 | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
| id | 岗位id | query | true | integer(int64) | |

**响应状态**:

| 状态码 | 说明 | schema |
| -------- | -------- | ----- |
| 200 | OK | Result |
| 401 | Unauthorized | |
| 403 | Forbidden | |
| 404 | Not Found | |

**响应参数**:

| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- | ----- |
| data | 返回数据 | object | |
| errorMsg | 错误信息 | string | |
| success | 是否成功 | boolean | |
| total | 总数 | integer(int64) | integer(int64) |

**响应示例**:
```javascript
{
  "data": null,
  "errorMsg": "",
  "success": true,
  "total": 0
}
```

# 兼职岗位相关接口

## 创建兼职岗位

**接口地址**: `/position/parttime/create`

**请求方式**: `POST`

**请求数据类型**: `application/json`

**响应数据类型**: `application/json`

**接口描述**: 创建兼职岗位

**请求示例**:
```javascript
{
  "companyId": 456,
  "title": "前端开发兼职",
  "salaryMin": 100.0,
  "salaryMax": 200.0,
  "mainText": "兼职岗位详细描述...",
  "province": "上海市",
  "city": "上海市",
  "district": "浦东新区",
  "type": "兼职",
  "tag": ["Vue", "React", "JavaScript"],
  "category": "技术",
  "financingProgress": "天使轮",
  "enterpriseScale": "50-100人",
  "industry": "互联网"
}
```

**请求参数**:

| 参数名称 | 参数说明 | 请求类型 | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
| createDTO | 创建兼职岗位请求体 | body | true | JobPartTimeCreateDTO | JobPartTimeCreateDTO |
| &emsp;&emsp;companyId | 关联的公司id | | true | integer(int64) | |
| &emsp;&emsp;title | 标题 | | true | string | |
| &emsp;&emsp;salaryMin | 薪水下限（单位元） | | false | number(double) | |
| &emsp;&emsp;salaryMax | 薪水上限（单位元） | | false | number(double) | |
| &emsp;&emsp;mainText | 正文 | | true | string | |
| &emsp;&emsp;province | 公司所在省份 | | true | string | |
| &emsp;&emsp;city | 城市 | | true | string | |
| &emsp;&emsp;district | 区县 | | false | string | |
| &emsp;&emsp;type | 正职/兼职/实习 | | true | string | |
| &emsp;&emsp;tag | 标签列表 | | false | array[string] | |
| &emsp;&emsp;category | 职位 | | true | string | |
| &emsp;&emsp;financingProgress | 融资进度 | | false | string | |
| &emsp;&emsp;enterpriseScale | 企业规模 | | false | string | |
| &emsp;&emsp;industry | 企业所在行业 | | false | string | |

**响应状态**:

| 状态码 | 说明 | schema |
| -------- | -------- | ----- |
| 200 | OK | Result |
| 401 | Unauthorized | |
| 403 | Forbidden | |
| 404 | Not Found | |

**响应参数**:

| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- | ----- |
| data | 返回数据 | object | |
| errorMsg | 错误信息 | string | |
| success | 是否成功 | boolean | |
| total | 总数 | integer(int64) | integer(int64) |

**响应示例**:
```javascript
{
  "data": null,
  "errorMsg": "",
  "success": true,
  "total": 0
}
```

## 更新兼职岗位

**接口地址**: `/position/parttime/update`

**请求方式**: `PUT`

**请求数据类型**: `application/json`

**响应数据类型**: `application/json`

**接口描述**: 更新兼职岗位

**请求示例**:
```javascript
{
  "id": 1,
  "companyId": 456,
  "title": "高级前端开发兼职",
  "salaryMin": 150.0,
  "salaryMax": 300.0,
  "mainText": "更新后的兼职岗位详细描述...",
  "province": "上海市",
  "city": "上海市",
  "district": "浦东新区",
  "type": "兼职",
  "tag": ["Vue3", "TypeScript", "Node.js"],
  "status": 1,
  "category": "技术",
  "financingProgress": "A轮",
  "enterpriseScale": "100-200人",
  "industry": "互联网"
}
```

**请求参数**:

| 参数名称 | 参数说明 | 请求类型 | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
| updateDTO | 更新兼职岗位请求体 | body | true | JobPartTimeUpdateDTO | JobPartTimeUpdateDTO |
| &emsp;&emsp;id | 岗位id | | true | integer(int64) | |
| &emsp;&emsp;companyId | 关联的公司id | | false | integer(int64) | |
| &emsp;&emsp;title | 标题 | | false | string | |
| &emsp;&emsp;salaryMin | 薪水下限（单位元） | | false | number(double) | |
| &emsp;&emsp;salaryMax | 薪水上限（单位元） | | false | number(double) | |
| &emsp;&emsp;mainText | 正文 | | false | string | |
| &emsp;&emsp;province | 公司所在省份 | | false | string | |
| &emsp;&emsp;city | 城市 | | false | string | |
| &emsp;&emsp;district | 区县 | | false | string | |
| &emsp;&emsp;type | 正职/兼职/实习 | | false | string | |
| &emsp;&emsp;tag | 标签列表 | | false | array[string] | |
| &emsp;&emsp;status | 状态 0/1 可见/不可见 | | false | integer | |
| &emsp;&emsp;category | 职位 | | false | string | |
| &emsp;&emsp;financingProgress | 融资进度 | | false | string | |
| &emsp;&emsp;enterpriseScale | 企业规模 | | false | string | |
| &emsp;&emsp;industry | 企业所在行业 | | false | string | |

**响应状态**:

| 状态码 | 说明 | schema |
| -------- | -------- | ----- |
| 200 | OK | Result |
| 401 | Unauthorized | |
| 403 | Forbidden | |
| 404 | Not Found | |

**响应参数**:

| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- | ----- |
| data | 返回数据 | object | |
| errorMsg | 错误信息 | string | |
| success | 是否成功 | boolean | |
| total | 总数 | integer(int64) | integer(int64) |

**响应示例**:
```javascript
{
  "data": null,
  "errorMsg": "",
  "success": true,
  "total": 0
}
```

## 删除兼职岗位

**接口地址**: `/position/parttime/delete`

**请求方式**: `DELETE`

**请求数据类型**: `application/x-www-form-urlencoded`

**响应数据类型**: `application/json`

**接口描述**: 删除兼职岗位

**请求参数**:

| 参数名称 | 参数说明 | 请求类型 | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
| id | 岗位id | query | true | integer(int64) | |

**响应状态**:

| 状态码 | 说明 | schema |
| -------- | -------- | ----- |
| 200 | OK | Result |
| 401 | Unauthorized | |
| 403 | Forbidden | |
| 404 | Not Found | |

**响应参数**:

| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- | ----- |
| data | 返回数据 | object | |
| errorMsg | 错误信息 | string | |
| success | 是否成功 | boolean | |
| total | 总数 | integer(int64) | integer(int64) |

**响应示例**:
```javascript
{
  "data": null,
  "errorMsg": "",
  "success": true,
  "total": 0
}
```

# 实习岗位相关接口

## 创建实习岗位

**接口地址**: `/position/internship/create`

**请求方式**: `POST`

**请求数据类型**: `application/json`

**响应数据类型**: `application/json`

**接口描述**: 创建实习岗位

**请求示例**:
```javascript
{
  "companyId": 456,
  "title": "Java开发实习生",
  "salaryMin": 80.0,
  "salaryMax": 150.0,
  "mainText": "实习岗位详细描述...",
  "province": "广东省",
  "city": "深圳市",
  "district": "南山区",
  "type": "实习",
  "tag": ["Java", "Spring", "学习能力强"],
  "category": "技术",
  "financingProgress": "B轮",
  "enterpriseScale": "200-500人",
  "industry": "互联网"
}
```

**请求参数**:

| 参数名称 | 参数说明 | 请求类型 | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
| createDTO | 创建实习岗位请求体 | body | true | JobInternshipCreateDTO | JobInternshipCreateDTO |
| &emsp;&emsp;companyId | 关联的公司id | | true | integer(int64) | |
| &emsp;&emsp;title | 标题 | | true | string | |
| &emsp;&emsp;salaryMin | 薪水下限（单位元） | | false | number(double) | |
| &emsp;&emsp;salaryMax | 薪水上限（单位元） | | false | number(double) | |
| &emsp;&emsp;mainText | 正文 | | true | string | |
| &emsp;&emsp;province | 公司所在省份 | | true | string | |
| &emsp;&emsp;city | 城市 | | true | string | |
| &emsp;&emsp;district | 区县 | | false | string | |
| &emsp;&emsp;type | 正职/兼职/实习 | | true | string | |
| &emsp;&emsp;tag | 标签列表 | | false | array[string] | |
| &emsp;&emsp;category | 职位 | | true | string | |
| &emsp;&emsp;financingProgress | 融资进度 | | false | string | |
| &emsp;&emsp;enterpriseScale | 企业规模 | | false | string | |
| &emsp;&emsp;industry | 企业所在行业 | | false | string | |

**响应状态**:

| 状态码 | 说明 | schema |
| -------- | -------- | ----- |
| 200 | OK | Result |
| 401 | Unauthorized | |
| 403 | Forbidden | |
| 404 | Not Found | |

**响应参数**:

| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- | ----- |
| data | 返回数据 | object | |
| errorMsg | 错误信息 | string | |
| success | 是否成功 | boolean | |
| total | 总数 | integer(int64) | integer(int64) |

**响应示例**:
```javascript
{
  "data": null,
  "errorMsg": "",
  "success": true,
  "total": 0
}
```

## 更新实习岗位

**接口地址**: `/position/internship/update`

**请求方式**: `PUT`

**请求数据类型**: `application/json`

**响应数据类型**: `application/json`

**接口描述**: 更新实习岗位

**请求示例**:
```javascript
{
  "id": 1,
  "companyId": 456,
  "title": "高级Java开发实习生",
  "salaryMin": 100.0,
  "salaryMax": 200.0,
  "mainText": "更新后的实习岗位详细描述...",
  "province": "广东省",
  "city": "深圳市",
  "district": "南山区",
  "type": "实习",
  "tag": ["Java", "Spring Boot", "微服务"],
  "status": 1,
  "category": "技术",
  "financingProgress": "C轮",
  "enterpriseScale": "500-1000人",
  "industry": "互联网"
}
```

**请求参数**:

| 参数名称 | 参数说明 | 请求类型 | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
| updateDTO | 更新实习岗位请求体 | body | true | JobInternshipUpdateDTO | JobInternshipUpdateDTO |
| &emsp;&emsp;id | 岗位id | | true | integer(int64) | |
| &emsp;&emsp;companyId | 关联的公司id | | false | integer(int64) | |
| &emsp;&emsp;title | 标题 | | false | string | |
| &emsp;&emsp;salaryMin | 薪水下限（单位元） | | false | number(double) | |
| &emsp;&emsp;salaryMax | 薪水上限（单位元） | | false | number(double) | |
| &emsp;&emsp;mainText | 正文 | | false | string | |
| &emsp;&emsp;province | 公司所在省份 | | false | string | |
| &emsp;&emsp;city | 城市 | | false | string | |
| &emsp;&emsp;district | 区县 | | false | string | |
| &emsp;&emsp;type | 正职/兼职/实习 | | false | string | |
| &emsp;&emsp;tag | 标签列表 | | false | array[string] | |
| &emsp;&emsp;status | 状态 0/1 可见/不可见 | | false | integer | |
| &emsp;&emsp;category | 职位 | | false | string | |
| &emsp;&emsp;financingProgress | 融资进度 | | false | string | |
| &emsp;&emsp;enterpriseScale | 企业规模 | | false | string | |
| &emsp;&emsp;industry | 企业所在行业 | | false | string | |

**响应状态**:

| 状态码 | 说明 | schema |
| -------- | -------- | ----- |
| 200 | OK | Result |
| 401 | Unauthorized | |
| 403 | Forbidden | |
| 404 | Not Found | |

**响应参数**:

| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- | ----- |
| data | 返回数据 | object | |
| errorMsg | 错误信息 | string | |
| success | 是否成功 | boolean | |
| total | 总数 | integer(int64) | integer(int64) |

**响应示例**:
```javascript
{
  "data": null,
  "errorMsg": "",
  "success": true,
  "total": 0
}
```

## 删除实习岗位

**接口地址**: `/position/internship/delete`

**请求方式**: `DELETE`

**请求数据类型**: `application/x-www-form-urlencoded`

**响应数据类型**: `application/json`

**接口描述**: 删除实习岗位

**请求参数**:

| 参数名称 | 参数说明 | 请求类型 | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
| id | 岗位id | query | true | integer(int64) | |

**响应状态**:

| 状态码 | 说明 | schema |
| -------- | -------- | ----- |
| 200 | OK | Result |
| 401 | Unauthorized | |
| 403 | Forbidden | |
| 404 | Not Found | |

**响应参数**:

| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- | ----- |
| data | 返回数据 | object | |
| errorMsg | 错误信息 | string | |
| success | 是否成功 | boolean | |
| total | 总数 | integer(int64) | integer(int64) |

**响应示例**:
```javascript
{
  "data": null,
  "errorMsg": "",
  "success": true,
  "total": 0
}
```

# 数据模型

## JobVO

岗位视图对象，用于返回岗位信息

| 字段名称 | 字段说明 | 类型 | 备注 |
| -------- | -------- | ----- | ----- |
| id | 岗位id | integer(int64) | |
| publisherId | 发布岗位者id | integer(int64) | |
| companyId | 关联的公司id | integer(int64) | |
| title | 标题 | string | |
| salaryMin | 薪水下限 | number(double) | 全职单位K，兼职/实习单位元 |
| salaryMax | 薪水上限 | number(double) | 全职单位K，兼职/实习单位元 |
| salaryRound | 年发薪次数 | string | 仅全职岗位有 |
| mainText | 正文 | string | |
| province | 公司所在省份 | string | |
| city | 城市 | string | |
| district | 区县 | string | |
| type | 正职/兼职/实习 | string | |
| tag | 标签列表 | array[string] | |
| status | 状态 | integer | 0/1 可见/不可见 |
| category | 职位 | string | |
| label | 系统打标 | string | |
| financingProgress | 融资进度 | string | |
| enterpriseScale | 企业规模 | string | |
| industry | 企业所在行业 | string | |
| createTime | 创建时间 | string(date-time) | |
| updateTime | 更新时间 | string(date-time) | |

## PageResult

分页结果对象

| 字段名称 | 字段说明 | 类型 | 备注 |
| -------- | -------- | ----- | ----- |
| total | 总记录数 | integer(int64) | |
| records | 数据列表 | array | 具体类型根据接口而定 |

## Result

统一响应结果对象

| 字段名称 | 字段说明 | 类型 | 备注 |
| -------- | -------- | ----- | ----- |
| data | 返回数据 | object | 具体类型根据接口而定 |
| errorMsg | 错误信息 | string | |
| success | 是否成功 | boolean | |
| total | 总数 | integer(int64) | 部分接口使用 |

# 错误码说明

| 错误码 | 错误信息 | 说明 |
| ------ | -------- | ---- |
| 400 | 参数错误 | 请求参数不正确 |
| 401 | 用户未登录或登录已过期 | 需要重新登录 |
| 403 | 权限不足 | 无权限访问该资源 |
| 404 | 资源不存在 | 请求的资源不存在 |
| 500 | 服务器内部错误 | 服务器异常 |

# 业务规则说明

## 岗位类型
- **正职**: 全职工作岗位，薪资单位为K（千元）
- **兼职**: 兼职工作岗位，薪资单位为元
- **实习**: 实习工作岗位，薪资单位为元

## 薪资限制
- 全职岗位：薪资上限不能超过10000K
- 兼职岗位：薪资上限不能超过10000元
- 实习岗位：无特殊限制
- 最低薪资不能大于最高薪资

## 标签限制
- 标签数量不能超过10个

## 权限控制
- 只有岗位发布者才能更新和删除自己发布的岗位
- 收藏功能需要用户登录
- 查询我发布的岗位需要用户登录

## 状态说明
- status字段：0表示不可见，1表示可见
- 只有状态为1的岗位才会在搜索中显示
- 逻辑删除：删除的岗位不会物理删除，而是标记为已删除