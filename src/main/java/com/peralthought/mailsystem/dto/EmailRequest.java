package com.peralthought.mailsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmailRequest {
    private String key;
    private String to;
    private String subject;
    private String body;


}
