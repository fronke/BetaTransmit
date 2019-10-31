package com.fronke.betatransmit.utils;

public class Response {

    private Boolean mSuccess = true;
    private String mContent = "";


    public Response(String _content) {
        if (_content.equals("null")) {
            mSuccess = false;
        }
        mContent = _content;
    }

    public Boolean isSuccess() {
        return mSuccess;
    }

    public String getContent() {
        return mContent;
    }


}
