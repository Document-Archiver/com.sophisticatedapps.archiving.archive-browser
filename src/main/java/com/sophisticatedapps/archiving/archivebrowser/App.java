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

package com.sophisticatedapps.archiving.archivebrowser;

import com.sophisticatedapps.archiving.documentarchiver.util.FXMLUtil;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URL;
import java.util.Objects;

public class App extends Application {

    /**
     * Main method.
     *
     * @param   args    Command line arguments.
     */
    public static void main(String[] args) {

        launch(args);
    }

    /**
     * Application start method.
     *
     * @param   aPrimaryStage   The primary stage.
     */
    @Override
    public void start(Stage aPrimaryStage) {

        // Set dimensions
        Rectangle2D tmpBounds = Screen.getPrimary().getVisualBounds();
        aPrimaryStage.setX(tmpBounds.getMinX());
        aPrimaryStage.setY(tmpBounds.getMinY());
        aPrimaryStage.setWidth(tmpBounds.getWidth());
        aPrimaryStage.setHeight(tmpBounds.getHeight());

        // Create root pane
        BorderPane tmpRootPane = (BorderPane)FXMLUtil.loadAndRampUpRegion("view/AbRootPane.fxml",
                aPrimaryStage, GlobalConstants.DEFAULT_RESOURCE_LOAD_CONTEXT).getRegion();

        // Place icons
        //placeIcons(aPrimaryStage);

        // Show
        aPrimaryStage.setScene(new Scene(tmpRootPane));
        aPrimaryStage.show();
    }

    private static void placeIcons(Stage aStage) {

        // JavaFX Image
        Image tmpIconImage = new Image(
                Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResourceAsStream("binder-icon.png")));

        // Set stage icon
        aStage.getIcons().add(tmpIconImage);

        // AWT Image
        final URL imageResource =
                Thread.currentThread().getContextClassLoader().getResource("binder-icon.png");
        final java.awt.Image tmpAwtImage = Toolkit.getDefaultToolkit().getImage(imageResource);

        // Set taskbar icon (may not be supported on all systems (e.g. Linux))
        try {

            final Taskbar taskbar = Taskbar.getTaskbar();
            taskbar.setIconImage(tmpAwtImage);
        }
        catch (UnsupportedOperationException e) {

            // never mind.
        }
    }

}
