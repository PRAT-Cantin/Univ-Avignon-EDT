package com.example.edtunivavignon;

import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.control.CheckComboBox;
import org.controlsfx.control.SearchableComboBox;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EDTController {
    @FXML
    private Button addEventButton;
    @FXML
    private HBox modesHbox;
    @FXML
    private HBox controlsHbox;
    @FXML
    private Label leftArrow;
    @FXML
    private Button today;
    @FXML
    private Label rightArrow;
    @FXML
    private Label month;
    @FXML
    private ComboBox displayMode;
    @FXML
    private StackPane controls;
    @FXML
    private VBox vBox;
    @FXML
    private AnchorPane root;
    @FXML
    private HBox calendarHbox;
    AnchorPane edtView;
    CalendarController edtController;
    private User user;
    private CheckComboBox<String> courses;
    private CheckComboBox<String> groups;
    private CheckComboBox<String> rooms;
    private CheckComboBox<String> types;


    private String urlDisplayed;

    private HashMap<String,String> roomToUrl;
    private HashMap<String,String> formationToUrl;
    private URL loaderDisplayed;
    private String currentDisplay;

    public EDTController() {
        roomToUrl = new HashMap<>();
        roomToUrl.put("s1 = c 042 nodes","https://edt-api.univ-avignon.fr/api/exportAgenda/salle/def50200ad9fe27455ee03bf2cf0e47efc49a9dc81c79268ed537b2bb235b36f15fc24b55facf6fbda22234d7d9eb5367edfd57d5361f23fca98bfd8600a8e0e40ce1d8ff582ee1194947d6e08c345d581d37715bef4fd92b7ee51");
        roomToUrl.put("stat9 = info - c 026","https://edt-api.univ-avignon.fr/api/exportAgenda/salle/def50200716c9fd9bd807e62a9da45fbfd5878b1d2d70135856f8319055660a62aef5d245650b6a0d06d777ae783dc55eba0f073e609e1e9969ca056a6dd5d909070d65154ba74e1834aad55d41ad800ec43bf94f4bca0b501bc0611a4d1");
        roomToUrl.put("amphi blaise","https://edt-api.univ-avignon.fr/api/exportAgenda/salle/def502008b758216e6bbe0000fba983ee57ddc11c0f0b7e460e2628c2838df29277b5e54b4e32f86e8593b7e6cc0cbaedbde9253b499d542d085bcbb501833d5765ca80faa94b7ba94e6f34c5ee71ea1b5eeb8d734d2555cd5452d85068e6d");
        roomToUrl.put("s2 bis = c 038","https://edt-api.univ-avignon.fr/api/exportAgenda/salle/def502008c106aa12e2ee2ef1b943bfa434a611e38b2d198d0fa62cba5b11eee6859ceb983398c61db614fb61ba57f4670fee9395339e4ff5f5346be2159362a2217753df1020783b5102642cbbd66c263a7f64fe6ad568b3b43ccd81ed88d");

        formationToUrl = new HashMap<>();
        formationToUrl.put("m1 inge du logiciel de la societe num (ilsen)","https://edt-api.univ-avignon.fr/api/exportAgenda/tdoption/def50200f9715db1cee6bf26de7116662b173f8a6a9e4520342189dacc7e2a5fe2f57e1450f049ccf54252d360bd92fe42084c8cd94829d294efc71a7ba3c0efaa67a0814efc2daae6953229d48224c9ee899b37077ead0b6d3136ece26147414d17e08d9d75fd");
        formationToUrl.put("m1 intelligence artificielle (ia)","https://edt-api.univ-avignon.fr/api/exportAgenda/tdoption/def50200cd22b6278c2ee046a551cf866d019d40dc6fad6499d25e531554a4b7eaac1475ccf7f28e67f8007ef0632aa5f11d6caa45e925bad62ece901a1e191546a23046d77b7300de14f089d3eaa74bf44da062e16b729b270549318b");
    }

    @FXML
    public void initialize() throws IOException {
        vBox.prefWidthProperty().bind(root.widthProperty());
        vBox.prefHeightProperty().bind(root.heightProperty());
        controls.prefHeightProperty().bind(vBox.prefHeightProperty().divide(10));
        controls.setMinHeight(0);
        loaderDisplayed = EDTController.class.getResource("weekView.fxml");
        calendarHbox.prefHeightProperty().bind(vBox.prefHeightProperty().divide(10).multiply(9));
        calendarHbox.prefWidthProperty().bind(vBox.prefWidthProperty());
        calendarHbox.setMinWidth(0);
        month.prefWidthProperty().bind(controls.prefWidthProperty());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        month.setText(LocalDateTime.now().format(dateTimeFormatter).toUpperCase());
        StackPane.setAlignment(controlsHbox, Pos.TOP_LEFT);
        StackPane.setAlignment(displayMode,Pos.TOP_RIGHT);
        StackPane.setAlignment(month,Pos.BOTTOM_CENTER);
        displayMode.getItems().addAll("Jour","Semaine","Mois");
        displayMode.setValue("Semaine");
        currentDisplay = "Personnel";
        setDisplayMode();
    }

    public void setToToday(ActionEvent  event) throws IOException {
        edtController.displayToday();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        month.setText(edtController.getDisplayedDate().format(dateTimeFormatter).toUpperCase());
    }

    public void setToPrevious(MouseEvent  event) throws IOException {
        edtController.displayPrevious();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        month.setText(edtController.getDisplayedDate().format(dateTimeFormatter).toUpperCase());
    }

    public void setToNext(MouseEvent event) throws IOException {
        edtController.displayNext();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        month.setText(edtController.getDisplayedDate().format(dateTimeFormatter).toUpperCase());
    }

    public void setUser(User user) throws IOException {
        this.user = user;
        edtController.setEdtToDisplay(user.getEdtURL());
        urlDisplayed = user.getEdtURL();
        edtController.displayToday();
        setDisplayMode();
    }

    public void setFilters() throws IOException {
        edtController.setFilterCourses(new ArrayList<>(courses.getCheckModel().getCheckedItems()));
        edtController.setFilterGroups(new ArrayList<>(groups.getCheckModel().getCheckedItems()));
        edtController.setFilterRooms(new ArrayList<>(rooms.getCheckModel().getCheckedItems()));
        edtController.setFilterTypes(new ArrayList<>(types.getCheckModel().getCheckedItems()));
    }

    public void createFilters() {
        VBox filters = new VBox();
        filters.prefWidthProperty().bind(calendarHbox.prefWidthProperty().divide(10));
        filters.prefHeightProperty().bind(calendarHbox.prefHeightProperty());
        courses = new CheckComboBox<>();
        for (String course : edtController.getCourses()
             ) {
            courses.getItems().add(course);
        }
        courses.prefWidthProperty().bind(filters.prefWidthProperty());
        courses.getCheckModel().getCheckedItems().addListener((ListChangeListener.Change<? extends String> tmp) -> {
            try {
                setFilters();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        filters.getChildren().add(courses);
        
        groups = new CheckComboBox<>();
        for (String group : edtController.getGroups()
        ) {
            if (!groups.getItems().contains(group.replace(", ","")))
                groups.getItems().add(group.replace(", ",""));
        }
        groups.prefWidthProperty().bind(filters.prefWidthProperty());
        groups.getCheckModel().getCheckedItems().addListener((ListChangeListener.Change<? extends String> tmp) -> {
            try {
                setFilters();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        filters.getChildren().add(groups);

        rooms = new CheckComboBox<>();
        for (String room : edtController.getRooms()
        ) {
            if (!rooms.getItems().contains(room.replace(", ","")))
                rooms.getItems().add(room.replace(", ",""));
        }
        rooms.prefWidthProperty().bind(filters.prefWidthProperty());
        rooms.getCheckModel().getCheckedItems().addListener((ListChangeListener.Change<? extends String> tmp) -> {
            try {
                setFilters();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        filters.getChildren().add(rooms);

        types = new CheckComboBox<>();
        for (String type : edtController.getTypes()
        ) {
            types.getItems().add(type);
        }
        types.prefWidthProperty().bind(filters.prefWidthProperty());
        types.getCheckModel().getCheckedItems().addListener((ListChangeListener.Change<? extends String> tmp) -> {
            try {
                setFilters();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        filters.getChildren().add(types);
        calendarHbox.getChildren().add(filters);
    }

    public void setDisplayMode() throws IOException {
        calendarHbox.getChildren().clear();
        FXMLLoader fxmlLoader = new FXMLLoader(loaderDisplayed);
        edtView = fxmlLoader.load();
        edtView.prefHeightProperty().bind(calendarHbox.prefHeightProperty());
        edtView.prefWidthProperty().bind(calendarHbox.prefWidthProperty().divide(10).multiply(9));
        edtView.setMinHeight(0);
        LocalDateTime previousDate = LocalDateTime.now();
        if (edtController != null) {
            previousDate = edtController.getDisplayedDate();
        }
        edtController = fxmlLoader.getController();
        if (urlDisplayed != null) {
            edtController.setEdtToDisplay(urlDisplayed);
            createFilters();
            if (Objects.equals(currentDisplay, "Salle")) {
                Map<String, String> reversed = new HashMap<>();
                for(Map.Entry<String, String> entry : roomToUrl.entrySet()){
                    reversed.put(entry.getValue(), entry.getKey());
                }
                edtController.setCustomCalendar(reversed.get(urlDisplayed));
            }
            if (Objects.equals(currentDisplay, "Personnel")) {
                edtController.setCustomCalendar(user.getUserName());
            }
            edtController.displaySpecific(previousDate);
        }
        calendarHbox.getChildren().add(edtView);
    }

    public void changeDisplayMode(ActionEvent actionEvent) throws IOException {
        switch (displayMode.getValue().toString()) {
            case "Jour":
                System.out.println("jour");
                loaderDisplayed = EDTController.class.getResource("weekDay.fxml");
                setDisplayMode();
                return;
            case "Semaine":
                System.out.println("Semaine");
                loaderDisplayed = EDTController.class.getResource("weekView.fxml");
                setDisplayMode();
                return;
            case "Mois":
                System.out.println("Mois");
                loaderDisplayed = EDTController.class.getResource("monthView.fxml");
                setDisplayMode();
                return;
        }
    }

    public void toPersonal(ActionEvent actionEvent) throws IOException {
        urlDisplayed = user.getEdtURL();
        addEventButton.setVisible(true);
        currentDisplay = "Personnel";
        setDisplayMode();
    }

    public void toFormation(ActionEvent actionEvent) {
        calendarHbox.getChildren().clear();
        addEventButton.setVisible(false);
        currentDisplay = "null";
        SearchableComboBox<String> searchableComboBox = new SearchableComboBox<>();
        searchableComboBox.setPromptText("Taper le nom d'une formation");
        searchableComboBox.prefWidthProperty().bind(calendarHbox.prefWidthProperty().divide(2));
        searchableComboBox.prefHeightProperty().bind(calendarHbox.prefHeightProperty().divide(10));
        searchableComboBox.setMaxHeight(50);
        for (Map.Entry<String,String> entry : formationToUrl.entrySet()
        ) {
            searchableComboBox.getItems().add(entry.getKey());
        }
        searchableComboBox.setOnAction(event -> {
            urlDisplayed = formationToUrl.get(searchableComboBox.getValue());
            try {
                setDisplayMode();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        calendarHbox.getChildren().add(searchableComboBox);
    }

    public void toRooms(ActionEvent actionEvent) {
        calendarHbox.getChildren().clear();
        addEventButton.setVisible(false);
        currentDisplay = "null";
        SearchableComboBox<String> searchableComboBox = new SearchableComboBox<>();
        searchableComboBox.setPromptText("Taper le nom d'une salle");
        searchableComboBox.prefWidthProperty().bind(calendarHbox.prefWidthProperty().divide(2));
        searchableComboBox.setMaxHeight(50);
        for (Map.Entry<String,String> entry : roomToUrl.entrySet()
        ) {
            searchableComboBox.getItems().add(entry.getKey());
        }
        searchableComboBox.setOnAction(event -> {
            urlDisplayed = roomToUrl.get(searchableComboBox.getValue());
            currentDisplay = "Salle";
            if (user.isAdmin()) {
                addEventButton.setVisible(true);
            }
            try {
                setDisplayMode();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        calendarHbox.getChildren().add(searchableComboBox);
    }

    public void addReservation(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(EDTController.class.getResource("roomReservation.fxml"));
        RoomReservationController roomReservationController = null;
        if (Objects.equals(currentDisplay, "Salle")) {
            Map<String, String> reversed = new HashMap<>();
            for(Map.Entry<String, String> entry : roomToUrl.entrySet()){
                reversed.put(entry.getValue(), entry.getKey());
            }
            roomReservationController = new RoomReservationController(user.getUserName(), reversed.get(urlDisplayed),roomToUrl,reversed.get(urlDisplayed),true);
        }
        else if (Objects.equals(currentDisplay, "Personnel")) {
            roomReservationController = new RoomReservationController(user.getUserName(), "",roomToUrl,user.getUserName(),false);
        }
        fxmlLoader.setController(roomReservationController);
        Stage stage = new Stage();
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Création d'événement");
        roomReservationController.setStage(stage);
        stage.showAndWait();
        setDisplayMode();
    }
}
