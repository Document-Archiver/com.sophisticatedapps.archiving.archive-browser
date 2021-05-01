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

import com.sophisticatedapps.archiving.documentarchiver.api.ArchiveBrowsingService;
import com.sophisticatedapps.archiving.documentarchiver.util.FXMLUtil;
import com.sophisticatedapps.archiving.documentarchiver.util.ThemeUtil;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class AbArchiveBrowsingService implements ArchiveBrowsingService {

    @Override
    public void assemble(Stage aStage) {

        // Create root pane
        BorderPane tmpRootPane = (BorderPane) FXMLUtil.loadAndRampUpRegion("view/AbRootPane.fxml",
                aStage, GlobalConstants.DEFAULT_RESOURCE_LOAD_CONTEXT).getRegion();

        // Wrap root pane into a Scene and put it to the given stage.
        Scene tmpScene = new Scene(tmpRootPane);
        ThemeUtil.applyCurrentTheme(tmpScene);
        aStage.setScene(tmpScene);
    }

}
