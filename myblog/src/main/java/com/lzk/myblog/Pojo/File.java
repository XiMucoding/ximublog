package com.lzk.myblog.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author lzk
 * @Email 1801290586@qq.com
 * @Description <类说明>
 * @Date 19:21 2022/6/17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class File {
    private int mid;
    private String name;
    private int status;
    private String url;
}
