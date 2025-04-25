// Tic Tac Toe is fun! ^0^

package TicTacToe.src;
import java.awt.*;
import java.awt.event.*;

public class TicTacToe extends Frame implements ActionListener {
    private Button[][] buttons = new Button[3][3];
    private Label statusLabel = new Label("Player X's Turn");
    private Button resetButton = new Button("New Game");
    private char currentPlayer = 'X';

    public TicTacToe() {
        setTitle("Tic Tac Toe");
        setSize(400, 450);
        setLayout(new BorderLayout());

            Panel gamePanel = new Panel();
            gamePanel.setLayout(new GridLayout(3, 3));
            Font font = new Font("Arial", Font.BOLD, 60);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new Button("");
                buttons[i][j].setFont(font);
                buttons[i][j].addActionListener(this);
                gamePanel.add(buttons[i][j]);
            }
        }

        resetButton.addActionListener(e -> resetGame());

        Panel bottomPanel = new Panel(new BorderLayout());
        bottomPanel.add(statusLabel, BorderLayout.CENTER);
        bottomPanel.add(resetButton, BorderLayout.EAST);

        add(gamePanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
        Button clicked = (Button) e.getSource();
        if (!clicked.getLabel().equals("")) return;

        clicked.setLabel(String.valueOf(currentPlayer));
        if (checkWinner()) {
            statusLabel.setText("Player " + currentPlayer + " Wins!");
            disableAllButtons();
            showEndDialog("Player " + currentPlayer + " Wins!");
        } else if (isDraw()) {
            statusLabel.setText("It's a Draw!");
            showEndDialog("It's a Draw!");
        } else {
            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
            statusLabel.setText("Player " + currentPlayer + "'s Turn");
        }
    }

    private boolean checkWinner() {
        String p = String.valueOf(currentPlayer);
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getLabel().equals(p) &&
                buttons[i][1].getLabel().equals(p) &&
                buttons[i][2].getLabel().equals(p)) return true;

            if (buttons[0][i].getLabel().equals(p) &&
                buttons[1][i].getLabel().equals(p) &&
                buttons[2][i].getLabel().equals(p)) return true;
        }

        if (buttons[0][0].getLabel().equals(p) &&
            buttons[1][1].getLabel().equals(p) &&
            buttons[2][2].getLabel().equals(p)) return true;

        if (buttons[0][2].getLabel().equals(p) &&
            buttons[1][1].getLabel().equals(p) &&
            buttons[2][0].getLabel().equals(p)) return true;

        return false;
    }

    private boolean isDraw() {
        for (Button[] row : buttons)
            for (Button b : row)
                if (b.getLabel().equals("")) return false;
        return true;
    }

    private void disableAllButtons() {
        for (Button[] row : buttons)
            for (Button b : row)
                b.setEnabled(false);
    }

    private void resetGame() {
        for (Button[] row : buttons)
            for (Button b : row) {
                b.setLabel("");
                b.setEnabled(true);
            }
        currentPlayer = 'X';
        statusLabel.setText("Player X's Turn");
    }

    private void showEndDialog(String message) {
        Dialog dialog = new Dialog(this, "Game Over", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(250, 150);
        dialog.setLocationRelativeTo(this);

        Label msgLabel = new Label(message, Label.CENTER);
        msgLabel.setFont(new Font("Arial", Font.BOLD, 16));

        Button dialogNewGameBtn = new Button("New Game");
        dialogNewGameBtn.addActionListener(e -> {
            resetGame();
            dialog.dispose();
        });

        dialog.add(msgLabel, BorderLayout.CENTER);
        dialog.add(dialogNewGameBtn, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        new TicTacToe();
    }
}
