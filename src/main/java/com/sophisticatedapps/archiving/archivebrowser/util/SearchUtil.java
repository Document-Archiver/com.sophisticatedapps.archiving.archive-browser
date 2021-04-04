package com.sophisticatedapps.archiving.archivebrowser.util;

import com.sophisticatedapps.archiving.archivebrowser.type.InputData;
import com.sophisticatedapps.archiving.archivebrowser.type.Result;
import com.sophisticatedapps.archiving.documentarchiver.GlobalConstants;
import com.sophisticatedapps.archiving.documentarchiver.model.Tags;
import com.sophisticatedapps.archiving.documentarchiver.util.CollectionUtil;
import com.sophisticatedapps.archiving.documentarchiver.util.DirectoryUtil;
import com.sophisticatedapps.archiving.documentarchiver.util.FileUtil;
import com.sophisticatedapps.archiving.documentarchiver.util.StringUtil;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class SearchUtil {

    /**
     * Private constructor.
     */
    private SearchUtil() {
    }

    public static Set<Result> search(InputData anInputData) {

        SortedSet<Result> tmpReturn = new TreeSet<>();

        getMatchingResultsFromDirectory(DirectoryUtil.getArchivingRootFolder(), anInputData, tmpReturn);

        return tmpReturn;
    }

    private static void getMatchingResultsFromDirectory(File aDirectoryPath, InputData anInputData,
                                                        SortedSet<Result> aResultSet) {

        File[] tmpFilesList = Objects.requireNonNull(aDirectoryPath.listFiles());

        for (File tmpCurrentFile : tmpFilesList) {

            if (tmpCurrentFile.isDirectory()) {

                getMatchingResultsFromDirectory(tmpCurrentFile, anInputData, aResultSet);
            }
            else if (tmpCurrentFile.isFile()) {

                boolean tmpAddResult = checkIfSearchForMatches(anInputData, tmpCurrentFile);
                tmpAddResult = (tmpAddResult && checkIfTagsAreMatching(anInputData, tmpCurrentFile));
                tmpAddResult = (tmpAddResult && checkIfDateIsFitting(anInputData, tmpCurrentFile));

                if (tmpAddResult) {

                    aResultSet.add(new Result(tmpCurrentFile, (new ArrayList<>()), LocalDateTime.now(),
                            FileUtil.getFiletype(tmpCurrentFile)));
                }
            }
        }
    }

    private static boolean checkIfSearchForMatches(InputData anInputData, File aFile) {

        String tmpSearchForString = anInputData.getSearchFor();

        if (StringUtil.EMPTY_STRING.equals(tmpSearchForString)) {
            return true;
        }

        String tmpFileName = aFile.getName();
        int tmpStartDescriptionIndex = tmpFileName.lastIndexOf("--");
        int tmpStopDescriptionIndex = tmpFileName.lastIndexOf("__");

        if ((tmpStartDescriptionIndex > 0) && (tmpStopDescriptionIndex > (tmpStartDescriptionIndex + 2))) {

            String tmpDescription = tmpFileName.substring((tmpStartDescriptionIndex + 2), tmpStopDescriptionIndex);
            return (tmpDescription.toLowerCase().contains(tmpSearchForString.toLowerCase()));
        }
        else {
            return false;
        }
    }

    private static boolean checkIfTagsAreMatching(InputData anInputData, File aFile) {

        List<String> tmpFilterTags = anInputData.getTagList();

        if (!CollectionUtil.isNullOrEmpty(tmpFilterTags)) {

            return Tags.getTagsFromFile(aFile).containsAll(tmpFilterTags);
        }
        else {

            return true;
        }
    }

    private static boolean checkIfDateIsFitting(InputData anInputData, File aFile) {

        LocalDateTime tmpFromDate = anInputData.getFromDate();
        LocalDateTime tmpToDate = anInputData.getToDate();

        if ((!Objects.isNull(tmpFromDate)) || (!Objects.isNull(tmpToDate))) {

            String tmpFileName = aFile.getName();
            int tmpDateSeparatorIndex = tmpFileName.indexOf("--");

            if (tmpDateSeparatorIndex < 10) {

                return false;
            }

            LocalDateTime tmpFileDate;

            if (tmpDateSeparatorIndex == 10) {
                tmpFileDate = LocalDate.from(GlobalConstants.FILENAME_ONLY_DATE_DATE_TIME_FORMATTER.parse(
                        tmpFileName.substring(0, 10))).atStartOfDay();
            }
            else {
                tmpFileDate = LocalDateTime.from(GlobalConstants.FILENAME_DATE_TIME_FORMATTER.parse(
                        tmpFileName.substring(0, 19)));
            }

            if (!Objects.isNull(tmpFromDate) && (tmpFileDate.isBefore(tmpFromDate))) {
                return false;
            }
            if (!Objects.isNull(tmpToDate) && (tmpFileDate.isAfter(tmpToDate))) {
                return false;
            }
        }

        return true;
    }

}
