package com.peralthought.mailsystem.dto;

import lombok.Data;

@Data
public class EmailRequest {
    private String key;
    private String to;
    private String subject;
    private String body;


}
