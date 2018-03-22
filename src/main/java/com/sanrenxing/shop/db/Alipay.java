package com.sanrenxing.shop.db;

import lombok.Data;

/**
 * Created on 2018/3/22.
 * @author tony
 */
@Data
public class Alipay {

    private String openApiDomain;   // 支付宝openapi域名

    private String mcloudApiDomain;  // 支付宝mcloudmonitor域名

    private String pid;             // 商户partner id

    private String appid;           // 商户应用id

    private String privateKey;      // RSA私钥，用于对商户请求报文加签

    private String publicKey;       // RSA公钥，仅用于验证开发者网关

    private String alipayPublicKey; // 支付宝RSA公钥，用于验签支付宝应答

    private String signType;     // 签名类型

    private int maxQueryRetry;   // 最大查询次数

    private long queryDuration;  // 查询间隔（毫秒）

    private int maxCancelRetry;  // 最大撤销次数

    private long cancelDuration; // 撤销间隔（毫秒）

    private long heartbeatDelay ; // 交易保障线程第一次调度延迟（秒）

    private long heartbeatDuration ; // 交易保障线程调度间隔（秒）
}
