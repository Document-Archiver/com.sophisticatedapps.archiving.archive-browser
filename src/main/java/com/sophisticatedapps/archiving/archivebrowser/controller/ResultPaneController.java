package com.sophisticatedapps.archiving.archivebrowser.controller;

import com.sophisticatedapps.archiving.archivebrowser.GlobalConstants;
import com.sophisticatedapps.archiving.archivebrowser.type.InputData;
import com.sophisticatedapps.archiving.archivebrowser.type.Result;
import com.sophisticatedapps.archiving.archivebrowser.util.SearchUtil;
import com.sophisticatedapps.archiving.documentarchiver.api.ApplicationContext;
import com.sophisticatedapps.archiving.documentarchiver.controller.BaseController;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

import java.util.Objects;

public class ResultPaneController extends BaseController {

    @FXML
    private Pane resultPane;

    @FXML
    private ListView<Result> foundDocumentsListView;

    @Override
    public void rampUp(ApplicationContext anApplicationContext) {

        super.rampUp(anApplicationContext);

        // Set cell factory for documents ListView.
        foundDocumentsListView.setCellFactory(aParam -> new ListCell<>() {

            @Override
            protected void updateItem(Result aResult, boolean anEmpty) {

                super.updateItem(aResult, anEmpty);

                if (anEmpty || Objects.isNull(aResult)) {

                    setText(null);
                }
                else {

                    setText(aResult.getFile().getName());
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

    @FXML
    protected void handleFoundDocumentsListViewClicked() {

        setNewCurrentDocument(foundDocumentsListView.getSelectionModel().getSelectedItem().getFile());
    }

    @FXML
    protected void handleFoundDocumentsListKeyPressed(KeyEvent aKeyEvent) {

        if (aKeyEvent.getCode().equals(KeyCode.UP) || aKeyEvent.getCode().equals(KeyCode.DOWN)) {

            // Same as if the user would click on an item
            handleFoundDocumentsListViewClicked();
        }
    }

}
