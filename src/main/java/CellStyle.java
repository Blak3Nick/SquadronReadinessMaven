import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.HashMap;
import java.util.Map;

public class CellStyle {
    XSSFWorkbook workbook;
    public Map<String, XSSFCellStyle> cellStyles = new HashMap<>();
    Font mainFont;
    XSSFCellStyle overDueStyle;
    XSSFCellStyle comingDueStyle;
    XSSFCellStyle headerStyle;
    XSSFCellStyle greyStyle;
    XSSFCellStyle yellowStyle;
    XSSFCellStyle lightGreen;

    CellStyle(XSSFWorkbook workbook) {
        this.workbook = workbook;
        mainFont = workbook.createFont();
        mainFont.setFontHeight((short)12);
        BorderStyle borderNew = BorderStyle.DASH_DOT;


        overDueStyle = workbook.createCellStyle();
        comingDueStyle = workbook.createCellStyle();
        headerStyle = workbook.createCellStyle();
        greyStyle = workbook.createCellStyle();
        yellowStyle = workbook.createCellStyle();
        lightGreen = workbook.createCellStyle();


        overDueStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
        overDueStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        comingDueStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        comingDueStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        headerStyle.setFillForegroundColor(IndexedColors.CORNFLOWER_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        greyStyle.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
        greyStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        yellowStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        yellowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        lightGreen.setFillForegroundColor(IndexedColors.AQUA.getIndex());
        lightGreen.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        cellStyles.put("overdue", overDueStyle);
        cellStyles.put("comingDue", comingDueStyle);
        cellStyles.put("header", headerStyle);
        cellStyles.put("greyStyle", greyStyle);
        cellStyles.put("yellowStyle", yellowStyle);
        cellStyles.put("lightGreen", lightGreen);

    }


}
