package com.chigolite.jobtracker_application_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationStats {
    private long total;
    private long applied;
    private long interviewScheduled;
    private long interviewed;
    private long offerReceived;
    private long accepted;
    private long rejected;
    private long withdrawn;
}
