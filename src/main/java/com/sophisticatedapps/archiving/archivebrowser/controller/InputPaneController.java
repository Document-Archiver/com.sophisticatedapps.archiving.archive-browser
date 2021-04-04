package com.sophisticatedapps.archiving.archivebrowser.controller;

import com.sophisticatedapps.archiving.archivebrowser.GlobalConstants;
import com.sophisticatedapps.archiving.archivebrowser.type.InputData;
import com.sophisticatedapps.archiving.documentarchiver.controller.BaseController;
import com.sophisticatedapps.archiving.documentarchiver.model.Tags;
import com.sophisticatedapps.archiving.documentarchiver.util.CollectionUtil;
import com.sophisticatedapps.archiving.documentarchiver.util.DatePickerStringConverter;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InputPaneController extends BaseController {

    @FXML
    private TextField searchForTextField;

    @FXML
    private DatePicker fromDatePicker;

    @FXML
    private DatePicker toDatePicker;

    @FXML
    private TextField tagsTextField;

    @FXML
    private ListView<String> existingTagsListView;

    @FXML
    private ListView<String> selectedTagsListView;

    @Override
    public void rampUp(Stage aStage) {

        super.rampUp(aStage);

        // Set converter, filter, etc.
        DatePickerStringConverter tmpDatePickerStringConverter = new DatePickerStringConverter();
        fromDatePicker.setConverter(tmpDatePickerStringConverter);
        toDatePicker.setConverter(tmpDatePickerStringConverter);
        setNewExistingTagsToListView(new ArrayList<>(Tags.getExistingTags()));

        searchForTextField.textProperty().addListener((anObservable, anOldValue, aNewValue) ->
                setNewInputDataToStageProperties());

        final ChangeListener<Boolean> tmpFromDatePickerFocusedPropertyListener =
                ((aChangedValue, anOldValue, aNewValue) ->
                        handleDatePickerFocusedPropertyValueChanged(fromDatePicker, aNewValue));
        final ChangeListener<Boolean> tmpToDatePickerFocusedPropertyListener =
                ((aChangedValue, anOldValue, aNewValue) ->
                        handleDatePickerFocusedPropertyValueChanged(toDatePicker, aNewValue));
        //datePickerFocusedPropertyListenersList.add(tmpDatePickerFocusedPropertyListener);
        fromDatePicker.focusedProperty().addListener(tmpFromDatePickerFocusedPropertyListener);
        toDatePicker.focusedProperty().addListener(tmpToDatePickerFocusedPropertyListener);

        fromDatePicker.valueProperty().addListener((observable, oldValue, newValue) ->
                setNewInputDataToStageProperties());
        toDatePicker.valueProperty().addListener((observable, oldValue, newValue) ->
                setNewInputDataToStageProperties());

        tagsTextField.textProperty().addListener((anObservable, anOldValue, aNewValue) ->
                handleTagsTextFieldTextChanged(aNewValue));
    }

    /**
     * Method to call when the value of the DatePicker was changed.
     * Not possible to set via FXML, has to be called via 'datePicker.focusedProperty().addListener'.
     *
     * @param   aDatePicker DatePicker of which FocusedProperty was changed.
     * @param   aNewValue   Value after change.
     */
    protected void handleDatePickerFocusedPropertyValueChanged(DatePicker aDatePicker, boolean aNewValue) {

        // Without this, manual set dates are not taken over
        if (!aNewValue) {

            LocalDate tmpCurrentValue = aDatePicker.getValue();
            LocalDate tmpValueOfText = aDatePicker.getConverter().fromString(aDatePicker.getEditor().getText());

            if(!String.valueOf(tmpCurrentValue).equals(String.valueOf(tmpValueOfText))) {

                aDatePicker.setValue(tmpValueOfText);
                setNewInputDataToStageProperties();
            }
        }
    }

    /**
     * Method to call when the text of the tag TextField was changed.
     * Not possible to set via FXML, has to be called via 'tagsTextField.textProperty().addListener'.
     *
     * @param   aNewValue   Value after change.
     */
    protected void handleTagsTextFieldTextChanged(String aNewValue) {

        FilteredList<String> tmpFilteredList = (FilteredList<String>)existingTagsListView.getItems();

        if (aNewValue.isEmpty()) {

            tmpFilteredList.setPredicate(null);
        }
        else {

            final String tmpNewValueString = aNewValue.toUpperCase();
            tmpFilteredList.setPredicate(s -> s.toUpperCase().contains(tmpNewValueString));
        }
    }

    @FXML
    protected void handleTagsTextFieldKeyPressed(KeyEvent aKeyEvent) {

        if (aKeyEvent.getCode().equals(KeyCode.DOWN) && (!existingTagsListView.getItems().isEmpty())) {

            existingTagsListView.requestFocus();
            existingTagsListView.getFocusModel().focus(0);
            existingTagsListView.getSelectionModel().select(0);
        }
    }

    @FXML
    protected void handleExistingTagsListViewClicked() {

        String tmpSelectedItem = existingTagsListView.getSelectionModel().getSelectedItem();
        CollectionUtil.addToListIfNotContainedYet(selectedTagsListView.getItems(), tmpSelectedItem);
        tagsTextField.setText("");
        tagsTextField.requestFocus();

        setNewInputDataToStageProperties();
    }

    @FXML
    protected void handleExistingTagsListViewKeyPressed(KeyEvent aKeyEvent) {

        if (aKeyEvent.getCode().equals(KeyCode.UP)) {

            if (existingTagsListView.getSelectionModel().getSelectedIndex() == 0) {

                tagsTextField.requestFocus();
            }
        }
        else if (aKeyEvent.getCode().equals(KeyCode.ENTER)) {

            // Same as if clicked.
            handleExistingTagsListViewClicked();
        }
    }

    @FXML
    protected void handleSelectedTagsListViewClicked() {

        String tmpSelectedItem = selectedTagsListView.getSelectionModel().getSelectedItem();
        selectedTagsListView.getItems().remove(tmpSelectedItem);

        setNewInputDataToStageProperties();
    }

    protected void setNewExistingTagsToListView(List<String> aNewExistingTagsList) {

        ObservableList<String> tmpObservableList = FXCollections.observableList(aNewExistingTagsList);
        existingTagsListView.setItems(new FilteredList<>(tmpObservableList));
    }

    protected void setNewInputDataToStageProperties() {

        LocalDate tmpFromDate = fromDatePicker.getValue();
        LocalDate tmpToDate = toDatePicker.getValue();

        InputData tmpInputData = new InputData();
        tmpInputData.setSearchFor(searchForTextField.getText());
        tmpInputData.setTagList(selectedTagsListView.getItems());
        tmpInputData.setFromDate(Objects.isNull(tmpFromDate) ? null : tmpFromDate.atStartOfDay());
        tmpInputData.setToDate(Objects.isNull(tmpToDate) ? null : tmpToDate.atTime(23, 59, 59));

        stage.getProperties().put(GlobalConstants.INPUT_DATA_PROPERTY_KEY, tmpInputData);
    }

}
