package com.example.webhookclient.service;

import com.example.webhookclient.config.Const;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class MergeReqService {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    String accessToken;

    static final String GITLAB_BASEURL = "https://gitlab.com/api/v4/";
    static final String MERGE_ACTION = "projects/%s/merge_requests/%s/merge";
    static final String TOKEN_HEADER = "PRIVATE-TOKEN";

    public String acceptMergeReq(String mergeData) {
        try {
            Map mrData = objectMapper.readValue(mergeData, Map.class);
            Map project = (Map) mrData.get("project");
            Map mergeReq = (Map) mrData.get("object_attributes");
            Map assignees = (Map)((List) mrData.get("assignees")).get(0);

            String projectId = String.valueOf(project.get("id"));
            String mergeReqIid = String.valueOf(mergeReq.get("iid"));
            String targetBranch = (String)mergeReq.get("target_branch");
            String assignName = (String)assignees.get("username");
            Integer state = (Integer)mergeReq.get("state_id");

            System.out.printf("========== project id : %s, merge iid : %s, targetBranch : %s, assignee : %s ==========",
                    projectId,
                    mergeReqIid,
                    targetBranch,
                    assignName);

            if(targetBranch.equals("develop") && assignName.equals("HoanNgHuy183920") && state == Const.MergeReqState.OPENED.getValue()) {
                String url = GITLAB_BASEURL + String.format(MERGE_ACTION, projectId, mergeReqIid);
                RestTemplate restTemplate = new RestTemplate();
                HttpHeaders headers = new HttpHeaders();
                headers.add(TOKEN_HEADER, accessToken);
                HttpEntity httpEntity = new HttpEntity(headers);
                restTemplate.exchange(url, HttpMethod.PUT, httpEntity, Object.class);
            }

            return "success";
        }
        catch (Exception e) {
            System.out.printf("========== failed : %s =======", e.getMessage());
            return "failed : " + e.getMessage();
        }
    }
}
