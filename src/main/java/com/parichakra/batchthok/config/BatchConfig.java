package com.parichakra.batchthok.config;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.parameters.RunIdIncrementer;
import org.springframework.batch.core.listener.JobExecutionListener;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemReader;
import org.springframework.batch.infrastructure.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.parichakra.batchthok.model.Student;
import com.parichakra.batchthok.processor.BatchProcessor;
import com.parichakra.batchthok.reader.BatchReader;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Bean
    public ItemReader<Student> reader() throws Exception {
        return new BatchReader("/home/bikram/opt/excelSource/students.xlsx");
    }
    @Bean
    public BatchProcessor processor() {
        return new BatchProcessor();
    }

    @Bean
    public Step studentStep(JobRepository jobRepository,
                            PlatformTransactionManager transactionManager,
                            ItemReader<Student> reader,
                            BatchProcessor processor,
                            @Qualifier("jdbcStudentWriter") JdbcBatchItemWriter<Student> writer) {
        return new StepBuilder("studentStep", jobRepository)
                .<Student, Student>chunk(10, transactionManager)
                .listener(new JobExecutionListener() {
                	@Override
       			    public void beforeJob(JobExecution jobExecution) {
       			        System.out.println("Job starting: Meow " + jobExecution.getJobInstance().getJobName());
       			    }

       			    @Override
       			    public void afterJob(JobExecution jobExecution) {
       			        System.out.println("Job finished with status: " + jobExecution.getStatus());
       			    }
				})
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Job importStudentJob(JobRepository jobRepository, Step studentStep) {
        return new JobBuilder("importStudentJob", jobRepository)
        		.listener(new JobExecutionListener() {
       			 	@Override
       			    public void beforeJob(JobExecution jobExecution) {
       			        System.out.println("Job starting: " + jobExecution.getJobInstance().getJobName());
       			    }

       			    @Override
       			    public void afterJob(JobExecution jobExecution) {
       			        System.out.println("Job finished with status: " + jobExecution.getStatus());
       			    }
				})
        		.preventRestart()
                .incrementer(new RunIdIncrementer())
                .flow(studentStep)
                
                .end()
                .build();
    }
    
}