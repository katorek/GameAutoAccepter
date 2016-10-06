package games.csgo;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import main.sample.Main;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.text.ParsePosition;

/**
 * Created by wojtas on 26.09.2016.
 */
public class CsgoTab {
    private Button monitorButton, simulateClick;
    private TextField xCord, yCord;
    private CheckBox mouseFirstLocationCheckBox;
    private Boolean started = false;
    private Robot bot;
    private Label runningTime, mouseFirstLocationLabel, xLabel, yLabel;
    private int cycle;
    private Tab csgoTab;

    private void simulateClick() {
        if (bot == null) {
            try {
                bot = new Robot();
            } catch (AWTException e) {
                e.printStackTrace();
            }
        }
        cycle = 0;
        while (started) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    runningTime.setText("CYKLES - " + cycle++);
                }
            });
            if(xCord.getText().length()==0)xCord.setText("180");
            if(yCord.getText().length()==0)yCord.setText("260");
            int     xNewString = Integer.parseInt(xCord.getText()),
                    yNewString = Integer.parseInt(yCord.getText()),
                    xNew = (xNewString > 1280) ? 1280 : xNewString,
                    yNew = (yNewString > 1024) ? 1024 : yNewString,
                    xOld = MouseInfo.getPointerInfo().getLocation().x,
                    yOld = MouseInfo.getPointerInfo().getLocation().y;
            if(started){
                bot.mouseMove(xNew, yNew);
                bot.mousePress(InputEvent.BUTTON1_MASK);
                bot.mouseRelease(InputEvent.BUTTON1_MASK);
            }
            if (mouseFirstLocationCheckBox.isSelected()) {
                bot.mouseMove(xOld, yOld);
                bot.keyPress(KeyEvent.VK_ALT);
                bot.keyPress(KeyEvent.VK_TAB);
//                bot.delay(100);
                bot.keyRelease(KeyEvent.VK_TAB);
                bot.keyRelease(KeyEvent.VK_ALT);
            }
            bot.delay(5000);
        }


    }

    public CsgoTab(){
        csgoTab = new Tab("CSGO");
        GridPane grid = new GridPane();

//        csgoTab.setId("csgo");
//        csgoTab.getStyleableParent().getS
        csgoTab.setClosable(false);

        simulateClick = new Button();
        simulateClick.setText("AutoAccept");
        simulateClick.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!started) {
//                    grid.setDisable(true);
                    Main.disableTabs(0, true);
                    started = true;
                    simulateClick.setText("Started...");
                    Runnable r = new Runnable() {
                        @Override
                        public void run() {
                            simulateClick();
                        }
                    };
                    new Thread(r).start();
                } else {
                    Main.disableTabs(0, false);
                    started = false;
                    simulateClick.setText("AutoAccept");
                    runningTime.setText("STOPPED");
                }

            }
        });

        runningTime = new Label();
        runningTime.setText("STOPPED");

        mouseFirstLocationCheckBox = new CheckBox();
        mouseFirstLocationLabel = new Label("Mouse to original point");

        DecimalFormat format = new DecimalFormat("#.0");

        xCord = new TextField();
        xCord.setTextFormatter(new TextFormatter<>(c -> {
            if (c.getControlNewText().isEmpty()) {
                return c;
            }
            ParsePosition parsePosition = new ParsePosition(0);
            Object object = format.parse(c.getControlNewText(), parsePosition);

            if (object == null || parsePosition.getIndex() < c.getControlNewText().length()) {
                return null;
            } else {
                return c;
            }
        }));
        yCord = new TextField();
        yCord.setTextFormatter(new TextFormatter<>(c -> {
            if (c.getControlNewText().isEmpty()) {
                return c;
            }
            ParsePosition parsePosition = new ParsePosition(0);
            Object object = format.parse(c.getControlNewText(), parsePosition);

            if (object == null || parsePosition.getIndex() < c.getControlNewText().length()) {
                return null;
            } else {
                return c;
            }
        }));

        xLabel = new Label();
        yLabel = new Label();

        grid.setAlignment(Pos.BOTTOM_CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.add(simulateClick, 0, 0);
        grid.add(runningTime, 1, 0);
        grid.add(xCord, 0, 1);
        grid.add(yCord, 1, 1);
        grid.add(mouseFirstLocationLabel, 0, 2);
        grid.add(mouseFirstLocationCheckBox, 1, 2);
        grid.setId("csgo");
        Node content = grid;
        content.getStyleClass().add("csgo");
        csgoTab.setContent(content);
    }

    public Tab getCsgoTab(){return csgoTab;}
}
