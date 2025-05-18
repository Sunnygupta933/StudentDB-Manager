import javax.swing.*;
 import javax.swing.table.DefaultTableModel;
 import java.awt.*;
 import java.awt.event.*;
 import java.sql.*;
 import java.util.Vector;
 /**
 * Main application GUI for Student Database Management.
 * Supports CRUD operations with role-based access control.
 */
 public class AppGUI extends JFrame implements ActionListener {
    // Labels
    private JLabel studentIdLabel, firstNameLabel, lastNameLabel, majorLabel, phoneLabel, gpaLabel, dobLabel;
    // Text Fields
    private JTextField studentIdField, firstNameField, lastNameField, majorField, phoneField, gpaField, dobField;
    // Buttons
    private JButton addButton, displayButton, sortButton, searchButton, modifyButton, deleteButton, clearAllButton;
    private String userRole;  // To determine role privileges
    public AppGUI(String role) {
        this.userRole = role;
        setTitle("Student Database Management - Role: " + role);
        setSize(600, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponents();
        layoutComponents();
        configureRoleAccess();
        setVisible(true);
    }
    private void initComponents() {
        // Initialize labels
        studentIdLabel = new JLabel("Student ID:");
        firstNameLabel = new JLabel("First Name:");
        lastNameLabel = new JLabel("Last Name:");
        majorLabel = new JLabel("Major:");
        phoneLabel = new JLabel("Phone:");
        gpaLabel = new JLabel("GPA:");
        dobLabel = new JLabel("DOB (yyyy-mm-dd):");
        // Initialize text fields
        studentIdField = new JTextField(15);
        firstNameField = new JTextField(15);
        lastNameField = new JTextField(15);
        majorField = new JTextField(15);
        phoneField = new JTextField(15);
        gpaField = new JTextField(15);
        dobField = new JTextField(15);
        // Initialize buttons
        addButton = new JButton("Add");
        displayButton = new JButton("Display");
        sortButton = new JButton("Sort");
        searchButton = new JButton("Search");
        modifyButton = new JButton("Modify");
        deleteButton = new JButton("Delete");
        clearAllButton = new JButton("Clear All Data");
        // Add action listeners
        addButton.addActionListener(this);
        displayButton.addActionListener(this);
        sortButton.addActionListener(this);
        searchButton.addActionListener(this);
        modifyButton.addActionListener(this);
        deleteButton.addActionListener(this);
        clearAllButton.addActionListener(this);
    }
    private void layoutComponents() {
        JPanel inputPanel = new JPanel(new GridLayout(7, 2, 8, 8));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        inputPanel.add(studentIdLabel);
        inputPanel.add(studentIdField);
        inputPanel.add(firstNameLabel);
        inputPanel.add(firstNameField);
        inputPanel.add(lastNameLabel);
        inputPanel.add(lastNameField);
        inputPanel.add(majorLabel);
        inputPanel.add(majorField);
        inputPanel.add(phoneLabel);
        inputPanel.add(phoneField);
        inputPanel.add(gpaLabel);
        inputPanel.add(gpaField);
        inputPanel.add(dobLabel);
        inputPanel.add(dobField);
        JPanel buttonPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 15, 15, 15));
        buttonPanel.add(addButton);
        buttonPanel.add(displayButton);
        buttonPanel.add(sortButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(modifyButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearAllButton);
        buttonPanel.add(new JLabel(""));  // filler
        setLayout(new BorderLayout());
        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    private void configureRoleAccess() {
        if ("user".equalsIgnoreCase(userRole)) {
            // Disable editing capabilities for regular users
            addButton.setEnabled(false);
            modifyButton.setEnabled(false);
            deleteButton.setEnabled(false);
            clearAllButton.setEnabled(false);
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        dbConnect db = new dbConnect();
        Connection conn = null;
        PreparedStatement pstmt = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = db.getConnection();
            stmt = conn.createStatement();
            Table tableHelper = new Table();
            if (e.getSource() == addButton) {
                if (anyFieldEmpty(true)) {
                    JOptionPane.showMessageDialog(this, "All fields including Student ID are required for adding.");
                    return;
                }
                String sql = "INSERT INTO sdata (Student_ID, first_name, last_name, major, Phone, GPA, DOB) VALUES (?, ?, ?, ?, ?, ?, ?)";
                pstmt = conn.prepareStatement(sql);
                setPreparedStatementFields(pstmt, true);
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Student added successfully.");
                clearInputFields();
            } else if (e.getSource() == displayButton) {
                rs = stmt.executeQuery("SELECT * FROM sdata");
                displayResultSet(rs, tableHelper);
            } else if (e.getSource() == sortButton) {
                String[] options = {"First Name", "Last Name", "Major"};
                int choice = JOptionPane.showOptionDialog(this, "Sort students by:", "Sort Options",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
                if (choice < 0) return;
                String column = switch (choice) {
                    case 0 -> "first_name";
                    case 1 -> "last_name";
                    case 2 -> "major";
                    default -> "";
                };
                rs = stmt.executeQuery("SELECT * FROM sdata ORDER BY " + column);
                displayResultSet(rs, tableHelper);
            } else if (e.getSource() == searchButton) {
                String[] options = {"Student ID", "Last Name", "Major"};
                int choice = JOptionPane.showOptionDialog(this, "Search students by:", "Search Options",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
                if (choice < 0) return;
                String column = switch (choice) {
                    case 0 -> "Student_ID";
                    case 1 -> "last_name";
                    case 2 -> "major";
                    default -> "";
                };
                String value = JOptionPane.showInputDialog(this, "Enter " + column + ":");
                if (value == null || value.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Search value cannot be empty.");
                    return;
                }
                String sql = "SELECT * FROM sdata WHERE " + column + " = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, value.trim());
                rs = pstmt.executeQuery();
                displayResultSet(rs, tableHelper);
            } else if (e.getSource() == modifyButton) {
                if (studentIdField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Enter Student ID to modify.");
                    return;
                }
                if (anyFieldEmpty(false)) {
                    JOptionPane.showMessageDialog(this, "Fill all fields except Student ID to modify.");
                    return;
                }
                // Check if record exists
                pstmt = conn.prepareStatement("SELECT * FROM sdata WHERE Student_ID = ?");
                pstmt.setString(1, studentIdField.getText().trim());
                rs = pstmt.executeQuery();
                if (!rs.next()) {
                    JOptionPane.showMessageDialog(this, "Student ID not found.");
                    return;
                }
                String updateSQL = "UPDATE sdata SET first_name = ?, last_name = ?, major = ?, Phone = ?, GPA = ?, DOB = ? WHERE Student_ID = ?";
                pstmt = conn.prepareStatement(updateSQL);
                setPreparedStatementFields(pstmt, false);
                pstmt.setString(7, studentIdField.getText().trim());
                int rows = pstmt.executeUpdate();
                if (rows > 0) {
                    JOptionPane.showMessageDialog(this, "Student record updated.");
                    clearInputFields();
                } else {
                    JOptionPane.showMessageDialog(this, "Update failed.");
                }
            } else if (e.getSource() == deleteButton) {
                String id = studentIdField.getText().trim();
                if (id.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Enter Student ID to delete.");
                    return;
                }
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Delete student with ID " + id + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    pstmt = conn.prepareStatement("DELETE FROM sdata WHERE Student_ID = ?");
                    pstmt.setString(1, id);
                    int deleted = pstmt.executeUpdate();
                    if (deleted > 0) {
                        JOptionPane.showMessageDialog(this, "Student deleted.");
                        clearInputFields();
                    } else {
                        JOptionPane.showMessageDialog(this, "Student ID not found.");
                    }
                }
            } else if (e.getSource() == clearAllButton) {
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Delete ALL student records?", "Confirm Clear All", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    int deleted = stmt.executeUpdate("DELETE FROM sdata");
                    JOptionPane.showMessageDialog(this, deleted + " records deleted.");
                    clearInputFields();
                }
            }
        } catch (SQLException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ignored) {}
        }
    }
    /**
     * Checks if input fields are empty.
     * @param includeId - whether to include Student ID field in check
     * @return true if any required field is empty
     */
    private boolean anyFieldEmpty(boolean includeId) {
        if (includeId && studentIdField.getText().trim().isEmpty()) return true;
        if (firstNameField.getText().trim().isEmpty()) return true;
        if (lastNameField.getText().trim().isEmpty()) return true;
        if (majorField.getText().trim().isEmpty()) return true;
        if (phoneField.getText().trim().isEmpty()) return true;
        if (gpaField.getText().trim().isEmpty()) return true;
        if (dobField.getText().trim().isEmpty()) return true;
        return false;
    }
    /**
     * Set parameters for PreparedStatement for insert/update.
     * @param pstmt - PreparedStatement
     * @param includeId - whether to set Student_ID as first parameter
     * @throws SQLException
     */
    private void setPreparedStatementFields(PreparedStatement pstmt, boolean includeId) throws SQLException {
        int index = 1;
        if (includeId) {
            pstmt.setString(index++, studentIdField.getText().trim());
        }
        pstmt.setString(index++, firstNameField.getText().trim());
        pstmt.setString(index++, lastNameField.getText().trim());
        pstmt.setString(index++, majorField.getText().trim());
        pstmt.setString(index++, phoneField.getText().trim());
        pstmt.setDouble(index++, Double.parseDouble(gpaField.getText().trim()));
    }
        pstmt.setDate(index++, Date.valueOf(dobField.getText().trim()));
    /**
     * Display a ResultSet in a JTable inside a scroll pane dialog.
     * @param rs - ResultSet
     * @param tableHelper - Table helper instance to build model
     * @throws SQLException
     */
    private void displayResultSet(ResultSet rs, Table tableHelper) throws SQLException {
        DefaultTableModel model = tableHelper.buildTableModel(rs);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(750, 300));
        JOptionPane.showMessageDialog(this, scrollPane, "Student Data", JOptionPane.INFORMATION_MESSAGE);
    }
    /**
     * Clear all input fields.
     */
    private void clearInputFields() {
        studentIdField.setText("");
        firstNameField.setText("");
        lastNameField.setText("");
        majorField.setText("");
        phoneField.setText("");
        gpaField.setText("");
        dobField.setText("");
    }
    /**
     * Inner helper class to build table model from ResultSet.
     */
    private static class Table {
        public DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
            ResultSetMetaData metaData = rs.getMetaData();
            // Column names
            Vector<String> columnNames = new Vector<>();
            int columnCount = metaData.getColumnCount();
            for (int column = 1; column <= columnCount; column++) {
                columnNames.add(metaData.getColumnName(column));
            }
            // Data rows
            Vector<Vector<Object>> data = new Vector<>();
            while (rs.next()) {
                Vector<Object> vector = new Vector<>();
                for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                    vector.add(rs.getObject(columnIndex));
                }
                data.add(vector);
            }
            return new DefaultTableModel(data, columnNames) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false; // All cells non-editable
                }
            };
        }
    }
    public static void main(String[] args) {
        String role = "user";
        if (args.length > 0) {
            role = args[0];
        }
        SwingUtilities.invokeLater(() -> new AppGUI(role));
    }
 }