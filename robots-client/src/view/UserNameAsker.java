package view;

import network.exception.InvalidUserNameException;
import network.name.UserName;

import javax.swing.*;

public class UserNameAsker {
    public UserName ask(String infoMessage) {
        JOptionPane jOptionPane = new JOptionPane();
        JFrame jFrame = new JFrame();
        JDialog jDialog = new JDialog(jFrame);
        jDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        jDialog.setContentPane(jOptionPane);
        UserName userName = null;
        while (true) {
            try {
                userName = new UserName(JOptionPane.showInputDialog(infoMessage));
                break;
            }
            catch (InvalidUserNameException ignore) {}
        }
        return userName;
    }
}
