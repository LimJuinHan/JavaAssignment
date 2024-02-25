package com.mycompany.javaassignment1;

import com.mycompany.javaassignment1.gui.DeleteModifySaleOrder;
import com.mycompany.javaassignment1.gui.LoginMenu;
import com.mycompany.javaassignment1.gui.SalesReport;
import com.mycompany.javaassignment1.gui.SelfProfile;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.DefaultCellEditor;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class SalesProcess extends JFrame {  
    
    private static DefaultTableModel tableModel;
    private static TableRowSorter<DefaultTableModel> sorter;
    private static void filterAndCopyUnapprovedRows(String inputFile, String outputFile) throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
         BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile, true))) { // Append flag set to true
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.contains("Unapproved")) {
                line = line.replace("Unapproved", "In Progress");
                writer.write(line);
                writer.newLine();
            }
            
        }
    
    }
    }
   
   
    public SalesProcess() {
        
        // Create and configure the frame
        JFrame frame = new JFrame("Officer Menu");
        frame.setSize(1200, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);      
        JPanel panel = new JPanel(new BorderLayout());
        frame.add(panel);

        // Create search pane
        
   
        JPanel searchPanel = new JPanel(new GridLayout(1, 2));

        // Add a JTextField for search input
        JTextField searchField = new JTextField();
        searchPanel.add(searchField);

        // Create search button
        JButton searchButton = new JButton("Search");
        searchPanel.add(searchButton);

        panel.add(searchPanel, BorderLayout.NORTH);

        // Create and configure the panel

        // Create table model with columns
        tableModel = new DefaultTableModel(
                new Object[][]{},
                new String[]{"SaleID", "ProductID", "ProductName", "Category", "Type", "Price", "Quantity", "State", "Date"}
        );

        // Create JTable with the table model
        JTable table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
        table.setAutoCreateRowSorter(true);
        table.setCellSelectionEnabled(true);
        table.setColumnSelectionAllowed(true);
        table.setRowSelectionAllowed(false);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Create approve panel
        
        JPanel approvePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        // Create approve button
        JButton approveButton = new JButton("Approve Sale");
        approvePanel.add(approveButton);
        
         approveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                String inputFile = "sales.txt";
                String outputFile = "work_done.txt";
                try {
                    filterAndCopyUnapprovedRows(inputFile, outputFile);
                    System.out.println("Filtered rows copied to " + outputFile);
                } catch (IOException ex) {
                    System.err.println("Error: " + ex.getMessage());
                }
            }
        });
         
        

        // Create close button
        JButton closeButton = new JButton("Close Sale");
        approvePanel.add(closeButton);
        
        JPanel workdonePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton workdoneButton = new JButton("Work Done");
        approvePanel.add(workdoneButton);
        
        JPanel invoicePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton showInvoiceButton = new JButton("Show Invoice");
        approvePanel.add(showInvoiceButton);
        
        JPanel PersonalInformationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton PIButton = new JButton("Personal Information");
        approvePanel.add(PIButton);
        
        

        JPanel ModifyPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton ModifyButton = new JButton("Modify Sales");
        approvePanel.add(ModifyButton);
        
        ModifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                DeleteModifySaleOrder DeleteModifySaleOrder1 = new DeleteModifySaleOrder();
        DeleteModifySaleOrder1.setVisible(true);
        
            }
        });
        

        
        JPanel GRPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton GRButton = new JButton("Generate Report");
        approvePanel.add(GRButton);
        
        GRButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                SalesReport SalesReport1 = new SalesReport();
        SalesReport1.setVisible(true);
        }
        });
        
        JPanel loPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton loButton = new JButton("LOG OUT");
        approvePanel.add(loButton);
        
        loButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                Session session = Session.getInstance();
                session.clearSession();
                SalesProcess.this.dispose();
                LoginMenu loginMenu = new LoginMenu();
                loginMenu.setVisible(true);
                }
        });
        
        // Create save panel
        JPanel savePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Create save button
        JButton saveButton = new JButton("Save");
        savePanel.add(saveButton);

        // Create a panel to hold the approvePanel and savePanel
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(approvePanel, BorderLayout.WEST);
        bottomPanel.add(savePanel, BorderLayout.EAST);
  

        panel.add(bottomPanel, BorderLayout.SOUTH);

        // Initialize sorter
        sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        // Event handler for search button
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchTerm = searchField.getText().toLowerCase();
                if (!searchTerm.isEmpty()) {
                    RowFilter<DefaultTableModel, Object> rf = RowFilter.regexFilter("(?i)" + searchTerm);
                    sorter.setRowFilter(rf);
                } else {
                    // If the search term is empty, remove the filter
                    sorter.setRowFilter(null);
                }
            }
        });
        
        PIButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                Session session = Session.getInstance();
                String userID = session.getUserID();
                String userName = session.getUsername();
                String password = session.getPassword();
                String role = session.getRole();
                String status = session.getStatus();
                int age = session.getAge();
                String email = session.getEmail();
                String phoneNumber = session.getPhoneNumber();

                Salesperson currentUser = new Salesperson(userID, userName, password, role, status, age, email, phoneNumber);
                SelfProfile selfProfileWindow = new SelfProfile(currentUser);
                selfProfileWindow.setVisible(true);  // TODO add your handling code here:
                    }
                });

        showInvoiceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                issueInvoice(table);
            }
        });

        // Event handler for save button
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveDataToFile();
            }
        });
        
        workdoneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            tableModel.setValueAt("Work Done", selectedRow, tableModel.getColumnCount() - 2);
            saveDataToFile();
        } else {
            JOptionPane.showMessageDialog(frame, "Please select a row to mark as 'Work Done'.", "No Row Selected", JOptionPane.WARNING_MESSAGE);
        }
            }
        
        });

        // Event handler for close button
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    tableModel.setValueAt("Closed", selectedRow, tableModel.getColumnCount() - 1);
                }
            }
        });
        
        
        
      
        // Event handler for approve button
        approveButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    tableModel.setValueAt("Approved", selectedRow, tableModel.getColumnCount() - 1);
                     saveDataToFile(); // Save the changes to the file
                      
                  
                }              
                SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    reloadData();
            }
        });   
            }
        });
    
        // Mouse listener to handle adding new row when clicking on empty row
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = table.rowAtPoint(e.getPoint());
                    int col = table.columnAtPoint(e.getPoint());
                    if (row == -1) {
                        tableModel.addRow(new Object[tableModel.getColumnCount()]);
                    }
                }
            }
        });

        // Load initial data
        loadData();

        // Set custom cell editor for the "Price" column
        table.getColumnModel().getColumn(3).setCellEditor(new NumberCellEditor());

        // Set frame visibility
        frame.setVisible(true);
    }

    private static void saveDataToFile() {
        String filePath = "work_done.txt";
        try {
            
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            for (int row = 0; row < tableModel.getRowCount(); row++) {
                for (int col = 0; col < tableModel.getColumnCount(); col++) {
                    String value = (String) tableModel.getValueAt(row, col);
                    if (value != null) {
                        writer.write(value);
                        if (col < tableModel.getColumnCount() - 1) {
                            writer.write(",");
                        }
                    }
                }
                writer.newLine();
            }
            writer.flush();
            writer.close();
            System.out.println("Data saved to " + filePath + " successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadData() {
    String filePath = "work_done.txt";
    try {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] rowData = line.split(",");
            tableModel.addRow(rowData);
        }
        reader.close();
        System.out.println("Data loaded from " + filePath + " successfully.");
    } catch (IOException e) {
        System.err.println("Error loading data from " + filePath + ": " + e.getMessage());
        e.printStackTrace();
    }
}


    static class NumberCellEditor extends DefaultCellEditor {
        public NumberCellEditor() {
            super(new JTextField());
        }

        @Override
        public boolean stopCellEditing() {
            try {
                Integer.parseInt((String) getCellEditorValue());
                return super.stopCellEditing();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a numeric value for Price.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        
    }

    

    private static void replaceTextInFile(String filePath, String targetText, String replacementText) {
        try {
            // Read content from the file
            Path path = Paths.get(filePath);
            Charset charset = StandardCharsets.UTF_8;
            String content = new String(Files.readAllBytes(path), charset);

            // Replace the target text with the replacement text
            content = content.replaceAll(targetText, replacementText);

            // Write the modified content back to the file
            Files.write(path, content.getBytes(charset));
            System.out.println("Text replaced successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void reloadData() {
    replaceTextInFile("sales.txt", "Unapproved", "Approved");
    String filePath = "work_done.txt";
    try {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        
        // Clear existing data from the table model
        tableModel.setRowCount(0);
        
        String line;
        while ((line = reader.readLine()) != null) {
            String[] rowData = line.split(",");
            tableModel.addRow(rowData);
        }
        reader.close();
        System.out.println("Data loaded from " + filePath + " successfully.");
    } catch (IOException e) {
        System.err.println("Error loading data from " + filePath + ": " + e.getMessage());
        e.printStackTrace();
    }
   }
    private static void issueInvoice(JTable table) {
    int selectedRow = table.getSelectedRow();
    if (selectedRow != -1) {
        String SaleID = (String) table.getValueAt(selectedRow, 0); // Assuming SaleID is in the first column (index 0)
        String ProductID = (String) table.getValueAt(selectedRow, 1); // Assuming ProductID is in the second column (index 1)
        String ProductName = (String) table.getValueAt(selectedRow, 2); // Assuming ProductName is in the third column (index 2)
        String Category = (String) table.getValueAt(selectedRow, 3); // Assuming Category is in the fourth column (index 3)
        String Type = (String) table.getValueAt(selectedRow, 4); // Assuming Type is in the fifth column (index 4)
        String Price = (String) table.getValueAt(selectedRow, 5); // Assuming Price is in the sixth column (index 5)
        String Quantity = (String) table.getValueAt(selectedRow, 6); // Assuming Quantity is in the seventh column (index 6)
        String Date = (String) table.getValueAt(selectedRow, 8); // Assuming Date is in the ninth column (index 8)
        // Your further logic here


        // Generate invoice content
        String invoiceContent = generateInvoiceContent(SaleID, ProductID, ProductName, Category, Type, Price, Quantity, Date);

        // Display invoice in a pop-up window
        displayInvoicePopup(invoiceContent);
    } else {
        JOptionPane.showMessageDialog(null, "Please select a row to issue the invoice.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    private static void displayInvoicePopup(String invoiceContent) {
        // Create a new JFrame to display the invoice
        JFrame invoiceFrame = new JFrame("Invoice");
        invoiceFrame.setSize(400, 300);
        invoiceFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create a JPanel to hold the invoice content
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setLayout(new BorderLayout());
        contentPane.setBackground(Color.WHITE); // Set background color

        // Create a JLabel for the header
        JLabel headerLabel = new JLabel("Invoice");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Set header font
        headerLabel.setHorizontalAlignment(JLabel.CENTER); // Center align header
        headerLabel.setForeground(Color.BLACK); // Set header text color
        contentPane.add(headerLabel, BorderLayout.NORTH);

        // Create a JTextArea to display the invoice content
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setText(invoiceContent);
        textArea.setFont(new Font("Arial", Font.PLAIN, 14)); // Set content font
        textArea.setLineWrap(true); // Enable line wrapping
        textArea.setWrapStyleWord(true); // Wrap at word boundaries
        textArea.setBackground(Color.WHITE); // Set background color
        textArea.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Add border

        // Add the JTextArea to a JScrollPane
        JScrollPane scrollPane = new JScrollPane(textArea);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        // Add a JPanel for the footer
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(Color.WHITE); // Set background color
        footerPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY)); // Add bottom border

        // Add the footer panel to the content pane
        contentPane.add(footerPanel, BorderLayout.SOUTH);

        // Add the content pane to the frame
        invoiceFrame.setContentPane(contentPane);

        // Center the frame on the screen
        invoiceFrame.setLocationRelativeTo(null);

        // Make the frame visible
        invoiceFrame.setVisible(true);
    }

    private static String generateInvoiceContent(String SaleID, String ProductID, String ProductName, String Category, String Price, String Type, String Quantity, String Date) {
        // Implement your invoice generation logic here
        // For example:
        StringBuilder invoiceContent = new StringBuilder();
        invoiceContent.append("Sale ID: ").append(SaleID).append("\n");
        invoiceContent.append("Product ID: ").append(ProductID).append("\n");
        invoiceContent.append("Product Name: ").append(ProductName).append("\n");
        invoiceContent.append("Category: ").append(Category).append("\n");
        invoiceContent.append("Type: ").append(Type).append("\n");        
        invoiceContent.append("Price: ").append(Price).append("\n");
        invoiceContent.append("Date: ").append(Date).append("\n");
        return invoiceContent.toString();
    }
    
}



 
 
