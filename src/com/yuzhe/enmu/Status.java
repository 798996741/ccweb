package com.yuzhe.enmu;

/**
 * @Author: Aliar
 * @Time: 2020-12-11 15:18
 **/
public enum Status {
    //物品状态与局方物品状态对应枚举类
    TOBEMAILED("520dbd4d-d87a-11ea-bd5a-fa163e003af7","1"),
    MAILED("5d9a0a7b-d87a-11ea-bd5a-fa163e003af7","5"),
    REPLACE("81daa0b6-d87a-11ea-bd5a-fa163e003af7","5"),
    ABANDONMENT("923fafde-d87a-11ea-bd5a-fa163e003af7","2"),
    RECEIVED("b59e684c-d87a-11ea-bd5a-fa163e003af7","5"),
    TEMP("a0df5d69-d87b-11ea-bd5a-fa163e003af7","1"),
    UNCLAIMED("30d07f54-d87a-11ea-bd5a-fa163e003af7","1");

    private String dictionId;
    private String status;

    public String getDictionId() {
        return dictionId;
    }

    public void setDictionId(String dictionId) {
        this.dictionId = dictionId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    Status(String dictionId, String status) {
        this.dictionId = dictionId;
        this.status = status;
    }

    public static String receiveStatuOf(String dictionId){
        for (Status status:values()){
            if (status.getDictionId().equals(dictionId)){
                return status.getStatus();
            }
        }
        return "1";
    }
}
