package com.lzk.myblog.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    private int aid;
    private int uid;
    private String title;
    private String content;
    private String summary;
    private String time;
    private int status;
}
