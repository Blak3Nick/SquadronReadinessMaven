import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public  class WorkbookCreator {
    XSSFWorkbook workbook;
    static CellStyle cellStyle;
    ArrayList<Member> allMembers;
    static ArrayList<XSSFCell> allCells = new ArrayList<>();
    static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

    public WorkbookCreator(ArrayList<Member> allMembers) throws IOException {
        this.workbook = new XSSFWorkbook();
        this.cellStyle = new CellStyle(workbook);
        this.allMembers = allMembers;

        for (Member member : allMembers) {
            try {
                printData(member, workbook);
            } catch (IllegalArgumentException ill) {
                ill.printStackTrace();
            }
        }

    }


    public static void printData(Member member, XSSFWorkbook workbook) throws IOException, IllegalArgumentException{
        String filePath = fxGUI.filePath;
        String lastName = member.getLastName();
        String firstName = member.getFirstName();
        XSSFSheet sheet = workbook.createSheet(lastName);
        if (member.isContainsFitnessData()) {
            try {
            sheet = addFitnessSheet(sheet, member);
            } catch (NullPointerException ne) {
                System.out.println(member.getLastName());

            }

        }
        if(member.isContainsARCNetData()) {
            sheet = addARCNetData(sheet, member);

        }
        if (member.isContainsMedicalData()) {
            sheet = addMedicalData(sheet, member);
        }
        if (member.isContainsEPRData()) {
            sheet = addEPRData(sheet, member);
        }

        for (int i = 0; i < 9; i++) {
            sheet.autoSizeColumn(i);
        }

        try {
            String fileName = filePath + "Report.xlsx";
            FileOutputStream out = new FileOutputStream(fileName);
            workbook.write(out);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("File not found");
            throw new FileNotFoundException();

        }

    }
    public static XSSFSheet addFitnessSheet(XSSFSheet sheet, Member member)throws NullPointerException {

        LocalDate todaysDate = LocalDate.now();

        LocalDate dueDate = member.getFitnessData().testDueDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate sixtyDaysOut = dueDate.minusDays(60);



        boolean overdue = false;
        boolean comingDue = false;

        FitnessData fitnessData = member.getFitnessData();
        Date testTaken = fitnessData.testLastTakenDate;
        String testTakenString = dateFormat.format(testTaken);
        Date testDue = fitnessData.testDueDate;
        LocalDate testDueLocalDate = fitnessData.testDueDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        if (todaysDate.compareTo(testDueLocalDate) > 0) {
            overdue = true;
            System.out.println("Overdue");
        }
        if (todaysDate.compareTo(sixtyDaysOut) > 0) {
            comingDue = true;
            System.out.println("Coming due");
        }
        String testDueString = dateFormat.format(testDue);
        Double compositeScore = fitnessData.compositeScore;
        String fitLevel = fitnessData.getFitLevel();
        String currentTestingStatus = fitnessData.getCurrentTestingStatus();

        XSSFRow row1 = sheet.createRow(0);
        XSSFCell cell1 = row1.createCell(0);
        cell1.setCellValue("FITNESS DATA");
        cell1.setCellStyle(cellStyle.cellStyles.get("header"));



        row1 = sheet.createRow(1);
        cell1 = row1.createCell(0);
        cell1.setCellValue("Last name");
        cell1.setCellStyle(cellStyle.lightGreen);
        allCells.add(cell1);

        cell1 = row1.createCell(1);
        cell1.setCellValue("First name");
        cell1.setCellStyle(cellStyle.lightGreen);
        allCells.add(cell1);

        cell1 = row1.createCell(2);
        cell1.setCellValue("Test taken");
        cell1.setCellStyle(cellStyle.lightGreen);
        allCells.add(cell1);

        cell1 = row1.createCell(3);
        cell1.setCellValue("Test due");
        cell1.setCellStyle(cellStyle.lightGreen);
        allCells.add(cell1);

        cell1 = row1.createCell(4);
        cell1.setCellValue("Composite score");
        cell1.setCellStyle(cellStyle.lightGreen);
        allCells.add(cell1);

        cell1 = row1.createCell(5);
        cell1.setCellValue("Fit level");
        cell1.setCellStyle(cellStyle.lightGreen);
        allCells.add(cell1);

        cell1 = row1.createCell(6);
        cell1.setCellValue("Testing status");
        cell1.setCellStyle(cellStyle.lightGreen);
        allCells.add(cell1);


        row1 = sheet.createRow(2);

        cell1 = row1.createCell(0);
        cell1.setCellValue(member.getLastName());
        allCells.add(cell1);

        cell1 = row1.createCell(1);
        cell1.setCellValue(member.getFirstName());
        allCells.add(cell1);

        cell1 = row1.createCell(2);
        cell1.setCellValue(testTakenString);
        allCells.add(cell1);

        cell1 = row1.createCell(3);
        cell1.setCellValue(testDueString);
        if (comingDue) {
            cell1.setCellStyle(cellStyle.comingDueStyle);
        }
        if (overdue) {
            cell1.setCellStyle(cellStyle.overDueStyle);
        }


        allCells.add(cell1);

        cell1 = row1.createCell(4);
        cell1.setCellValue(compositeScore);
        allCells.add(cell1);

        cell1 = row1.createCell(5);
        cell1.setCellValue(fitLevel);
        allCells.add(cell1);

        cell1 = row1.createCell(6);
        cell1.setCellValue(currentTestingStatus);
        allCells.add(cell1);


        return sheet;
    }
    public static XSSFSheet addARCNetData (XSSFSheet sheet, Member member) {
        //course name due date completion date
        ArrayList<String[]> arcNetReport = member.getArcNetData().arcNetReport;
        XSSFRow row1 = sheet.createRow(4);
        ArrayList<XSSFCell> allCells = new ArrayList<>();
        ARCNetData arcNetData = member.getArcNetData();

        XSSFCell cell1 = row1.createCell(0);
        cell1.setCellValue("ARCNET DATA");
        cell1.setCellStyle(cellStyle.headerStyle);
        allCells.add(cell1);

        row1 = sheet.createRow(5);
        cell1 = row1.createCell(0);
        cell1.setCellValue("Course:");
        cell1.setCellStyle(cellStyle.lightGreen);
        allCells.add(cell1);

        cell1 = row1.createCell(1);
        cell1.setCellValue("Completed on:");
        cell1.setCellStyle(cellStyle.lightGreen);
        allCells.add(cell1);

        cell1 = row1.createCell(2);
        cell1.setCellValue("Due date:");
        cell1.setCellStyle(cellStyle.lightGreen);
        allCells.add(cell1);

        XSSFCell cell2;
        XSSFCell cell3;
        int counter = 0;
        try {
            for (int i = 6; i < 15; i++) {
                row1 = sheet.createRow(i);

                //course name
                cell1 = row1.createCell(0);
                cell1.setCellValue(arcNetReport.get(counter)[5]);
                allCells.add(cell1);

                //completion date
                cell2 = row1.createCell(1);
                cell2.setCellValue(arcNetReport.get(counter)[7]);
                //due date
                cell3 = row1.createCell(2);
                cell3.setCellValue(arcNetReport.get(counter)[6]);


                if (arcNetReport.get(counter)[9].equalsIgnoreCase("overdue")) {
                    cell3.setCellStyle(cellStyle.overDueStyle);
                }
                if (arcNetReport.get(counter)[9].equalsIgnoreCase("comingdue")) {
                    cell3.setCellStyle(cellStyle.comingDueStyle);
                }
                allCells.add(cell1);
                counter++;
            }
        } catch (IndexOutOfBoundsException ie) {
            System.out.println("index error");
        }
        return sheet;

    }


    public static XSSFSheet addMedicalData(XSSFSheet sheet, Member member)throws NullPointerException {
        LocalDate todaysDate = LocalDate.now();

        XSSFRow row1 = sheet.createRow(17);
        ArrayList<XSSFCell> allCells = new ArrayList<>();
        MedicalData medicalData = member.getMedicalData();

        String[] dueOverdue = medicalData.getDueOverdue();
        String[] medApt = medicalData.getMedAppointment();

        XSSFCell cell1 = row1.createCell(0);
        cell1.setCellValue("Due/ Overdue Medical");
        cell1.setCellStyle(cellStyle.headerStyle);
        allCells.add(cell1);

        String[] categories = {"Immunizations", "Dental", "Lab", "PHA", "Eqp", "IMR", "Details"};

        row1 = sheet.createRow(18);
        XSSFRow row2 = sheet.createRow(19);
        XSSFCell cell2;
        for (int i=0; i <7; i ++) {
            cell1 = row1.createCell(i);
            cell1.setCellValue(categories[i]);
            cell1.setCellStyle(cellStyle.lightGreen);
            allCells.add(cell1);

            cell2 = row2.createCell(i);
            cell2.setCellValue(dueOverdue[i]);
            allCells.add(cell2);
        }
        if(!medicalData.hasOverdueItems) {
            cell2 = row2.createCell(0);
            cell2.setCellValue("NOTHING OVERDUE");
        }

        row1 = sheet.createRow(22);
        cell1 = row1.createCell(0);
        cell1.setCellValue("Medical Appointments");
        cell1.setCellStyle(cellStyle.headerStyle);
        allCells.add(cell1);

        categories = new String[] {"Date", "Appt. Type", "Time", "Last name", "First name", "Appt. details"};
        row1 = sheet.createRow(23);
        row2 = sheet.createRow(24);



        for (int i=0; i < categories.length; i ++) {
            cell1 = row1.createCell(i);
            cell1.setCellValue(categories[i]);
            cell1.setCellStyle(cellStyle.lightGreen);
            allCells.add(cell1);

            cell2 = row2.createCell(i);
            cell2.setCellValue(medApt[i]);
            allCells.add(cell2);
        }
        if (!medicalData.hasMedicalAppointment) {
            cell2 = row2.createCell(0);
            cell2.setCellValue("NO MEDICAL APPOINTMENTS");
        }
        row1 = sheet.createRow(25);
        cell1 = row1.createCell(0);
        cell1.setCellValue("Dental Exam Details");
        cell1.setCellStyle(cellStyle.headerStyle);
        allCells.add(cell1);

        String[] dentalInfo = medicalData.getDentalStatus();
        categories= new String[]{"Name", "Rank", "Dental Class", "Exam Due Date", "Last Military Exam", "Next exam type"};
        row1 = sheet.createRow(26);
        row2 = sheet.createRow(27);
        for (int i=0; i < categories.length; i ++) {
            cell1 = row1.createCell(i);
            cell1.setCellValue(categories[i]);
            cell1.setCellStyle(cellStyle.lightGreen);
            allCells.add(cell1);

                cell2 = row2.createCell(i);
                cell2.setCellValue(dentalInfo[i]);
                allCells.add(cell2);

        }

        return sheet;
    }
    public static XSSFSheet addEPRData(XSSFSheet sheet, Member member)throws NullPointerException {
        EPRData eprData = member.getEprData();

        String[] categories = {"Due Date", "Closeout date", "Status", "Grade", "Ratee first name", "Ratee Middle", "Ratee Last name", "Pending coordination", "Days pending"};
        XSSFRow row1 = sheet.createRow(28);
        XSSFCell cell1 = row1.createCell(0);
        cell1.setCellValue("EPR Data");
        cell1.setCellStyle(cellStyle.headerStyle);
        allCells.add(cell1);
        row1 = sheet.createRow(29);
        XSSFRow row2 = sheet.createRow(30);
        XSSFCell cell2 = row2.createCell(0);
        cell2.setCellValue("No EPRs at this time");


        int rowTracker = 30;

        for (int i=0; i < categories.length; i ++) {
            cell1 = row1.createCell(i);
            cell1.setCellValue(categories[i]);
            cell1.setCellStyle(cellStyle.lightGreen);
            allCells.add(cell1);
        }

        ArrayList<String[]> eprs = eprData.getEprData();
        for (int i=0; i < eprData.numberOfEPRSTracking; i++) {
            row2 = sheet.createRow(rowTracker);
            String[] array = eprs.get(i);
            for (String data : array) {
                System.out.println(data);
            }
            for (int j=0; j < 9; j++) {
                cell2 = row2.createCell(j);
                cell2.setCellValue(array[j]);
                allCells.add(cell2);
            }
        rowTracker++;
        }
        return sheet;
    }
}
