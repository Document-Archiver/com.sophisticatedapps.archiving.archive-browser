module ArchiveBrowser {

    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.web;
    requires javafx.fxml;
    requires javafx.media;
    requires java.desktop;
    requires DocumentArchiver;

    opens com.sophisticatedapps.archiving.archivebrowser;
    opens com.sophisticatedapps.archiving.archivebrowser.view;
    opens com.sophisticatedapps.archiving.archivebrowser.controller;
}
