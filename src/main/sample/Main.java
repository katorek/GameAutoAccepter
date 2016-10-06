package main.sample;

import games.Tabs;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Main extends Application {

    private Button monitorButton, simulateClick;
    private TextField xCord, yCord;
    private CheckBox mouseFirstLocationCheckBox;
    private Boolean started = false;
    private Robot bot;
    private Label runningTime, mouseFirstLocationLabel, xLabel, yLabel;
    private int cycle;
    private static TabPane tabPane;
    private TrayIcon trayIcon;
    private boolean firstTime;
    private java.awt.Image imageIcon;
    private void stopMonitor() {
        started = false;
        monitorButton.setText("Start Monitor");
    }
    private void startMonitor() {
        started = true;
        monitorButton.setText("Stop Monitor !");
    }


    public static void disableTabs(int i, boolean status){
        switch (i){
            case 0: {
//                DotaTab
            }
        }
    }
    @Override
    public void start(Stage stage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("main.sample.fxml"));
        createTrayIcon(stage);
        tabPane = new Tabs().getTabPane();
        Scene scene = new Scene(tabPane, 300, 250);
        firstTime = true;
        Platform.setImplicitExit(false);
        scene.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
        imageIcon = ImageIO.read(new File("src/img/icon.png"));
        stage.getIcons().add(new Image( new File("src/img/icon2.png").toURI().toString()));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Auto Accepter for games");
        stage.show();
    }

    public void createTrayIcon(final Stage stage) {
        if(SystemTray.isSupported()){
            SystemTray tray = SystemTray.getSystemTray();
            java.awt.Image image = null;
            try{
                image = ImageIO.read(new File("src/img/icon.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }

            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    hide(stage);
                }
            });

            final ActionListener closeListener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            };

            ActionListener showListener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            stage.show();
                        }
                    });
                }
            };

            PopupMenu popup = new PopupMenu();

            MenuItem showItem = new MenuItem("Show");
            showItem.addActionListener(showListener);
            popup.add(showItem);

            MenuItem dotaAccept = new MenuItem("Dota AutoAccept");
            MenuItem csgoAccept = new MenuItem("Csgo AutoAccept");
            MenuItem paladinsAccept = new MenuItem("Paladins AutoAccept");

            MenuItem closeItem = new MenuItem("Close");
            closeItem.addActionListener(closeListener);
            popup.add(closeItem);

            trayIcon = new TrayIcon(image, "AutoAccept",popup);
            trayIcon.addActionListener(showListener);
            try{
                tray.add(trayIcon);
            }catch (AWTException e){
                e.printStackTrace();
            }
        }
    }

    public void showProgramIsMinimizedMsg() {
        if (firstTime) {
            trayIcon.displayMessage("Minimized",
                    "Minimized to tray",
                    TrayIcon.MessageType.INFO);
            firstTime = false;
        }
    }

    private void hide(final Stage stage) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if(SystemTray.isSupported()) {
                    stage.hide();
                    showProgramIsMinimizedMsg();
                }
                else
                    System.exit(0);

            }
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
