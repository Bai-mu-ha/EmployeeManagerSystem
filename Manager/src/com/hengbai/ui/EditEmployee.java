package com.hengbai.ui;

import com.hengbai.bean.Employee;
import com.hengbai.utils.InputValidator;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditEmployee extends BaseScreen {
    private final EmployeeManagementScreen employeeManagementScreen;
    private final Employee employee;

    private JTextField nameField, ageField, jobField, phoneField, emailField, entryTimeField, salaryField;
    private JComboBox<String> sexComboBox;

    public EditEmployee(EmployeeManagementScreen employeeManagementScreen, Employee employee) {
        super("编辑员工");
        this.employeeManagementScreen = employeeManagementScreen;
        this.employee = employee;

        // 初始化界面
        initComponents();

        // 设置窗口大小并居中显示
        setSize(650, 500);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    protected void initComponents() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        formPanel.setBackground(SECONDARY_COLOR);

        JLabel titleLabel = new JLabel("编辑员工信息", SwingConstants.CENTER);
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(PRIMARY_COLOR);
        add(titleLabel, BorderLayout.NORTH);

        // 统一输入框尺寸
        Dimension inputSize = new Dimension(250, 30);

        // 姓名
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(5, 0, 5, 10);
        formPanel.add(createStyledLabel("姓名："), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        nameField = new JTextField(employee.getName());
        nameField.setPreferredSize(inputSize);
        ((AbstractDocument) nameField.getDocument()).setDocumentFilter(new LengthLimitFilter(20));
        formPanel.add(nameField, gbc);

        // 年龄
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(5, 0, 5, 10);
        formPanel.add(createStyledLabel("年龄："), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        ageField = new JTextField(String.valueOf(employee.getAge()));
        ageField.setPreferredSize(inputSize);
        ((AbstractDocument) ageField.getDocument()).setDocumentFilter(new LengthLimitFilter(3));
        formPanel.add(ageField, gbc);

        // 性别
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(5, 0, 5, 10);
        formPanel.add(createStyledLabel("性别："), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        sexComboBox = new JComboBox<>(new String[]{"男", "女"});
        sexComboBox.setSelectedItem(employee.getSex());
        sexComboBox.setPreferredSize(inputSize);
        formPanel.add(sexComboBox, gbc);

        // 职位
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(5, 0, 5, 10);
        formPanel.add(createStyledLabel("职位："), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        jobField = new JTextField(employee.getJob());
        jobField.setPreferredSize(inputSize);
        ((AbstractDocument) jobField.getDocument()).setDocumentFilter(new LengthLimitFilter(20));
        formPanel.add(jobField, gbc);

        // 电话
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(5, 0, 5, 10);
        formPanel.add(createStyledLabel("电话："), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        phoneField = new JTextField(employee.getPhone());
        phoneField.setPreferredSize(inputSize);
        ((AbstractDocument) phoneField.getDocument()).setDocumentFilter(new LengthLimitFilter(11));
        formPanel.add(phoneField, gbc);

        // 邮箱
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(5, 0, 5, 10);
        formPanel.add(createStyledLabel("邮箱："), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        emailField = new JTextField(employee.getEmail());
        emailField.setPreferredSize(inputSize);
        formPanel.add(emailField, gbc);

        // 入职时间
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(5, 0, 5, 10);
        formPanel.add(createStyledLabel("入职时间："), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        entryTimeField = new JTextField(employee.getEntryTime());
        entryTimeField.setPreferredSize(inputSize);
        ((AbstractDocument) entryTimeField.getDocument()).setDocumentFilter(new LengthLimitFilter(10));
        formPanel.add(entryTimeField, gbc);

        // 薪水
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(5, 0, 5, 10);
        formPanel.add(createStyledLabel("薪水："), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        salaryField = new JTextField(String.valueOf(employee.getSalary()));
        salaryField.setPreferredSize(inputSize);
        formPanel.add(salaryField, gbc);

        // 按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = createStyledButton("保存");
        JButton cancelButton = createStyledButton("取消");

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateInputs()) {
                    try {
                        String name = nameField.getText().trim();
                        int age = Integer.parseInt(ageField.getText().trim());
                        String sex = (String) sexComboBox.getSelectedItem();
                        String job = jobField.getText().trim();
                        String phone = phoneField.getText().trim();
                        String email = emailField.getText().trim();
                        String entryTime = entryTimeField.getText().trim();
                        double salary = Double.parseDouble(salaryField.getText().trim());

                        // 更新员工信息
                        employee.setName(name);
                        employee.setAge(age);
                        employee.setSex(sex);
                        employee.setJob(job);
                        employee.setPhone(phone);
                        employee.setEmail(email);
                        employee.setEntryTime(entryTime);
                        employee.setSalary(salary);

                        // 刷新表格
                        employeeManagementScreen.refreshTable();
                        dispose(); // 关闭窗口
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(EditEmployee.this, "请输入正确的数字格式！", "错误", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(LABEL_FONT);
        label.setForeground(PRIMARY_COLOR);
        return label;
    }

    // 数据校验方法
    private boolean validateInputs() {
        StringBuilder errorMessage = new StringBuilder();

        if (!InputValidator.isValidName(nameField.getText())) {
            errorMessage.append("姓名有误；");
        }

        if (!InputValidator.isValidAge(ageField.getText())) {
            errorMessage.append("年龄有误；");
        }

        if (!InputValidator.isValidPhone(phoneField.getText())) {
            errorMessage.append("电话号码有误；");
        }

        if (!InputValidator.isValidEmail(emailField.getText())) {
            errorMessage.append("邮箱有误；");
        }

        if (!InputValidator.isValidSalary(salaryField.getText())) {
            errorMessage.append("薪水有误；");
        }

        if (!InputValidator.isValidEntryTime(entryTimeField.getText())) {
            errorMessage.append("入职时间格式应为 yyyy-MM-dd；");
        }

        if (!errorMessage.isEmpty()) {
            JOptionPane.showMessageDialog(this, "编辑失败：" + errorMessage, "数据校验失败", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        return true;
    }

    // 字符长度限制器
    static class LengthLimitFilter extends DocumentFilter {
        private final int limit;

        public LengthLimitFilter(int limit) {
            this.limit = limit;
        }

        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            if ((fb.getDocument().getLength() + string.length()) <= limit)
                super.insertString(fb, offset, string, attr);
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if ((fb.getDocument().getLength() + text.length() - length) <= limit)
                super.replace(fb, offset, length, text, attrs);
        }
    }
}
