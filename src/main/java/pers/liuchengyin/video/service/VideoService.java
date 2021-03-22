package pers.liuchengyin.video.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @ClassName VideoService
 * @Description 阿里云视频点播服务Service
 * @Author 柳成荫
 */
public interface VideoService {
    /**
     * 上传视频
     * @param file 视频文件
     * @return
     */
    String uploadAlyVideo(MultipartFile file);

    /**
     * 批量删除视频文件
     * @param videoList
     */
    void removeMoreAlyVideo(List<String> videoList) throws Exception;
}
