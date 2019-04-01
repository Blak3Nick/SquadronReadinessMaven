import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EPRData {
    private ArrayList<String[]> eprData = new ArrayList<>();
    private String lastName;
    private String firstName;
    private String filePath = fxGUI.filePath;
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    public int numberOfEPRSTracking =0;

    public EPRData(String lastName, String firstName) {
        this.lastName = lastName.toUpperCase();
        this.firstName = firstName.toUpperCase();
        try {
            findMemberDueOverdue();
        } catch (Exception e) {
            System.out.println("error with the epr file");
            e.printStackTrace();
        }
    }

    public void findMemberDueOverdue() throws IOException, NullPointerException {
        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(filePath + "EPR.xls"));
        HSSFSheet sheet = workbook.getSheetAt(0);
        HSSFRow row;

        int rowCount = sheet.getPhysicalNumberOfRows();
        for (int i =0; i < rowCount; i++) {
            String lastNameSheet = "";
            String firstNameSheet = "";
            row = sheet.getRow(i);
            String pending = "";
            try {
                 lastNameSheet = row.getCell(6).getStringCellValue();
                 firstNameSheet = row.getCell(4).getStringCellValue();
                 pending = row.getCell(7).getStringCellValue();
            } catch (Exception e) {
                System.out.println("error with the epr file");
            }

            if ((lastNameSheet.equalsIgnoreCase(lastName) && firstNameSheet.equalsIgnoreCase(firstName)) || (pending.contains(lastName) && pending.contains(firstName))) {
                processEPR(row);
            }
        }
    }
    public void processEPR(HSSFRow row) {
        String[] array = new String[9];
        numberOfEPRSTracking++;
        for (int i=0; i< 9; i++) {

            HSSFCell cell = row.getCell(i);
            CellType type =  cell.getCellType();
            if(type.equals(CellType.NUMERIC)) {
                if (i <2 ) {
                    Date date = row.getCell(i).getDateCellValue();
                    array[i] = dateFormat.format(date);
                }
                else {
                    int num = (int) row.getCell(i).getNumericCellValue();
                    array[i] = Integer.toString(num);
                }
            }
            else {
                array[i] = row.getCell(i).getStringCellValue();
            }
        }
        eprData.add(array);
    }

    public ArrayList<String[]> getEprData() {
        return eprData;
    }
}
