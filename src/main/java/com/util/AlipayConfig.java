package com.util;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by upupgogogo on 2018/11/21.下午2:28
 */
public class AlipayConfig {

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2018040302494883";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCWIieo2pC8wm0khQY/HSOGhtmPHvLrGKZIrGGCVXwN5cHWzSN+v2oTNe06vrUd6KsQ6UMd83PI5l656l6O7ynBbaCcowN+zi+NoXZzk2SSDkklFWg3EahDxtKK6JIl9PPVSlefjcs6/ICMvy7ziWsb0NaWuiMthyScbkkUWAdxHaBZiazdC2W4FZ02dbvBFID3BsO31nNRKANd0HZCLMmMmmYATXY2UpAsEmSXkDNZ//w9V1KWmOdRh7yy/TVxnPSG/ErcuQZLqAuh2P1WweG/IPEZ7IxYowCSFIYjRSoWOt0o5UymLkeZuM765eNUND8B9NHbZac9NT7bwhBvHiqHAgMBAAECggEAFl4YUovdrIG7CQnYmGaa+Fv25d/SiTwu6fzkuipKRTfJ3VrKwwN5pksOgQYQrdgQRIC8QyYWRgQscL5+QAVZzzuqBiwzRR0q8Irxvt5iyW/fber2j93Nl+tzSsbDn2wTN9/PljHl6W/dJxsEv7vlfUtr7SDmRM4xoiy8Lo++7BcmmCPdBX3bMear57Nhvb9Eft4mM35WN3fpP9tj2GZQWUzm8l7gRwDifVO470Vbmzpj8JUWfBEuvIKp/iIjbhRiM4PxS7DFarhVVaY9G1AMwl2LdWcAy4/7yyQcdhTatKK+gJcVtvc8SMfGPKpn1XgPoFz92kEw9sar2YoAFYCBAQKBgQDo2iY+/Zn3PiycowBZhRasmxJ8YfVmf/IYkXiiT5vw7VgfBs3KjkeKphZm6arZJaEuTkHa2b64ZVzW473/5uuFJJL8k+ExLivbPAk79KEbruKAx41Z3D2VzPRLXOLxty8oFJLY5TTHk9q3BMgPmwURimB3EzIojOaHlXMkj0oEYQKBgQClDuXdAT4GV3ZTkymgSnAVo+8cm7Y3WWM0UltL7hq8vEbHx716NgNucgAFkkDwDj+Sz7MDtzKf1B1NnW2F3s7K8WebRb9eGGe8qyUoA+t+yjtZfJJsaiCiy9ckYQo1mv8KQWBx6st6x1xIt31AEAYJsY8oW12s1acOhnF80x+X5wKBgQCLPO+D2Tf5LXeVzq7F2Rye6DfcouMQgM+uu0ZMoJgaMILyQqpKRlwnX3uj3HRqVuMUAn3EtVIjh5FFenfCDz6kpiMy3+bX9skI1tSawoSYPjJ5WVp/8n/tFNq8OfYHHkgXin3pLt4pvZMav8Q1+LfGLl3StmrW7rJnPwnUQc674QKBgBMFJHfTLNKOQZRf4OwXp9LWkTc6ukSmiEf/NYYKagpVp9JdsffjQYH4oQgDUnhXK/w+8uH6ulqwlq/a9EP46Nt59fZukx7EsS+MiTuTOA/kPfKR6E2V7SzVUHVguUx0D++emP6Hj+sWDgxTGJvJPOjhDR4ZaTIBbL6/v1QjAAEBAoGBAL2eqN1fDd8Sa8hvGnUEvsQLyl8Fd9DSIlXgQQBPDQ1kLmmhseQvphaOplP5eNsXFsoUiAauUJ+bIYdT1AquceOGOuPjXdTnyQy6O+fkfC7kPQKy4Uiff6gOiSiHRB3IoF88kYc1ZMoNsxyghwRD34wc6wNSPi33pmvXFyKMQAzJ";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmYnqyMIojGHeqbnUkFdjGYA2b86NdfwvpWmC/Y0U/LTY/+YfiEKalsWtUDYJgy0otimjsadoe36O/mMBbSKY5hL6slajXdks+1GUiezeV2+nTfmUKmhgn66KjfQOW/uv4pgPkf+1nmSgmo1d7/cbBQeUSjaBoJmgkYhf1GR1O4YNBigMEVyQq5LsbH40gpIA6sFMJGgV/PP7op6E1e4wYgRoYcP4PRGY/jma/WMOsf5HKyyBK2z82jgZF4pkDi9x5vJquzoJS6Z+6pMxblARc4t5gLtlYEKzVHvIkbVKa0xzXtBI+YMqWtb22SAIgvXIpJmVKEx4P7tKuEdCv0OOYQIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://193.112.26.167:8080/o2o/customer/orderInfo/callback.do";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "o2o.upupgogogo.cn/index/paySuccess.html";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipay.com/gateway.do";

    // 支付宝网关
    public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
