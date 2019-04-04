import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.scene.layout.Panel;

import java.io.IOException;
import java.util.ArrayList;

public class fxGUI extends Application implements EventHandler<ActionEvent> {
    //static String filePath = "E:\\DocumentsForLongTermStorage\\SquadronReadiness\\";
    static String filePath = "Z:\\25_REPORTS\\SquadronReadiness\\ProgramFiles\\";
    Stage primaryStage;
    SplitMenuButton indvSplitMenu;
    Button squadSelectionButton;
    Button fireSelectionButton;
    Button individualSelectionButton;
    Button squadOneButton;
    Button squadTwoButton;
    Button CATMButton;
    Button fire11;
    Button fire12;
    Button fire13;
    Button fire14;
    Button fire21;
    Button fire22;
    Button fire23;
    Button fire24;
    Button fire15;
    Button fire25;
    Button fireHome;
    Button indvHome;
    VBox individualSceneLayout;
    Scene individualScene;

    GridPane openingSceneLayout;
    VBox squadSelectionSceneLayout;
    Scene openingScene;
    Scene squadSelectionScene;
    Scene fireSelectionScene;
    ArrayList<Button> squadSelectionButtons = new ArrayList<>();
    Button homeButton;
    Panel topPanel = new Panel();
    Panel allPanelsMainScene = new Panel();
    TextArea explanatoryPanel = new TextArea("The purpose of this application is to process reports. It will read through all relevant" +
            " excel files and print out a consolidated report for the selected members. Choose whether you wish to pull a" +
            " squad, fire team, or individual report by selecting the corresponding button.");
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Squadron Readiness");
        topPanel.getStyleClass().add("panel-primary");
        topPanel.setText("Squadron readiness");
        explanatoryPanel.getStyleClass().add("panel-info");
        explanatoryPanel.getStyleClass().add("h4");
        explanatoryPanel.setWrapText(true);
        Panel indvPanel = new Panel("Select an individual from the drop down menu");
        indvPanel.getStyleClass().add("panel-primary");

        try {
        setUp();
        } catch (IOException ioe) {
            System.out.println("Error setting up...");
            ioe.printStackTrace();
        }
        addButtons();
        openingSceneLayout = new GridPane();
        GridPane fireSelectionSceneLayout = new GridPane();
        openingSceneLayout.setAlignment(Pos.CENTER);
        openingSceneLayout.setMinSize(800, 650);
        openingSceneLayout.setMaxSize(800, 650);
        squadSelectionSceneLayout = new VBox(20);
        openingScene = new Scene(openingSceneLayout, 600, 450);
        squadSelectionScene = new Scene(squadSelectionSceneLayout, 600, 450);
        individualSceneLayout = new VBox(20);
        individualScene = new Scene(individualSceneLayout, 600, 400);
        individualSceneLayout.getChildren().add(indvPanel);
        individualSceneLayout.getChildren().add(indvSplitMenu);
        individualSceneLayout.getChildren().add(indvHome);
        individualSceneLayout.setAlignment(Pos.CENTER);

        fireSelectionSceneLayout.setHgap(10);
        fireSelectionSceneLayout.setVgap(5);

        Panel firePanel = new Panel("Choose your fire team.");
        firePanel.getStyleClass().add("panel-primary");
        fireSelectionSceneLayout.add(firePanel, 0, 0,5 ,2);

        fireSelectionSceneLayout.add(fire11, 0,2);
        fireSelectionSceneLayout.add(fire12, 1,2);
        fireSelectionSceneLayout.add(fire13, 2,2);
        fireSelectionSceneLayout.add(fire14, 3,2);
        fireSelectionSceneLayout.add(fire15, 4,2);
        fireSelectionSceneLayout.add(fire21, 0, 3);
        fireSelectionSceneLayout.add(fire22, 1, 3);
        fireSelectionSceneLayout.add(fire23, 2, 3);
        fireSelectionSceneLayout.add(fire24, 3, 3);
        fireSelectionSceneLayout.add(fire25, 4, 3);
        fireSelectionSceneLayout.add(fireHome, 0,4);

        fireSelectionScene = new Scene(fireSelectionSceneLayout, 455, 250);


        squadSelectionButton.setOnAction(e -> primaryStage.setScene(squadSelectionScene));
        squadSelectionButton.getStyleClass().add("btn btn-lg btn-danger");

        fireSelectionButton.setOnAction(e -> primaryStage.setScene(fireSelectionScene));
        homeButton.setOnAction(e -> primaryStage.setScene(openingScene));
        fireHome.setOnAction(e -> primaryStage.setScene(openingScene));
        indvHome.setOnAction(e -> primaryStage.setScene(openingScene));
        individualSelectionButton.setOnAction(e -> primaryStage.setScene(individualScene));
        squadOneButton.setOnAction(e -> handleButton.handle(squadOneButton));
        squadTwoButton.setOnAction(e -> handleButton.handle(squadTwoButton));
        CATMButton.setOnAction(e -> handleButton.handle(CATMButton));
        fire11.setOnAction(e -> handleButton.handle(fire11));
        fire12.setOnAction(e -> handleButton.handle(fire12));
        fire13.setOnAction(e -> handleButton.handle(fire13));
        fire14.setOnAction(e -> handleButton.handle(fire14));
        fire15.setOnAction(e -> handleButton.handle(fire15));
        fire21.setOnAction(e -> handleButton.handle(fire21));
        fire22.setOnAction(e -> handleButton.handle(fire22));
        fire23.setOnAction(e -> handleButton.handle(fire23));
        fire24.setOnAction(e -> handleButton.handle(fire24));
        fire25.setOnAction(e -> handleButton.handle(fire25));




        Panel squadSelectionPanel = new Panel("Choose a squad to get reports from.");
        squadSelectionPanel.getStyleClass().add("panel-primary");


        System.out.println(MemberAssignment.allSFSMembers);
        openingSceneLayout.add(topPanel,0,0,3,1);
        openingSceneLayout.add(explanatoryPanel, 0,1, 3,1);

        openingSceneLayout.add(squadSelectionButton, 0, 4);
        openingSceneLayout.add(fireSelectionButton, 1,4);
        openingSceneLayout.add(individualSelectionButton, 2,4);
        openingSceneLayout.setHgap(130);
        openingSceneLayout.setVgap(8);


        squadSelectionSceneLayout.getChildren().add(squadSelectionPanel);
        squadSelectionSceneLayout.getChildren().addAll(squadSelectionButtons);

        openingScene.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");
        squadSelectionScene.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");
        fireSelectionScene.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");
        individualScene.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");
        primaryStage.setScene(openingScene);
        primaryStage.show();

    }

    @Override
    public void handle(ActionEvent event) {

    }

    public static void main(String[] args) {
        launch(args);
    }
    public void addButtons() {

        squadSelectionButton = new Button();
        squadSelectionButton.setText("Squads");
        squadSelectionButton.setId("squadSelection");
        squadSelectionButton.getStyleClass().setAll("btn-lg", "btn-primary");


        fireSelectionButton = new Button();
        fireSelectionButton.setText("Fire Teams");
        fireSelectionButton.setId("fireSelection");
        fireSelectionButton.getStyleClass().setAll("btn-lg", "btn-danger");

        individualSelectionButton = new Button();
        individualSelectionButton.setText("Individual");
        individualSelectionButton.setId("individualSelection");
        individualSelectionButton.getStyleClass().setAll("btn-lg", "btn-success");


        squadOneButton = new Button();
        squadOneButton.setText("Squad 1");
        squadOneButton.setId("squad1");
        squadOneButton.getStyleClass().setAll("btn-lg", "btn-warning");

        squadTwoButton = new Button();
        squadTwoButton.setText("Squad 2");
        squadTwoButton.setId("squad2");
        squadTwoButton.getStyleClass().setAll("btn-lg", "btn-success");


        CATMButton = new Button();
        CATMButton.setText("CATM");
        CATMButton.setId("catm");
        CATMButton.getStyleClass().setAll("btn-lg", "btn-danger");

        homeButton = new Button();
        homeButton.setText("Home");
        homeButton.setId("home");
        homeButton.getStyleClass().setAll("btn-lg", "btn-info");

        fireHome = new Button();
        fireHome.setText("Home");
        fireHome.setId("fireHome");
        fireHome.getStyleClass().setAll("btn-lg", "btn-success");

        indvHome = new Button();
        indvHome.setText("Home");
        indvHome.setId("indvhome");
        indvHome.getStyleClass().setAll("btn-lg", "btn-success");

        fire11 = new Button("  1-1  ");
        fire11.setId("fire11");
        fire11.getStyleClass().setAll("btn-lg", "btn-info");

        fire12 = new Button("  1-2  ");
        fire12.setId("fire12");
        fire12.getStyleClass().setAll("btn-lg", "btn-info");

        fire13 = new Button("  1-3  ");
        fire13.setId("fire13");
        fire13.getStyleClass().setAll("btn-lg", "btn-info");

        fire14 = new Button("  1-4  ");
        fire14.setId("fire14");
        fire14.getStyleClass().setAll("btn-lg", "btn-info");

        fire15 = new Button("  1-5  ");
        fire15.setId("fire15");
        fire15.getStyleClass().setAll("btn-lg", "btn-info");

        fire21 = new Button("  2-1  ");
        fire21.setId("fire21");
        fire21.getStyleClass().setAll("btn-lg", "btn-warning");

        fire22 = new Button("  2-2  ");
        fire22.setId("fire22");
        fire22.getStyleClass().setAll("btn-lg", "btn-warning");

        fire23 = new Button("  2-3  ");
        fire23.setId("fire23");
        fire23.getStyleClass().setAll("btn-lg", "btn-warning");

        fire24 = new Button("  2-4  ");
        fire24.setId("fire24");
        fire24.getStyleClass().setAll("btn-lg", "btn-warning");

        fire25 = new Button("  2-5  ");
        fire25.setId("fire25");
        fire25.getStyleClass().setAll("btn-lg", "btn-warning");

        indvSplitMenu = new SplitMenuButton();
        indvSplitMenu.setText("All SFS Members");
        for (String name : MemberAssignment.allSFSMembers) {
            MenuItem menuItem = new MenuItem(name);
            menuItem.setOnAction(e->handleButton.runIndividual(name));
            indvSplitMenu.getItems().add(menuItem);
        }

        squadSelectionButtons.add(squadOneButton);
        squadSelectionButtons.add(squadTwoButton);
        squadSelectionButtons.add(CATMButton);
        squadSelectionButtons.add(homeButton);
    }

    public void setUp() throws IOException {
        MemberAssignment.assignMembers("allSFSMembers.txt", 0);
        MemberAssignment.assignMembers("SQUAD1.txt", 1);
        MemberAssignment.assignMembers("SQUAD2.txt", 2);
        MemberAssignment.assignMembers("CATM.txt", 3);
        MemberAssignment.assignFire();

    }



}
