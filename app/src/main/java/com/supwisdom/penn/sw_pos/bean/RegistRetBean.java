package com.supwisdom.penn.sw_pos.bean;

/**
 * Created by jov on 2015/1/21.
 */
public class RegistRetBean {

    private String uid;
    private String timestamp;
    private String gid;
    private String rsapbulic;


    private boolean isfirstreg;
    private boolean ispwdset;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getRsapbulic() {
        return rsapbulic;
    }

    public void setRsapbulic(String rsapbulic) {
        this.rsapbulic = rsapbulic;
    }

    public boolean isIsfirstreg() {
        return isfirstreg;
    }

    public void setIsfirstreg(boolean isfirstreg) {
        this.isfirstreg = isfirstreg;
    }

    public boolean isIspwdset() {
        return ispwdset;
    }

    public void setIspwdset(boolean ispwdset) {
        this.ispwdset = ispwdset;
    }

    @Override
    public String toString() {
        return "RegistRetBean{" +
                "uid='" + uid + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", gid='" + gid + '\'' +
                ", rsapbulic='" + rsapbulic + '\'' +
                ", isfirstreg=" + isfirstreg +
                ", ispwdset=" + ispwdset +
                '}';
    }
}
