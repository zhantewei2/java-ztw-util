package org.cm.pro.HttpException;

public enum ReqBadEnum {
    /***
     * author: zhantewei
     * 通用性的状态，每个状态必须有明确的意义
     * 即不存在 查询失败，插入失败这种错误
     * creation time:2019-10-14
     ****/
    //默认未知错误
    Normal(0),
    //未登陆
    NotLogin(1),
    //登陆已过期
    ExpiredLogin(2),
    //未通过授权
    NotAuth(3),
    //查询-未找到
    FindEmpty(101),
    //查询-未通过认证
    FindUnAuth(102),
    //插入-已存在
    InsertDup(201),
    //更新-不存在
    UpdateNotFound(301),
    // 已经存在的不能再更新
    UpdateAlreadyExist(302);

    private int typeCode;
    private ReqBadEnum(int type){
        this.typeCode=type;
    }
    public int getType(){
        return this.typeCode;
    }
}
