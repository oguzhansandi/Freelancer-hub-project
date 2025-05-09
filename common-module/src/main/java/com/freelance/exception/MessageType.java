package com.freelance.exception;

import lombok.Getter;

@Getter
public enum MessageType {
    USER_NOT_FOUND("1000", "kullanıcı bulunamadı"),
    TOKEN_IS_EXPIRED("1001","token süresi dolmuş"),
    USER_ALREADY_EXISTS("1002", "kullanıcı zaten kayıt olmuş"),
    REGISTER_FAILED("1003", "kayıt sırasında bir hata oluştu"),
    GENERAL_EXCEPTION("9999", "genel bir hata oluştu");

    private String code;
    private String message;

    MessageType(String code, String message){
        this.code = code;
        this.message = message;
    }
}
