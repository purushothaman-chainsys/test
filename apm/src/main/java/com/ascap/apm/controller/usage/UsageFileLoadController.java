package com.ascap.apm.controller.usage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ascap.apm.common.context.PREPContext;
import com.ascap.apm.common.exception.PrepFunctionalException;
import com.ascap.apm.common.exception.PrepSystemException;
import com.ascap.apm.common.helpers.PrepExtPropertyHelper;
import com.ascap.apm.common.utils.constants.PrepPropertiesConstants;
import com.ascap.apm.handler.usage.UsageHandler;
import com.ascap.apm.vob.usage.ApmFileUpload;

/**
 * @author Raju_Ayanampudi To change this generated comment edit the template variable "type comment":
 *         Window>Preferences>Java>Templates. To enable and disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */
@Controller
@RequestMapping("/usage")
public class UsageFileLoadController extends BaseUsageController {

    private static final String APMFILE_UPLOAD = "/usage/apmFileUpload";

    @Autowired
    private UsageHandler usageHandler;

    @RequestMapping(value = "/apmFileUpload", method = {RequestMethod.GET, RequestMethod.POST})
    public String UsageFileLoad(@ModelAttribute ApmFileUpload apmFileUpload, HttpServletRequest request,
        HttpServletResponse response, Model model)
        throws PrepFunctionalException, IllegalAccessException, InvocationTargetException, IOException {
        log.debug("Entering UsageFileLoad method in UsageFileLoadController");
        List<String> systemerrorlist = apmFileUpload.validate(request, messageSource);
        if (systemerrorlist != null && !systemerrorlist.isEmpty()) {
            model.addAttribute("systemerrorlist", systemerrorlist);
            return APMFILE_UPLOAD;
        }
        String forwardKey = "";
        PREPContext inputContext = getPREPContext(request);
        ApmFileUpload radioFileIn = new ApmFileUpload();
        String fileName = apmFileUpload.getFileName();
        apmFileUpload.setFileName(fileName);
        apmFileUpload.setFilePath(
            PrepExtPropertyHelper.getInstance().getPropertyValue(PrepPropertiesConstants.USAGE_FILES_PATH));
        apmFileUpload.setFileOrigPath(
            PrepExtPropertyHelper.getInstance().getPropertyValue(PrepPropertiesConstants.USAGE_FILES_PATH_ORIG));
        log.debug("setFileOrigPath " + apmFileUpload.getFileOrigPath());

        if (fileName == null || fileName.indexOf('.') == -1) {
            model.addAttribute("systemerror", getMessage("error.usage.filename.invalid"));
            forwardKey = APMFILE_UPLOAD;
        }
        try {
            BeanUtils.copyProperties(radioFileIn, apmFileUpload);
            if (!uploadFile(apmFileUpload)) {
                model.addAttribute("systemerror", getMessage("error.usage.upload.file.notFound"));
                forwardKey = APMFILE_UPLOAD;
            }
            inputContext.addInputValueObject(radioFileIn);
            usageHandler.loadFile2Usage(inputContext);
        } catch (PrepFunctionalException dae) {
            log.warn(dae.getMessage());
            model.addAttribute("systemmessage", dae.getMessage());
            forwardKey = "/usage/apmFiles";
        } catch (PrepSystemException pse) {
            log.error(pse.getMessage());
            model.addAttribute("systemmessage", pse.getMessage());
            forwardKey = APMFILE_UPLOAD;
        }
        if (!model.containsAttribute(SYSTEMERROR)) {
            model.addAttribute("message", getMessage("us.message.apm.file.upload.success"));
            forwardKey = "/usage/apmFiles";
        }
        log.debug("forwardKey is " + forwardKey);
        log.debug("Exiting UsageFileLoad method in UsageFileLoadController");
        return forwardKey;
    }

    private boolean uploadFile(ApmFileUpload usageFileForm) throws IOException {
        File dir =
            new File(PrepExtPropertyHelper.getInstance().getPropertyValue(PrepPropertiesConstants.USAGE_FILES_PATH));
        log.debug("dir  " + dir);
        if (!dir.isDirectory()) {
            dir.mkdir();
        }
        InputStreamReader in = new InputStreamReader(((ServletRequest) usageFileForm.getRadioFile()).getInputStream());
        BufferedReader bufferedReader = new BufferedReader(in);
        if (!bufferedReader.ready()) {
            return false;
        }
        log.debug("Got the input stream");
        OutputStream os = new FileOutputStream(
            PrepExtPropertyHelper.getInstance().getPropertyValue(PrepPropertiesConstants.USAGE_FILES_PATH)
                + usageFileForm.getFileName());
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os));
        log.debug("Got the output stream : "
            + (PrepExtPropertyHelper.getInstance().getPropertyValue(PrepPropertiesConstants.USAGE_FILES_PATH)
                + usageFileForm.getFileName()));
        String read = null;
        while ((read = bufferedReader.readLine()) != null) {
            bufferedWriter.write(read);
            bufferedWriter.write("\n");
        }
        bufferedWriter.close();
        os.close();
        if (bufferedReader != null) {
            bufferedReader.close();
        }
        in.close();
        log.debug("Exiting uploadFile method in UsageFileLoadController");
        return true;
    }

}
