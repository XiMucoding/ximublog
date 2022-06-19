package com.lzk.myblog.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoticeUser {
    private int nid;
    private String context;
    private String avatar;
    private String nickName;
    private String time;
}
