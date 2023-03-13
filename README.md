# 阿里云OSS
需要自己写个阿里云OSSUtils类
例如：
```java
@Component
public class OssUtil implements InitializingBean {
    private String endpoint = "http://xxx.com";
    private String accessKeyID = "xxxx";
    private String accesskeySecret = "xxx";
    private String bucketName = "xxxxx";

    public static String END_POINT;
    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;
    public static String BUCKET_NAME;

    @Override
    public void afterPropertiesSet() throws Exception {
        END_POINT = endpoint;
        ACCESS_KEY_ID = accessKeyID;
        ACCESS_KEY_SECRET = accesskeySecret;
        BUCKET_NAME = bucketName;
    }
}