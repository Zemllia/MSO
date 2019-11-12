/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.zemlia.madnessserverorganiser;

import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONObject;

/**
 *
 * @author zemlia
 */
public class ProjectManager {
    
    JSONObject projectInfo = new JSONObject();

    public ProjectManager() {

    }

    public void makeNewProject(String path, String projectName, String MSOVersion) {
        try (FileWriter writer = new FileWriter(path + "/settings.json", false)) {
            projectInfo.put("MSOVersion", MSOVersion);
            projectInfo.put("project_name", projectName);
            try (FileWriter file = new FileWriter(path + "/settings.json")) {
                file.write(projectInfo.toJSONString());
                file.flush();
            } catch (IOException e) {
            }

            writer.flush();
        } catch (IOException ex) {

            System.out.println(ex.getMessage());
        }
    }

}
