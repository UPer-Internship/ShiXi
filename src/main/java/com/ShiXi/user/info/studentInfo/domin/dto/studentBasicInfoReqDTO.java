package com.ShiXi.user.info.studentInfo.domin.dto;

import lombok.Data;

@Data
@Deprecated
public class studentBasicInfoReqDTO {
    /**
     * 姓名
     */
    private String name;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 性别，例如：男 / 女
     */
    private String gender;
    /**
     * 出生年月，格式为 yyyy/MM
     */
    private String birthDate;
    /**
     * 学校名称
     */
    private String schoolName;
    /**
     * 专业名称
     */
    private String major;

}
