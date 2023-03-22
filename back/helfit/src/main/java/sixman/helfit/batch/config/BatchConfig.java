package sixman.helfit.batch.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import sixman.helfit.batch.reader.QueueItemReader;
import sixman.helfit.domain.user.entity.User;
import sixman.helfit.domain.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static sixman.helfit.domain.user.entity.User.*;


@Configuration
@RequiredArgsConstructor
@Slf4j
public class BatchConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final UserRepository userRepository;

    @Bean
    public Job physicalJob() throws Exception {
        return jobBuilderFactory.get("inactiveUserJob")
                   .preventRestart()
                   .start(inactiveUserJob())
                   .build();
    }

    @Bean
    @JobScope
    public Step inactiveUserJob() throws Exception {
        return stepBuilderFactory.get("physicalStep")
                   .<User, User>chunk(10) // <In, Out>chunk()
                   .reader(inactiveUserReader(null))
                   .processor(inactiveUserProcessor())
                   .writer(inactiveUserWriter())
                   .build();
    }

    @Bean
    @StepScope
    public QueueItemReader<? extends User> inactiveUserReader(
        @Value("#{jobParameters[requestDate]}") final String requestDate
    ) throws Exception {
        log.info("reader = {}", requestDate);

        List<User> byModifiedAtAndUserStatusEquals = userRepository.findByModifiedAtBeforeAndUserStatusEquals(
                LocalDateTime.of(LocalDateTime.now().toLocalDate().minusYears(1), LocalTime.MIN),
                UserStatus.USER_ACTIVE
            );

        return new QueueItemReader<>(byModifiedAtAndUserStatusEquals);
    }

    @Bean
    @StepScope
    public ItemProcessor<? super User, ? extends User> inactiveUserProcessor() {
        return new ItemProcessor<User, User>() {
            @Override
            public User process(User user) throws Exception {
                user.setUserStatus(UserStatus.USER_INACTIVE);
                return user;
            }
        };
    }

    @Bean
    @StepScope
    public ItemWriter<? super User> inactiveUserWriter() {
        return userRepository::saveAll;
    }
}
