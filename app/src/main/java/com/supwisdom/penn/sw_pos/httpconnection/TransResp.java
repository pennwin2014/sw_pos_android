package com.supwisdom.penn.sw_pos.httpconnection;

/**
 * Created by linqing.he on 2015/1/12.
 */
public class TransResp {
    /**
     * HTTP返回码
     */
    private int retcode;
    /**
     * 返回消息，如果是200 则为空，非200则为返回错误信息
     */
    private String retmsg;
    /**
     * 返回json信息
     */
    private String retjson;

    public int getRetcode() {
        return retcode;
    }

    public void setRetcode(int retcode) {
        this.retcode = retcode;
    }

    public String getRetmsg() {
        return retmsg;
    }

    public void setRetmsg(String retmsg) {
        this.retmsg = retmsg;
    }

    public String getRetjson() {
        return retjson;
    }

    public void setRetjson(String retjson) {
        this.retjson = retjson;
    }

    @Override
    public String toString() {
       return retcode+retmsg+retjson;
    }
}
