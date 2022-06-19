package com.lzk.myblog.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private int mid;
    private int uid;
    private String context;
    private String time;
    private int replayUid;
    private int status;
}

