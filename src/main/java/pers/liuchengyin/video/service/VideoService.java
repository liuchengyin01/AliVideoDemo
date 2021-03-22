package pers.liuchengyin.video.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @ClassName VideoService
 * @Description
 * @Author 柳成荫
 * @Date 2021/3/21
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
