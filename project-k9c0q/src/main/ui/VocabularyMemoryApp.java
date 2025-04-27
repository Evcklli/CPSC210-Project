package ui;

import model.BackgroundPanel;
import model.EventLog;
import model.Event;
import model.Word;
import model.WordList;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.io.IOException;
import java.util.ArrayList;

public class VocabularyMemoryApp extends JFrame implements WindowListener {
    private WordList wordList;
    private static final String JSON_STORE = "./data/wordlist.json";
    private CardLayout cardLayout;
    private JsonReader reader = new JsonReader(JSON_STORE);
    private JsonWriter writer = new JsonWriter(JSON_STORE);
    private BackgroundPanel backgroundPanel;
    private JPanel cardPanel;

    public VocabularyMemoryApp() {
        wordList = new WordList();
        addWindowListener(this);
        backgroundPanel = new BackgroundPanel("./data/background.jpg");

        initializeGUI();
    }

    // MODIFIES: this
    // EFFECTS: Initializes the GUI.
    private void initializeGUI() {
        setTitle("Vocabulary Memory App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        
        backgroundPanel.setLayout(new BorderLayout());
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setOpaque(false);

        JPanel menuPanel = createMenuPanel();
        cardPanel.add(menuPanel, "Menu");

        JPanel addWordPanel = createAddWordPanel();
        cardPanel.add(addWordPanel, "AddWord");

        backgroundPanel.add(cardPanel, BorderLayout.CENTER);

        setContentPane(backgroundPanel);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: Creates the main menu panel.
    private JPanel createMenuPanel() {
        JPanel menuPanel = createTransparentPanel(new BorderLayout());

        JLabel titleLabel = createLabel("Vocabulary Memory App", 36, JLabel.CENTER, Color.WHITE);
        menuPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = createTransparentPanel(new GridLayout(0, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JButton[] buttons = {
            createButton("Add Word", e -> cardLayout.show(cardPanel, "AddWord")),
            createButton("List Words", e -> displayWords()),
            createButton("Find Word", e -> showFindWordDialog()),
            createButton("Filter Words by First Letter", e -> showFilterWordsDialog()),
            createButton("Save Word List", e -> saveWordList()),
            createButton("Load Word List", e -> loadWordList()),
            createButton("Exit", e -> exit())
        };

        for (JButton button : buttons) {
            buttonPanel.add(button);
        }

        menuPanel.add(buttonPanel, BorderLayout.CENTER);
        return menuPanel;
    }


    // MODIFIES: this
    // EFFECTS: Creates the Add Word panel.
    private JPanel createAddWordPanel() {
        JPanel addWordPanel = createTransparentPanel(new GridBagLayout());
        GridBagConstraints gbc = createGridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;

        JLabel titleLabel = createLabel("Add a New Word", 10, JLabel.CENTER, Color.BLACK);
        addWordPanel.add(titleLabel, gridPosition(gbc, 1, 0, 4));

        JTextField spellingField = addLabeledField(addWordPanel, gbc, "Spelling:", 1);
        JTextField posField = addLabeledField(addWordPanel, gbc, "Part of Speech:", 2);
        JTextField definitionField = addLabeledField(addWordPanel, gbc, "Definition:", 3);

        JButton saveButton = createButton("Save Word", e -> saveWord(spellingField, posField, definitionField));
        addWordPanel.add(saveButton, gridPosition(gbc, 0, 4, 2));

        JButton backButton = createButton("Back to Menu", e -> cardLayout.show(cardPanel, "Menu"));
        addWordPanel.add(backButton, gridPosition(gbc, 0, 5, 2));

        return addWordPanel;
    }

    // MODIFIES: this
    // EFFECTS: Creates a label.
    private JLabel createLabel(String text, int fontSize, int alignment, Color color) {
        JLabel label = new JLabel(text, alignment);
        label.setFont(new Font("DialogInput", Font.BOLD, fontSize));
        label.setForeground(color);
        return label;
    }

    // MODIFIES: this
    // EFFECTS: Creates a button.
    private JButton createButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.setFont(new Font("DialogInput", Font.BOLD, 16));
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        button.addActionListener(action);
        return button;
    }

    // MODIFIES: this
    // EFFECTS: Creates a transparent panel
    private JPanel createTransparentPanel(LayoutManager layout) {
        JPanel panel = new JPanel(layout);
        panel.setOpaque(false);
        return panel;
    }

    // MODIFIES: this
    // EFFECTS: Creates GridBagConstraints.
    private GridBagConstraints createGridBagConstraints() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        return gbc;
    }

    // MODIFIES: this
    // EFFECTS: Creates gridPosition.
    private GridBagConstraints gridPosition(GridBagConstraints gbc, int x, int y, int width) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        return gbc;
    }

    // MODIFIES: this
    // EFFECTS: Creates a textField.
    private JTextField addLabeledField(JPanel panel, GridBagConstraints gbc, String label, int y) {
        JLabel fieldLabel = createLabel(label, 8, JLabel.LEFT, Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        panel.add(fieldLabel, gbc);

        JTextField textField = new JTextField();
        gbc.gridx = 1;
        panel.add(textField, gbc);
        return textField;
    }


    // MODIFIES: this
    // EFFECTS: Save word to wordlist.
    private void saveWord(JTextField spellingField, JTextField posField, JTextField definitionField) {
        String spelling = spellingField.getText();
        String partOfSpeech = posField.getText();
        String definition = definitionField.getText();

        if (!spelling.isEmpty() && !partOfSpeech.isEmpty() && !definition.isEmpty()) {
            Word word = new Word(spelling, partOfSpeech, definition);
            wordList.addWord(word);
            

            JOptionPane.showMessageDialog(this, "Word added successfully!");
            spellingField.setText("");
            posField.setText("");
            definitionField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "ERROR", "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // MODIFIES: this
    // EFFECTS: Display words.
    private void displayWords() {
        ArrayList<String> words = wordList.listWords();
        StringBuilder message = new StringBuilder("Words in the Vocabulary List:\n\n");
        if (words.isEmpty()) {
            message.append("No words found.");
        } else {
            for (String word : words) {
                message.append("- ").append(word).append("\n");
            }
        }
        JOptionPane.showMessageDialog(this, message.toString(), "List Words", JOptionPane.INFORMATION_MESSAGE);
    }

    // MODIFIES: this
    // EFFECTS: Show find Word Dialog.
    private void showFindWordDialog() {
        String spelling = JOptionPane.showInputDialog(this, "Enter the word to find:", "Find Word",
                JOptionPane.INFORMATION_MESSAGE);
        if (spelling != null) {
            Word word = wordList.findWord(spelling);
            if (word != null) {
                String message = "Word Details:\n\n" 
                
                        + "Spelling: " + word.getSpelling() + "\n" 
                        + "Part of Speech: " + word.getPartOfSpeech() + "\n" 
                        + "Definition: " + word.getDefinition();
                JOptionPane.showMessageDialog(this, message, "Word Found", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Word not found.", "Error", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: Show filter Word Dialog.
    private void showFilterWordsDialog() {
        String firstLetter = JOptionPane.showInputDialog(this, "Enter the first letter to filter words:",
                "Filter Words", JOptionPane.INFORMATION_MESSAGE);
        if (firstLetter != null) {
            ArrayList<String> filteredWords = wordList.filterByFirstLetter(firstLetter);
            if (filteredWords.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No words found starting with \"" + firstLetter + "\".",
                        "Filter Words", JOptionPane.INFORMATION_MESSAGE);
            } else {
                StringBuilder message = new StringBuilder("Words starting with \"" + firstLetter + "\":\n\n");
                for (String word : filteredWords) {
                    message.append("- ").append(word).append("\n");
                }
                JOptionPane.showMessageDialog(this, message.toString(), "Filter Words",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: Save word list and show result.
    private void saveWordList() {
        try {
            
            writer.open();
            writer.write(wordList);
            writer.close();
            JOptionPane.showMessageDialog(this, "Word list saved successfully!", "Save Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving word list: " + e.getMessage(), "Save Error",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // MODIFIES: this
    // EFFECTS: Load word list and show result.
    private void loadWordList() {
        try {
            
            wordList = reader.read();
            JOptionPane.showMessageDialog(this, "Word list loaded successfully!", "Load Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading word list: " + e.getMessage(), "Load Error",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // EFFECTS: Creates and returns a JButton with black background and white text.
    private JButton blackButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("DialogInput", Font.BOLD, 16));
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        return button;
    }


    // EFFECTS: Print log when close.
    public void windowClosed(WindowEvent e) {
        EventLog eventLog = EventLog.getInstance();
        for (Event event : eventLog) {
            System.out.println(event.getDescription());
        }
    }

    public void windowOpened(WindowEvent e) {
        
    }

    public void windowIconified(WindowEvent e) {
        
    }

    public void windowDeiconified(WindowEvent e) {
        
    }

    public void windowActivated(WindowEvent e) {
        
    }

    public void windowDeactivated(WindowEvent e) {
        
    }

    // EFFECTS: Print log when close.
    public void windowClosing(WindowEvent e) {
        EventLog eventLog = EventLog.getInstance();
        for (Event event : eventLog) {
            System.out.println(event.getDescription());
        }


       

    }

    // EFFECTS: Exit the app.
    public void exit() {
        EventLog eventLog = EventLog.getInstance();
        for (Event event : eventLog) {
            System.out.println(event.getDescription());
        }

        System.exit(0);
    }









   

}
