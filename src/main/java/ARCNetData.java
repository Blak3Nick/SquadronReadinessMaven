
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;


public class ARCNetData {
    ArrayList<String[]> arcNetReport = new ArrayList<>();
    String name;
    String filePath;
    String[] coursesToTrack;

    private int maxCourses =0;
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

    public ARCNetData(String lastName, String firstName) {
        this.filePath = fxGUI.filePath;
        this.name = lastName + ", " + firstName;
        try {
            assignCourses();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Could not assign courses");
        }
        try {
            findMember();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Could not open file.");
        } catch (NullPointerException e) {

        }
    }
    public void findMember() throws IOException, NullPointerException {
        //linux file location
        ///home/blak3nick/IdeaProjects/SquadronReadiness/ARCNet.xlsx
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream( filePath + "ARCNet.xlsx"));
        XSSFSheet sheet = workbook.getSheetAt(0);
        XSSFRow row;
        int rowCount = sheet.getPhysicalNumberOfRows();
        for (int i=0; i < rowCount; i++) {
            if (maxCourses > 8){
                break;
            }
            row = sheet.getRow(i);
            String nameSheet = "";
            try {
                nameSheet = row.getCell(0).getStringCellValue();
            } catch (NullPointerException ne) {
                System.out.println("null name");
            }

            if (nameSheet.equalsIgnoreCase(name)) {
                try {
                    processARCNetMetrics(row);
                } catch (NullPointerException ne) {

                }
            }
        }
    }

    public void processARCNetMetrics(XSSFRow row) {
        Date dueDateSheet;
        LocalDate localDateToday = LocalDate.now();
        LocalDate sixtyOut = localDateToday.minusDays(60);

        String[] array = new String[10];
        array[9] = "";
        for (int i=0; i < 9; i++){
            XSSFCell cell;
            try {
                 cell = row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            } catch (IllegalArgumentException e) {
                cell = row.createCell(i, CellType.BLANK);
            }


                if (cell.getCellType() == CellType.STRING) {
                    array[i] = cell.getStringCellValue();
                }
                if (cell.getCellType() == CellType.BLANK || cell.getCellType() == null) {
                    array[i] = "n/a";
                }
                if(cell.getCellType() == CellType.NUMERIC) {
                    switch (i){
                        case (6):
                            dueDateSheet = cell.getDateCellValue();
                            LocalDate localDateSheet = dueDateSheet.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                            array[i] = dateFormat.format(dueDateSheet);
                            if (localDateToday.compareTo(localDateSheet) > 0 ) {
                               array[9] = "overdue";
                            }
                            else if (sixtyOut.compareTo(localDateSheet) > 0) {
                                array[9] = "comingdue";
                            }
                            break;
                        case (7):
                            dueDateSheet = cell.getDateCellValue();
                            array[i] = dateFormat.format(dueDateSheet);
                            break;
                            }
                }

        }
        arcNetReport.add(array);
    }

    void assignCourses( ) throws IOException {
        this.coursesToTrack = new String[9];
        File file;
        BufferedReader bufferedReader;
        String string;
        int i =0;
        file = new File(filePath+"arcnetCourses.txt");
        bufferedReader = new BufferedReader(new FileReader(file));
        while ((string = bufferedReader.readLine()) != null) {
            string = string.trim();
            this.coursesToTrack[i] = string;
        }
    }

}
