package com.sprve.ai.service.impl;
import com.sprve.ai.service.AiService;
import com.sprve.ai.utils.HttpUtils;
import org.springframework.stereotype.Service;
import java.util.HashMap;
@Service
public class AiServiceImpl implements AiService {
    public String getMessage(String role,String question){
        HashMap<String, String> headers = new HashMap();
        String requestUrl = "https://test.sprve.com/v1/chat/completions";
        String jsonStr="{\n" +
                 "\"model\": \"gpt-3.5-turbo\",\n" +
                 "\"max_tokens\":2000,\n" +
                 "\"messages\": [\n" +
                 "    {\n" +
                 "    \"role\": \"system\",\n" +
                 "    \"content\": \""+role+"\"\n" +
                 "    },\n" +
                 "    {\n" +
                 "    \"role\": \"user\",\n" +
                 "    \"content\": \""+question+"\"\n" +
                 "    }\n" +
                 "    ]\n" +
                 " }";
        headers.put("content-type", "application/json");
        headers.put("Authorization"," ");
        String answer = HttpUtils.sendPostWithJson(requestUrl, jsonStr,headers);
        String [] first = answer.split("\"content\": \"");
        String [] second = first[1].split("\"");
        answer=second[0];
        return answer;
    }
}
