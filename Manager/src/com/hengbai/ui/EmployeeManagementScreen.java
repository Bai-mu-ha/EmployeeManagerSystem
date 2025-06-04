package com.hengbai.ui;

import com.hengbai.bean.Employee;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class EmployeeManagementScreen extends BaseScreen {
    private final List<Employee> employees = new ArrayList<>();
    private static final int PAGE_SIZE = 40; // 每页显示数量
    private int currentPage = 1;
    private DefaultTableModel tableModel;
    private JTable employeeTable;

    private JTextField searchField;
    private JButton prevBtn, nextBtn;
    private JLabel pageLabel;
    private List<Employee> filteredEmployees = new ArrayList<>(); // 存储筛选结果用于分页

    public EmployeeManagementScreen(String str) {
        super("员工信息管理: " + str + " 已登录");
        initComponents();
        loadData();
    }

    @Override
    protected void initComponents() {
        // 主面板使用 BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());

        // 工具栏 - 顶部放置搜索和添加按钮，并居中显示
        JPanel topToolbar = createTopToolbar();
        mainPanel.add(topToolbar, BorderLayout.NORTH);

        // 表格区域
        mainPanel.add(createTablePanel(), BorderLayout.CENTER);

        // 底部页码区域
        JPanel bottomPagination = createBottomPagination();
        mainPanel.add(bottomPagination, BorderLayout.SOUTH);

        cardPanel.add(mainPanel, "main");
    }

    /**
     * 创建工具栏（搜索框 + 添加按钮 + 分页控件）
     */
    private JPanel createTopToolbar() {
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10)); // 居中对齐
        toolbar.setBackground(SECONDARY_COLOR);

        // 退出登录按钮（新增）
        JButton logoutBtn = createStyledButton("退出登录");
        logoutBtn.setPreferredSize(new Dimension(100, 30));
        logoutBtn.setFont(new Font("微软雅黑", Font.BOLD, 13));
        logoutBtn.setForeground(Color.RED); // 可选：红色文字更醒目
        logoutBtn.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(this,
                    "确定要退出当前用户吗？",
                    "退出登录",
                    JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                // 关闭当前窗口
                SwingUtilities.getWindowAncestor(toolbar).setVisible(false);
                // 打开登录界面
                new LoginScreen().setVisible(true);
            }
        });

        // 工具栏左侧放退出按钮
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setBackground(SECONDARY_COLOR);
        leftPanel.add(logoutBtn);

        // 搜索输入框
        searchField = new JTextField(20);
        searchField.setFont(LABEL_FONT);
        searchField.setPreferredSize(new Dimension(200, 30));

        // 搜索按钮
        JButton searchBtn = createStyledButton("搜索");
        searchBtn.setPreferredSize(new Dimension(90, 30));
        searchBtn.setFont(new Font("微软雅黑", Font.PLAIN, 13));

        // 添加员工按钮
        JButton addBtn = createStyledButton("添加员工");
        addBtn.setPreferredSize(new Dimension(100, 30));
        addBtn.setFont(new Font("微软雅黑", Font.BOLD, 13));
        addBtn.addActionListener(e -> showAddDialog());

        // 绑定搜索事件
        searchBtn.addActionListener(e -> {
            String keyword = searchField.getText().trim();
            if (!keyword.isEmpty()) {
                currentPage = 1;
                searchAndDisplayResults(keyword);
            } else {
                currentPage = 1;
                displayPage(currentPage);
            }
        });

        // 添加组件
        toolbar.add(searchField);
        toolbar.add(searchBtn);
        toolbar.add(addBtn);

        JPanel fullToolbar = new JPanel(new BorderLayout());
        fullToolbar.setBackground(SECONDARY_COLOR);
        fullToolbar.add(leftPanel, BorderLayout.WEST);
        fullToolbar.add(toolbar, BorderLayout.CENTER);

        return fullToolbar;
    }

    private JPanel createBottomPagination() {
        JPanel paginationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        paginationPanel.setBackground(SECONDARY_COLOR);

        // 分页按钮
        prevBtn = createStyledButton("上一页");
        nextBtn = createStyledButton("下一页");
        prevBtn.setPreferredSize(new Dimension(90, 30));
        nextBtn.setPreferredSize(new Dimension(90, 30));
        prevBtn.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        nextBtn.setFont(new Font("微软雅黑", Font.PLAIN, 13));

        // 页面标签
        pageLabel = new JLabel("第 1 页");
        pageLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        pageLabel.setForeground(PRIMARY_COLOR);

        // 添加到面板
        paginationPanel.add(prevBtn);
        paginationPanel.add(pageLabel);
        paginationPanel.add(nextBtn);

        // 上一页点击
        prevBtn.addActionListener(e -> {
            if (currentPage > 1) {
                currentPage--;
                displayPage(currentPage);
            }
        });

        // 下一页点击
        nextBtn.addActionListener(e -> {
            int maxPage = (int) Math.ceil((double) filteredEmployees.size() / PAGE_SIZE);
            if (currentPage < maxPage) {
                currentPage++;
                displayPage(currentPage);
            }
        });

        return paginationPanel;
    }

    /**
     * 创建表格组件
     */
    private JScrollPane createTablePanel() {
        String[] columns = {"ID", "姓名", "年龄", "性别", "职位", "电话", "邮箱", "入职时间", "薪水"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        employeeTable = new JTable(tableModel);
        employeeTable.setRowHeight(30);
        setupContextMenu();

        return new JScrollPane(employeeTable);
    }

    /**
     * 设置右键菜单（编辑/删除）
     */
    private void setupContextMenu() {
        JPopupMenu menu = new JPopupMenu();

        JMenuItem edit = new JMenuItem("编辑");
        edit.addActionListener(e -> {
            int row = employeeTable.getSelectedRow();
            if (row >= 0) editEmployee(filteredEmployees.get(row));
        });

        JMenuItem delete = new JMenuItem("删除");
        delete.addActionListener(e -> {
            int row = employeeTable.getSelectedRow();
            if (row >= 0) deleteEmployee(filteredEmployees.get(row));
        });

        menu.add(edit);
        menu.add(delete);

        employeeTable.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    int row = employeeTable.rowAtPoint(e.getPoint());
                    if (row >= 0) {
                        employeeTable.setRowSelectionInterval(row, row);
                        menu.show(employeeTable, e.getX(), e.getY());
                    }
                }
            }
        });
    }

    /**
     * 加载初始数据（模拟加载50条）
     */
    private void loadData() {
        new SwingWorker<Void, Employee>() {
            @Override
            protected Void doInBackground() {
                for (int i = 1; i <= 50; i++) {
                    publish(createSampleEmployee(i));
                }
                return null;
            }

            @Override
            protected void process(List<Employee> chunks) {
                chunks.forEach(emp -> {
                    employees.add(emp);
                    tableModel.addRow(new Object[]{
                            emp.getId(),
                            emp.getName(),
                            emp.getAge(),
                            emp.getSex(),
                            emp.getJob(),
                            emp.getPhone(),
                            emp.getEmail(),
                            emp.getEntryTime(),
                            emp.getSalary()
                    });
                });
                // 初始化时展示第一页数据
                filteredEmployees.addAll(employees);
                displayPage(currentPage);
            }
        }.execute();
    }

    /**
     * 创建测试用的员工对象
     */
    private Employee createSampleEmployee(int id) {
        return new Employee(
                id,
                "员工" + id,
                20 + id % 20,
                id % 2 == 0 ? "男" : "女",
                "职位" + (id % 3 + 1),
                "138001380" + String.format("%02d", id),
                "emp" + id + "@company.com",
                "2023-" + String.format("%02d", (id % 12 + 1)) + "-" + String.format("%02d", (id % 28 + 1)),
                5000 + id * 500
        );
    }

    /**
     * 显示指定页码的数据
     */
    public void displayPage(int pageNumber) {
        tableModel.setRowCount(0); // 清空表格

        int start = (pageNumber - 1) * PAGE_SIZE;
        int end = Math.min(pageNumber * PAGE_SIZE, filteredEmployees.size());

        for (int i = start; i < end; i++) {
            Employee emp = filteredEmployees.get(i);
            tableModel.addRow(new Object[]{
                    emp.getId(),
                    emp.getName(),
                    emp.getAge(),
                    emp.getSex(),
                    emp.getJob(),
                    emp.getPhone(),
                    emp.getEmail(),
                    emp.getEntryTime(),
                    emp.getSalary()
            });
        }

        updatePageLabel(); // 更新页码提示
    }

    /**
     * 更新页码显示
     */
    private void updatePageLabel() {
        int maxPage = (int) Math.ceil((double) filteredEmployees.size() / PAGE_SIZE);
        pageLabel.setText("第 " + currentPage + " 页 / 共 " + maxPage + " 页");
    }

    /**
     * 搜索并展示匹配的员工
     */
    private void searchAndDisplayResults(String keyword) {
        filteredEmployees.clear();

        for (Employee emp : employees) {
            boolean matches = false;

            // 姓名 - 部分匹配
            if (emp.getName().contains(keyword)) {
                matches = true;
            }
            // 年龄 - 精确匹配（如果输入是数字）
            else if (isNumeric(keyword) && emp.getAge() == Integer.parseInt(keyword)) {
                matches = true;
            }
            // 职位 - 部分匹配
            else if (emp.getJob().contains(keyword)) {
                matches = true;
            }
            // 电话 - 部分匹配
            else if (emp.getPhone().contains(keyword)) {
                matches = true;
            }
            // 邮箱 - 部分匹配
            else if (emp.getEmail().contains(keyword)) {
                matches = true;
            }
            // 入职时间 - 完全匹配
            else if (emp.getEntryTime().equals(keyword)) {
                matches = true;
            }

            if (matches) {
                filteredEmployees.add(emp);
            }
        }

        currentPage = 1;
        displayPage(currentPage);
    }

    /**
     * 判断字符串是否为数字
     */
    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 刷新表格显示全部员工
     */
    public void refreshTable() {
        filteredEmployees.clear();
        filteredEmployees.addAll(employees);
        currentPage = 1;
        displayPage(currentPage);
    }

    /**
     * 编辑员工（弹窗提示）
     */
    private void editEmployee(Employee emp) {
        new EditEmployee(this, emp).setVisible(true);
    }

    /**
     * 删除员工
     */
    private void deleteEmployee(Employee emp) {
        if (JOptionPane.showConfirmDialog(this,
                "确定删除 " + emp.getName() + "?",
                "确认删除",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            employees.remove(emp);
            refreshTable();
        }
    }

    /**
     * 打开添加员工窗口
     */
    private void showAddDialog() {
        new AddEmployee(this).setVisible(true);
    }

    /**
     * 获取下一个可用ID
     */
    public int getNextId() {
        if (employees.isEmpty()) {
            return 1;
        }
        return employees.stream().mapToInt(Employee::getId).max().getAsInt() + 1;
    }

    /**
     * 添加员工到列表并刷新表格
     */
    public void addEmployee(Employee emp) {
        employees.add(emp);
        refreshTable();
    }
}
