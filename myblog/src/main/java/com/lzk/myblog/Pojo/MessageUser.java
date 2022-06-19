package com.lzk.myblog.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageUser {
    private int mid;
    private int uid;
    private String context;
    private String avatar;
    private String nickName;
    private int status;
}
