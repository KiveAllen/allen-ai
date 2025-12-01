package com.allen.agent.tools;

import cn.hutool.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

/**
 * 网页抓取工具
 */
public class WebScrapingTool {

    @Tool(description = "Scrape the content of a web page")
    public String scrapeWebPage(@ToolParam(description = "URL of the web page to scrape") String url) {
        try {
            Document document = Jsoup.connect(url).get();
            
            // 提取关键信息
            String title = document.title();
            String text = document.body().text();
            
            // 限制文本长度，避免返回过多内容
            if (text.length() > 2000) {
                text = text.substring(0, 2000) + "...";
            }
            
            // 返回格式化的JSON结果，便于前端处理
            JSONObject resultObject = new JSONObject();
            resultObject.set("type", "scrape");
            resultObject.set("url", url);
            resultObject.set("title", title);
            resultObject.set("content", text);
            return resultObject.toString();
        } catch (Exception e) {
            JSONObject errorObject = new JSONObject();
            errorObject.set("type", "error");
            errorObject.set("message", "Error scraping web page: " + e.getMessage());
            return errorObject.toString();
        }
    }
}