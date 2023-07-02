import jui.AuthFrame;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import db.DB;
import jui.NewUserFrame;
import jui.UsersForm;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Properties props = new Properties();
        try(InputStream in = Files.newInputStream(Paths.get("database.properties"))) {
            props.load(in);
        } catch (Exception ex) {
            System.out.println(ex);
        }

        DB db = new DB(props.getProperty("url"), props.getProperty("username"), props.getProperty("password"));

        JFrame frame = new AuthFrame(db);
        frame.setVisible(true);
    }
}
