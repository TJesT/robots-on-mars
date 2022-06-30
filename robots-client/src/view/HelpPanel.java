package view;

import network.name.UserName;

import javax.swing.*;

public class HelpPanel extends JPanel {
    private final String text = "\nThis is help panel\n\nYou can use the following commands to interact with chat:\n/users - shows you the users connected\n/exit - live the chat";
    private JTextArea textArea;
    private JTextField userNameArea;

    public HelpPanel() {
        textArea = new JTextArea(6, 25);
        textArea.setEditable(false);
        textArea.setText(text);
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        add(textArea);

        userNameArea = new JTextField();
        userNameArea.setEditable(false);
        add(userNameArea);


        setBounds(0, 0, 100, 50);
    }

    public void updateUserName(UserName userName) {
        userNameArea.setText("Your username:" + System.lineSeparator() + userName.getName());
    }
}
