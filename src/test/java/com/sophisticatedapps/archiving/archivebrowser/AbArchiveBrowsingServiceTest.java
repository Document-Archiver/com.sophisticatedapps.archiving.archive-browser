package com.sophisticatedapps.archiving.archivebrowser;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.collections.ObservableMap;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(ApplicationExtension.class)
class AbArchiveBrowsingServiceTest {

    @Test
    void assemble() {

        Stage tmpMockedStage = Mockito.mock(Stage.class);
        @SuppressWarnings("unchecked")
        ObservableMap<Object, Object> tmpMockedStageProperties = Mockito.mock(ObservableMap.class);
        when(tmpMockedStage.getProperties()).thenReturn(tmpMockedStageProperties);
        ReadOnlyDoubleProperty tmpMockedReadOnlyDoubleProperty = Mockito.mock(ReadOnlyDoubleProperty.class);
        when(tmpMockedStage.widthProperty()).thenReturn(tmpMockedReadOnlyDoubleProperty);
        when(tmpMockedStage.heightProperty()).thenReturn(tmpMockedReadOnlyDoubleProperty);

        (new AbArchiveBrowsingService()).assemble(tmpMockedStage);
        WaitForAsyncUtils.waitForFxEvents();

        verify(tmpMockedStage, Mockito.times(1)).setScene(any(Scene.class));
    }

    @Test
    void getVersion() {

        assertEquals("1.0.0", (new AbArchiveBrowsingService()).getVersion());
    }

}
