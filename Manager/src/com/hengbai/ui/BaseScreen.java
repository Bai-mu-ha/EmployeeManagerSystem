package com.hengbai.ui;

import javax.swing.*;
import java.awt.*;

public abstract class BaseScreen extends JFrame {
    protected static final int WIDTH = 600;  // 加宽窗口
    protected static final int HEIGHT = 400; // 加高窗口
    protected CardLayout cardLayout;
    protected JPanel cardPanel;

    // 定义颜色方案
    protected static final Color PRIMARY_COLOR = new Color(70, 130, 180); // 钢蓝色
    protected static final Color SECONDARY_COLOR = new Color(255, 255, 255); // 白色
    protected static final Color ACCENT_COLOR = new Color(100, 149, 237); // 矢车菊蓝
    protected static final Font TITLE_FONT = new Font("微软雅黑", Font.BOLD, 24);
    protected static final Font LABEL_FONT = new Font("微软雅黑", Font.PLAIN, 14);
    protected static final Font BUTTON_FONT = new Font("微软雅黑", Font.BOLD, 14);

    public BaseScreen(String title) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setResizable(true);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setBackground(SECONDARY_COLOR);
        add(cardPanel, BorderLayout.CENTER);
    }

    public void showPanel(String panelName) {
        cardLayout.show(cardPanel, panelName);
    }

    protected abstract void initComponents();

    // 创建美化按钮的通用方法
    protected JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(BUTTON_FONT);
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(SECONDARY_COLOR);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
}
