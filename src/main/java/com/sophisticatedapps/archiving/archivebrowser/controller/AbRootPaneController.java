/*
 * Copyright 2021 by Stephan Sann (https://github.com/stephansann)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sophisticatedapps.archiving.archivebrowser.controller;

import com.sophisticatedapps.archiving.archivebrowser.GlobalConstants;
import com.sophisticatedapps.archiving.documentarchiver.api.ApplicationContext;
import com.sophisticatedapps.archiving.documentarchiver.controller.BaseController;
import com.sophisticatedapps.archiving.documentarchiver.controller.DisplayFilePaneController;
import com.sophisticatedapps.archiving.documentarchiver.util.FXMLUtil;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class AbRootPaneController extends BaseController {

    private final List<BaseController> paneControllerList = new ArrayList<>();
    private final List<ChangeListener<Number>> stageWidthPropertyListenersList = new ArrayList<>();
    private final List<ChangeListener<Number>> stageHeightPropertyListenersList = new ArrayList<>();

    private Pane inputPane;
    private Pane resultsPane;
    private Pane displayFilePane;

    @FXML
    private BorderPane rootPane;

    @Override
    public void rampUp(ApplicationContext anApplicationContext) {

        super.rampUp(anApplicationContext);

        // Load pane's sub-panes
        FXMLUtil.ControllerRegionPair<InputPaneController,Pane> tmpInputPaneControllerRegionPair = FXMLUtil
                .loadAndRampUpRegion("view/InputPane.fxml", applicationContext,
                        GlobalConstants.DEFAULT_RESOURCE_LOAD_CONTEXT);
        FXMLUtil.ControllerRegionPair<ResultPaneController,Pane> tmpResultPaneControllerRegionPair = FXMLUtil
                .loadAndRampUpRegion("view/ResultPane.fxml", applicationContext,
                        GlobalConstants.DEFAULT_RESOURCE_LOAD_CONTEXT);
        FXMLUtil.ControllerRegionPair<DisplayFilePaneController,Pane> tmpDisplayFilePaneControllerRegionPair =
                FXMLUtil.loadAndRampUpRegion("view/DisplayFilePane.fxml", applicationContext);

        inputPane = tmpInputPaneControllerRegionPair.getRegion();
        resultsPane = tmpResultPaneControllerRegionPair.getRegion();
        displayFilePane = tmpDisplayFilePaneControllerRegionPair.getRegion();

        // Remember the controller for later
        paneControllerList.add(tmpInputPaneControllerRegionPair.getController());
        paneControllerList.add(tmpResultPaneControllerRegionPair.getController());
        paneControllerList.add(tmpDisplayFilePaneControllerRegionPair.getController());

        // Set dimensions
        setWidths();
        setHeights();

        // Add change listeners
        final ChangeListener<Number> tmpStageWidthPropertyListener =
                ((anObservable, anOldValue, aNewValue) -> setWidths());
        stageWidthPropertyListenersList.add(tmpStageWidthPropertyListener);
        stage.widthProperty().addListener(tmpStageWidthPropertyListener);

        final ChangeListener<Number> tmpStageHeightPropertyListener =
                ((anObservable, anOldValue, aNewValue) -> setHeights());
        stageHeightPropertyListenersList.add(tmpStageHeightPropertyListener);
        stage.heightProperty().addListener(tmpStageHeightPropertyListener);

        // Set sub-panes
        rootPane.setLeft(inputPane);
        rootPane.setCenter(resultsPane);
        rootPane.setRight(displayFilePane);
    }

    @Override
    public void rampDown() {

        // Ramp down all Pane controllers
        for (BaseController tmpCurrentController : paneControllerList) {
            tmpCurrentController.rampDown();
        }

        // Remove width and height listeners
        ReadOnlyDoubleProperty tmpStageWidthProperty = stage.widthProperty();
        for (ChangeListener<Number> tmpCurrentListener : stageWidthPropertyListenersList) {
            tmpStageWidthProperty.removeListener(tmpCurrentListener);
        }
        stageWidthPropertyListenersList.clear();

        ReadOnlyDoubleProperty tmpStageHeightProperty = stage.heightProperty();
        for (ChangeListener<Number> tmpCurrentListener : stageHeightPropertyListenersList) {
            tmpStageHeightProperty.removeListener(tmpCurrentListener);
        }
        stageHeightPropertyListenersList.clear();

        // Do this after we did our own ramp down. Because stage will be set to null there.
        super.rampDown();
    }

    private void setWidths() {

        final double tmpStageWidth = stage.getWidth();
        inputPane.setPrefWidth(tmpStageWidth * 0.2);
        resultsPane.setPrefWidth(tmpStageWidth * 0.4);
        displayFilePane.setPrefWidth(tmpStageWidth * 0.4);
    }

    private void setHeights() {

        final double aStageHeight = stage.getHeight();
        inputPane.setPrefHeight(aStageHeight);
        resultsPane.setPrefHeight(aStageHeight);
        displayFilePane.setPrefHeight(aStageHeight);
    }

}
