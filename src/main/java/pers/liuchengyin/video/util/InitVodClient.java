package pers.liuchengyin.video.util;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;

/**
 * @ClassName InitVodClient
 * @Description 阿里云视频点播服务 - 初始化
 * @Author 柳成荫
 */
public class InitVodClient {

    /** 点播服务接入区域 */
    public static final String REGION_ID = "cn-shanghai";

    /**
     * 获取视频播放凭证的Client
     * @param accessKeyId accessKeyId
     * @param accessKeySecret accessKeySecret
     * @return
     * @throws ClientException
     */
    public static DefaultAcsClient initVodCredentialsClient(String accessKeyId, String accessKeySecret) throws ClientException {
        DefaultProfile profile = DefaultProfile.getProfile(REGION_ID, accessKeyId, accessKeySecret);
        return new DefaultAcsClient(profile);
    }

    /**
     * 获取视频播放地址的Client
     * @param accessKeyId accessKeyId
     * @param accessKeySecret accessKeySecret
     * @return
     * @throws ClientException
     */
    public static IAcsClient initVodUrlClient(String accessKeyId, String accessKeySecret) throws ClientException {
        DefaultProfile profile = DefaultProfile.getProfile(REGION_ID, accessKeyId, accessKeySecret);
        return new DefaultAcsClient(profile);
    }
}
