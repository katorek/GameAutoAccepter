package games.dota;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

/**
 * Created by wojtas on 26.09.2016.
 */
public class DotaTab {
    private Tab dotaTab;
    private Robot robot;
    private boolean started = false;
    private static Button monitor;

    private void monitor() {
        if (robot == null) {
            try {
                robot = new Robot();
            } catch (AWTException e) {
                e.printStackTrace();
            }
        }

        while (started) {
            int xOld = MouseInfo.getPointerInfo().getLocation().x,
                    yOld = MouseInfo.getPointerInfo().getLocation().y;
            robot.mouseMove(1280, 1024);
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            robot.mouseMove(xOld, yOld);
            robot.delay(5000);
        }
    }

    public DotaTab() {
        dotaTab = new Tab("Dota");
        dotaTab.setClosable(false);

        monitor = new Button("AutoAccept");
        monitor.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!started) {
                    started = true;
                    monitor.setText("Started...");
                    Runnable r = new Runnable() {
                        @Override
                        public void run() {
                            monitor();
                        }
                    };
                    new Thread(r).start();
                } else {
                    started = false;
                    monitor.setText("AutoAccept");
                }

            }
        });

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.TOP_CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10,10,10,10));
        gridPane.add(monitor, 0,0);
//        dotaTab.setContent(gridPane);

        gridPane.setId("dota");
        Node content = gridPane;
        content.getStyleClass().add("dota");
        dotaTab.setContent(content);
    }

    public Tab getDotaTab() {
        return dotaTab;
    }
}
