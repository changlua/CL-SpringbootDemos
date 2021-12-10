package com.changlu.springboot.domain.Request;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName BasicRequest
 * @Author ChangLu
 * @Date 2021/12/10 23:53
 * @Description TODO
 */
@Data
public class BasicRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private String url;

}