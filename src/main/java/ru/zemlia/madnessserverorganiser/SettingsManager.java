/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.zemlia.madnessserverorganiser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author zemlia
 */
public class SettingsManager {

    String path;

    JSONObject settings;

    JSONObject settingsDefault = new JSONObject();

    public SettingsManager(int default_position_x, int default_position_y, String version) {

        String OSName = System.getProperty("os.name");
        String username = System.getProperty("user.name");
        if (OSName.equals("Linux")) {
            path = "/home/"
                    + System.getProperty("user.name")
                    + "/.mso";
            if (!(new File(path + "/settings.json").exists())) {
                System.out.println("Not exists");
                new File(path).mkdir();
                createSettingsFile(path, username, OSName, default_position_x, default_position_y, version);
                getJSONObject(path);
            } else {
                System.out.println("Exists");
                getJSONObject(path);
                if (!((String) settings.get("version")).equals(version)) {
                    System.out.println("Wrong version");
                    new File(path).mkdir();
                    createSettingsFile(path, username, OSName, default_position_x, default_position_y, version);
                    getJSONObject(path);
                }
            }
        }
    }

    public String getUserName() {
        return (String) settings.get("OS");
    }

    public String getOS() {
        return (String) settings.get("OS");
    }
    
    public String getCurWorkingProject(){
        return (String) settings.get("cur_working_project");
    }
    
    public int getWidth() {
        int value_to_return = ((Long) settings.get("window_width")).intValue();
        return value_to_return;
    }

    public int getHeight() {
        int value_to_return = ((Long) settings.get("window_height")).intValue();
        return value_to_return;
    }

    public int getPos_x() {
        int value_to_return = ((Long) settings.get("window_position_x")).intValue();

        return value_to_return;
    }

    public int getPos_y() {
        int value_to_return = ((Long) settings.get("window_position_y")).intValue();
        return value_to_return;
    }

    public void setPos_x(int newPos) {
        JSONObject obj;
        try {
            obj = (JSONObject) new JSONParser().parse(new FileReader(path + "/settings.json"));
            obj.replace("window_position_x", newPos);
            try (FileWriter file = new FileWriter(path + "/settings.json")) {
                file.write(obj.toJSONString());
                file.flush();
            } catch (IOException e) {
                System.out.println(e);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SettingsManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | ParseException ex) {
            Logger.getLogger(SettingsManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setPos_y(int newPos) {
        JSONObject obj;
        try {
            obj = (JSONObject) new JSONParser().parse(new FileReader(path + "/settings.json"));
            obj.replace("window_position_x", newPos);
            try (FileWriter file = new FileWriter(path + "/settings.json")) {
                file.write(obj.toJSONString());
                file.flush();
            } catch (IOException e) {
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SettingsManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | ParseException ex) {
            Logger.getLogger(SettingsManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void setCurWorkingProjectPath(String newPath) {
        JSONObject obj;
        try {
            obj = (JSONObject) new JSONParser().parse(new FileReader(path + "/settings.json"));
            obj.replace("cur_working_project", newPath);
            try (FileWriter file = new FileWriter(path + "/settings.json")) {
                file.write(obj.toJSONString());
                file.flush();
            } catch (IOException e) {
                System.out.println(e);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SettingsManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | ParseException ex) {
            Logger.getLogger(SettingsManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void createSettingsFile(String path, String username, String OSName, int default_position_x, int default_position_y, String version) {
        try (FileWriter writer = new FileWriter(path + "/settings.json", false)) {
            settingsDefault.put("cur_working_project", "null");
            settingsDefault.put("username", username);
            settingsDefault.put("version", version);
            settingsDefault.put("OS", OSName);
            settingsDefault.put("window_width", 800);
            settingsDefault.put("window_height", 600);
            settingsDefault.put("window_position_x", default_position_x);
            settingsDefault.put("window_position_y", default_position_y);
            try (FileWriter file = new FileWriter(path + "/settings.json")) {
                file.write(settingsDefault.toJSONString());
                file.flush();
            } catch (IOException e) {
            }

            writer.flush();
        } catch (IOException ex) {

            System.out.println(ex.getMessage());
        }

    }

    private void getJSONObject(String path) {
        Object obj;
        try {
            obj = new JSONParser().parse(new FileReader(path + "/settings.json"));
            settings = (JSONObject) obj;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SettingsManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | ParseException ex) {
            Logger.getLogger(SettingsManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
