package com.parichakra.batchthok.processor;

import org.springframework.batch.infrastructure.item.ItemProcessor;

import com.parichakra.batchthok.model.Student;

public class BatchProcessor implements ItemProcessor<Student, Student> {
    @Override
    public Student process(Student student) {
        student.setFirstName(student.getFirstName().toUpperCase());
        student.setLastName(student.getLastName().toUpperCase());
        return student;
    }
}