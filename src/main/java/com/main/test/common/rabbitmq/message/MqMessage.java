package com.main.test.common.rabbitmq.message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MqMessage<T> implements Serializable {
    private List<T> dataList = new ArrayList<>(0);

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }
}