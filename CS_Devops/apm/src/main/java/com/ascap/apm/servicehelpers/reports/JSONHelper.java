package com.ascap.apm.servicehelpers.reports;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ascap.apm.vob.reports.rest.Logon;
import com.ascap.apm.vob.reports.rest.Parameter;

public class JSONHelper {

    public String buildLogonJSON(Logon logon) {

        String response = null;
        try {
            JSONObject logonJSON = new JSONObject();
            if (logon != null) {
                logonJSON.put("userName", logon.getUserName());
                logonJSON.put("password", logon.getPassword());
                logonJSON.put("auth", logon.getAuth());
            }

            response = logonJSON.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    public String parseLogonTokenJSON(String incomingJSON) {
        String response = null;

        try {
            JSONObject logonTokenJSON = new JSONObject(incomingJSON);

                response = "\"" + (String) logonTokenJSON.get("logonToken") + "\"";
        } catch (JSONException e) {
            System.out.println("Exception occurred while generating Logontoken Object from Incoming JSON");
            e.printStackTrace();
        }

        return response;
    }

    public HashMap<String, Parameter> parseParametersJSON(String incomingJSON) {

        HashMap<String, Parameter> response = new HashMap<>();
        String parameterCons = "parameter";

        try {
            JSONObject rootJSON = new JSONObject(incomingJSON);
            System.out.println("1");
            JSONObject parametersJSON = (JSONObject) rootJSON.get("parameters");
            if (parametersJSON != null) {
                System.out.println("2");
                if (parametersJSON.get(parameterCons) != null) {
                    System.out.println("3");
                    if (parametersJSON.get(parameterCons) instanceof JSONArray) {
                        System.out.println("4");
                        JSONArray parametersArray = (JSONArray) parametersJSON.get(parameterCons);
                        if (parametersArray != null) {
                            System.out.println("5");
                            for (int i = 0; i < parametersArray.length(); i++) {
                                JSONObject parameterJSON = (JSONObject) parametersArray.get(i);
                                Parameter parameter = buildParameter(parameterJSON);
                                if (parameter != null) {
                                    System.out.println("6");
                                    response.put(parameter.getId(), parameter);
                                    System.out.println(parameter);
                                }
                            }
                        }
                    } else {
                        JSONObject parameterJSON = (JSONObject) parametersJSON.get(parameterCons);
                        Parameter parameter = buildParameter(parameterJSON);
                        if (parameter != null) {
                            System.out.println("11");
                            response.put(parameter.getId(), parameter);
                            System.out.println(parameter);
                        }
                    }
                }
            }
        } catch (JSONException e) {
            System.out.println("Exception occurred while generating Parameters Object from Incoming JSON");
            e.printStackTrace();
        }

        return response;
    }

    public Parameter buildParameter(JSONObject incomingJSON) {
        return null;
    }

}
