package com.parichakra.batchthok.writer;

import javax.sql.DataSource;

import org.springframework.batch.infrastructure.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.infrastructure.item.database.JdbcBatchItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.parichakra.batchthok.model.Student;

@Configuration

public class BatchWriter {
	@Bean(name = "jdbcStudentWriter")
    public JdbcBatchItemWriter<Student> studentWriter(DataSource dataSource) {
        JdbcBatchItemWriter<Student> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());

 
        writer.setSql("INSERT INTO student (first_name, last_name, gender, age, email, english, science, math) " +
                "VALUES (:firstName, :lastName, :gender, :age, :email, :english, :science, :math) " +
                "ON DUPLICATE KEY UPDATE " +
                "first_name = VALUES(first_name), " +
                "last_name = VALUES(last_name), " +
                "gender = VALUES(gender), " +
                "age = VALUES(age), " +
                "english = VALUES(english), " +
                "science = VALUES(science), " +
                "math = VALUES(math)");


        writer.setDataSource(dataSource);
        return writer;
    }
}