package org.cm.pro.utils.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SaltProfile{
    private String salt;
    private String password;
    private String finalPassword;
}
