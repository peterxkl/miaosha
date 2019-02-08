package com.miaoshaproject.error;

/**
 * @author hongjun500
 * @date 2019/2/1 10:39
 */
public interface CommonError {
    public int getErrCode();
    public String getErrMsg();
    public CommonError setErrMsg(String errMsg);
}
