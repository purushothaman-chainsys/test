package com.ascap.apm.vob.usage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.MessageSource;
import org.springframework.web.multipart.MultipartFile;

import com.ascap.apm.common.utils.UsageCommonValidations;
import com.ascap.apm.common.utils.constants.UsageConstants;
import com.ascap.apm.controller.utils.HtmlSelectOption;
import com.ascap.apm.controller.utils.Utils;
import com.ascap.apm.vob.BaseVOB;

public class ApmFileUpload extends BaseVOB {

    private static final long serialVersionUID = -2200015875338685521L;

    private MultipartFile radioFile;

    String fileName;

    String userId = null;

    String fileStatus;

    String fileType;

    String uploadDate;

    String targetYearQuarter;

    String fileId = null;

    String filePath = null;

    String fileOrigPath = null;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFileStatus() {
        return fileStatus;
    }

    public void setFileStatus(String fileStatus) {
        this.fileStatus = fileStatus;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[ fileName=");
        builder.append(fileName);
        builder.append("[ userId=");
        builder.append(userId);
        builder.append("]");
        return builder.toString();
    }

    public String getTargetYearQuarter() {
        return targetYearQuarter;
    }

    public void setTargetYearQuarter(String targetYearQuarter) {
        this.targetYearQuarter = targetYearQuarter;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileOrigPath() {
        return fileOrigPath;
    }

    public void setFileOrigPath(String fileOrigPath) {
        this.fileOrigPath = fileOrigPath;
    }

    public MultipartFile getRadioFile() {
        return radioFile;
    }

    public void setRadioFile(MultipartFile radioFile) {
        this.radioFile = radioFile;
    }

    public List<String> validate(HttpServletRequest request, MessageSource messageSource) {

        List<String> errors = new ArrayList<>();
        String loadFileName = radioFile.getName();

        if (loadFileName.trim().equals("") || loadFileName.lastIndexOf('.') == -1) {
            errors.add(messageSource.getMessage("us.error.apm.fileUpload.noFile", null, Locale.getDefault()));
        } else if (getRadioFile().getName().length() > 1024000) {
            errors.add(messageSource.getMessage("us.error.apm.fileUpload.fileName.tooLong", null, Locale.getDefault()));
            return errors;
        } else {

            if (!Utils.fileExtensionValid(loadFileName))
                errors.add(messageSource.getMessage("us.error.apm.fileUpload.fileExtn.notTxt", null, Locale.getDefault()));
            if (!Utils.fileNameStartingCharValid(loadFileName))
                errors
                    .add(messageSource.getMessage("us.error.apm.filenamestartingchar.invalid", null, Locale.getDefault()));

            if (!Utils.fileNameValid(loadFileName))
                errors.add(messageSource.getMessage("us.error.apm.filename.invalid", null, Locale.getDefault()));

            if (getRadioFile().isEmpty()) {
                errors.add(messageSource.getMessage("us.error.apm.fileUpload.file.empty", null, Locale.getDefault()));
                return errors;
            }

        }

        if (UsageCommonValidations.isEmptyOrNull(getFileType())) {
            errors.add(messageSource.getMessage("us.error.apm.fileUpload.fileType.null", null, Locale.getDefault()));
            return errors;

        } else if (getFileType().equalsIgnoreCase(UsageConstants.FILE_TYPE_DISTRIBUTION)) {
            errors
                .add(messageSource.getMessage("us.error.apm.fileUpload.fileType.distribution", null, Locale.getDefault()));
            return errors;
        }

        if (UsageCommonValidations.isEmptyOrNull(getTargetYearQuarter())) {
            errors.add(messageSource.getMessage("us.error.apm.fileUpload.distribution.null", null, Locale.getDefault()));
            return errors;
        } else {

            List<Object> col = null;
            col = HtmlSelectOption.getLookUpTable("ActiveTargetSurveyYearQtr");
            Iterator<Object> itr = col.iterator();
            HtmlSelectOption prepKeyValueObject = null;

            while (itr.hasNext()) {
                prepKeyValueObject = (HtmlSelectOption) itr.next();
                if (Integer.parseInt(getTargetYearQuarter()) < Integer.parseInt(prepKeyValueObject.getId())) {
                    errors.add(messageSource.getMessage("us.error.apm.fileUpload.distribution.invalid", null,
                        Locale.getDefault()));
                    return errors;
                }
            }
        }

        return errors;
    }

}
