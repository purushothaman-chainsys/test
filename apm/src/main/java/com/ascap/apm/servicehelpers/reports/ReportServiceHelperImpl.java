package com.ascap.apm.servicehelpers.reports;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.stereotype.Service;

import com.ascap.apm.common.exception.PrepFunctionalException;
import com.ascap.apm.common.exception.PrepSystemException;
import com.ascap.apm.common.helpers.PrepExtPropertyHelper;
import com.ascap.apm.common.utils.constants.PrepConstants;
import com.ascap.apm.common.utils.constants.PrepPropertiesConstants;
import com.ascap.apm.vob.reports.Report;

@Service("reportServiceHelper")
public class ReportServiceHelperImpl implements ReportServiceHelper {
    
    private static final String SIMPLE_DATE_FORMAT = "MM/dd/yyyy";
    
    private static final String TIME_FORMAT = "HH:mm:ss";
    
    private static final String ERROR_KEY = "error.report.format";

    /**
     * Constructor for ReportServiceHelper.
     */
    public ReportServiceHelperImpl() {
        super();
    }

    /**
     * Method formetInstaceDate.
     * 
     * @param value
     * @param type
     * @return String
     */
    public String formatInstaceDate(String value, int type) throws PrepFunctionalException {
        String formattedValue = value;

        SimpleDateFormat input = null;
        SimpleDateFormat output = null;
        String subval = null;
        Date df = null;
        String strType = "" + type;

        if (value == null || value.length() <= 0) {
            return value;
        }

        if (PrepConstants.TYPE_DATETIME.equalsIgnoreCase(strType) || PrepConstants.TYPE_TIME.equalsIgnoreCase(strType)
            || PrepConstants.TYPE_DATE.equalsIgnoreCase(strType))
            subval = value.substring(value.indexOf('(') + 1, value.indexOf(')'));
        try {
            if (PrepConstants.TYPE_DATETIME.equalsIgnoreCase(strType)) {
                input = new SimpleDateFormat("yyyy, M, d");
                output = new SimpleDateFormat(SIMPLE_DATE_FORMAT);
                df = input.parse(subval);
                formattedValue = output.format(df);
            } else if (PrepConstants.TYPE_TIME.equalsIgnoreCase(strType)) {
                input = new SimpleDateFormat("H,m,s");
                new SimpleDateFormat(TIME_FORMAT);
                df = input.parse(subval);
                formattedValue = df.toString();
            } else if (PrepConstants.TYPE_DATE.equalsIgnoreCase(type + "")) {
                input = new SimpleDateFormat("y,M,d");
                new SimpleDateFormat(SIMPLE_DATE_FORMAT);
                df = input.parse(subval);
                formattedValue = df.toString();
            }
        } catch (Exception e) {
            String[] errorStrings = new String[1];
            errorStrings[0] = e.getMessage();
            throw new PrepFunctionalException(ERROR_KEY, errorStrings);

        }
        return formattedValue;
    }

    public String formatdisplayDate(String dateTime) throws PrepSystemException{

        String displayDate = null;
        try {

            SimpleDateFormat sd = new SimpleDateFormat("E MMM dd HH:mm:ss zz yyyy");
            sd.setLenient(false);
            Date date = sd.parse(dateTime);
            Calendar gcal = GregorianCalendar.getInstance();
            gcal.setTime(date);

            String monthStr = (String.valueOf(gcal.get(Calendar.MONTH) + 1).length()) > 1
                ? String.valueOf(gcal.get(Calendar.MONTH) + 1) : "0" + (gcal.get(Calendar.MONTH) + 1);
            String dateStr = (String.valueOf(gcal.get(Calendar.DAY_OF_MONTH)).length()) > 1
                ? String.valueOf(gcal.get(Calendar.DAY_OF_MONTH))
                : "0" + (gcal.get(Calendar.DAY_OF_MONTH));
            String minStr = (String.valueOf(gcal.get(Calendar.MINUTE)).length()) > 1
                ? String.valueOf(gcal.get(Calendar.MINUTE)) : "0" + (gcal.get(Calendar.MINUTE));

            displayDate = monthStr + "/" + dateStr + "/" + gcal.get(Calendar.YEAR) + " "
                + gcal.get(Calendar.HOUR_OF_DAY) + ":" + minStr;
        } catch (ParseException pse) {
            String[] errorStrings = new String[1];
            errorStrings[0] = pse.getMessage();
            throw new PrepSystemException(ERROR_KEY, errorStrings);
        } catch (Exception e) {
            String[] errorStrings = new String[1];
            errorStrings[0] = e.getMessage();
            throw new PrepSystemException(ERROR_KEY, errorStrings);
        }

        return displayDate.trim();
    }

    public String formatdisplayNumber(String value) throws PrepSystemException {
        StringBuilder sb = new StringBuilder();
        try {
            int index = 0;
            int indexTemp = 0;
            String token = "";
            while (indexTemp != -1) {
                indexTemp = value.indexOf(',', index);
                if (indexTemp != -1) {
                    token = value.substring(index, indexTemp).trim();
                    sb.append(token);
                    index = indexTemp + 1;
                } else {
                    token = value.substring(index, value.length()).trim();
                    sb.append(token);
                }
            }
        } catch (Exception e) {
            String[] errorStrings = new String[1];
            errorStrings[0] = e.getMessage();
            throw new PrepSystemException(ERROR_KEY, errorStrings);
        }
        return sb.toString();
    }

    
    /**
     * Method formatParameterName.
     * @param string
     * @return String
     */
     public String formatParameterName(String paramName) throws PrepFunctionalException{
         String newString= null;
         try{
         int index = paramName.lastIndexOf('*');
         newString = paramName.substring(index + 1, paramName.length());
         }catch(Exception e){
             String[] errorStrings  = new String[1];
             errorStrings[0] = e.getMessage();
             throw new PrepFunctionalException(ERROR_KEY,errorStrings);
             
         }
         return newString;
     }
     
     public boolean isParamRequired(String paramName) throws PrepSystemException {

         boolean isRequired = false;

         try {
             int index = paramName.lastIndexOf('*');
             if (index != -1) {
                 isRequired = true;
             }

         } catch (Exception e) {
             String[] errorStrings = new String[1];
             errorStrings[0] = e.getMessage();
             throw new PrepSystemException(ERROR_KEY, errorStrings);

         }
         return isRequired;
     }

     public String formatInDateParam(String value, int type) throws PrepFunctionalException {
         String formattedValue = value;

         SimpleDateFormat input = null;
         SimpleDateFormat output = null;
         String subval = null;
         Date df = null;

         if (value == null || value.length() <= 0) {

             return value;
         }

         try {
             if (PrepConstants.TYPE_DATETIME.equalsIgnoreCase(type + "")) {
                 // 5

                 subval = value.substring(value.indexOf('(') + 1, value.indexOf(')'));
                 input = new SimpleDateFormat("y,M,d,H,m,s");
                 output = new SimpleDateFormat(SIMPLE_DATE_FORMAT);
                 df = input.parse(subval);
                 formattedValue = output.format(df);

             } else if (PrepConstants.TYPE_TIME.equalsIgnoreCase(type + "")) { // 4
                 subval = value.substring(value.indexOf('(') + 1, value.indexOf(')'));
                 input = new SimpleDateFormat("H,m,s)");
                 new SimpleDateFormat(TIME_FORMAT);
                 df = input.parse(subval);
                 formattedValue = df.toString();

             } else if (PrepConstants.TYPE_DATE.equalsIgnoreCase(type + "")) { // 3

                 subval = value.substring(value.indexOf('(') + 1, value.indexOf(')'));
                 input = new SimpleDateFormat("y,M,d)");
                 new SimpleDateFormat(SIMPLE_DATE_FORMAT);
                 df = input.parse(subval);
                 formattedValue = df.toString();
             }

         } catch (Exception e) {
             String[] errorStrings = new String[1];
             errorStrings[0] = e.getMessage();
             throw new PrepFunctionalException(ERROR_KEY, errorStrings);
         }

         return formattedValue;

     }

     public String formatOutDateParam(String value, String type) throws PrepFunctionalException {
         String formattedValue = value;

         SimpleDateFormat input = null;
         SimpleDateFormat output = null;
         Date df = null;

         String subval = null;

         if (value == null || value.length() <= 0) {

             return value;
         }

         try {
             if (PrepConstants.TYPE_DATETIME.equalsIgnoreCase(type)) {

                 subval = value;

                 input = new SimpleDateFormat(SIMPLE_DATE_FORMAT);
                 output = new SimpleDateFormat("yyyy,M,d,H,m,s");
                 df = input.parse(subval);
                 formattedValue = "DateTime(" + output.format(df) + ")";

             } else if (PrepConstants.TYPE_TIME.equalsIgnoreCase(type + "")) {
                 subval = value.substring(value.indexOf('(') + 1, value.indexOf(')'));

                 input = new SimpleDateFormat(TIME_FORMAT);
                 output = new SimpleDateFormat("H,m,s)");
                 df = input.parse(subval);

                 formattedValue = "Time(" + output.format(df) + ")";

             } else if (PrepConstants.TYPE_DATE.equalsIgnoreCase(type + "")) {

                 subval = value.substring(value.indexOf('(') + 1, value.indexOf(')'));

                 input = new SimpleDateFormat(SIMPLE_DATE_FORMAT);
                 output = new SimpleDateFormat("yyyy,M,d)");
                 df = input.parse(subval);
                 formattedValue = "Date(" + output.format(df) + ")";
             }

         } catch (Exception e) {
             String[] errorStrings = new String[1];
             errorStrings[0] = e.getMessage();
             throw new PrepFunctionalException(ERROR_KEY, errorStrings);

         }

         return formattedValue;

     }
     
     public String getMailSubject(Report report) throws PrepSystemException {
         String subject = null;
         try {
             subject = report.getReportName() + " " + PrepConstants.PREP_REPORT_MAIL_SUBJECT;
         } catch (Exception e) {
             String[] errorStrings = new String[1];
             errorStrings[0] = e.getMessage();
             throw new PrepSystemException("error.report.sdk.sendEmail.exception", errorStrings);
         }
         return subject;
     }

     public String getMailMessage(Report report) throws PrepSystemException {
         String message = null;
         try {
             message = report.getReportName() + " " + "Report Failed on "
                 + PrepExtPropertyHelper.getInstance().getPropertyValue(PrepPropertiesConstants.CE_HOST) + " for "
                 + PrepExtPropertyHelper.getInstance().getPropertyValue(PrepPropertiesConstants.CE_USER);
         } catch (Exception e) {
             String[] errorStrings = new String[1];
             errorStrings[0] = e.getMessage();
             throw new PrepSystemException("error.report.sdk.sendEmail.exception", errorStrings);
         }
         return message;
     }

}
