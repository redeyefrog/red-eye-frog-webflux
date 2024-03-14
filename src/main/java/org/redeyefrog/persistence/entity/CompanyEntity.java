package org.redeyefrog.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CompanyEntity {

    private Long seq;

    public String uid;

    private String name;

    private String address;

    private String phone;

    private String email;

    private String delStatus;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
