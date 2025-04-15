# weidentity-demo



pom.xml文件中，引用了weid-sdk的引用。

```xml
<properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
        <!-- 使用新版 SDK -->
        <weid-sdk.version>1.8.6-rc1</weid-sdk.version>
        <web3sdk.version>2.6.6</web3sdk.version>
    </properties>
```

运行时，sdk默认读取两个配置文件：fisco.properties和weidentity.properties。

fisco.properties配置如下：

```properties
# Fisco-bcos blockchain node related properties

#######################################################################################################
#                                                                                                     #
#         fisco bcos 2 or 3 version config                                                                   #
#                                                                                                     #
#######################################################################################################
# Version
bcos.version=2
#nodes = 127.0.0.1:20200

#######################################################################################################
#                                                                                                     #
#         contract address config                                                                     #
#                                                                                                     #
#######################################################################################################
# contract address
weId.contractaddress=0xcafaba62ceb9fe82eb7f3dd7031f666b8e7b6848
cpt.contractaddress=0xf9609838c75f017f8a278aeac928daee6a07953b
issuer.contractaddress=0x249547e4994a17b4e7e364881e0b0fab3d46c09a
evidence.contractaddress=0x2c997d327555e3dc0154cf6308e623d2a60446cb
specificissuer.contractaddress=0xbcc79826c1e85955d79ca81645d1df1249b44754

# This variable is used to distinguish the environment. You can use "dev" to set the development environment, 
# "stg" to set the test environment, "prd" to set the production environment,
# If you do not set it, the system will use allOrg as the environment by default. 
# It is not recommended. Production use default configuration
cns.profile.active=2

#######################################################################################################
#                                                                                                     #
#         web3sdk connection config                                                                   #
#                                                                                                     #
#######################################################################################################
# blockchain connection params
web3sdk.timeout=30
web3sdk.core-pool-size=100
web3sdk.max-pool-size=200
web3sdk.queue-capacity=1000
web3sdk.keep-alive-seconds=60


#######################################################################################################
#                                                                                                     #
#         fisco bcos 2.0 related config                                                               #
#                                                                                                     #
#######################################################################################################
# Fisco-Bcos 2.x params, including Group ID and Encrypt Type
group.id=1

#######################################################################################################
#                                                                                                     #
#         fisco bcos node cert related config                                                         #
#                                                                                                     #
#######################################################################################################
# Fisco-Bcos sdk SSL encrypt type, 0:ECDSA, 1:SaM2
sdk.sm-crypto=0
# fisco-bcos sdk cert path contains[ca.crt,sdk.crt,sdk.key]
# if sdk.sm-crypto is true, contains [gm] directory, and gm dir contains [gmca.crt,gmsdk.crt,gmsdk.key,gmensdk.crt,gmensdk.key]
sdk.cert-path=/Users/yk/etc/conf

# amop public key of pem and private key of p12 configuration
amop.pub-path=/Users/yk/etc/amop/consumer_public_key.pem
amop.pri-path=/Users/yk/etc/amop/consumer_private_key.p12
amop.p12-password=123456
```

sdk.cert-path=/Users/yk/etc/conf这个地方经过测试，在这个sdk版本下要写绝对路径。需要注意的是还有sdk的3个文件：ca.crt,sdk.crt,sdk.key要放在配置文件的绝对路径下。consumer_public_key.pem和consumer_private_key.p12这两个文件同理。



weidentity.properties如下：

```properties
# FISCO BCOS 节点信息
nodes = 127.0.0.1:20200
network.channelService.caCert = ca.crt
network.channelService.nodeCert = sdk.crt
network.channelService.nodeKey = sdk.key

# 合约地址配置
weId.contractaddress = 0xcafaba62ceb9fe82eb7f3dd7031f666b8e7b6848
cpt.contractaddress = 0xf9609838c75f017f8a278aeac928daee6a07953b
issuer.contractaddress = 0x249547e4994a17b4e7e364881e0b0fab3d46c09a
evidence.contractaddress = 0x2c997d327555e3dc0154cf6308e623d2a60446cb
specificissuer.contractaddress = 0xbcc79826c1e85955d79ca81645d1df1249b44754

# 密码学算法配置
chain.id = 1
group.id = 1
sdk.sm-crypto = 0
# 补充必要属性
bcos.version = 2
web3sdk.timeout = 5000
web3sdk.core-pool-size = 10
web3sdk.max-pool-size = 20
web3sdk.queue-capacity = 50
web3sdk.keep-alive-seconds = 60
blockchain.orgid = weid
# amop.pub-path = amop/consumer_public_key.pem
# amop.pri-path = amop/consumer_private_key.p12
#amop.p12-password = 123456
amop.id = weid
cns.contract.follow = true
```

Java代码

```java
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
```



构建项目：

```sh
mvn clean package
```

运行项目

```sh
java -jar target/weid-demo-1.0.0-jar-with-dependencies.jar
```

