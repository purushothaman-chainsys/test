package com.ascap.apm.servicehelpers.reports;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.Locale;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import com.ascap.apm.common.exception.PrepSystemException;
import com.ascap.apm.common.helpers.LogHelper;
import com.ascap.apm.common.utils.constants.PrepConstants;
import com.ascap.apm.service.reports.CSVColumn;
import com.ascap.apm.service.reports.CSVReformat;
import com.crystaldecisions.sdk.framework.IEnterpriseSession;
import com.crystaldecisions.sdk.framework.ISessionMgr;
import com.crystaldecisions.sdk.occa.infostore.IInfoObject;
import com.crystaldecisions.sdk.occa.infostore.IInfoObjects;
import com.crystaldecisions.sdk.occa.infostore.IInfoStore;
import com.crystaldecisions.sdk.occa.infostore.ISchedulingInfo;
import com.crystaldecisions.sdk.occa.managedreports.IReportAppFactory;
import com.crystaldecisions.sdk.occa.report.application.ReportAppSession;
import com.crystaldecisions.sdk.occa.report.application.ReportClientDocument;
import com.crystaldecisions.sdk.occa.report.exportoptions.CharacterSeparatedValuesExportFormatOptions;
import com.crystaldecisions.sdk.occa.report.exportoptions.ExportOptions;
import com.crystaldecisions.sdk.occa.report.exportoptions.ICharacterSeparatedValuesExportFormatOptions;
import com.crystaldecisions.sdk.occa.report.exportoptions.ReportExportFormat;

/**
 * @author Manoj Puli To change this generated comment edit the template variable "type comment":
 *         Window>Preferences>Java>Templates. To enable and disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */
@Service("reportExportHelper")
public class ReportExportHelperImpl implements ReportExportHelper {

    IInfoObject iio = null;

    IInfoObject obj = null;

    ISchedulingInfo newschedInfo = null;

    IReportAppFactory oIReportAppFactory = null;

    ISessionMgr sm = null;

    IInfoStore oIInfoStore = null;

    IInfoObjects oInfoObjects = null;

    byte[] exportData = null;

    RandomAccessFile fileHandle = null;

    InputStream byteIS = null;

    protected LogHelper log = new LogHelper(this.getClass().getName());

    public RandomAccessFile exportReport(IEnterpriseSession eSession, String instanceId, String reportName,
        String xmlPath, IInfoStore istore, String exportFormat) throws PrepSystemException {
        log.debug("Entering ReportExportHelperImpl - exportReport method");
        try {
            IEnterpriseSession enterpriseSession = eSession;
            log.debug("Sesson::" + eSession);
            log.debug("instance id::" + instanceId);
            log.debug("xml path::" + xmlPath);
            ReportAppSession ra = new ReportAppSession();
            Object obj1 = ra.createService("com.crystaldecisions.sdk.occa.report.application.ReportClientDocument");
            log.debug("**2**ServiceObject:" + obj1.toString());
            oInfoObjects = istore.query("Select * From CI_INFOOBJECTS Where SI_INSTANCE=1 And SI_ID =" + instanceId);
            IInfoObject oInfoObject = (IInfoObject) oInfoObjects.get(0);
            String reportKind = oInfoObject.getKind();
            if (PrepConstants.REPORT_KIND_WEBI.equalsIgnoreCase(reportKind)) {
            } else {
                IReportAppFactory rptAppFactory = (IReportAppFactory) enterpriseSession.getService("RASReportFactory");
                ReportClientDocument clientDoc =
                    rptAppFactory.openDocument(((IInfoObject) oInfoObjects.get(0)), 0, Locale.ENGLISH);
                log.debug("**3**ReportclientDocument::" + clientDoc.toString());
                if (PrepConstants.REPORT_EXPORT_FORMAT_CSV.equalsIgnoreCase(exportFormat)) {
                    CharacterSeparatedValuesExportFormatOptions oCSVExportOptions =
                        new CharacterSeparatedValuesExportFormatOptions();
                    oCSVExportOptions.setExportMode(ICharacterSeparatedValuesExportFormatOptions.ExportMode.legacyMode);
                    ExportOptions oExportOptions = new ExportOptions();
                    ReportExportFormat oExportFormat = ReportExportFormat.characterSeparatedValues;
                    oExportOptions.setExportFormatType(oExportFormat);
                    oExportOptions.setFormatOptions(oCSVExportOptions);
                    byteIS = clientDoc.getPrintOutputController().export(oExportOptions);
                    if (reportName != null) {
                        StringBuilder filebuff = new StringBuilder("/reports/");
                        filebuff.append(reportName).append(".xml");
                        InputStream csvFormatData = this.getClass().getResourceAsStream(filebuff.toString());
                        if (log.isDebugEnabled())
                            log.debug("xml file name : " + filebuff.toString());
                        if (csvFormatData != null) {
                            BufferedReader input = new BufferedReader(new InputStreamReader(byteIS));

                            if (log.isDebugEnabled())
                                log.debug("XML Config File Found : " + csvFormatData.toString());

                            CSVColumn[] csvColumns = CSVReformat.xmlToCSVColumnArray(csvFormatData);
                            fileHandle = CSVReformat.reformat(input, csvColumns);
                            clientDoc.close();
                        } else {
                            log.debug("XML Config File for the report is Not Found");
                            byte csv[] = new byte[byteIS.available()];
                            byteIS.read(csv, 0, byteIS.available());
                            clientDoc.close();
                            exportData = csv;
                        }
                    }
                } else if (PrepConstants.REPORT_EXPORT_FORMAT_PDF.equalsIgnoreCase(exportFormat)) {
                    String tempFileName = RandomStringUtils.randomNumeric(8);
                    File temp = File.createTempFile(tempFileName, ".tmp");
                    String path = temp.getAbsolutePath();
                    log.debug("Temp file created: " + path);
                    fileHandle = new RandomAccessFile(temp, "rw");
                    byteIS = clientDoc.getPrintOutputController().export(ReportExportFormat.PDF);
                    byte[] b = new byte[4096];
                    for (int n; (n = byteIS.read(b)) > -1;) {
                        log.debug("index - " + n);
                        fileHandle.write(b);
                    }
                    fileHandle.seek(0);
                    clientDoc.close();
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            String errorStrings[] = new String[1];
            errorStrings[0] = e.getMessage();
            throw new PrepSystemException("error.report.common.exportReport.exception", errorStrings);
        }
        return fileHandle;
    }
}
