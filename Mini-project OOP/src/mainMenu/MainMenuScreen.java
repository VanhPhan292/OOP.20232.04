package mainMenu;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import algorithm.*;
import treeScreen.TreeScreen;
import treeScreen.TreeScreenController;

public class MainMenuScreen extends JFrame{
	
    private TreeScreen treeScreen;

    public MainMenuScreen() {
        super();
        JFXPanel fxPanel = new JFXPanel();
        this.add(fxPanel);
        this.setSize(800, 600);
        this.setTitle("Main Menu");
        this.setVisible(true);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                int res = JOptionPane.showConfirmDialog(null, "Exit ?", "Confirmation: ", JOptionPane.YES_NO_OPTION);
                if(res == JOptionPane.YES_OPTION)
                    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                else if(res == JOptionPane.NO_OPTION)
                    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            }
        });

        MainMenuController ctl = new MainMenuController();
        ctl.setMainMenuScreen(this);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
                    loader.setController(ctl);
                    Parent root = loader.load();
                    fxPanel.setScene(new Scene(root));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void setTreeScreen(TreeScreen treeScreen) {
        this.treeScreen = treeScreen;
    }

    public TreeScreen getTreeScreen() {
        return treeScreen;
    }
}
