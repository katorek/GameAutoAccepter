package games;

import games.paladins.PaladinsTab;
import javafx.scene.control.TabPane;
import games.csgo.CsgoTab;
import games.dota.DotaTab;

/**
 * Created by wojtas on 26.09.2016.
 */
public class Tabs {
    TabPane tabPane;
    public Tabs(){
        tabPane = new TabPane();
        tabPane.getTabs().add(new CsgoTab().getCsgoTab());
        tabPane.getTabs().add(new DotaTab().getDotaTab());
        tabPane.getTabs().add(new PaladinsTab().getPaladinsTab());
    }
    public TabPane getTabPane(){
        return tabPane;
    }
}
