package com.example.webhookclient.controller;

import com.example.webhookclient.service.MergeReqService;
import com.example.webhookclient.utils.HttpUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class WebhookController {

    @Autowired
    MergeReqService mergeReqService;

    @PostMapping("/web-hook/receive")
    public String receiveWebhook(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String body = HttpUtils.getBody(request);
        return mergeReqService.acceptMergeReq(body);
    }


}
