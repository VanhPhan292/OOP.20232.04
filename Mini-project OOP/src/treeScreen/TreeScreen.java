package treeScreen;

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
import mainMenu.MainMenuScreen;
import algorithm.GenericTree;
public class TreeScreen extends JFrame{
    private MainMenuScreen mainMenu;
    private TreeScreenController treeController;

    private boolean balance;

    public TreeScreen(GenericTree tree) {
        super();
        JFXPanel fxPanel = new JFXPanel();
        this.add(fxPanel);

        this.setSize(1024, 768);
        this.setTitle("Tree");
        this.setVisible(false);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                int result = JOptionPane.showConfirmDialog(null,
                        "Do you want to Exit ?", "Exit Confirmation : ",
                        JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION)
                    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                else if (result == JOptionPane.NO_OPTION)
                    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            }
        });

        treeController = new TreeScreenController(tree);
        treeController.setTreeScreen(this);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    FXMLLoader loader = new FXMLLoader(getClass()
                            .getResource("Tree.fxml"));
                    loader.setController(treeController);
                    Parent root = loader.load();
                    fxPanel.setScene(new Scene(root));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    public void setMainMenu(MainMenuScreen mainMenu) {
        this.mainMenu = mainMenu;
    }

    public TreeScreenController getTreeController() {
        return treeController;
    }

    public MainMenuScreen getMainMenu() {
        return mainMenu;
    }
}
