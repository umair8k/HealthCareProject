package com.hc.controller;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import com.hc.model.EmailRequest;
import com.hc.quartzjob.EmailJob;

public class EmailReminederController {

	
	
	
	
	private JobDetail buildJobDetail(EmailRequest reminederEmail) {
		
		JobDataMap jobDataMap=new JobDataMap();
		
		jobDataMap.put("to",reminederEmail.getTo());
		jobDataMap.put("subject",reminederEmail.getSubject());
		jobDataMap.put("body",reminederEmail.getBody());
		
		return JobBuilder.newJob(EmailJob.class)
				.withIdentity(UUID.randomUUID().toString())
				.withDescription("send Email")
				.usingJobData(jobDataMap)
				.storeDurably()
				.build();
	}
	
	private Trigger builTrigger(JobDetail jobDetail, ZonedDateTime startAt) {
		return TriggerBuilder.newTrigger()
				.forJob(jobDetail)
				.withIdentity(jobDetail.getKey().getName())
				.withDescription("send email trigger")
				.startAt(Date.from(startAt.toInstant()))
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
				.build();
				
	}
}
