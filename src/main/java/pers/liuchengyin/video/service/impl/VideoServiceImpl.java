package pers.liuchengyin.video.service.impl;

import com.aliyun.vod.upload.UploadVideo;
import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pers.liuchengyin.video.service.VideoService;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import pers.liuchengyin.video.util.ConstantVodUtils;
import pers.liuchengyin.video.util.InitVodClient;
import java.io.InputStream;
import java.util.List;

/**
 * @ClassName VideoServiceImpl
 * @Description 阿里云视频点播服务Service
 * @Author 柳成荫
 */
@Service
public class VideoServiceImpl implements VideoService {
    @Override
    public String uploadAlyVideo(MultipartFile file) {
        try{
            // 视频 - 源文件名
            String fileName = file.getOriginalFilename();
            // 标题 - 上传到阿里云的文件标题
            String title = fileName.substring(0, fileName.lastIndexOf("."));
            // 上传文件的输入流
            InputStream inputStream = file.getInputStream();
            // 实际这个request还可以设置更多参数，更多请见官方文档
            // https://help.aliyun.com/document_detail/53406.html?spm=a2c4g.11186623.6.1065.5e5d3bd9a6AtYl
            UploadStreamRequest request = new UploadStreamRequest(
                    ConstantVodUtils.ACCESS_KEY_ID,
                    ConstantVodUtils.ACCESS_KEY_SECRET,
                    title,
                    fileName,
                    inputStream
            );
            UploadVideo uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            // VideoId
            String videoId = null;
            if (response.isSuccess()){
                // 上传成功
                videoId = response.getVideoId();
            }else{
                // 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                // ....
                videoId = response.getVideoId();
            }
            // 将Video的ID返回
            return videoId;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void removeMoreAlyVideo(List<String> videoList) throws Exception {
        try{
            DefaultAcsClient client = InitVodClient.initVodCredentialsClient(ConstantVodUtils.ACCESS_KEY_ID,ConstantVodUtils.ACCESS_KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            // videoList转换成以逗号隔开的字符串
            String videoIds = StringUtils.join(videoList.toArray(),",");
            request.setVideoIds(videoIds);
            client.getAcsResponse(request);
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("删除视频失败！");
        }
    }
}
