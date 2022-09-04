package view;

import network.name.UserName;

import javax.swing.*;

public class HelpPanel extends JPanel {
    private final String text = "\nThis is help panel\n" +
            "\nYou can use the following commands to interact with chat:" +
            "\n/users - shows you the users connected" +
            "\n/exit - live the chat" +
            "\nadd_robot [name] [type] - create robot:" +
            "\n\t[name] - robot's name" +
            "\n\t[type] - collector / sapper - robot's type" +
            "\nchange_mode [name] [type] - change mode for robot" +
            "\n\t[name] - robot's name" +
            "\n\t[type] - manual / auto / scan" +
            "\nscan [name] - scan nearest cells to robot" +
            "\nmove [name] [direction]- move robot 1 cell" +
            "\n\t[direction] - u / r / d / l - stands for first letters of UP, RIGHT, DOWN and LEFT" +
            "\ngrab [name] - grab item from cell, robot's standing in";
    private JTextArea textArea;
    private JTextField userNameArea;

    public HelpPanel() {
        textArea = new JTextArea(15, 25);
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
