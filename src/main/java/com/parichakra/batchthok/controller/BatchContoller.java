package com.parichakra.batchthok.controller;

import java.util.Map;
import java.util.Properties;

import org.springframework.batch.core.launch.JobOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jobs")
public class BatchContoller {
	
	@Autowired
    private JobOperator jobOperator;

    @PostMapping("/start/{jobName}")
    public String startJob(@PathVariable String jobName,
                          @RequestParam Map<String, String> params) throws Exception {
        Properties jobParams = new Properties();
        jobParams.putAll(params);
        Long executionId = jobOperator.start(jobName, jobParams);
        return "Job started with executionId: " + executionId;
    }

//    @PostMapping("/stop/{executionId}")
//    public String stopJob(@PathVariable Long executionId) throws Exception {
//        jobOperator.stop(executionId);
//        return "Job stopped: " + executionId;
//    }
//
//    @PostMapping("/restart/{executionId}")
//    public String restartJob(@PathVariable Long executionId) throws Exception {
//        Long newExecutionId = jobOperator.restart(executionId);
//        return "Job restarted with new executionId: " + newExecutionId;
//    }
}