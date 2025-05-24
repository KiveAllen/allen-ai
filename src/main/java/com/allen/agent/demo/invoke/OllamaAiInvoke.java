package com.allen.agent.demo.invoke;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.boot.CommandLineRunner;

// 取消注释即可在 SpringBoot 项目启动时执行
//@Component
public class OllamaAiInvoke implements CommandLineRunner {

    @Resource
    private ChatModel ollamaChatModel;

    @Override
    public void run(String... args) {
        AssistantMessage output = ollamaChatModel.call(new Prompt("用户：帮我写一首关于春天的短诗\n"))
                .getResult()
                .getOutput();
        System.out.println(output.getText());
    }
}
