package com.freelance.exception;

import lombok.Getter;

@Getter
public enum MessageType {
    USER_NOT_FOUND("1000", "kullanıcı bulunamadı"),
    TOKEN_IS_EXPIRED("1001","token süresi dolmuş"),
    USER_ALREADY_EXISTS("1002", "kullanıcı zaten kayıt olmuş"),
    REGISTER_FAILED("1003", "kayıt sırasında bir hata oluştu"),
    AUTHENTICATION_FAILED("1004", "giriş sırasında bir hata oluştu"),
    UPDATE_FAILED("1005", "güncelleme işlemi yapılamadı "),
    DATA_NOT_FOUND("1006", "veri bulunamadı"),
    ACCESS_DENIED("1007", "yetkisiz erişim"),
    LOGOUT_EXCEPTION("1008","çıkış işleminde hata meydana geldi"),
    ALREADY_EXISTS("1009", "mevcut kayıt bulundu"),
    GENERAL_EXCEPTION("9999", "genel bir hata oluştu");

    private String code;
    private String message;

    MessageType(String code, String message){
        this.code = code;
        this.message = message;
    }
}
