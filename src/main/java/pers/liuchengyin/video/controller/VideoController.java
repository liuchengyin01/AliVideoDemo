package pers.liuchengyin.video.controller;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pers.liuchengyin.video.service.VideoService;
import pers.liuchengyin.video.util.ConstantVodUtils;
import pers.liuchengyin.video.util.InitVodClient;

import java.util.List;

/**
 * @ClassName VideoController
 * @Description 阿里云视频点播服务Controller
 * @Author 柳成荫
 */
@RestController
@RequestMapping("/video")
public class VideoController {
    @Autowired
    private VideoService videoService;

    /**
     * 上传视频到阿里云
     * @param file 上传的视频文件
     */
    @PostMapping("/uploadAlyVideo")
    public String uploadAlyVideo(MultipartFile file){
        return videoService.uploadAlyVideo(file);
    }

    /**
     * 删除阿里云视频
     * @param id 视频id
     */
    @DeleteMapping("/removeAlyVideo/{id}")
    public String removeAlyVideo(@PathVariable String id) throws Exception {
        try {
            DefaultAcsClient client = InitVodClient.initVodCredentialsClient(ConstantVodUtils.ACCESS_KEY_ID,ConstantVodUtils.ACCESS_KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            // 设置视频id
            request.setVideoIds(id);
            // 删除
            client.getAcsResponse(request);
            return "删除成功！";
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("删除视频失败！可以尝试重新删除文件！");
        }
    }

    /**
     * 删除多个阿里云视频的方法
     * @param videoList 视频id集合
     */
    @DeleteMapping("/delete-batch")
    public String deleteBatch(@RequestParam("videoList") List<String> videoList) throws Exception {
        videoService.removeMoreAlyVideo(videoList);
        return "删除成功！";
    }

    /**
     * 获取视频播放凭证
     * @param id 视频id
     * @return
     * @throws Exception
     */
    @GetMapping("/getPlayAuth/{id}")
    public GetVideoPlayAuthResponse getPlayAuth(@PathVariable String id) throws Exception {
        try {
            // 创建初始化对象
            DefaultAcsClient client =
                    InitVodClient.initVodCredentialsClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            // 创建获取凭证request和response对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            // 向request设置视频id
            request.setVideoId(id);
            // 调用方法得到凭证
            // 凭证
            return client.getAcsResponse(request);
        }catch (Exception e){
            throw new Exception("获取凭证失败！");
        }
    }

    /**
     * 获取视频播放地址
     * @param id 视频id
     * @return
     * @throws Exception
     */
    @GetMapping("/getPlayAuthIA/{id}")
    public String getUrl(@PathVariable String id) throws Exception {
        try{
            IAcsClient client = InitVodClient.initVodUrlClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            CommonRequest request = new CommonRequest();
            request.setSysMethod(MethodType.POST);
            request.setSysDomain("vod.cn-shanghai.aliyuncs.com");
            request.setSysVersion("2017-03-21");
            request.setSysAction("GetPlayInfo");
            request.putQueryParameter("VideoId", id);
            CommonResponse response = client.getCommonResponse(request);
            // 返回的是一个JSON字符串，可以将其转换为对应的对象，然后获取URL
            return response.getData();
        } catch (ClientException e) {
            e.printStackTrace();
            throw new Exception("获取视频信息失败！");
        }
    }
}
