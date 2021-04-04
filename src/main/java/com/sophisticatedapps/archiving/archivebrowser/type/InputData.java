package com.sophisticatedapps.archiving.archivebrowser.type;

import com.sophisticatedapps.archiving.documentarchiver.util.StringUtil;

import java.time.LocalDateTime;
import java.util.List;

public class InputData {

    private String searchFor = StringUtil.EMPTY_STRING;
    private List<String> tagList;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;

    public String getSearchFor() {
        return searchFor;
    }

    public void setSearchFor(String aSearchFor) {
        this.searchFor = aSearchFor;
    }

    public List<String> getTagList() {
        return tagList;
    }

    public void setTagList(List<String> aTagList) {
        this.tagList = aTagList;
    }

    public LocalDateTime getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDateTime aFromDate) {
        this.fromDate = aFromDate;
    }

    public LocalDateTime getToDate() {
        return toDate;
    }

    public void setToDate(LocalDateTime aToDate) {
        this.toDate = aToDate;
    }

}
