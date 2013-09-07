/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) UTF8StringHttpMessageConverter.java 2013-08-19 00:38
 */
package com.ibbface.interfaces.web;

import com.google.common.base.Charsets;
import com.ibbface.domain.model.user.User;
import com.ibbface.util.RandomStrings;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

/**
 * @author Fuchun
 * @since 1.0
 */
public class UTF8StringHttpMessageConverter extends StringHttpMessageConverter {

    private static final MediaType utf8 = new MediaType("text", "plain", Charsets.UTF_8);

    private boolean writeAcceptCharset = true;

    protected List<Charset> getAcceptedCharsets() {
        return Arrays.asList(Charsets.UTF_8);
    }

    @Override
    protected MediaType getDefaultContentType(String t) throws IOException {
        return utf8;
    }

    @Override
    protected void writeInternal(String s, HttpOutputMessage outputMessage) throws IOException {
        if (this.writeAcceptCharset) {
            outputMessage.getHeaders().setAcceptCharset(getAcceptedCharsets());
        }
        Charset charset = getContentTypeCharset(outputMessage.getHeaders().getContentType());
        StreamUtils.copy(s, charset, outputMessage.getBody());
    }

    @Override
    public void setWriteAcceptCharset(boolean writeAcceptCharset) {
        this.writeAcceptCharset = writeAcceptCharset;
    }

    private Charset getContentTypeCharset(MediaType contentType) {
        if (contentType != null && contentType.getCharSet() != null) {
            return contentType.getCharSet();
        } else {
            return Charsets.UTF_8;
        }
    }

    public static void main(String[] args) {
        String salt = RandomStrings.random(16, true, true);
        String email = "email@ibbface.com";
        String password = "admin123456";
        String hashPassword = User.hashPassword(password, salt);
        System.out.println("salt=" + salt + ", hash_password=" + hashPassword);
    }
}
