
public class Member {
    private String lastName;
    private String firstName;
    private FitnessData fitnessData;
    private ARCNetData arcNetData;
    private EPRData eprData;
    public String filePath;
    private boolean containsFitnessData = false;
    private boolean containsARCNetData = false;
    private MedicalData medicalData;
    private boolean containsMedicalData = false;
    private boolean containsEPRData = false;

    public Member(String lastName, String firstName) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.filePath = fxGUI.filePath;
    }
    public void assignFitnessData() {
        this.fitnessData = new FitnessData( lastName, firstName);
        System.out.println("Running member fitness data...");
        containsFitnessData = true;
    }

    public void assignARCNetData() {
       this.arcNetData = new ARCNetData(lastName, firstName);
        System.out.println("Running arcnet data....");
        containsARCNetData = true;
    }

    public void assignMedicalData() {
        this.medicalData = new MedicalData(lastName, firstName);
        System.out.println("Running medical data");
        containsMedicalData = true;
    }
    public void assignEPRData() {
        this.eprData = new EPRData(lastName, firstName);
        System.out.println("Running epr data");
        containsEPRData = true;

    }


    public FitnessData getFitnessData() {
        return fitnessData;
    }
    public ARCNetData getArcNetData() {return arcNetData; }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public boolean isContainsFitnessData() {
        return containsFitnessData;
    }

    public boolean isContainsARCNetData() {
        return containsARCNetData;
    }

    public MedicalData getMedicalData() {
        return medicalData;
    }

    public boolean isContainsMedicalData() {
        return containsMedicalData;
    }

    public EPRData getEprData() {
        return eprData;
    }

    public boolean isContainsEPRData() {
        return containsEPRData;
    }
}
