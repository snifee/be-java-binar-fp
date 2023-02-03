package com.kostserver.config;

import com.kostserver.config.job.UpdateRoomJob;
import com.kostserver.model.entity.Transaction;
import org.quartz.*;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

public class SchedulerConfig {
    private JobDetail jobDetail(Transaction transaction){
        JobDataMap jobDataMap = new JobDataMap();

        jobDataMap.put("id",transaction.getId());
        jobDataMap.put("room_id",transaction.getRoomKost().getId());

        return JobBuilder.newJob(UpdateRoomJob.class)
                .withIdentity(UUID.randomUUID().toString(),"update-ended-transaction-jobs")
                .usingJobData(jobDataMap)
                .storeDurably()
                .build();
    }

    private Trigger buildTrigger(JobDetail jobDetail, ZonedDateTime startAt){
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(jobDetail.getKey().getName(),"room-triggers")
                .withDescription("update available room when transaction has end-rent outdated")
                .startAt(Date.from(startAt.toInstant()))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
                .build();
    }
}
