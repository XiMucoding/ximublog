package com.lzk.myblog.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private int cid;
    private int aid;
    private int uid;
    private String time;
    private String remark;
    private String replayCid;
    private String replayName;
    private String avatar;
    private String nickName;
}
