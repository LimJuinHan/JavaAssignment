package com.mycompany.javaassignment1;

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
import java.awt.GridLayout;
import java.awt.FlowLayout;
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
import javax.swing.SwingUtilities;

public class SalesProcess {
        
       


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
    
    

    public static void main(String[] args) {
        
 
        
    
        // Create and configure the frame
        JFrame frame = new JFrame("Furniture Sale Ordering Management System");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create and configure the panel
        JPanel panel = new JPanel(new BorderLayout());
        frame.add(panel);

        // Create search panel
        JPanel searchPanel = new JPanel(new GridLayout(1, 2));

        // Add a JTextField for search input
        JTextField searchField = new JTextField();
        searchPanel.add(searchField);

        // Create search button
        JButton searchButton = new JButton("Search");
        searchPanel.add(searchButton);

        panel.add(searchPanel, BorderLayout.NORTH);

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
}



 
 