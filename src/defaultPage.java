import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class defaultPage {
    JFrame frame = new JFrame();
    JPanel panelCenter = new JPanel();
    JPanel panelLeft = new JPanel();
    JList<String> notesList = new JList<>();
    JTextArea noteArea = new JTextArea();
    DefaultListModel<String> model = new DefaultListModel<>();
    JButton newFile = new JButton("+");

    defaultPage() {

        frame.setSize(700, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());
        panelLeft.setPreferredSize(new Dimension(100, 100));
        panelCenter.setPreferredSize(new Dimension(600, 100));

        frame.add(panelLeft, BorderLayout.WEST);
        frame.add(panelCenter, BorderLayout.CENTER);


        notesList.setModel(model);
        notesList.setPreferredSize(new Dimension(100, 600));
        panelLeft.add(notesList);

        newFile.setBounds(0, 600, 100, 50);
        panelLeft.add(newFile);

        noteArea.setPreferredSize(new Dimension(550, 650));
        noteArea.setLineWrap(true);
        noteArea.setWrapStyleWord(true);
        panelCenter.add(new JScrollPane(noteArea), BorderLayout.CENTER);



        fillList();
        frame.setVisible(true);



        notesList.addListSelectionListener(e -> {
            File filename = new File(notesList.getSelectedValue() + ".txt");
            noteArea.setText(getContent(filename));
        });

        noteArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_S && e.isControlDown()) {
                    try {
                        saveContent();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        notesList.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DELETE && notesList.getSelectedIndex() != -1) {
                    try {
                        deleteContent();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        newFile.addActionListener(e -> {
            JPanel optionPanel = new JPanel();
            JLabel label = new JLabel("Enter a filename:");
            JTextField filename = new JTextField(20);

            optionPanel.add(label);
            optionPanel.add(filename);

            String x = JOptionPane.showInputDialog(null,
                    "Name your file:",
                    "This is shit",
                    JOptionPane.QUESTION_MESSAGE);
            if (x != null) {
                System.out.println("itt");
                model.addElement(x);
                notesList.setSelectedIndex(notesList.getLastVisibleIndex());

            }
        });

    }

    private void fillList() {
        File folder = new File(".");
        System.out.println(folder.getName());
        model.clear();

        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".txt")) {
                        model.addElement(file.getName().substring(0, file.getName().length() - 4));
                    }
                }
            }
        }
    }

    private String getContent (File filename) {
        StringBuilder content = new StringBuilder();
        if (filename.exists()) {
            try {
                Scanner scanner = new Scanner(filename);
                while (scanner.hasNext()) {
                    content.append(scanner.nextLine());
                }
                scanner.close();
                return content.toString();
            } catch (SecurityException e) {
                System.out.println("File cannot be opened");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return " ";
    }

    private void saveContent() throws IOException {
        String currFilename = notesList.getSelectedValue();
        try {
            File file = new File(currFilename + ".txt");

            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fileWriter = new FileWriter(file, false);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(noteArea.getText());
            bufferedWriter.close();

            System.out.println("Successful save to file: " + file.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void deleteContent() throws IOException {
        int choice = JOptionPane.showConfirmDialog(panelCenter,
                "Do you want to delete the file?",
                "Confirm action",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (choice == JOptionPane.YES_OPTION) {
            File file = new File(notesList.getSelectedValue() + ".txt");
            if (Files.deleteIfExists(Path.of(file.getAbsolutePath()))) {
                    System.out.println("File " + file.getName() + " successfully deleted");
            }
            model.removeElementAt(notesList.getSelectedIndex());
        }
    }
}
