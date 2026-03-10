package com.parichakra.batchthok.reader;

import java.io.FileInputStream;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.batch.infrastructure.item.ItemReader;

import com.parichakra.batchthok.model.Student;

public class BatchReader implements ItemReader<Student> {
    private Iterator<Row> rowIterator;

//    public ExcelStudentReader(String filePath) throws Exception {
//        FileInputStream fis = new FileInputStream(filePath);
//        Workbook workbook = WorkbookFactory.create(fis);
//        Sheet sheet = workbook.getSheetAt(0);
//        this.rowIterator = sheet.iterator();
//        if (rowIterator.hasNext()) rowIterator.next(); // skip header
//    }
    public BatchReader(String filePath) throws Exception {
        // Open and close the file immediately
        try (FileInputStream fis = new FileInputStream(filePath)) {
            Workbook workbook = WorkbookFactory.create(fis);
            Sheet sheet = workbook.getSheetAt(0);
            this.rowIterator = sheet.iterator();
            if (rowIterator.hasNext()) rowIterator.next(); // skip header
        }
        // At this point, fis and workbook are closed, so the file is not locked anymore
    }

    
    @Override
    public Student read() {
        if (!rowIterator.hasNext()) return null;

        Row row = rowIterator.next();
        Student student = new Student();

        student.setFirstName(row.getCell(0).getStringCellValue());
        student.setLastName(row.getCell(1).getStringCellValue());
        student.setGender(row.getCell(2).getStringCellValue());

        student.setAge(getIntValue(row.getCell(3)));

        student.setEmail(row.getCell(4).getStringCellValue());
        student.setEnglish(getIntValue(row.getCell(5)));
        student.setScience(getIntValue(row.getCell(6)));
        student.setMath(getIntValue(row.getCell(7)));

        return student;
    }

    private int getIntValue(Cell cell) {
        if (cell == null) return 0;
        switch (cell.getCellType()) {
            case NUMERIC:
                return (int) cell.getNumericCellValue();
            case STRING:
                try {
                    return Integer.parseInt(cell.getStringCellValue());
                } catch (NumberFormatException e) {
                    return 0; 
                }
            default:
                return 0;
        }
    }

}