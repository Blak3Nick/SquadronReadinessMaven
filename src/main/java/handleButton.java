import javafx.scene.control.Button;

import java.io.IOException;
import java.util.ArrayList;

public class handleButton {
    public static boolean executedSuccessfully = true;

    public static void handle(Button button) {
        executedSuccessfully = true;
        String id = button.getId();
        switch(id) {
            case("squad1"):
                MemberAssignment.assignMembersForEvaluation(MemberAssignment.squad1Members);
                reportHelper();
                break;
            case("squad2"):
                MemberAssignment.assignMembersForEvaluation(MemberAssignment.squad2Members);
                reportHelper();
                break;
            case("catm"):
                MemberAssignment.assignMembersForEvaluation(MemberAssignment.catmMembers);
                reportHelper();
                break;
            case("fire11"):
                MemberAssignment.assignMembersForEvaluation(MemberAssignment.fireTeamMembers[0]);
                reportHelper();
                break;
            case("fire12"):
                MemberAssignment.assignMembersForEvaluation(MemberAssignment.fireTeamMembers[1]);
                reportHelper();
                break;
            case("fire13"):
                MemberAssignment.assignMembersForEvaluation(MemberAssignment.fireTeamMembers[2]);
                reportHelper();
                break;
            case("fire14"):
                MemberAssignment.assignMembersForEvaluation(MemberAssignment.fireTeamMembers[3]);
                reportHelper();
                break;
            case("fire21"):
                MemberAssignment.assignMembersForEvaluation(MemberAssignment.fireTeamMembers[4]);
                reportHelper();
                break;
            case("fire22"):
                MemberAssignment.assignMembersForEvaluation(MemberAssignment.fireTeamMembers[5]);
                reportHelper();
                break;
            case("fire23"):
                MemberAssignment.assignMembersForEvaluation(MemberAssignment.fireTeamMembers[6]);
                reportHelper();
                break;
            case("fire24"):
                MemberAssignment.assignMembersForEvaluation(MemberAssignment.fireTeamMembers[7]);
                reportHelper();
                break;
        }//end switch statement

        if(handleButton.executedSuccessfully) {
            AlertBox.display("Success!", "The report was printed.");
        }

    }
    public static void runIndividual(String name) {
        ArrayList<String> indv = new ArrayList<>();
        indv.add(name);
        MemberAssignment.assignMembersForEvaluation(indv);
        reportHelper();
        AlertBox.display("Success!", "The report was printed.");
    }

    private static void runReport(Member member){
        member.assignFitnessData();
        member.assignARCNetData();
        member.assignMedicalData();
        member.assignEPRData();
    }
    private static void reportHelper() {
        for (Member member : MemberAssignment.allMembersSelectedForEvaluation) {
            runReport(member);
        }
        try {
            WorkbookCreator workbookCreator = new WorkbookCreator(MemberAssignment.allMembersSelectedForEvaluation);
        } catch (IOException ioe) {
            System.out.println("Could not create the file");
            ioe.printStackTrace();
            executedSuccessfully = false;
        }
    }



}
