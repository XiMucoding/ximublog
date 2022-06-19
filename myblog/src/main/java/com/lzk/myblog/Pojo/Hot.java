package com.lzk.myblog.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hot {
    private int hid;
    private String title;
    private String link;
}
