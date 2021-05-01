import com.sophisticatedapps.archiving.documentarchiver.api.ArchiveBrowsingService;
import com.sophisticatedapps.archiving.archivebrowser.AbArchiveBrowsingService;

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

    exports com.sophisticatedapps.archiving.archivebrowser to DocumentArchiver;
    //exports com.sophisticatedapps.archiving.archivebrowser.view to DocumentArchiver;
    exports com.sophisticatedapps.archiving.archivebrowser.controller to DocumentArchiver;

    provides ArchiveBrowsingService with AbArchiveBrowsingService;
}
