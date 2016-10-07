package main.sample;

import games.Tabs;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Main extends Application {

    private static TabPane tabPane;
    private Button monitorButton, simulateClick;
    private TextField xCord, yCord;
    private CheckBox mouseFirstLocationCheckBox;
    private Boolean started = false;
    private Robot bot;
    private Label runningTime, mouseFirstLocationLabel, xLabel, yLabel;
    private int cycle;
    private TrayIcon trayIcon;
    private boolean firstTime;
    private java.awt.Image imageIcon;

    public static void disableTabs(int i, boolean status) {
        switch (i) {
            case 0: {
//                DotaTab
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void stopMonitor() {
        started = false;
        monitorButton.setText("Start Monitor");
    }

    private void startMonitor() {
        started = true;
        monitorButton.setText("Stop Monitor !");
    }

    @Override
    public void start(Stage stage) throws Exception {
        createIcons(stage);
//        tabPane = new Tabs().getTabPane();
        Scene scene = new Scene(new Tabs().getTabPane(), 300, 250);
        firstTime = true;
        Platform.setImplicitExit(false);
        scene.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
//        imageIcon = ImageIO.read(new File("src/img/icon.png"));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Auto Accepter for games");
        stage.show();
    }

    private void createIcons(final Stage stage) {
//        ClassLoader cl = this.getClass().getClassLoader(); //test for learning
//        Icon icon = new ImageIcon(cl.getResource("img/icon.png"));
//        stage.getIcons().add(new javafx.scene.image.Image("file:resources/icon.png"));
        stage.getIcons().add(new javafx.scene.image.Image(Main.class.getResourceAsStream("/img/icon.png")));
        createTrayIcon(stage);
    }

    private void createTrayIcon(final Stage stage) {
        if(SystemTray.isSupported()){
            SystemTray tray = SystemTray.getSystemTray();
            java.awt.Image image = null;
            try{
                image = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("img/icon.png"));// ("file:resources/icon.png");
//                image = ImageIO.read(Main.class.getResourceAsStream("img/icon.png"));
//                image = ImageIO.read(new File("src/img/icon.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }

            stage.setOnCloseRequest(event -> hide(stage));

            final ActionListener closeListener = e -> System.exit(0);

            ActionListener showListener = e -> Platform.runLater(stage::show);

            PopupMenu popup = new PopupMenu();

            MenuItem showItem = new MenuItem("Show");
            showItem.addActionListener(showListener);
            popup.add(showItem);

//            In plans: execute auto start click from tray menu
//            MenuItem dotaAccept = new MenuItem("Dota AutoAccept");
//            MenuItem csgoAccept = new MenuItem("Csgo AutoAccept");
//            MenuItem paladinsAccept = new MenuItem("Paladins AutoAccept");

            MenuItem closeItem = new MenuItem("Close");
            closeItem.addActionListener(closeListener);
            popup.add(closeItem);

            if (image != null) {
//                trayIcon = new TrayIcon(new javafx.scene.image.Image(Main.class.getResourceAsStream("/img/icon.png")),"AutoAccept",popup);
                trayIcon = new TrayIcon(image, "AutoAccept", popup);
            }
            trayIcon.addActionListener(showListener);
            try{
                tray.add(trayIcon);
            }catch (AWTException e){
                e.printStackTrace();
            }
        }
    }

    private void hide(final Stage stage) {
        Platform.runLater(() -> {
            if (SystemTray.isSupported()) {
                stage.hide();
                showProgramIsMinimizedMsg();
            } else
                System.exit(0);

        });
    }

    private void showProgramIsMinimizedMsg() {
        if (firstTime) {
            trayIcon.displayMessage("Minimized",
                    "Minimized to tray",
                    TrayIcon.MessageType.INFO);
            firstTime = false;
        }
    }
}
