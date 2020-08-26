package com.ascap.apm.service.reports;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.HttpStatus;

import com.ascap.apm.common.exception.PrepSystemException;
import com.ascap.apm.common.helpers.PrepExtPropertyHelper;
import com.ascap.apm.common.utils.constants.PrepPropertiesConstants;
import com.ascap.apm.service.BaseService;
import com.ascap.apm.servicehelpers.reports.JSONHelper;
import com.ascap.apm.servicehelpers.reports.XMLHelper;
import com.ascap.apm.vob.reports.Parameter;
import com.ascap.apm.vob.reports.rest.Logon;

public class ReportRESTService extends BaseService {

    /**
     * 
     */
    private static final long serialVersionUID = 3051113954253715062L;

    private static JSONHelper jsonHelper = new JSONHelper();

    private static XMLHelper xmlHelper = new XMLHelper();

    private String hostname = PrepExtPropertyHelper.getInstance().getPropertyValue(PrepPropertiesConstants.CE_HOST);

    private String port = "6405";

    private String username = PrepExtPropertyHelper.getInstance().getPropertyValue(PrepPropertiesConstants.CE_USER);

    private String password = PrepExtPropertyHelper.getInstance().getPropertyValue(PrepPropertiesConstants.CE_PASSWORD);

    private String authType =
        PrepExtPropertyHelper.getInstance().getPropertyValue(PrepPropertiesConstants.CE_AUTH_TYPE);

    private static String CONTENT_TYPE_JSON = "application/json:";

    private static String CONTENT_TYPE_XML = "application/xml";

    private static String REPORT_EXPORT_TYPE_XML = "XML";

    private static String REPORT_EXPORT_TYPE_HTML = "HTML";

    private static String REPORT_EXPORT_TYPE_ZIP = "ZIP";

    private static String REPORT_EXPORT_TYPE_PDF = "PDF";

    private static String REPORT_EXPORT_TYPE_EXCEL_2003 = "VND.MS-EXCEL";

    private static String REPORT_EXPORT_TYPE_EXCEL_2007 = "VND.OPENXMLFORMATS-OFFICEDOCUMENT.SPREAD-SHEETML.SHEET";

    private static String REPORT_EXPORT_TYPE_CSV = "CSV";

    private static String REPORT_EXPORT_CONTENT_TYPE_XML = "text/xml";

    private static String REPORT_EXPORT_CONTENT_TYPE_HTML = "text/html";

    private static String REPORT_EXPORT_CONTENT_TYPE_ZIP = "application/zip";

    private static String REPORT_EXPORT_CONTENT_TYPE_PDF = "application/pdf";

    private static String REPORT_EXPORT_CONTENT_TYPE_EXCEL_2003 = "application/vnd.ms-excel";

    private static String REPORT_EXPORT_CONTENT_TYPE_EXCEL_2007 =
        "application/vnd.openxmlformats-officedocument.spread-sheetml.sheet";

    private static String REPORT_EXPORT_CONTENT_TYPE_CSV = "text/csv";

    private static String HTTP = "http://";

    private static String URL_VAL = "/biprws/raylight/v1/documents/";

    private static String ERROR_KEY = "error.report.common.http.returncode";

    private static String CONTENT_TYPE = "Content-type";

    private static String URL = "URL : ";

    private static String TOKEN = "Token: ";

    private static String ACCEPT = "Accept";

    private static String LOGONTOKEN = "X-SAP-LogonToken";

    private static HashMap<String, String> reportExportLookup = new HashMap<>();

    static {
        reportExportLookup.put(REPORT_EXPORT_TYPE_XML, REPORT_EXPORT_CONTENT_TYPE_XML);
        reportExportLookup.put(REPORT_EXPORT_TYPE_HTML, REPORT_EXPORT_CONTENT_TYPE_HTML);
        reportExportLookup.put(REPORT_EXPORT_TYPE_ZIP, REPORT_EXPORT_CONTENT_TYPE_ZIP);
        reportExportLookup.put(REPORT_EXPORT_TYPE_PDF, REPORT_EXPORT_CONTENT_TYPE_PDF);
        reportExportLookup.put(REPORT_EXPORT_TYPE_EXCEL_2003, REPORT_EXPORT_CONTENT_TYPE_EXCEL_2003);
        reportExportLookup.put(REPORT_EXPORT_TYPE_EXCEL_2007, REPORT_EXPORT_CONTENT_TYPE_EXCEL_2007);
        reportExportLookup.put(REPORT_EXPORT_TYPE_CSV, REPORT_EXPORT_CONTENT_TYPE_CSV);
    }

    public Logon logon(Logon logon, String type) throws PrepSystemException {

        log.debug("Entering logon method in ReportRESTService");

        String url = HTTP + hostname + ":" + port + "/biprws/logon/long";

        if (CONTENT_TYPE_JSON.equals(type)) {
            String logonTokenJSON = post(url, jsonHelper.buildLogonJSON(logon), null, type);
            logon.setLogonToken(jsonHelper.parseLogonTokenJSON(logonTokenJSON));
        } else if (CONTENT_TYPE_XML.equals(type)) {
            String logonTokenJSON = post(url, xmlHelper.buildLogonXML(logon), null, type);
            logon.setLogonToken(xmlHelper.parseLogonTokenXML(logonTokenJSON));
        }

        log.debug("Exiting logon method in ReportRESTService");
        return logon;
    }

    public Logon logoff(Logon logon, String type) {

        String url = HTTP + hostname + ":" + port + "/biprws/logoff";

        try {
            post(url, null, logon.getLogonToken(), type);
        } catch (PrepSystemException e) {
            log.error(e.getMessage());
        }

        return logon;
    }

    public List<Parameter> getReportParameters(String reportId) throws PrepSystemException {

        log.debug("Entering getReportParameters method in ReportRESTService");

        List<Parameter> response = null;

        Logon logon = new Logon();
        logon.setUserName(username);
        logon.setPassword(password);
        logon.setAuth(authType);
        logon = logon(logon, CONTENT_TYPE_XML);

        try {
            response = getParameters(reportId, logon.getLogonToken(), CONTENT_TYPE_XML);
        } catch (PrepSystemException e) {
            log.error(e.getMessage());
            throw e;
        } finally {
            logoff(logon, CONTENT_TYPE_XML);
        }

        log.debug("Exiting getReportParameters method in ReportRESTService");
        return response;
    }

    public List<Parameter> getParameters(String docId, String logonToken, String type) throws PrepSystemException {

        log.debug("Entering getParameters method in ReportRESTService");
        ArrayList<Parameter> response = null;

        String url = HTTP + hostname + ":" + port + URL_VAL + docId + "/parameters";

        if (CONTENT_TYPE_JSON.equals(type)) {
            get(url, logonToken, CONTENT_TYPE_JSON);
        } else if (CONTENT_TYPE_XML.equals(type)) {
            String reportParametersXML = get(url, logonToken, CONTENT_TYPE_XML);
            response = xmlHelper.parseParametersXML(reportParametersXML);
        }
        log.debug("Exiting getParameters method in ReportRESTService");
        return response;
    }

    public String getParametersXML(String docId, String logonToken, String type) throws PrepSystemException {
        log.debug("Entering getParametersXML method in ReportRESTService");

        String response = null;

        String url = HTTP + hostname + ":" + port + URL_VAL + docId + "/parameters";

        if (CONTENT_TYPE_JSON.equals(type)) {
            response = get(url, logonToken, CONTENT_TYPE_JSON);
        } else if (CONTENT_TYPE_XML.equals(type)) {
            response = get(url, logonToken, CONTENT_TYPE_XML);
        }

        log.debug("Exiting getParametersXML method in ReportRESTService");
        return response;
    }

    public void scheduleReport(String reportId, String reportName, List<Parameter> parameters)
        throws PrepSystemException {
        log.debug("Entering scheduleReport method in ReportRESTService");

        Logon logon = new Logon();
        logon.setUserName(username);
        logon.setPassword(password);
        logon.setAuth(authType);
        logon = logon(logon, CONTENT_TYPE_XML);

        try {
            String parametersXML = getParametersXML(reportId, logon.getLogonToken(), CONTENT_TYPE_XML);
            String scheduleXML = xmlHelper.buildScheduleXML(parametersXML, parameters);
            String scheduleHeader = "<schedule> " + "<name>" + reportName + "</name>" + "<format type=\"webi\"/>"
                + "<now retriesAllowed=\"2\" retryIntervalInSeconds=\"60\"/> ";
            String scheduleTrailer = "</schedule> ";
            scheduleXML = scheduleHeader + scheduleXML + scheduleTrailer;
            scheduleReport(reportId, scheduleXML, logon.getLogonToken(), CONTENT_TYPE_XML);
        } catch (PrepSystemException e) {
            log.error(e.getMessage());
            throw e;
        } finally {
            logoff(logon, CONTENT_TYPE_XML);
        }

        log.debug("Exiting scheduleReport method in ReportRESTService");
    }

    private void scheduleReport(String reportId, String parameterXML, String logonToken, String type)
        throws PrepSystemException {
        log.debug("Entering scheduleReport method in ReportRESTService");
        String url = HTTP + hostname + ":" + port + URL_VAL + reportId + "/schedules";

        try {
            post(url, parameterXML, logonToken, type);
        } catch (PrepSystemException e) {
            log.error(e.getMessage());
            throw e;
        }

        log.debug("Exiting scheduleReport method in ReportRESTService");
    }

    public RandomAccessFile exportReport(String docId, String format) throws PrepSystemException {
        log.debug("Entering exportReport method in ReportRESTService");

        Logon logon = new Logon();
        logon.setUserName(username);
        logon.setPassword(password);
        logon.setAuth(authType);
        logon = logon(logon, CONTENT_TYPE_XML);
        RandomAccessFile response = null;

        try {
            String url = HTTP + hostname + ":" + port + URL_VAL + docId + "/reports/1";
            response = getByteArray(url, logon.getLogonToken(), getContentType(format));
        } catch (PrepSystemException e) {
            log.error(e.getMessage());
            throw e;
        } finally {
            logoff(logon, CONTENT_TYPE_XML);
        }
        log.debug("Exiting exportReport method in ReportRESTService");
        return response;
    }

    private String getContentType(String type) {
        log.debug("Entering getContentType method in ReportRESTService");

        String response = null;

        if (type != null) {
            response = reportExportLookup.get(type);
        }

        log.debug("Exiting getContentType method in ReportRESTService");
        return response;
    }

    public String get(String url, String logonToken, String type) throws PrepSystemException {
        log.debug("Entering get method in ReportRESTService");

        // Prepare HTTP GET
        HttpGet getMethod = new HttpGet(url);
        String response = "";

        getMethod.addHeader(CONTENT_TYPE, type);
        getMethod.addHeader(ACCEPT, type);
        if (logonToken != null) {
            getMethod.addHeader(LOGONTOKEN, logonToken);
        }

        // Get HTTP client
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        HttpResponse httpResponse = null;
        // Execute request
        try {
            httpResponse = httpclient.execute(getMethod);
            int result = httpResponse.getStatusLine().getStatusCode();

            // Display status code
            log.debug(URL + url);
            log.debug(TOKEN + logonToken);
            log.debug("GET Response status code: " + result);

            if (result != HttpStatus.OK.value()) {
                String[] errors = {"" + result};
                throw new PrepSystemException(ERROR_KEY, errors);
            }

            // Display response
            InputStream input = httpResponse.getEntity().getContent();

            StringBuilder out = new StringBuilder();
            byte[] b = new byte[4096];
            for (int n; (n = input.read(b)) != -1;) {
                out.append(new String(b, 0, n));
            }
            response = out.toString();

        } catch (IOException e) {
            log.error(e.getMessage());
            String[] errors = {e.getMessage()};
            throw new PrepSystemException(ERROR_KEY, errors);
        } finally {
            // Release current connection to the connection pool
            // once you are done
            getMethod.releaseConnection();
        }

        log.debug("Exiting get method in ReportRESTService");
        return response;
    }

    public RandomAccessFile getByteArray(String url, String logonToken, String type) throws PrepSystemException {
        log.debug("Entering getByteArray method in ReportRESTService");

        // Prepare HTTP GET
        HttpGet getMethod = new HttpGet(url);
        RandomAccessFile response = null;

        getMethod.addHeader(CONTENT_TYPE, type);
        getMethod.addHeader(ACCEPT, type);
        if (logonToken != null) {
            getMethod.addHeader(LOGONTOKEN, logonToken);
        }

        // Get HTTP client
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        HttpResponse httpResponse = null;

        // Execute request
        try {
            httpResponse = httpclient.execute(getMethod);
            int result = httpResponse.getStatusLine().getStatusCode();

            // Display status code
            log.debug(URL + url);
            log.debug(TOKEN + logonToken);
            log.debug("GET Response status code: " + result);

            if (result != HttpStatus.OK.value()) {
                String[] errors = {"" + result};
                throw new PrepSystemException(ERROR_KEY, errors);
            }

            // Display response
            InputStream input = httpResponse.getEntity().getContent();

            String tempFileName = RandomStringUtils.randomNumeric(8);
            File temp = File.createTempFile(tempFileName, ".tmp");
            String path = temp.getAbsolutePath();
            System.err.println("Temp file created: " + path);

            response = new RandomAccessFile(temp, "rw");

            byte[] b = new byte[4096];
            for (int n; (n = input.read(b)) != -1;) {
                log.debug("index - " + n);
                response.write(b);
            }
            response.seek(0);
        } catch (IOException e) {
            log.error(e.getMessage());
            String[] errors = {e.getMessage()};
            throw new PrepSystemException(ERROR_KEY, errors);
        } finally {
            // Release current connection to the connection pool
            // once you are done
            getMethod.releaseConnection();
        }

        log.debug("Exiting getByteArray method in ReportRESTService");

        return response;
    }

    public String post(String url, String body, String logonToken, String type) throws PrepSystemException {
        log.debug("Entering post method in ReportRESTService");

        // Prepare HTTP post
        HttpPost postMethod = new HttpPost(url);
        String response = "";

        log.debug(HttpPost.class.getProtectionDomain().getCodeSource());

        if (body != null) {
            HttpEntity requestEntity = new ByteArrayEntity(body.getBytes());
            postMethod.setEntity(requestEntity);
        }

        postMethod.addHeader(CONTENT_TYPE, type);
        postMethod.addHeader(ACCEPT, type);
        if (logonToken != null) {
            postMethod.addHeader(LOGONTOKEN, logonToken);
        }

        // Get HTTP client
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        HttpResponse httpResponse = null;

        // Execute request
        try {
            httpResponse = httpclient.execute(postMethod);
            int result = httpResponse.getStatusLine().getStatusCode();
            // Display status code
            log.debug(URL + url);
            log.debug(TOKEN + logonToken);
            log.debug("Post Response status code: " + result);

            if (result != HttpStatus.OK.value()) {
                String[] errors = {"" + result};
                throw new PrepSystemException(ERROR_KEY, errors);
            }

            // Display response
            InputStream input = postMethod.getEntity().getContent();

            StringBuilder out = new StringBuilder();
            byte[] b = new byte[4096];
            for (int n; (n = input.read(b)) != -1;) {
                out.append(new String(b, 0, n));
            }
            response = out.toString();

        } catch (IOException e) {
            log.error(e.getMessage());
            String[] errors = {e.getMessage()};
            throw new PrepSystemException(ERROR_KEY, errors);
        } finally {
            // Release current connection to the connection pool
            // once you are done
            postMethod.releaseConnection();
        }

        log.debug("Exiting post method in ReportRESTService");
        return response;
    }
}
