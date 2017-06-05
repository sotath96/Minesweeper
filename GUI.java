package minesweeper3;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import javax.swing.*;

/**
 *
 * @author Sot
 */
public class GUI extends JFrame {

    private JPanel mainPanel = new JPanel();
    private JPanel panel = new JPanel();
    private JPanel panel2 = new JPanel();
    private JButton squares[][];
    private Minefield m;
    private JMenuBar menubar = new JMenuBar();
    private JMenu menu1 = new JMenu("Options");
    private JMenuItem item1 = new JMenuItem("NewGame");
    private JMenuItem item2 = new JMenuItem("Difficulty");
    private JMenuItem item3 = new JMenuItem("Help");
    private int row;
    private int column;
    private int mines;
    private JFrame insert;
    private JPanel ipanel = new JPanel();
    private JLabel enterRow = new JLabel("insert the number of rows");
    private JTextField rows= new JTextField();
    private JLabel enterColumn = new JLabel("insert the number of columns");
    private JTextField columns= new JTextField();
    private JLabel enterMines = new JLabel("insert the number of mines");
    private JTextField mine= new JTextField();
    private JButton ok = new JButton("click");
    
    
    
    /**
     * This is the constructor of GUI class.
     * @param row
     * @param column
     * @param mines 
     */
    public GUI(int row, int column, int mines) {
        super("Minesweeper");
      
        squares = new JButton[row][column];
        m = new Minefield(row,column);
        m.populate(mines);
        createWindow();
       

    }
/**
 * This method is responsible for the construction of the graphical user interface
 * as it connects all the components
 */
    private void createWindow() {
        item1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                GUI g = new GUI(10,15,20);
               
            }
        });
        item2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insert = new JFrame();
                ipanel.setLayout(new GridLayout(4,2));
                insert.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                ipanel.add(enterRow);
                ipanel.add(rows);
                ipanel.add(enterColumn);
                ipanel.add(columns);
                ipanel.add(enterMines);
                ipanel.add(mine);
                ipanel.add(ok);
                ok.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                    int r=Integer.parseInt(rows.getText());
                    int c=Integer.parseInt(columns.getText());
                    int mi=Integer.parseInt(mine.getText());
                
                    row=r;
                    column=c;
                    mines=mi;
                    if(row>0 && column>0 && mines<=row*column){
                    insert.dispose();
                    dispose();
                    GUI k = new GUI(row,column,mines);
                    }else{
                        JOptionPane.showMessageDialog(null, "the values you gave are incompatible with the game.\n"+
                                        "Try again");
                    
                                
                    }
                    }
                });
                insert.add(ipanel);
                
                insert.setSize(400,100);
                insert.setVisible(true);
                
                
                
            }
        });
        item3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "This is a Minesweeper game.In order to win you have to flag "
                        + "all the mines correctly." + "the controls of the games are:"
                        + "Left-click an empty square to reveal it.\n" +
"                        Right-click an empty square to flag it."+
                        "If you want to start a New game click at the New Game at Options menu.\n"+
                        "If you want to start a New game with a different level of difficulty(different size of board"+
                        " and different number of mines)click at Difficulty at Options menu.");
            }
        });
        menu1.add(item1);
        menu1.add(item2);
        menu1.add(item3);
        menubar.add(menu1);
        //panel2.add(menubar);
        //panel2.setLayout(new GridLayout(1, 100));
      

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(380, 375);
        for (int i = 0; i < m.getRows(); i++) {
            for (int j = 0; j < m.getColumns(); j++) {
                squares[i][j] = new JButton();
                squares[i][j].setSize(300, 300);
                squares[i][j].setText(m.getMineTile(i, j).toString());
                int x = i;
                int y = j;
                squares[i][j].addMouseListener(new MouseListener() {

                    @Override
                    public void mouseClicked(MouseEvent e) {

                        if (e.getButton() == 3 && m.getMineTile(x, y).isMarked() == true) {
                            squares[x][y].setText(" ");
                            m.getMineTile(x, y).toggleMarked();
                            if (m.areAllMinesFound() == true) {
                                JOptionPane.showMessageDialog(null, "congratulations you flagged all the mines correctly");
                            }
                        } else if (e.getButton() == 3 && m.getMineTile(x, y).isRevealed() == false) {

                            squares[x][y].setText("F");
                            m.mark(x, y);
                            if (m.areAllMinesFound() == true) {
                                JOptionPane.showMessageDialog(null, "Congratulations!!! You flagged all the mines correctly");
                            }
                        } else {
                            boolean r = m.step(x, y);
                            if (!r) {
                                for (int i = 0; i < m.getRows(); i++) {
                                    for (int j = 0; j < m.getColumns(); j++) {
                                        if (m.getMineTile(i, j).isMined()) {
                                            squares[i][j].setText("*");
                                        }
                                    }
                                }

                                JOptionPane.showMessageDialog(null, "Game Over");
                            } else {
                                for (int i = 0; i < m.getRows(); i++) {
                                    for (int j = 0; j < m.getColumns(); j++) {
                                        squares[i][j].setText(m.getMineTile(i, j).toString());
                                        if (m.getMineTile(i, j).isMarked()) {
                                            squares[i][j].setText("F");
                                        }
                                    }

                                }
                            }

                        }
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                    }

                });

                panel.add(squares[i][j]);
                panel.setLayout(new GridLayout(m.getRows(),m.getColumns()));

            }
        }
        setJMenuBar(menubar);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        //mainPanel.add(panel2);
        
        mainPanel.add(panel);

        add(mainPanel);
        this.setVisible(true);
    }

}
