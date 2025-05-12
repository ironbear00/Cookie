package com.example.cookie.domain;

import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberVO {
    private String mid;
    private String mpw;
    private String mname;
    private String uuid;
}
