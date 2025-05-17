package com.freelance.exception;

import lombok.Getter;

@Getter
public enum MessageType {
    USER_NOT_FOUND("1000", "kullanıcı bulunamadı"),
    TOKEN_IS_EXPIRED("1001","token süresi dolmuş"),
    USER_ALREADY_EXISTS("1002", "kullanıcı zaten kayıt olmuş"),
    REGISTER_FAILED("1003", "kayıt sırasında bir hata oluştu"),
    AUTHENTICATION_FAILED("1004", "giriş sırasında bir hata oluştu"),
    REFRESH_TOKEN_NOT_FOUND("1005", "refresh token bulunamadı"),
    REFRESH_TOKEN_EXPIRED("1006", "refresh token süresi dolmuş"),
    REFRESH_TOKEN_FAILED("1007","refresh token oluşturulmadı"),
    LOGOUT_EXCEPTION("1008","çıkış işleminde hata meydana geldi"),
    GENERAL_EXCEPTION("9999", "genel bir hata oluştu");

    private String code;
    private String message;

    MessageType(String code, String message){
        this.code = code;
        this.message = message;
    }
}
