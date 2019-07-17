package com.utils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Font;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ExcelUtil {
    public static void exportExcel(HttpServletResponse response, String fileName, String[] titles, List<Map<String, Object>> result) {

        HSSFWorkbook wb;
        OutputStream output = null;
        try {
            wb = new HSSFWorkbook();
            //创建sheet
            HSSFSheet sh = wb.createSheet(fileName);

            Date date = new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            fileName += "_" + df.format(date) + ".xls";

            HSSFCellStyle style_title = wb.createCellStyle();
            Font titleFont = wb.createFont();
            titleFont.setItalic(true);
            titleFont.setColor(Font.COLOR_NORMAL);
            titleFont.setFontHeightInPoints((short) 12);
            titleFont.setFontName("仿宋");
            titleFont.setUnderline(Font.U_NONE);
            style_title.setFont(titleFont);

            HSSFCellStyle style_common = wb.createCellStyle();
            Font common_font = wb.createFont();
            common_font.setColor(Font.COLOR_NORMAL);
            common_font.setFontHeightInPoints((short) 10);
            common_font.setFontName("微软雅黑");
            style_common.setFont(common_font);


            // 设置列宽
            for (int i = 0; i < titles.length - 1; i++) {
                sh.setColumnWidth(i, 256 * 15 + 184);
            }

            HSSFRow row = sh.createRow(0);
            HSSFCell cell = null;
            /**
             * 表头
             //HSSFCell cell = row.createCell(0);
             //cell.setCellValue(new HSSFRichTextString(tempName));
             //cell.setCellStyle(style);
             //sh.addMergedRegion(new CellRangeAddress(0, 0, 0, titles.length - 1));
             **/

            // 第1行
            HSSFRow row3 = sh.createRow(0);

            // 第1行的列
            for (int i = 0; i < titles.length; i++) {
                cell = row3.createCell(i);
                cell.setCellValue(new HSSFRichTextString(titles[i]));
                cell.setCellStyle(style_title);
                sh.autoSizeColumn(i, true);
            }

            //填充数据的内容
            int i = 1, z = 0;
            while (z < result.size()) {
                row = sh.createRow(i);
                Map<String, Object> map = result.get(z);
                for (int j = 0; j < titles.length; j++) {
                    cell = row.createCell(j);
                    if (map.get(titles[j]) != null) {
                        cell.setCellValue(map.get(titles[j]).toString());
                        cell.setCellStyle(style_common);
                        sh.autoSizeColumn(j, true);
                    } else {
                        cell.setCellValue("");
                        cell.setCellStyle(style_common);
                        sh.autoSizeColumn(j, true);
                    }
                }
                i++;
                z++;
            }

            /**
             for(int k=0; k<result.get(0).size();k++){
             sh.autoSizeColumn((short)k);
             }
             **/

            output = response.getOutputStream();
            response.reset();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/vnd.ms-excel;charset=utf-8");// 设置contentType为excel格式
            response.setHeader("Content-Disposition", "Attachment;Filename=" + new String(fileName.getBytes(), "iso-8859-1"));
            wb.write(output);
            output.flush();
            output.close();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
