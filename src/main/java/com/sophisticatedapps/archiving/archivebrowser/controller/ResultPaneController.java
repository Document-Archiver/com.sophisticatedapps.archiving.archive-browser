package com.sophisticatedapps.archiving.archivebrowser.controller;

import com.sophisticatedapps.archiving.archivebrowser.GlobalConstants;
import com.sophisticatedapps.archiving.archivebrowser.util.SearchUtil;
import com.sophisticatedapps.archiving.archivebrowser.type.InputData;
import com.sophisticatedapps.archiving.archivebrowser.type.Result;
import com.sophisticatedapps.archiving.documentarchiver.controller.BaseController;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Objects;

public class ResultPaneController extends BaseController {

    @FXML
    private Pane resultPane;

    @FXML
    private ListView<Result> foundDocumentsListView;

    @Override
    public void rampUp(Stage aStage) {

        super.rampUp(aStage);

        // Set cell factory for documents ListView.
        foundDocumentsListView.setCellFactory(aParam -> new ListCell<>() {

            @Override
            protected void updateItem(Result aResult, boolean anEmpty) {

                super.updateItem(aResult, anEmpty);

                if (anEmpty || Objects.isNull(aResult)) {

                    setText(null);
                }
                else {

                    setText(aResult.getFile().getAbsolutePath());
                }
            }
        });

        // Add listeners
        resultPane.widthProperty().addListener((observable, oldValue, newValue) ->
                foundDocumentsListView.setPrefWidth(resultPane.getPrefWidth()));
        resultPane.heightProperty().addListener((observable, oldValue, newValue) ->
                foundDocumentsListView.setPrefHeight(resultPane.getPrefHeight() - 25));

        addListenerForProperty(aChange -> handleNewInputData((InputData)aChange.getValueAdded()),
                GlobalConstants.INPUT_DATA_PROPERTY_KEY);
    }

    private void handleNewInputData(InputData anInputData) {

        foundDocumentsListView.getItems().setAll(SearchUtil.search(anInputData));
    }

}
