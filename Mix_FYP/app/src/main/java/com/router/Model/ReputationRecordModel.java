package com.router.Model;

import java.io.Serializable;

/**
 * 用戶信譽變更記錄model
 */
public class ReputationRecordModel implements Serializable {
    private String createTime;
    private String operation; //原因
    private String record;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }
}
