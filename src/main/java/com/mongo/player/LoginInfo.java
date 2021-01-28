package com.mongo.player;

import lombok.Data;

@Data
public class LoginInfo {
    private long loginTime;
    private long logoutTime;
}
