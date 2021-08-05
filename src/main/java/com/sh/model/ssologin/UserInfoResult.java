package com.sh.model.ssologin;

import lombok.Data;
@Data
public class UserInfoResult {
    private String userInfoResult;
    private String code;
    private String msg;
    private UserInfo userInfo;

    @Data
    public static class UserInfo {
        private String deptCode;
        private String deptName;
        private String idcard_no;
        private String pcNumber;
        private String realName;
        private String ticketStr;
        private String userId;
        private String userName;
    }
}
