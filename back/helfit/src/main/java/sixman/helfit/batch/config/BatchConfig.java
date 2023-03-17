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
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import sixman.helfit.domain.physical.entity.Physical;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;

// @Configuration
@RequiredArgsConstructor
@Slf4j
public class BatchConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    // exampleJob 생성
    @Bean
    public Job physicalJob() throws Exception {
        return jobBuilderFactory.get("physicalJob")
                   .start(physicalStep()).build();
    }

    // exampleStep 생성
    @Bean
    @JobScope
    public Step physicalStep() throws Exception {
        return stepBuilderFactory.get("physicalStep").<Physical, Physical>chunk(10)
                   .reader(reader(null))
                   .processor(processor(null))
                   .writer(writer(null))
                   .build();
    }

    @Bean
    @StepScope
    public ItemReader<? extends Physical> reader(
        @Value("#{jobParameters[requestDate]}") String requestDate
    ) throws Exception {
        log.info("==> reader value : " + requestDate);

        Map<String, Object> parameterValues = new HashMap<>();

        return new JpaPagingItemReaderBuilder<Physical>()
                   .pageSize(10)
                   .parameterValues(parameterValues)
                   .queryString("SELECT p FROM Physical p WHERE p.userId = :userId")
                   .entityManagerFactory(entityManagerFactory)
                   .name("JpaPagingItemReader")
                   .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<? super Physical, ? extends Physical> processor(
        @Value("#{jobParameters[requestDate]}") String requestDate
    ) {
        return new ItemProcessor<Physical, Physical>() {
            @Override
            public Physical process(Physical physical) throws Exception {

                log.info("==> processor value : " + requestDate);
                log.info("==> processor Physical : " + physical);

                return physical;
            }
        };
    }

    @Bean
    @StepScope
    public ItemWriter<? super Physical> writer(
        @Value("#{jobParameters[requestDate]}") String requestDate
    ) {
        log.info("==> writer value : " + requestDate);

        return new JpaItemWriterBuilder<Physical>()
                   .entityManagerFactory(entityManagerFactory)
                   .build();
    }
}
