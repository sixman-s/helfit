package sixman.helfit.batch.schedulers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

@EnableScheduling
@Component
@RequiredArgsConstructor
@Slf4j
public class InactiveUserScheduler {
    private final Job job;
    private final JobLauncher jobLauncher;
    // # 1Day
    private final int fixedDelay = 1000 * 60 * 60 * 24;

    @Scheduled(fixedDelay = fixedDelay)
    public void startJob() {
        try {
            JobParameters parameters = new JobParameters(new HashMap<>() {{
                put(
                    "requestDate",
                    new JobParameter(
                        LocalDateTime.now()
                            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                    )
                );
            }});

            JobExecution jobExecution = jobLauncher.run(job, parameters);

            while (jobExecution.isRunning()) log.info("Jobs isRunning...");
        } catch (
              JobExecutionAlreadyRunningException
              | JobRestartException
              | JobInstanceAlreadyCompleteException
              | JobParametersInvalidException e
        ) {
            e.printStackTrace();
        }
    }
}
