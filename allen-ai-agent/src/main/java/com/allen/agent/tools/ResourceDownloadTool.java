package com.allen.agent.tools;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.allen.agent.constant.FileConstant;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

import java.io.File;
import java.util.Set;

/**
 * 资源下载工具
 */
public class ResourceDownloadTool {
    
    private static final Set<String> IMAGE_EXTENSIONS = Set.of(".jpg", ".jpeg", ".png", ".gif", ".bmp", ".webp");

    @Tool(description = "Download a resource from a given URL")
    public String downloadResource(@ToolParam(description = "URL of the resource to download") String url, @ToolParam(description = "Name of the file to save the downloaded resource") String fileName) {
        String fileDir = FileConstant.FILE_SAVE_DIR + "/download";
        String filePath = fileDir + "/" + fileName;
        try {
            // 创建目录
            FileUtil.mkdir(fileDir);
            // 使用 Hutool 的 downloadFile 方法下载资源
            HttpUtil.downloadFile(url, new File(filePath));
            
            // 检查是否为图片文件
            if (isImageFile(fileName)) {
                // 返回特殊格式，前端可以识别并显示图片
                return "{\"type\": \"image\", \"path\": \"" + filePath.replace("\\", "/") + "\"}";
            } else {
                return "Resource downloaded successfully to: " + filePath;
            }
        } catch (Exception e) {
            return "Error downloading resource: " + e.getMessage();
        }
    }
    
    /**
     * 判断文件是否为图片文件
     * @param fileName 文件名
     * @return 是否为图片文件
     */
    private boolean isImageFile(String fileName) {
        if (StrUtil.isBlank(fileName)) {
            return false;
        }
        String lowerFileName = fileName.toLowerCase();
        return IMAGE_EXTENSIONS.stream().anyMatch(lowerFileName::endsWith);
    }
}