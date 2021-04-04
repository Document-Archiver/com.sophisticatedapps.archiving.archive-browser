package com.sophisticatedapps.archiving.archivebrowser.type;

import com.sophisticatedapps.archiving.documentarchiver.type.FileTypeEnum;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

public class Result implements Comparable {

    private File file;
    private List<String> tagList;
    LocalDateTime dateTime;
    FileTypeEnum fileType;

    public Result(File file, List<String> tagList, LocalDateTime dateTime, FileTypeEnum fileType) {

        this.file = file;
        this.tagList = tagList;
        this.dateTime = dateTime;
        this.fileType = fileType;
    }

    public File getFile() {
        return file;
    }

    public List<String> getTagList() {
        return tagList;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public FileTypeEnum getFileType() {
        return fileType;
    }

    @Override
    public int compareTo(Object anOtherResult) {

        return this.file.getPath().compareTo(((Result)anOtherResult).file.getPath());
    }

}
