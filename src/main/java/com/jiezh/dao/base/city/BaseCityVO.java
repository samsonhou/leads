package com.jiezh.dao.base.city;

public class BaseCityVO {
    private Long id;
    private String cityCode;
    private String rankId;

    private String cityName;
    /**
     * 
     * 市城名称
     */
    private String abbrName;
    /**
     * 阶层 PID
     */
    private String rankPid;
    /**
     * 是否启用，0为禁用，1为启用
     */
    private String status;
    /**
     * 0-国家，1-省，2-市，3-区县，4-乡镇
     */
    private String cityRank;
    /**
     * 区分城市是一线还是二线
     */
    private String cityLevel;

    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getRankId() {
        return rankId;
    }

    public void setRankId(String rankId) {
        this.rankId = rankId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName == null ? null : cityName.trim();
    }

    public String getAbbrName() {
        return abbrName;
    }

    public void setAbbrName(String abbrName) {
        this.abbrName = abbrName == null ? null : abbrName.trim();
    }

    public String getRankPid() {
        return rankPid;
    }

    public void setRankPid(String rankPid) {
        this.rankPid = rankPid == null ? null : rankPid.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getCityRank() {
        return cityRank;
    }

    public void setCityRank(String cityRank) {
        this.cityRank = cityRank == null ? null : cityRank.trim();
    }

    public String getCityLevel() {
        return cityLevel;
    }

    public void setCityLevel(String cityLevel) {
        this.cityLevel = cityLevel == null ? null : cityLevel.trim();
    }
}
