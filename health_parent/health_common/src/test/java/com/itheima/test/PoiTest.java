package com.itheima.test;

import com.itheima.utils.POIUtils;
import com.itheima.utils.QiniuUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.*;
import java.util.Date;

public class PoiTest {
    @Test
    public void test1() {
        HSSFWorkbook xssfWorkbook = null;
        try {
            xssfWorkbook = new HSSFWorkbook(new FileInputStream(new File("G:\\poi.xls")));
            HSSFSheet sheetAt = xssfWorkbook.getSheetAt(0);
            for (Row row : sheetAt) {
                for (Cell cell : row) {
                    System.out.println(cell);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            try {
                xssfWorkbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Test
    public void test2() {
        HSSFWorkbook xssfWorkbook = null;
        try {
            xssfWorkbook = new HSSFWorkbook(new FileInputStream(new File("G:\\poi.xls")));
            HSSFSheet sheetAt = xssfWorkbook.getSheetAt(0);
            int lastRowNum = sheetAt.getLastRowNum();
            for (int i = 0; i <= lastRowNum; i++) {
                HSSFRow row = sheetAt.getRow(i);
                short lastCellNum = row.getLastCellNum();
                for (int j = 0; j < lastCellNum; j++) {
                    HSSFCell cell = row.getCell(j);
                    System.out.println(cell);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                xssfWorkbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Test
    public void test3() throws IOException {
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        HSSFSheet sheet = hssfWorkbook.createSheet("用户表");
        HSSFRow row1 = sheet.createRow(0);
        row1.createCell(0).setCellValue("姓名");
        row1.createCell(1).setCellValue("地址");
        row1.createCell(2).setCellValue("年龄");
        HSSFRow row2 = sheet.createRow(1);
        row2.createCell(0).setCellValue("罗国辉");
        row2.createCell(1).setCellValue("地狱");
        row2.createCell(2).setCellValue("-100");
        FileOutputStream fileOutputStream = new FileOutputStream(new File("g:\\a.xls"));
        hssfWorkbook.write(fileOutputStream);
    }

    @Test
    public void test4() {
        Date date = new Date("2019/11/23");
        System.out.println(date);

    }
}
