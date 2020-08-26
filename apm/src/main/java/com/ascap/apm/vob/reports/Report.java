package com.ascap.apm.vob.reports;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.GenericTypeValidator;
import org.apache.commons.validator.GenericValidator;
import org.springframework.context.MessageSource;

import com.ascap.apm.common.utils.constants.PrepConstants;
import com.ascap.apm.vob.BaseSearchVOB;

public class Report extends BaseSearchVOB implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 174183945971996167L;

    private String reportDesc;

    private String reportID;

    private String reportName;

    private String description;

    private String moduleName;

    private List<Parameter> parameters = null;

    private List<Object> statusList = null;

    private String reportType;

    private ScheduleInfo scheduleInfo;

    private String userId;

    private String fname;

    private String lname;

    private String requestedDate;

    private long partyId;

    private List<Object> businessErrors = null;

    private boolean adminNewReport;

    private int userInstanceCnt;

    private int archiveInstanceCnt;

    private String returnedEndDate;

    private boolean adminRunnableFlag;

    private String newJobId;

    private String databaseType;

    private String radioValue;

    private int page;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    /**
     * Constructor for Report.
     */
    public Report() {
        super();

    }

    public String toString() {
        return ("****Report                         >> \n" + "****reportID                       >> " + reportID + "\n"
            + "****reportName                     >> " + reportName + "\n" + "****description                    >> "
            + description + "\n" + "****moduleName                     >> " + moduleName + "\n"
            + "****parameters                     >> " + parameters + "\n" + "****statusList                     >> "
            + statusList + "\n" + "****reportType                     >> " + reportType + "\n"
            + "****scheduleInfo                   >> " + scheduleInfo + "\n" + "****userId                         >> "
            + userId + "\n" + "****fname                          >> " + fname + "\n"
            + "****lname                          >> " + lname + "\n" + "****requestedDate                  >> "
            + requestedDate + "\n" + "****partyId                        >> " + partyId + "\n"
            + "****businessErrors                 >> " + businessErrors + "\n"
            + "****adminNewReport                 >> " + adminNewReport + "\n"
            + "****userInstanceCnt                >> " + userInstanceCnt + "\n"
            + "****archiveInstanceCnt             >> " + archiveInstanceCnt + "\n"
            + "****returnedEndDate                >> " + returnedEndDate + "\n"
            + "****getNumberOfRecordsFound        >> " + super.getNumberOfRecordsFound() + "\n"
            + "****getNumberOfRecordsFoundbySearch>> " + super.getNumberOfRecordsFoundbySearch() + "\n"
            + "****adminRunnableFlag              >> " + adminRunnableFlag + "\n"
            + "****newJobId              			>> " + newJobId + "\n"
            + "****databaseType              			>> " + databaseType + "\n");

    }

    public Report(String reportID, String reportName, String description, String moduleName,
        List<Parameter> parameters) {
        this.reportID = reportID;
        this.reportName = reportName;
        this.description = description;
        this.moduleName = moduleName;
        this.parameters = parameters;
    }

    /**
     * Returns the description.
     * 
     * @return String
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the moduleName.
     * 
     * @return String
     */
    public String getModuleName() {
        return moduleName;
    }

    /**
     * Returns the parameters.
     * 
     * @return List
     */
    public List<Parameter> getParameters() {
        return parameters;
    }

    /**
     * Returns the reportID.
     * 
     * @return String
     */
    public String getReportID() {
        return reportID;
    }

    /**
     * Returns the reportName.
     * 
     * @return String
     */
    public String getReportName() {
        return reportName;
    }

    /**
     * Sets the description.
     * 
     * @param description The description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets the moduleName.
     * 
     * @param moduleName The moduleName to set
     */
    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    /**
     * Sets the parameters.
     * 
     * @param parameters The parameters to set
     */
    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    /**
     * Sets the reportID.
     * 
     * @param reportID The reportID to set
     */
    public void setReportID(String reportID) {
        this.reportID = reportID;
    }

    /**
     * Sets the reportName.
     * 
     * @param reportName The reportName to set
     */
    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    /**
     * Returns the statusList.
     * 
     * @return List
     */
    public List<Object> getStatusList() {
        return statusList;
    }

    /**
     * Sets the statusList.
     * 
     * @param statusList The statusList to set
     */
    public void setStatusList(List<Object> statusList) {
        this.statusList = statusList;
    }

    /**
     * Returns the reportType.
     * 
     * @return String
     */
    public String getReportType() {
        return reportType;
    }

    /**
     * Sets the reportType.
     * 
     * @param reportType The reportType to set
     */
    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    /**
     * Returns the scheduleInfo.
     * 
     * @return ScheduleInfo
     */
    public ScheduleInfo getScheduleInfo() {
        return scheduleInfo;
    }

    /**
     * Sets the scheduleInfo.
     * 
     * @param scheduleInfo The scheduleInfo to set
     */
    public void setScheduleInfo(ScheduleInfo scheduleInfo) {
        this.scheduleInfo = scheduleInfo;
    }

    /**
     * Returns the partyId.
     * 
     * @return String
     */
    public long getPartyId() {
        return partyId;
    }

    /**
     * Returns the requestedDate.
     * 
     * @return String
     */
    public String getRequestedDate() {
        return requestedDate;
    }

    /**
     * Returns the userId.
     * 
     * @return String
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the partyId.
     * 
     * @param partyId The partyId to set
     */
    public void setPartyId(long partyId) {
        this.partyId = partyId;
    }

    /**
     * Sets the requestedDate.
     * 
     * @param requestedDate The requestedDate to set
     */
    public void setRequestedDate(String requestedDate) {
        this.requestedDate = requestedDate;
    }

    /**
     * Sets the userId.
     * 
     * @param userId The userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Returns the businessErrors.
     * 
     * @return List
     */
    public List<Object> getBusinessErrors() {
        return businessErrors;
    }

    /**
     * Sets the businessErrors.
     * 
     * @param businessErrors The businessErrors to set
     */
    public void setBusinessErrors(List<Object> businessErrors) {
        this.businessErrors = businessErrors;
    }

    /**
     * Returns the adminNewReport.
     * 
     * @return boolean
     */
    public boolean isAdminNewReport() {
        return adminNewReport;
    }

    /**
     * Sets the adminNewReport.
     * 
     * @param adminNewReport The adminNewReport to set
     */
    public void setAdminNewReport(boolean adminNewReport) {
        this.adminNewReport = adminNewReport;
    }

    /**
     * @return Returns the fname.
     */
    public String getFname() {
        return fname;
    }

    /**
     * @param fname The fname to set.
     */
    public void setFname(String fname) {
        this.fname = fname;
    }

    /**
     * @return Returns the lname.
     */
    public String getLname() {
        return lname;
    }

    /**
     * @param lname The lname to set.
     */
    public void setLname(String lname) {
        this.lname = lname;
    }

    /**
     * @return Returns the returnedEndDate.
     */
    public String getReturnedEndDate() {
        return returnedEndDate;
    }

    /**
     * @param returnedEndDate The returnedEndDate to set.
     */
    public void setReturnedEndDate(String returnedEndDate) {
        this.returnedEndDate = returnedEndDate;
    }

    /**
     * @return Returns the archiveInstanceCnt.
     */
    public int getArchiveInstanceCnt() {
        return archiveInstanceCnt;
    }

    /**
     * @param archiveInstanceCnt The archiveInstanceCnt to set.
     */
    public void setArchiveInstanceCnt(int archiveInstanceCnt) {
        this.archiveInstanceCnt = archiveInstanceCnt;
    }

    /**
     * @return Returns the userInstanceCnt.
     */
    public int getUserInstanceCnt() {
        return userInstanceCnt;
    }

    /**
     * @param userInstanceCnt The userInstanceCnt to set.
     */
    public void setUserInstanceCnt(int userInstanceCnt) {
        this.userInstanceCnt = userInstanceCnt;
    }

    /**
     * @return Returns the adminRunnableFlag.
     */
    public boolean isAdminRunnableFlag() {
        return adminRunnableFlag;
    }

    /**
     * @param adminRunnableFlag The adminRunnableFlag to set.
     */
    public void setAdminRunnableFlag(boolean adminRunnableFlag) {
        this.adminRunnableFlag = adminRunnableFlag;
    }

    /**
     * @return Returns the newJobId.
     */
    public String getNewJobId() {
        return newJobId;
    }

    /**
     * @param newJobId The newJobId to set.
     */
    public void setNewJobId(String newJobId) {
        this.newJobId = newJobId;
    }

    public String getDatabaseType() {
        return databaseType;
    }

    public void setDatabaseType(String databaseType) {
        this.databaseType = databaseType;
    }

    /**
     * Returns the description.
     * 
     * @return String
     */
    public String getReportDesc() {
        return reportDesc;
    }

    /**
     * Sets the description.
     * 
     * @param description The description to set
     */
    public void setReportDesc(String reportDesc) {
        this.reportDesc = reportDesc;
    }

    public String getRadioValue() {
        return radioValue;
    }

    public void setRadioValue(String radioValue) {
        this.radioValue = radioValue;
    }

    public void validate(HttpServletRequest request, MessageSource messageSource) {
        List<String> errors = null;
        boolean status = false;
        String key = null;
        try {
            if (parameters != null) {
                for (int i = 0; i < parameters.size(); i++) {
                    status = true;
                    key = null;
                    Parameter param = parameters.get(i);

                    if (PrepConstants.TYPE_NUMBER.equalsIgnoreCase(param.getParameterType())) {

                        status = validateIntParam(param.getInputValue(), param.isRequired());
                        key = "ap.int.error";

                    } else if (PrepConstants.TYPE_DATE.equalsIgnoreCase(param.getParameterType())) {

                        status = validateDateParam(param.getInputValue(), param.isRequired());
                        key = "ap.date.error";

                    } else if (PrepConstants.TYPE_CURRENCY.equalsIgnoreCase(param.getParameterType())) {
                        status = validateCurrencyParam(param.getInputValue(), param.isRequired());
                        key = "ap.currency.error";

                    } else if (PrepConstants.TYPE_TIME.equalsIgnoreCase(param.getParameterType())) {
                        status = validateTimeParam(param.getInputValue(), param.isRequired());
                        key = "ap.time.error";

                    } else if (PrepConstants.TYPE_DATETIME.equalsIgnoreCase(param.getParameterType())) {
                        status = validateDateTimeParam(param.getInputValue(), param.isRequired());
                        key = "ap.datetime.error";

                    } else if (PrepConstants.TYPE_STRING.equalsIgnoreCase(param.getParameterType())) {
                        status = validateStringParam(param.getInputValue(), param.isRequired());
                        key = "ap.string.error";
                    }

                    if (!status) {
                        if (errors == null)
                            errors = new ArrayList<>();
                        if (param.getParamLabel() == null || "".equalsIgnoreCase(param.getParamLabel())) {
                            errors.add(messageSource.getMessage(key, new Object[] {param.getParamName()},
                                Locale.getDefault()));
                        } else {
                            errors.add(messageSource.getMessage(key, new Object[] {param.getParamLabel()},
                                Locale.getDefault()));
                        }
                    }
                }
            } else {
                parameters = new ArrayList<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.setAttribute("systemerrorlist", errors);

    }

    public boolean validateIntParam(String value, boolean required) {
        Object result = null;
        if (isNotNull(value)) {
            value = value.trim();
            result = GenericTypeValidator.formatDouble(value);
            return result == null ? false : true;
        } else if (!required) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validateStringParam(String value, boolean required) {
        boolean isValid = false;
        if (isNotNull(value)) {
            isValid = true;
        } else if (!required) {
            isValid = true;
        } else {
            return false;
        }
        return isValid;
    }

    public boolean validateTimeParam(String value, boolean required) {
        boolean validation = false;
        if (isNotNull(value)) {
            value = value.trim();
            validation = GenericValidator.isDate(value, "hh:mm:ss", false);
        } else if (!required) {
            validation = true;
        } else {
            validation = false;
        }
        return validation;
    }

    public boolean validateDateParam(String value, boolean required) {
        boolean validation = false;
        if (isNotNull(value)) {
            value = value.trim();
            validation = GenericValidator.isDate(value, "MM/dd/yyyy", false);
        } else if (!required) {
            validation = true;
        } else {
            validation = false;
        }
        return validation;
    }

    public boolean validateDateTimeParam(String value, boolean required) {
        boolean validation = false;
        if (isNotNull(value)) {
            value = value.trim();
            try {
                validation = GenericValidator.isDate(value, "MM/dd/yyyy hh:mm:ss", false);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!validation && value.length() == 10) {
                int i = 0;
                boolean flag = true;
                while ((i < value.length()) && flag) {
                    if (((value.charAt(i) < '0') || (value.charAt(i) > '9')) && (value.charAt(i) != '/'))
                        flag = false;
                    i++;
                }
                validation = flag;
                if (validation)
                    validation = GenericValidator.isDate(value, "MM/dd/yyyy", false);
            }
        } else if (!required) {
            validation = true;
        } else {
            validation = false;
        }
        return validation;
    }

    public boolean validateCurrencyParam(String value, boolean required) {
        return true;
    }

    public boolean isNotNull(String value) {
        boolean isValid = false;
        if (value == null || value.trim().length() <= 0)
            isValid = false;
        else
            isValid = true;
        return isValid;

    }
}
