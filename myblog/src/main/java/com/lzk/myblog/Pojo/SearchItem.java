package com.lzk.myblog.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchItem {
    private int aid;
    private String title;
    private String summary;
    private String time;
    private String nickName;
}

