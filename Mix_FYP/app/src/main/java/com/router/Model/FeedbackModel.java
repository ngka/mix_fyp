package com.router.Model;

public class FeedbackModel {
    /**
     * {
     * "createTime": "",
     * "id": 0,
     * "oid": 0,
     * "other": "",
     * "scoreFive": "",
     * "scoreFour": "",
     * "scoreOne": "",
     * "scoreThree": "",
     * "scoreTwo": "",
     * "uid": 0
     * }
     */
    private String createTime;
    private long id;
    private long oid;
    private String other;
    private String scoreFive;
    private String scoreFour;
    private String scoreOne;
    private String scoreThree;
    private String scoreTwo;
    private long uid;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOid() {
        return oid;
    }

    public void setOid(long oid) {
        this.oid = oid;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getScoreFive() {
        return scoreFive;
    }

    public void setScoreFive(String scoreFive) {
        this.scoreFive = scoreFive;
    }

    public String getScoreFour() {
        return scoreFour;
    }

    public void setScoreFour(String scoreFour) {
        this.scoreFour = scoreFour;
    }

    public String getScoreOne() {
        return scoreOne;
    }

    public void setScoreOne(String scoreOne) {
        this.scoreOne = scoreOne;
    }

    public String getScoreThree() {
        return scoreThree;
    }

    public void setScoreThree(String scoreThree) {
        this.scoreThree = scoreThree;
    }

    public String getScoreTwo() {
        return scoreTwo;
    }

    public void setScoreTwo(String scoreTwo) {
        this.scoreTwo = scoreTwo;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }
}
