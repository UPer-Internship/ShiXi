# 接口文档
---
## 获取在线简历信息接口
### 请求信息

- **请求类型**：`GET`
- **请求路径**：`/student/me/myInfo`
- **请求参数**：无

### 返回示例

```json
{
  "success": true,
  "data": {
    "id": 1,
    "userId": 1,
    "name": "cyc",
    "schoolName": "华南理工大学",
    "major": "CS",
    "graduationDate": "2027-07-01",
    "age": 19,
    "phone": "18378059289",
    "wechat": "cloogcyc",
    "educationLevel": "本科",
    "advantages": "是个好人",
    "createTime": "2025-05-18T11:34:38",
    "updateTime": "2025-05-18T11:34:48",
    "internshipExperiences": [
      {
        "id": 1,
        "onlineResumeId": 1,
        "type": "实习",
        "description": "Java开发",
        "link": "https://**",
        "createTime": "2025-05-18T11:35:20",
        "updateTime": "2025-05-18T11:35:20"
      }
    ],
    "workExperiences": [],
    "projectExperiences": [],
    "portfolioExperiences": []
  }
}
```