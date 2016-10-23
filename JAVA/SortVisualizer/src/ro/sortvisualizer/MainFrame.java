package ro.sortvisualizer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

import ro.sortvisualizer.algos.BubbleSort;
import ro.sortvisualizer.algos.InsertionSort;

/**
 * Created by dimitrie on 27.04.2015.
 */
public class MainFrame extends JFrame implements ActionListener{

    //Graphics stuff
    private  GridBagLayout gridBagLayout;
    private GridBagConstraints gridBagConstraints;
    private Label labelAlgorithmComboBox, labelArraySize, labelScale, labelSpeed;

    private TextField arraySizeInput;
    private TextField scaleInput;
    private TextField speedInput;
    private Choice algorithmInput;
    private Button runButton, addButton;

    private java.util.List<SortAlgorithm> algorithms;

    //
    private List<SortThread> threadList = new ArrayList<SortThread>();

    public MainFrame(){
        //Default close operation
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.initializeAlgorithms();
        //Initialize graphic components
        this.initializeGraphicComponents();
    }

    private void initializeAlgorithms(){
        this.algorithms = new ArrayList<SortAlgorithm>();
        Collections.addAll(this.algorithms,
                new BubbleSort(),
                new InsertionSort()
        );
    }
    private void initializeGraphicComponents(){
        this.setTitle("Sort Visualizer");
        //Initialize graphics components
        this.gridBagConstraints = new GridBagConstraints();
        this.gridBagLayout = new GridBagLayout();

        this.setLayout(this.gridBagLayout);
        this.gridBagConstraints.anchor = GridBagConstraints.WEST;
        this.gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        this.gridBagConstraints.ipadx = 0;
        this.gridBagConstraints.ipady = 0;
        this.gridBagConstraints.insets = new Insets(4,4,4,4);
        this.gridBagConstraints.weighty = 0;

        // Labels
        this.gridBagConstraints.gridx = 0;
        this.labelAlgorithmComboBox = new Label("Algorithm:");
        this.gridBagConstraints.weightx = 1;
        this.gridBagConstraints.gridy = 0;
        this.gridBagLayout.setConstraints(this.labelAlgorithmComboBox, this.gridBagConstraints);
        add(this.labelAlgorithmComboBox);

        this.labelArraySize = new Label("Array size:");
        this.gridBagConstraints.gridy = 1;
        this.gridBagLayout.setConstraints(this.labelArraySize, this.gridBagConstraints);
        add(this.labelArraySize);

        this.labelScale = new Label("Scale:");
        this.gridBagConstraints.gridy = 2;
        this.gridBagLayout.setConstraints(this.labelScale, this.gridBagConstraints);
        add(this.labelScale);

        this.labelSpeed = new Label("Speed:");
        this.gridBagConstraints.gridy = 3;
        this.gridBagLayout.setConstraints(this.labelSpeed, this.gridBagConstraints);
        add(this.labelSpeed);


        // Algorithm
        this.gridBagConstraints.gridx = 1;
        this.gridBagConstraints.weightx = 2;

        algorithmInput = new Choice();
        for (SortAlgorithm algo : this.algorithms)
            algorithmInput.add(algo.getName());
        this.gridBagConstraints.gridy = 0;
        this.gridBagLayout.setConstraints(algorithmInput, this.gridBagConstraints);
        add(algorithmInput);


        // Array size
        arraySizeInput = new TextField("30");
        arraySizeInput.addActionListener(this);
        this.gridBagConstraints.gridy = 1;
        this.gridBagLayout.setConstraints(arraySizeInput, this.gridBagConstraints);
        add(arraySizeInput);

        // Scale
        scaleInput = new TextField("12");
        scaleInput.addActionListener(this);
        this.gridBagConstraints.gridy = 2;
        this.gridBagLayout.setConstraints(scaleInput, this.gridBagConstraints);
        add(scaleInput);

        // Speed
        speedInput = new TextField("10");
        speedInput.addActionListener(this);
        this.gridBagConstraints.gridy = 3;
        this.gridBagLayout.setConstraints(speedInput, this.gridBagConstraints);
        add(speedInput);

        // Run button
        this.runButton = new Button("Run");
        this.runButton.addActionListener(this);
        this.gridBagConstraints.anchor = GridBagConstraints.NORTH;
        this.gridBagConstraints.fill = GridBagConstraints.NONE;
        this.gridBagConstraints.gridx = 0;
        this.gridBagConstraints.gridy = 4;
        this.gridBagConstraints.gridwidth = 2;
        this.gridBagConstraints.weighty = 1;
        this.gridBagLayout.setConstraints(this.runButton, this.gridBagConstraints);
        add(this.runButton);

        this.addButton = new Button("Add");
        this.addButton.addActionListener(this);
        this.gridBagConstraints.anchor = GridBagConstraints.NORTH;
        this.gridBagConstraints.fill = GridBagConstraints.NONE;
        this.gridBagConstraints.gridx = 1;
        this.gridBagConstraints.gridy = 4;
        this.gridBagConstraints.gridwidth = 2;
        this.gridBagConstraints.weighty = 1;
        this.gridBagLayout.setConstraints(this.addButton, this.gridBagConstraints);
        add(this.addButton);
        
        //Pack and set visible
        this.pack();
        this.setVisible(true);

    }

    public void actionPerformed(ActionEvent ev) {

        if(ev.getSource() == this.runButton) {
            for(SortThread currentThread: this.threadList){
                System.out.println("Start thread : " +  currentThread.getName());
                currentThread.start();
            }
        }
        else if(ev.getSource() == this.addButton){
            try {
                int size = Integer.parseInt(arraySizeInput.getText());
                int scale = Integer.parseInt(scaleInput.getText());
                double speed = Double.parseDouble(speedInput.getText());
                if (size <= 0 || scale <= 0 || speed <= 0 || Double.isInfinite(speed))
                    return;

                VisualSortArray array = new VisualSortArray(size, scale, speed);
                SortAlgorithm algorithm = algorithms.get(algorithmInput.getSelectedIndex());
                this.threadList.add(new SortThread(array, algorithm));
              //  new SortThread(array, algorithm).start();
                System.out.println("Thread for : " + algorithm.getName() + " created");
            } catch (NumberFormatException e) {}
        }else{
            System.err.println("What source ?");
        }
    }
}
