import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FitnessData {

    String lastName;
    String firstName;
    Date testLastTakenDate;
    Date testDueDate;
    Double compositeScore;
    String fitLevel;
    String currentTestingStatus;
    String filePath;


    public FitnessData(String lastName, String firstName) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.filePath = fxGUI.filePath;
        try {
            findMember();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Could not open file.");
        } catch (NullPointerException e) {

        }


    }

    public void findMember() throws FileNotFoundException, IOException, NullPointerException{
        //file location linux
        ///home/blak3nick/IdeaProjects/SquadronReadiness/Fitness.xls
        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(filePath + "Fitness.xls"));
        HSSFSheet sheet = workbook.getSheetAt(0);
        HSSFRow row;

        int rowCount = sheet.getPhysicalNumberOfRows();
        for (int i =0; i < rowCount; i++) {
            row = sheet.getRow(i);
            String lastNameSheet = "";
            String firstNameSheet = "";
            try {
                 lastNameSheet = row.getCell(1).getStringCellValue();
                 firstNameSheet = row.getCell(2).getStringCellValue();
            } catch (Exception ne) {

            }

            if (lastNameSheet.equalsIgnoreCase(lastName) && firstNameSheet.equalsIgnoreCase(firstName)) {
                processFitnessMetrics(row);
            }
        }
    }
    public void processFitnessMetrics(HSSFRow row) throws NullPointerException{
        testLastTakenDate = row.getCell(6).getDateCellValue();
        testDueDate = row.getCell(7).getDateCellValue();
        compositeScore = row.getCell(16).getNumericCellValue();
        fitLevel = row.getCell(20).getStringCellValue();
        currentTestingStatus = row.getCell(21).getStringCellValue();
    }

    public String getSummary() throws NullPointerException{
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String summary = " Name: " + lastName + ',' + firstName +"\n"
                + "Last tested: " + dateFormat.format(testLastTakenDate) +"\n"
                + "Test due: " + dateFormat.format(testDueDate) + "\n"
                + "Composite score: " + compositeScore + "\n"
                + "Fit level: " + fitLevel + "\n"
                + "Current testing status: " + currentTestingStatus;
        return summary;
    }


    public String getFitLevel() {
        return fitLevel;
    }

    public String getCurrentTestingStatus() {
        return currentTestingStatus;
    }
}
