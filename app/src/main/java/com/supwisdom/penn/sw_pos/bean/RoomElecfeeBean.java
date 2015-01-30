package com.supwisdom.penn.sw_pos.bean;

/**
 * Created by jov on 2015/1/27.
 */
public class RoomElecfeeBean {
   private String  areaId;
   private String buildId;
   private String roomId;
   private String roomName;
   private String restElecDegree;

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getBuildId() {
        return buildId;
    }

    public void setBuildId(String buildId) {
        this.buildId = buildId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRestElecDegree() {
        return restElecDegree;
    }

    public void setRestElecDegree(String restElecDegree) {
        this.restElecDegree = restElecDegree;
    }
}
