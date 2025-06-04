package com.hengbai.ui;

import com.hengbai.bean.User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class LoginScreen extends BaseScreen {
    private  JTextField userField;
    private JPasswordField passField;
    //生成一个用户表，存放用户名和密码,用户是一个类，用户表长度可变，也就是可以增加用户删除用户
    private static final List<User> USERS = new ArrayList<>();

    // 初始化默认用户
    static {
        USERS.add(new User(1, "admin", "11112222"));
        USERS.add(new User(2, "user", "123456"));
        USERS.add(new User(3, "test", "123456"));
    }

    public LoginScreen() {
        super("人事管理系统");
        initComponents();
    }

    @Override
    protected void initComponents() {
        initLoginPanel();
        initRegisterPanel();
        showPanel("login");
    }



    private void initLoginPanel() {
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(SECONDARY_COLOR);
        loginPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 标题
        JLabel titleLabel = new JLabel("用户登录", SwingConstants.CENTER);
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(PRIMARY_COLOR);
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        loginPanel.add(titleLabel, gbc);

        // 用户名标签
        JLabel userLabel = new JLabel("用户名:");
        userLabel.setFont(LABEL_FONT);
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        loginPanel.add(userLabel, gbc);

        // 用户名输入框
        userField = new JTextField(15);
        userField.setFont(LABEL_FONT);
        userField.setText("admin");
        gbc.gridx = 1;
        loginPanel.add(userField, gbc);

        // 密码标签
        JLabel passLabel = new JLabel("密码:");
        passLabel.setFont(LABEL_FONT);
        gbc.gridx = 0;
        gbc.gridy = 2;
        loginPanel.add(passLabel, gbc);

        // 密码输入框
        passField = new JPasswordField(15);
        passField.setFont(LABEL_FONT);
        passField.setText("11112222");
        gbc.gridx = 1;
        loginPanel.add(passField, gbc);

        // 按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);

        JButton loginBtn = createStyledButton("登录");
        JButton registerBtn = createStyledButton("注册");

        buttonPanel.add(loginBtn);
        buttonPanel.add(registerBtn);

        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 3;
        loginPanel.add(buttonPanel, gbc);

        loginBtn.addActionListener(e -> {
            if (authenticate()) {
                //建立管理界面，要传入用户名
                EmployeeManagementScreen employeeManagementScreen = new EmployeeManagementScreen(
                        userField.getText()
                );
                employeeManagementScreen.setVisible(true);
                dispose();
            }
            else {
                JOptionPane.showMessageDialog(this,
                        "用户名或密码错误",
                        "错误",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        registerBtn.addActionListener(e -> showPanel("register"));

        cardPanel.add(loginPanel, "login");
    }

    private boolean authenticate() {
        String username = userField.getText();
        String password = new String(passField.getPassword());
        for (User user : USERS) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    private void initRegisterPanel() {
        JPanel registerPanel = new JPanel(new GridBagLayout());
        registerPanel.setBackground(SECONDARY_COLOR);
        registerPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 标题
        JLabel titleLabel = new JLabel("用户注册", SwingConstants.CENTER);
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(PRIMARY_COLOR);
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        registerPanel.add(titleLabel, gbc);

        // 用户名标签
        JLabel userLabel = new JLabel("用户名:");
        userLabel.setFont(LABEL_FONT);
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        registerPanel.add(userLabel, gbc);

        // 用户名输入框
        JTextField userField = new JTextField(15);
        userField.setFont(LABEL_FONT);
        gbc.gridx = 1;
        registerPanel.add(userField, gbc);

        // 密码标签
        JLabel passLabel = new JLabel("密码:");
        passLabel.setFont(LABEL_FONT);
        gbc.gridx = 0;
        gbc.gridy = 2;
        registerPanel.add(passLabel, gbc);

        // 密码输入框
        JPasswordField passField = new JPasswordField(15);
        passField.setFont(LABEL_FONT);
        gbc.gridx = 1;
        registerPanel.add(passField, gbc);

        // 确认密码标签
        JLabel confirmLabel = new JLabel("确认密码:");
        confirmLabel.setFont(LABEL_FONT);
        gbc.gridx = 0;
        gbc.gridy = 3;
        registerPanel.add(confirmLabel, gbc);

        // 确认密码输入框
        JPasswordField confirmField = new JPasswordField(15);
        confirmField.setFont(LABEL_FONT);
        gbc.gridx = 1;
        registerPanel.add(confirmField, gbc);

        // 按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);

        JButton registerBtn = createStyledButton("注册");
        JButton backBtn = createStyledButton("返回登录");

        buttonPanel.add(registerBtn);
        buttonPanel.add(backBtn);

        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 4;
        registerPanel.add(buttonPanel, gbc);

        backBtn.addActionListener(e -> showPanel("login"));

        // 注册按钮点击事件
        registerBtn.addActionListener(e -> {
            String username = userField.getText().trim();
            String password = new String(passField.getPassword()).trim();
            String confirmPassword = new String(confirmField.getPassword()).trim();

            // 1. 校验格式
            if (username.isEmpty()) {
                JOptionPane.showMessageDialog(this, "用户名不能为空！", "注册失败", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "密码不能为空！", "注册失败", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (password.length() < 6) {
                JOptionPane.showMessageDialog(this, "密码不能少于6位！", "注册失败", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "两次输入的密码不一致！", "注册失败", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 2. 检查用户名是否存在
            for (User user : USERS) {
                if (user.getUsername().equals(username)) {
                    JOptionPane.showMessageDialog(this, "用户名已存在！", "注册失败", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }

            // 3. 注册成功，添加新用户
            int newId = USERS.isEmpty() ? 1 : USERS.get(USERS.size() - 1).getId() + 1;
            User newUser = new User(newId, username, password);

            // ✅ 将新用户加入列表
            USERS.add(newUser);

            JOptionPane.showMessageDialog(this, "注册成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
            showPanel("login");
        });

        cardPanel.add(registerPanel, "register");
    }
}
