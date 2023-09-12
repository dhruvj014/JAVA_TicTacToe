import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class TicTacToe implements ActionListener{

    int p1wins=0;
    int p2wins=0;
    String x;
    boolean tabs = true;
    Random random = new Random();
    JFrame frame = new JFrame();
    JPanel scorer = new JPanel();
    JPanel main_title_panel = new JPanel();
    JLabel main_textfield = new JLabel();
    JPanel title_panel = new JPanel();
    JPanel button_panel = new JPanel();
    JLabel textfield = new JLabel();
    JButton restart = new JButton();
    JButton score = new JButton();
    JButton[] buttons = new JButton[9];
    boolean player1_turn;

    DefaultTableModel model = new DefaultTableModel(
            new Object[][] {
                    {"X Wins","O Wins"},
                    {p1wins,p2wins}
            },
            new String[] {"Player 1", "Player 2"}
    );

    TicTacToe(){

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,800);
        frame.getContentPane().setBackground(new Color(50,50,50));
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);

        main_textfield.setBackground(new Color(25,25,25));
        main_textfield.setForeground(new Color(25,255,0));
        main_textfield.setFont(new Font("Ink Free",Font.BOLD,75));
        main_textfield.setHorizontalAlignment(JLabel.CENTER);
        main_textfield.setText("Tic-Tac-Toe");
        main_textfield.setOpaque(true);
        
        textfield.setBackground(new Color(25,25,25));
        textfield.setForeground(new Color(25,255,0));
        textfield.setFont(new Font("Ink Free",Font.BOLD,75));
        textfield.setHorizontalAlignment(JLabel.CENTER);
        textfield.setOpaque(true);

        score.addActionListener(this);
        score.setBackground(new Color(25,25,25));
        score.setText("S");
        score.setForeground(Color.WHITE);
        score.setBounds(700,0,50,100);
        
        scorer.setLayout(new BorderLayout());
        scorer.setBounds(0,100,800,700);
        scorer.setBackground(new Color(25,25,25));

        JTable table = new JTable(model);
        table.setBounds(200,200,400,400);
        table.setRowHeight(30);
        table.setShowGrid(true);
        table.setGridColor(Color.BLACK);
        table.setSelectionBackground(Color.BLUE);
        table.setSelectionForeground(Color.WHITE);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);

        scorer.add(table);

        main_title_panel.setLayout(new BorderLayout());
        main_title_panel.setBounds(0,0,800,100);
        main_title_panel.setBackground(new Color(25,25,25));

        title_panel.setLayout(new BorderLayout());
        title_panel.setBounds(0,100,800,100);
        title_panel.setBackground(new Color(25,25,25));

        textfield.setBounds(0,0,600,100);

        button_panel.setLayout(new GridLayout(3,3));
        button_panel.setBackground(new Color(150,100,150));

        restart.addActionListener(this);
        restart.setBackground(new Color(25,25,25));
        restart.setText("R");
        restart.setForeground(Color.WHITE);
        restart.setBounds(0,0,100,100);

        for(int i=0;i<9;i++){
            buttons[i]=new JButton();
            button_panel.add(buttons[i]);
            buttons[i].setBackground(new Color(211,211,211));
            buttons[i].setFont(new Font("MV Boli",Font.BOLD,120));
            buttons[i].setFocusable(false);
            buttons[i].addActionListener(this);
        }

        JPanel combinedTitlePanel = new JPanel();
        combinedTitlePanel.setLayout(new GridLayout(2, 1)); // Two rows, one column
        frame.add(combinedTitlePanel, BorderLayout.NORTH);

        main_title_panel.add(main_textfield);
        title_panel.add(textfield, BorderLayout.WEST);
        title_panel.add(score,BorderLayout.EAST);
        title_panel.add(restart,BorderLayout.EAST);

        combinedTitlePanel.add(main_title_panel);
        combinedTitlePanel.add(title_panel);


        frame.add(button_panel);

        firstTurn();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int i = 0;
        for (i = 0; i < 9; i++) {
            if (e.getSource() == buttons[i]) {
                if (player1_turn) {
                    if (buttons[i].getText().equals("")) {
                        buttons[i].setForeground(new Color(255, 0, 0));
                        buttons[i].setText("X");
                        player1_turn = !player1_turn;
                        textfield.setText("O Turn");
                    } else {
                        textfield.setText("Tile is Occupied.");
                    }
                } else {
                    if (buttons[i].getText().equals("")) {
                        buttons[i].setForeground(new Color(0, 255, 0));
                        buttons[i].setText("O");
                        player1_turn = !player1_turn;
                        textfield.setText("X Turn");
                    } else {
                        textfield.setText("Tile is Occupied.");
                    }
                }
            }
        }
        check();

        if (e.getSource() == restart) {
            for (int j = 0; j < 9; j++) {
                buttons[j].setText("");
                buttons[j].setBackground(new Color(211, 211, 211));
                buttons[j].setEnabled(true);
            }
            firstTurn();
        }

        if(e.getSource()==score){
            if(tabs) {
                x=textfield.getText();
                textfield.setText("ScoreCard");
                frame.remove(button_panel);
                frame.revalidate();
                frame.repaint();
                frame.add(scorer);
                frame.revalidate();
                frame.repaint();
                tabs = false;
            }
            else{
                textfield.setText(x);
                frame.remove(scorer);
                frame.revalidate();
                frame.repaint();
                frame.add(button_panel);
                frame.revalidate();
                frame.repaint();
                tabs = true;
            }

        }
    }

        public void firstTurn(){
        if(random.nextInt(2)==0){
            player1_turn=true;
            textfield.setText("X Turn");
        }
        else{
            player1_turn=false;
            textfield.setText("O Turn");
        }
    }

    public void check(){
        //Winning at once condition :
        if(buttons[0].getText()==buttons[1].getText() && buttons[1].getText()==buttons[2].getText() && buttons[2].getText()!=""){
            if(buttons[2].getText()=="X"){
                xWins(0,1,2);
            }
            else{
                oWins(0,1,2);
            }
        }
        else if(buttons[0].getText()==buttons[4].getText() && buttons[4].getText()==buttons[8].getText() && buttons[8].getText()!=""){
            if(buttons[8].getText()=="X"){
                xWins(0,4,8);
            }
            else{
                oWins(0,4,8);
            }
        }
        else if(buttons[0].getText()==buttons[3].getText() && buttons[3].getText()==buttons[6].getText() && buttons[6].getText()!=""){
            if(buttons[6].getText()=="X"){
                xWins(0,3,6);
            }
            else{
                oWins(0,3,6);
            }
        }
        else if(buttons[8].getText()==buttons[2].getText() && buttons[2].getText()==buttons[5].getText() && buttons[5].getText()!=""){
            if(buttons[5].getText()=="X"){
                xWins(8,2,5);
            }
            else{
                oWins(8,2,5);
            }
        }
        else if(buttons[8].getText()==buttons[7].getText() && buttons[7].getText()==buttons[6].getText() && buttons[6].getText()!=""){
            if(buttons[6].getText()=="X"){
                xWins(8,7,6);
            }
            else{
                oWins(8,7,6);
            }
        }
        else if(buttons[4].getText()==buttons[2].getText() && buttons[2].getText()==buttons[6].getText() && buttons[6].getText()!=""){
            if(buttons[6].getText()=="X"){
                xWins(4,2,6);
            }
            else{
                oWins(4,2,6);
            }
        }
        else if(buttons[3].getText()==buttons[4].getText() && buttons[4].getText()==buttons[5].getText() && buttons[5].getText()!=""){
            if(buttons[5].getText()=="X"){
                xWins(3,4,5);
            }
            else{
                oWins(3,4,5);
            }
        }
        else if(buttons[1].getText()==buttons[4].getText() && buttons[4].getText()==buttons[7].getText() && buttons[7].getText()!=""){
            if(buttons[7].getText()=="X"){
                xWins(1,4,7);
            }
            else{
                oWins(1,4,7);
            }
        }
        int ctr=0;
        for(int i=0;i<9;i++){
            if(buttons[i].getText()==""){
                break;
            }
            ctr++;
        }
        if(ctr==9){
            Stalemate();
        }
    }

    public void xWins(int a, int b, int c){
        buttons[a].setBackground(new Color(0,255,0));
        buttons[b].setBackground(new Color(0,255,0));
        buttons[c].setBackground(new Color(0,255,0));
        for(int i=0;i<9;i++){
            buttons[i].setEnabled(false);
        }
        p1wins++;
        model.setValueAt(p1wins/2,1,0);
        textfield.setText("X Wins!!");
    }

    public void oWins(int a, int b, int c){
        buttons[a].setBackground(new Color(0,255,0));
        buttons[b].setBackground(new Color(0,255,0));
        buttons[c].setBackground(new Color(0,255,0));
        for(int i=0;i<9;i++){
            buttons[i].setEnabled(false);
        }
        p2wins++;
        model.setValueAt(p2wins/2,1,1);
        textfield.setText("O Wins!!");
    }

    //Stalemate when all boxes are filled but there's no pattern
    public void Stalemate(){
        for(int i=0;i<9;i++){
            buttons[i].setEnabled(false);
        }
        textfield.setText("Stalemate :( ");
    }
}