package com.easyshop.model;

import lombok.Getter;
import lombok.Setter;


/**
 * Created by pavan on 2/9/17.
 */
@Getter
@Setter
public class ForgotPasswordModel {

    public String emailId;

    public String password;

    public ForgotPasswordModel(){}
}
