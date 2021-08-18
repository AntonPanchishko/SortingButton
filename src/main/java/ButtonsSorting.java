/**
 * This application is able to sorting numbers.
 * App is using visualisation on Swing.
 * User is able to enter numbers of sort elements and
 * enter the speed of sorting
 */

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class ButtonsSorting extends JFrame implements ActionListener {
    public static final int X_INDENT_FOR_ENTER_BUTTON = 100;
    public static final int Y_INTEND_FOR_ENTER_BUTTON = 150;
    public static final int ENTER_BUTTON_WIDTH = 100;
    public static final int INT = 30;
    public static final int MAX_SORT_SPEED = INT;
    public static final int ENTER_BUTTON_HEIGHT = 30;
    public static final int X_INTEND_FOR_TEXT_LENGTH = 100;
    public static final int Y_INTEND_FOR_TEXT_LENGTH = 100;
    public static final int TEXT_ARRAY_WIDTH = 100;
    public static final int TEXT_ARRAY_HEIGHT = 20;
    public static final int X_INTEND_FOR_SORT = 650;
    public static final int Y_INTEND_FOR_SORT_BUTTON = 250;
    public static final int SPEED_SORT_WIDTH = 100;
    public static final int SPEED_SORT_HEIGHT = 20;
    public static final int X_INTEND_TEXT_ARRAY = 60;
    public static final int Y_INTEND_TEXT_ARRAY = 50;
    public static final int TEXT_ARRAY_WIDTH_LABEL = 180;
    public static final int TEXT_ARRAY_HEIGHT_LABEL = 20;
    public static final int X_INTER_FOR_SORT_LABEL = 650;
    public static final int Y_INTEND_FOR_SORT_LABEL = 200;
    public static final int WIDTH_SORT_LABEL = 200;
    public static final int HEIGHT_SORT_LABEL = 50;
    public static final int START_WINDOW_SIZE = 300;
    public static final int MAX_ELEMENT_SIZE = 50;
    public static final int SECOND_SCREEN_SIZE = 800;
    public static final int RANDOM_RANGE = 1000;
    public static final int SECOND_SCREEN_HEIGHT = 600;
    private int[] buttonValues;
    private List<JButton> buttons;
    private Thread thread;
    private JButton enterButton;
    private JButton sortButton;
    private JButton resetButton;
    private JTextField textArrayLength;
    private JTextField textSpeedSort;
    private JLabel labelArrayLength;
    private JLabel labelSpeedSort;

    /**
     * This is constructor for the start display
     * where you able to enter number of elements
     */
    public ButtonsSorting() {
        super("Amount of elements");
        enterButton = new JButton("Enter");
        enterButton.setBounds(X_INDENT_FOR_ENTER_BUTTON, Y_INTEND_FOR_ENTER_BUTTON,
                ENTER_BUTTON_WIDTH, ENTER_BUTTON_HEIGHT);
        enterButton.setForeground(Color.BLACK);
        enterButton.addActionListener(this);

        textArrayLength = new JTextField();
        textArrayLength.setBounds(X_INTEND_FOR_TEXT_LENGTH, Y_INTEND_FOR_TEXT_LENGTH,
                TEXT_ARRAY_WIDTH, TEXT_ARRAY_HEIGHT);

        textSpeedSort = new JTextField();
        textSpeedSort.setBounds(X_INTEND_FOR_SORT, Y_INTEND_FOR_SORT_BUTTON,
                SPEED_SORT_WIDTH, SPEED_SORT_HEIGHT);
        labelArrayLength = new JLabel("How many numbers to display?");
        labelArrayLength.setBounds(X_INTEND_TEXT_ARRAY, Y_INTEND_TEXT_ARRAY,
                TEXT_ARRAY_WIDTH_LABEL, TEXT_ARRAY_HEIGHT_LABEL);
        labelSpeedSort = new JLabel("<html>Enter speed <br/> show sort [1;30]</html>");
        labelSpeedSort.setBounds(X_INTER_FOR_SORT_LABEL, Y_INTEND_FOR_SORT_LABEL,
                WIDTH_SORT_LABEL, HEIGHT_SORT_LABEL);

        add(enterButton);
        add(textArrayLength);
        add(labelArrayLength);
        setSize(START_WINDOW_SIZE, START_WINDOW_SIZE);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == enterButton) {
            int arrayLength = Integer.parseInt(textArrayLength.getText());
            if (arrayLength > MAX_ELEMENT_SIZE || arrayLength <= 0) {
                JOptionPane.showMessageDialog(ButtonsSorting
                        .this, "This value bigger than 50 or less than 0");
                throw new RuntimeException();
            }
            enterButton.setVisible(false);
            textArrayLength.setVisible(false);
            labelArrayLength.setVisible(false);
            textSpeedSort.setText("");
            textSpeedSort.setVisible(true);
            labelSpeedSort.setVisible(true);
            createSecondScreen();
            generateRandomList(arrayLength);
            buttons.forEach(e -> e.setVisible(true));
        }

        if (actionEvent.getSource() == sortButton) {
            int speedSort = Integer.parseInt(textSpeedSort.getText());
            if (speedSort > MAX_SORT_SPEED || speedSort <= 0) {
                JOptionPane.showMessageDialog(ButtonsSorting
                        .this, "This value bigger than 30 or less than 0");
                throw new RuntimeException();
            }
            for (JButton button : buttons) {
                button.setForeground(Color.BLACK);
            }
            thread = new Thread() {
                @Override
                public void run() {
                    quickSort(buttonValues, 0, buttonValues.length - 1);
                }
            };
            thread.start();
            resetButton.setEnabled(false);
            sortButton.setEnabled(false);
        }
        if (actionEvent.getSource() == resetButton) {
            textArrayLength.setText("");
            setSize(START_WINDOW_SIZE, START_WINDOW_SIZE);
            setVisible(true);
            enterButton.setVisible(true);
            sortButton.setVisible(false);
            resetButton.setVisible(false);
            textArrayLength.setVisible(true);
            textSpeedSort.setVisible(false);
            labelArrayLength.setVisible(true);
            labelSpeedSort.setVisible(false);
            buttons.forEach(e -> e.setVisible(false));
            repaint();
        }
    }

    /**
     * This method create new display
     * where you able to enter speed of sorting
     * or return in previous display
     */
    private void createSecondScreen() {

        setSize(SECOND_SCREEN_SIZE, SECOND_SCREEN_HEIGHT);
        setLayout(null);
        sortButton = new JButton("Sort");
        sortButton.setBounds(X_INTEND_FOR_SORT, Y_INTEND_FOR_TEXT_LENGTH, SPEED_SORT_WIDTH, ENTER_BUTTON_HEIGHT);
        sortButton.setForeground(Color.BLACK);
        sortButton.addActionListener(this);
        resetButton = new JButton("Reset");
        resetButton.setBounds(X_INTEND_FOR_SORT, Y_INTEND_FOR_ENTER_BUTTON, SPEED_SORT_WIDTH, ENTER_BUTTON_HEIGHT);
        resetButton.setForeground(Color.BLACK);
        resetButton.addActionListener(this);

        add(sortButton);
        add(resetButton);
        add(textSpeedSort);
        add(labelSpeedSort);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * This method generate array of numbers
     * Every element of array it is value of buttons
     *
     * @param arrayLength it is size of new array
     */
    private void generateRandomList(int arrayLength) {

        buttonValues = new int[arrayLength];
        buttons = new ArrayList<>();

        int xIntendForElement = 20;
        int yIntendForElement = 20;
        int buttonWidth = 100;
        int buttonHeight = 30;

        for (int i = 0; i < arrayLength; i++) {
            if (i % 10 == 0 && i > 0) {
                xIntendForElement = xIntendForElement + 120;
                yIntendForElement = 20;
            }
            buttonValues[i] = (int) (Math.random() * RANDOM_RANGE);
            JButton button = new JButton(String.valueOf(buttonValues[i]));
            button.setVisible(true);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == button) {
                        if (Integer.parseInt(button.getText()) > MAX_ELEMENT_SIZE) {
                            JOptionPane.showMessageDialog(ButtonsSorting
                                    .this, "This value bigger than 50");
                        } else {
                            buttons.forEach(b -> b.setVisible(false));
                            buttons.forEach(b -> remove(b));
                            buttonValues = new int[5];
                            buttons = new ArrayList<>();
                            generateRandomList(Integer.parseInt(button.getText()));
                            buttons.forEach(b -> b.updateUI());
                        }
                    }
                }
            });
            button.setBounds(xIntendForElement, yIntendForElement, buttonWidth, buttonHeight);
            add(button);
            buttons.add(button);
            yIntendForElement += 40;
        }
        int number = (int) (Math.random() * arrayLength);
        buttonValues[number] = (int) (Math.random() * 29) + 1;
        buttons.get(number).setText(buttonValues[number] + "");

    }

    public int partition(int[] arr, int low, int high) {
        /**
         pivot
         */
        int pivot = arr[high];
        /** Index of smaller element and
         indicates the right position
         of pivot found so far*/
        int i = (low - 1);
        for (int j = low; j <= high - 1; j++) {
            /** If current element is smaller
             than the pivot*/
            if (arr[j] < pivot) {
                /** Increment index of
                 smaller element*/
                i++;
                swapNumbers(arr, i, j);
            }
        }
        swapNumbers(arr, i + 1, high);
        return (i + 1);
    }

    /**
     * The main function that implements QuickSort
     *
     * @param arr  --> Array to be sorted,
     * @param low  --> Starting index,
     * @param high --> Ending index
     */
    public void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            /** pi is partitioning index, arr[p]
             is now at right place*/
            int pi = partition(arr, low, high);

            /** Separately sort elements before
             partition and after partition*/
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
        resetButton.setEnabled(true);
        sortButton.setEnabled(true);
    }

    /**
     * @param array       this is array of swapping elements
     * @param firstIndex  index of first element in array which will be swap
     * @param secondIndex index of second element in array which will be swap
     */

    private void swapNumbers(int[] array, int firstIndex, int secondIndex) {
        int tmp = array[firstIndex];
        array[firstIndex] = array[secondIndex];
        array[secondIndex] = tmp;
        buttons.get(firstIndex)
                .setText(String.valueOf(buttonValues[firstIndex]));
        buttons.get(secondIndex)
                .setText(String.valueOf(buttonValues[secondIndex]));
        try {
            thread.sleep(1000 / Integer.parseInt(textSpeedSort.getText()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        buttons.get(firstIndex).setForeground(Color.BLACK);
        buttons.get(secondIndex).setForeground(Color.BLACK);
    }
}
