import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public final class MemberAssignment {
    static String filePath = fxGUI.filePath;
    static File file;
    static BufferedReader bufferedReader;
    static String string;
    public static ArrayList<String> allSFSMembers;
    public static ArrayList<String> squad1Members;
    public static ArrayList<String> squad2Members;
    public static ArrayList<String> catmMembers;
    public static ArrayList<String>[] fireTeamMembers = new ArrayList[8];
    public static ArrayList<Member> allMembersSelectedForEvaluation;

    private MemberAssignment(){}

    public static ArrayList<String> assignMembers(String nameOfFile, int order)throws IOException {
        ArrayList<String> requestedMembers = new ArrayList<>();
        file = new File(filePath+nameOfFile);

        bufferedReader = new BufferedReader(new FileReader(file));
        while ((string = bufferedReader.readLine()) != null) {
            string = string.trim();
            requestedMembers.add(string);
        }
        if (order == 0) {
            allSFSMembers = requestedMembers;
            allSFSMembers.sort(String::compareToIgnoreCase);
        }
        if (order == 1 ) {
            squad1Members = requestedMembers;
        }
        if (order == 2) {
            squad2Members = requestedMembers;
        }
        if (order == 3) {
            catmMembers = requestedMembers;
        }
        return requestedMembers;
    }

    public static  ArrayList<Member> assignMembersForEvaluation(ArrayList<String> memberList) {
        ArrayList<Member> members = new ArrayList<>();
        for (String memberName : memberList) {
            int index1 = memberName.indexOf(',');
            String last = memberName.substring(0,index1);
            int index2 = memberName.indexOf(' ');
            String first = memberName.substring(index2+1 );
            Member member = new Member(last, first);
            members.add(member);
        }
        allMembersSelectedForEvaluation = members;
        return members;
    }

    public static void assignFire() throws IOException {

        for (int i=0; i<8; i++) {
            int increment = 1;
            if(increment >4) {
                increment = 1;
            }
            String fileName = "fire";
            if(i<4){
                fileName+="1-";
            }
            else {
                fileName+="2-";
            }
            fileName+= increment + ".txt";
            ArrayList<String> requestedMembers = new ArrayList<>();
            file = new File(filePath+fileName);

            bufferedReader = new BufferedReader(new FileReader(file));
            while ((string = bufferedReader.readLine()) != null) {
                string = string.trim();
                requestedMembers.add(string);
            }

            fireTeamMembers[i] = requestedMembers;
        }

    }

}
