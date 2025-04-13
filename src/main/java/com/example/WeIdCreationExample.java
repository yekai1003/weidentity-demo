/*
 * This source file was generated by the Gradle 'init' task
 */
package com.example;

import com.webank.weid.protocol.response.CreateWeIdDataResult;
import com.webank.weid.protocol.response.ResponseData;
import com.webank.weid.service.impl.WeIdServiceImpl;

public class WeIdCreationExample {

    public static void main(String[] args) {
        // 初始化 WeIdService
        WeIdServiceImpl weIdService = new WeIdServiceImpl();

        // 创建 WeId
        ResponseData<CreateWeIdDataResult> response = weIdService.createWeId();

        // 处理响应结果
        if (response.getErrorCode() == 0) {
            CreateWeIdDataResult result = response.getResult();
            System.out.println("成功创建 WeId: " + result.getWeId());
            System.out.println("私钥: " + result.getUserWeIdPrivateKey().getPrivateKey());
        } else {
            System.out.println("创建 WeId 失败，错误码: " + response.getErrorCode());
            System.out.println("错误信息: " + response.getErrorMessage());
        }
    }
}
