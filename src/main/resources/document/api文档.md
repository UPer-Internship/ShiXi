# API文档


**简介**:API文档


**HOST**:localhost:8081


**联系人**:


**Version**:1.0


**接口路径**:/v2/api-docs?group=默认接口


[TOC]
# 企业相关接口


## 删除岗位


**接口地址**:`/enterprise/deleteJob`


**请求方式**:`POST`


**请求数据类型**:`application/json`


**响应数据类型**:`*/*`


**接口描述**:根据id删除岗位


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id|id|query|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|201|Created||
|401|Unauthorized||
|403|Forbidden||
|404|Not Found||


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|data||object||
|errorMsg||string||
|success||boolean||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"data": {},
	"errorMsg": "",
	"success": true,
	"total": 0
}
```


## 发布岗位


**接口地址**:`/enterprise/pubJob`


**请求方式**:`POST`


**请求数据类型**:`application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "category": "",
  "createTime": "",
  "detailedInformation": "",
  "enterpriseName": "",
  "enterpriseScale": "",
  "enterpriseType": "",
  "financingProgress": "",
  "frequency": "",
  "id": 0,
  "publisher": "",
  "publisherId": 0,
  "salary": "",
  "title": "",
  "totalTime": "",
  "type": "",
  "updateTime": "",
  "workLocation": ""
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|job|job|body|true|Job|Job|
|&emsp;&emsp;category|||false|string||
|&emsp;&emsp;createTime|||false|string(date-time)||
|&emsp;&emsp;detailedInformation|||false|string||
|&emsp;&emsp;enterpriseName|||false|string||
|&emsp;&emsp;enterpriseScale|||false|string||
|&emsp;&emsp;enterpriseType|||false|string||
|&emsp;&emsp;financingProgress|||false|string||
|&emsp;&emsp;frequency|||false|string||
|&emsp;&emsp;id|||false|integer(int64)||
|&emsp;&emsp;publisher|||false|string||
|&emsp;&emsp;publisherId|||false|integer(int64)||
|&emsp;&emsp;salary|||false|string||
|&emsp;&emsp;title|||false|string||
|&emsp;&emsp;totalTime|||false|string||
|&emsp;&emsp;type|||false|string||
|&emsp;&emsp;updateTime|||false|string(date-time)||
|&emsp;&emsp;workLocation|||false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|201|Created||
|401|Unauthorized||
|403|Forbidden||
|404|Not Found||


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|data||object||
|errorMsg||string||
|success||boolean||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"data": {},
	"errorMsg": "",
	"success": true,
	"total": 0
}
```


## 查询自己已经发布全部岗位


**接口地址**:`/enterprise/queryMyPubList`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|401|Unauthorized||
|403|Forbidden||
|404|Not Found||


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|data||object||
|errorMsg||string||
|success||boolean||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"data": {},
	"errorMsg": "",
	"success": true,
	"total": 0
}
```


## 根据id查询某个岗位


**接口地址**:`/enterprise/queryPubById`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id|id|query|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|401|Unauthorized||
|403|Forbidden||
|404|Not Found||


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|data||object||
|errorMsg||string||
|success||boolean||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"data": {},
	"errorMsg": "",
	"success": true,
	"total": 0
}
```


## 根据id查询某个简历


**接口地址**:`/enterprise/queryResumeById`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id|id|query|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|401|Unauthorized||
|403|Forbidden||
|404|Not Found||


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|data||object||
|errorMsg||string||
|success||boolean||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"data": {},
	"errorMsg": "",
	"success": true,
	"total": 0
}
```


## 查询收到的所有简历


**接口地址**:`/enterprise/queryResumeList`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|401|Unauthorized||
|403|Forbidden||
|404|Not Found||


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|data||object||
|errorMsg||string||
|success||boolean||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"data": {},
	"errorMsg": "",
	"success": true,
	"total": 0
}
```


## 更新某个岗位


**接口地址**:`/enterprise/updateJob`


**请求方式**:`POST`


**请求数据类型**:`application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "category": "",
  "createTime": "",
  "detailedInformation": "",
  "enterpriseName": "",
  "enterpriseScale": "",
  "enterpriseType": "",
  "financingProgress": "",
  "frequency": "",
  "id": 0,
  "publisher": "",
  "publisherId": 0,
  "salary": "",
  "title": "",
  "totalTime": "",
  "type": "",
  "updateTime": "",
  "workLocation": ""
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id|id|query|true|integer(int64)||
|job|job|body|true|Job|Job|
|&emsp;&emsp;category|||false|string||
|&emsp;&emsp;createTime|||false|string(date-time)||
|&emsp;&emsp;detailedInformation|||false|string||
|&emsp;&emsp;enterpriseName|||false|string||
|&emsp;&emsp;enterpriseScale|||false|string||
|&emsp;&emsp;enterpriseType|||false|string||
|&emsp;&emsp;financingProgress|||false|string||
|&emsp;&emsp;frequency|||false|string||
|&emsp;&emsp;id|||false|integer(int64)||
|&emsp;&emsp;publisher|||false|string||
|&emsp;&emsp;publisherId|||false|integer(int64)||
|&emsp;&emsp;salary|||false|string||
|&emsp;&emsp;title|||false|string||
|&emsp;&emsp;totalTime|||false|string||
|&emsp;&emsp;type|||false|string||
|&emsp;&emsp;updateTime|||false|string(date-time)||
|&emsp;&emsp;workLocation|||false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|201|Created||
|401|Unauthorized||
|403|Forbidden||
|404|Not Found||


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|data||object||
|errorMsg||string||
|success||boolean||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"data": {},
	"errorMsg": "",
	"success": true,
	"total": 0
}
```


# 在线简历相关接口


## 保存在线简历基本信息


**接口地址**:`/student/resume/basicInfo`


**请求方式**:`POST`


**请求数据类型**:`application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "advantages": "",
  "birthDate": "",
  "createTime": "",
  "educationLevel": "",
  "expectedPosition": "",
  "gender": "",
  "graduationDate": "",
  "icon": "",
  "id": 0,
  "major": "",
  "name": "",
  "phone": "",
  "schoolName": "",
  "updateTime": "",
  "userId": 0,
  "wechat": ""
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|studentInfo|studentInfo|body|true|StudentInfo|StudentInfo|
|&emsp;&emsp;advantages|||false|string||
|&emsp;&emsp;birthDate|||false|string||
|&emsp;&emsp;createTime|||false|string(date-time)||
|&emsp;&emsp;educationLevel|||false|string||
|&emsp;&emsp;expectedPosition|||false|string||
|&emsp;&emsp;gender|||false|string||
|&emsp;&emsp;graduationDate|||false|string(date)||
|&emsp;&emsp;icon|||false|string||
|&emsp;&emsp;id|||false|integer(int64)||
|&emsp;&emsp;major|||false|string||
|&emsp;&emsp;name|||false|string||
|&emsp;&emsp;phone|||false|string||
|&emsp;&emsp;schoolName|||false|string||
|&emsp;&emsp;updateTime|||false|string(date-time)||
|&emsp;&emsp;userId|||false|integer(int64)||
|&emsp;&emsp;wechat|||false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|201|Created||
|401|Unauthorized||
|403|Forbidden||
|404|Not Found||


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|data||object||
|errorMsg||string||
|success||boolean||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"data": {},
	"errorMsg": "",
	"success": true,
	"total": 0
}
```


## 修改在线简历基本信息


**接口地址**:`/student/resume/changeBasicInfo`


**请求方式**:`POST`


**请求数据类型**:`application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "advantages": "",
  "birthDate": "",
  "createTime": "",
  "educationLevel": "",
  "expectedPosition": "",
  "gender": "",
  "graduationDate": "",
  "icon": "",
  "id": 0,
  "major": "",
  "name": "",
  "phone": "",
  "schoolName": "",
  "updateTime": "",
  "userId": 0,
  "wechat": ""
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|studentInfo|studentInfo|body|true|StudentInfo|StudentInfo|
|&emsp;&emsp;advantages|||false|string||
|&emsp;&emsp;birthDate|||false|string||
|&emsp;&emsp;createTime|||false|string(date-time)||
|&emsp;&emsp;educationLevel|||false|string||
|&emsp;&emsp;expectedPosition|||false|string||
|&emsp;&emsp;gender|||false|string||
|&emsp;&emsp;graduationDate|||false|string(date)||
|&emsp;&emsp;icon|||false|string||
|&emsp;&emsp;id|||false|integer(int64)||
|&emsp;&emsp;major|||false|string||
|&emsp;&emsp;name|||false|string||
|&emsp;&emsp;phone|||false|string||
|&emsp;&emsp;schoolName|||false|string||
|&emsp;&emsp;updateTime|||false|string(date-time)||
|&emsp;&emsp;userId|||false|integer(int64)||
|&emsp;&emsp;wechat|||false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|201|Created||
|401|Unauthorized||
|403|Forbidden||
|404|Not Found||


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|data||object||
|errorMsg||string||
|success||boolean||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"data": {},
	"errorMsg": "",
	"success": true,
	"total": 0
}
```


## 修改在线简历经历信息


**接口地址**:`/student/resume/changeExperienceInfo`


**请求方式**:`POST`


**请求数据类型**:`application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "createTime": "",
  "description": "",
  "id": 0,
  "link": "",
  "studentInfoId": 0,
  "type": "",
  "updateTime": ""
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|resumeExperience|resumeExperience|body|true|ResumeExperience|ResumeExperience|
|&emsp;&emsp;createTime|||false|string(date-time)||
|&emsp;&emsp;description|||false|string||
|&emsp;&emsp;id|||false|integer(int64)||
|&emsp;&emsp;link|||false|string||
|&emsp;&emsp;studentInfoId|||false|integer(int64)||
|&emsp;&emsp;type|||false|string||
|&emsp;&emsp;updateTime|||false|string(date-time)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|201|Created||
|401|Unauthorized||
|403|Forbidden||
|404|Not Found||


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|data||object||
|errorMsg||string||
|success||boolean||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"data": {},
	"errorMsg": "",
	"success": true,
	"total": 0
}
```


## 保存在线简历经历信息


**接口地址**:`/student/resume/experience`


**请求方式**:`POST`


**请求数据类型**:`application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "createTime": "",
  "description": "",
  "id": 0,
  "link": "",
  "studentInfoId": 0,
  "type": "",
  "updateTime": ""
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|resumeExperience|resumeExperience|body|true|ResumeExperience|ResumeExperience|
|&emsp;&emsp;createTime|||false|string(date-time)||
|&emsp;&emsp;description|||false|string||
|&emsp;&emsp;id|||false|integer(int64)||
|&emsp;&emsp;link|||false|string||
|&emsp;&emsp;studentInfoId|||false|integer(int64)||
|&emsp;&emsp;type|||false|string||
|&emsp;&emsp;updateTime|||false|string(date-time)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|201|Created||
|401|Unauthorized||
|403|Forbidden||
|404|Not Found||


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|data||object||
|errorMsg||string||
|success||boolean||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"data": {},
	"errorMsg": "",
	"success": true,
	"total": 0
}
```


## 获取在线简历信息


**接口地址**:`/student/resume/myResume`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|401|Unauthorized||
|403|Forbidden||
|404|Not Found||


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|data||object||
|errorMsg||string||
|success||boolean||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"data": {},
	"errorMsg": "",
	"success": true,
	"total": 0
}
```


# 学生基本信息接口


## 修改学生基本信息


**接口地址**:`/student/changeInfo`


**请求方式**:`POST`


**请求数据类型**:`application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "advantages": "",
  "birthDate": "",
  "createTime": "",
  "educationLevel": "",
  "expectedPosition": "",
  "gender": "",
  "graduationDate": "",
  "icon": "",
  "id": 0,
  "major": "",
  "name": "",
  "phone": "",
  "schoolName": "",
  "updateTime": "",
  "userId": 0,
  "wechat": ""
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|studentInfo|studentInfo|body|true|StudentInfo|StudentInfo|
|&emsp;&emsp;advantages|||false|string||
|&emsp;&emsp;birthDate|||false|string||
|&emsp;&emsp;createTime|||false|string(date-time)||
|&emsp;&emsp;educationLevel|||false|string||
|&emsp;&emsp;expectedPosition|||false|string||
|&emsp;&emsp;gender|||false|string||
|&emsp;&emsp;graduationDate|||false|string(date)||
|&emsp;&emsp;icon|||false|string||
|&emsp;&emsp;id|||false|integer(int64)||
|&emsp;&emsp;major|||false|string||
|&emsp;&emsp;name|||false|string||
|&emsp;&emsp;phone|||false|string||
|&emsp;&emsp;schoolName|||false|string||
|&emsp;&emsp;updateTime|||false|string(date-time)||
|&emsp;&emsp;userId|||false|integer(int64)||
|&emsp;&emsp;wechat|||false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|201|Created||
|401|Unauthorized||
|403|Forbidden||
|404|Not Found||


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|data||object||
|errorMsg||string||
|success||boolean||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"data": {},
	"errorMsg": "",
	"success": true,
	"total": 0
}
```


## 获取用户基本信息（昵称，头像等）


**接口地址**:`/student/me`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|401|Unauthorized||
|403|Forbidden||
|404|Not Found||


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|data||object||
|errorMsg||string||
|success||boolean||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"data": {},
	"errorMsg": "",
	"success": true,
	"total": 0
}
```


## 保存学生基本信息


**接口地址**:`/student/register/basicInfo`


**请求方式**:`POST`


**请求数据类型**:`application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "advantages": "",
  "birthDate": "",
  "createTime": "",
  "educationLevel": "",
  "expectedPosition": "",
  "gender": "",
  "graduationDate": "",
  "icon": "",
  "id": 0,
  "major": "",
  "name": "",
  "phone": "",
  "schoolName": "",
  "updateTime": "",
  "userId": 0,
  "wechat": ""
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|studentInfo|studentInfo|body|true|StudentInfo|StudentInfo|
|&emsp;&emsp;advantages|||false|string||
|&emsp;&emsp;birthDate|||false|string||
|&emsp;&emsp;createTime|||false|string(date-time)||
|&emsp;&emsp;educationLevel|||false|string||
|&emsp;&emsp;expectedPosition|||false|string||
|&emsp;&emsp;gender|||false|string||
|&emsp;&emsp;graduationDate|||false|string(date)||
|&emsp;&emsp;icon|||false|string||
|&emsp;&emsp;id|||false|integer(int64)||
|&emsp;&emsp;major|||false|string||
|&emsp;&emsp;name|||false|string||
|&emsp;&emsp;phone|||false|string||
|&emsp;&emsp;schoolName|||false|string||
|&emsp;&emsp;updateTime|||false|string(date-time)||
|&emsp;&emsp;userId|||false|integer(int64)||
|&emsp;&emsp;wechat|||false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|201|Created||
|401|Unauthorized||
|403|Forbidden||
|404|Not Found||


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|data||object||
|errorMsg||string||
|success||boolean||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"data": {},
	"errorMsg": "",
	"success": true,
	"total": 0
}
```


# 岗位相关接口


## 投递简历


**接口地址**:`/job/deliverResume`


**请求方式**:`POST`


**请求数据类型**:`application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id|id|query|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|201|Created||
|401|Unauthorized||
|403|Forbidden||
|404|Not Found||


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|data||object||
|errorMsg||string||
|success||boolean||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"data": {},
	"errorMsg": "",
	"success": true,
	"total": 0
}
```


## 模糊查询Job信息


**接口地址**:`/job/fuzzyQuery`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "keyWord": "",
  "page": 0,
  "pageSize": 0
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|jobFuzzyQueryDTO|jobFuzzyQueryDTO|body|true|JobFuzzyQueryDTO|JobFuzzyQueryDTO|
|&emsp;&emsp;keyWord|||false|string||
|&emsp;&emsp;page|||false|integer(int32)||
|&emsp;&emsp;pageSize|||false|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|401|Unauthorized||
|403|Forbidden||
|404|Not Found||


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|data||object||
|errorMsg||string||
|success||boolean||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"data": {},
	"errorMsg": "",
	"success": true,
	"total": 0
}
```


## 分页且按条件查询岗位


**接口地址**:`/job/pageQuery`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "category": "",
  "page": 0,
  "pageSize": 0,
  "type": ""
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|jobPageQueryDTO|jobPageQueryDTO|body|true|JobPageQueryDTO|JobPageQueryDTO|
|&emsp;&emsp;category|||false|string||
|&emsp;&emsp;page|||false|integer(int32)||
|&emsp;&emsp;pageSize|||false|integer(int32)||
|&emsp;&emsp;type|||false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|401|Unauthorized||
|403|Forbidden||
|404|Not Found||


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|data||object||
|errorMsg||string||
|success||boolean||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"data": {},
	"errorMsg": "",
	"success": true,
	"total": 0
}
```


## 根据岗位id返回


**接口地址**:`/job/queryById`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id|id|query|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|401|Unauthorized||
|403|Forbidden||
|404|Not Found||


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|data||object||
|errorMsg||string||
|success||boolean||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"data": {},
	"errorMsg": "",
	"success": true,
	"total": 0
}
```


# 用户服务


## 账号密码登录,已弃用


**接口地址**:`/user/login/byAccount`


**请求方式**:`POST`


**请求数据类型**:`application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|account|account|query|true|string||
|password|password|query|true|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|201|Created||
|401|Unauthorized||
|403|Forbidden||
|404|Not Found||


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|data||object||
|errorMsg||string||
|success||boolean||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"data": {},
	"errorMsg": "",
	"success": true,
	"total": 0
}
```


## 手机验证码登录


**接口地址**:`/user/login/byPhone`


**请求方式**:`POST`


**请求数据类型**:`application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|code|code|query|true|string||
|phone|phone|query|true|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|201|Created||
|401|Unauthorized||
|403|Forbidden||
|404|Not Found||


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|data||object||
|errorMsg||string||
|success||boolean||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"data": {},
	"errorMsg": "",
	"success": true,
	"total": 0
}
```


## 发送验证码


**接口地址**:`/user/login/sendCode`


**请求方式**:`POST`


**请求数据类型**:`application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|phone|phone|query|true|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|
|201|Created||
|401|Unauthorized||
|403|Forbidden||
|404|Not Found||


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|data||object||
|errorMsg||string||
|success||boolean||
|total||integer(int64)|integer(int64)|


**响应示例**:
```javascript
{
	"data": {},
	"errorMsg": "",
	"success": true,
	"total": 0
}
```