import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MedicalData {
    //date, type, time,last, first, details
    private String[] medAppointment = new String[6];
    //name, rank(not used), dental class, dateExamDue, dateLastMilExam, nextExamType
    private String[] dentalStatus = new String[6];
    private String[] dueOverdue = new String[7];
    private String lastName;
    private String firstName;
    private String filePath;
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    boolean hasOverdueItems = false;
    boolean hasMedicalAppointment = false;

    public MedicalData(String lastName, String firstName) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.filePath = fxGUI.filePath;
        try {
            findMemberDueOverdue();
            findMemberMedAppointments();
            findMemberDentalInfo();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Could not open file.");
        } catch (NullPointerException e) {

        }
    }

    public void findMemberDueOverdue() throws IOException, NullPointerException {
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream( filePath + "DUEOVERDUE.xlsx"));
        XSSFSheet sheet = workbook.getSheetAt(0);
        XSSFRow row;
        int rowCount = sheet.getPhysicalNumberOfRows();
        for (int i=0; i < rowCount; i++) {
            row = sheet.getRow(i);
            String lastNameSheet = row.getCell(0).getStringCellValue();
            String firstNameSheet = row.getCell(1).getStringCellValue();

            if (lastNameSheet.equalsIgnoreCase(lastName) && firstNameSheet.equalsIgnoreCase(firstName)) {
                try {
                    processDueOverdue(row);
                    hasOverdueItems = true;
                } catch (NullPointerException ne) {

                }
            }
        }

    }
    public void findMemberMedAppointments() throws IOException, NullPointerException {
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream( filePath + "MEDAPT.xlsx"));
        XSSFSheet sheet = workbook.getSheetAt(0);
        XSSFRow row;
        int rowCount = sheet.getPhysicalNumberOfRows();

        for (int i=0; i < rowCount; i++) {
            row = sheet.getRow(i);

            String lastNameSheet;
            String firstNameSheet;
            try {
                 lastNameSheet = row.getCell(3).getStringCellValue();
                 firstNameSheet = row.getCell(4).getStringCellValue();
            }
            catch (NullPointerException ne) {
                lastNameSheet= "error";
                firstNameSheet = "error";
            }


            if (lastNameSheet.equalsIgnoreCase(lastName) && firstNameSheet.equalsIgnoreCase(firstName)) {
                try {
                    processMedApt(row);
                    hasMedicalAppointment = true;
                } catch (NullPointerException ne) {

                }
            }
        }

    }
    public void findMemberDentalInfo() throws IOException, NullPointerException {
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream( filePath + "DENTAL.xlsx"));
        XSSFSheet sheet = workbook.getSheet("Security");
        XSSFRow row;
        int rowCount = sheet.getPhysicalNumberOfRows();
        for (int i=0; i < rowCount; i++) {
            row = sheet.getRow(i);
            String name = row.getCell(0).getStringCellValue();
            if (name.contains(firstName ) && name.contains(lastName)) {
                try {
                    processDental(row);
                } catch (NullPointerException ne) {

                }
            }

        }

    }

    public void processMedApt(XSSFRow row) {

        for (int i=0; i< medAppointment.length; i++) {
            XSSFCell cell = row.getCell(i);
            CellType type =  cell.getCellType();
            if(type.equals(CellType.NUMERIC)) {
                if (i <2 ) {
                    Date date = row.getCell(i).getDateCellValue();
                    medAppointment[i] = dateFormat.format(date);
                }
                else {
                    int num = (int) row.getCell(i).getNumericCellValue();
                    medAppointment[i] = Integer.toString(num);
                }
            }
            else {
                medAppointment[i] = row.getCell(i).getStringCellValue();
            }
        }
    }
    public void processDental(XSSFRow row) {
        for (int i=0; i< dentalStatus.length; i++) {
            XSSFCell cell = row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            CellType type =  cell.getCellType();
            if(type.equals(CellType.NUMERIC)) {
                try {
                    dentalStatus[i] = dateFormat.format(cell.getDateCellValue());
                    continue;
                } catch (Exception e) {

                }
                int num = (int) row.getCell(i).getNumericCellValue();
                dentalStatus[i] = Integer.toString(num);
            }
            else {
                dentalStatus[i] = row.getCell(i).getStringCellValue();
            }
        }
    }

    public void processDueOverdue(XSSFRow row) {
        hasOverdueItems = true;
        for (int i= 0; i < dueOverdue.length; i++){
            int loc = i +2;
            dueOverdue[i] = row.getCell(loc).getStringCellValue();
        }
    }

    public String[] getMedAppointment() {
        return medAppointment;
    }

    public String[] getDentalStatus() {
        return dentalStatus;
    }

    public String[] getDueOverdue() {
        return dueOverdue;
    }
}
