module com.example.projectgrimoire {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.companion.dashboard to javafx.fxml;
    opens com.companion.dashboard.controllers to javafx.fxml;
    exports com.companion.dashboard;
}