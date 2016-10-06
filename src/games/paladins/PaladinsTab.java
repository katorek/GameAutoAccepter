package games.paladins;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;

import java.awt.*;
import java.awt.event.InputEvent;

/**
 * Created by wojtas on 26.09.2016.
 */
public class PaladinsTab {
    private Tab paladinsTab;
    private Robot robot;
    private Button monitor;
    private boolean started = false;
    private CheckBox checkBox;
    private void monitor(){
        if(robot == null){
            try {
                robot = new Robot();
            } catch (AWTException e) {
                e.printStackTrace();
            }
        }
        while(started) {
            int xOld = MouseInfo.getPointerInfo().getLocation().x,
                    yOld = MouseInfo.getPointerInfo().getLocation().y,
                    xNew = 640,
                    yNew = 580;
            robot.mouseMove(xNew, yNew);
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
            if(checkBox.isSelected()) robot.mouseMove(xOld, yOld);
            robot.delay(4000);
        }
    }

    public PaladinsTab(){
        paladinsTab = new Tab("Paladins");
        paladinsTab.setClosable(false);
        final Label checkLabel = new Label("Return mouse?");
        checkBox = new CheckBox();

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
        gridPane.setAlignment(Pos.BOTTOM_CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new javafx.geometry.Insets(10,10,10,10));
        gridPane.add(monitor, 0,0);
        gridPane.add(checkLabel,1,0);
        gridPane.add(checkBox,2,0);
        gridPane.setId("paladins");
        Node content = gridPane;
        content.getStyleClass().add("paladins");
        paladinsTab.setContent(content);
    }

    public Tab getPaladinsTab() {
        return paladinsTab;
    }
}
