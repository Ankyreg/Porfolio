import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class GuiMatrix {
    JFrame frame;

    public int[][] createMatrix() {
        int[][] matrix = new int[5][5];
        for (int i = 0; i < matrix.length; i++) {
            Arrays.fill(matrix[i], i + 1);
        }
        return matrix;
    }

    public void draw() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MyPanel myPanel = new MyPanel(createMatrix());
        frame.setTitle("Matrix Rotate");
        frame.getContentPane().add(myPanel);
        frame.pack();
        frame.setVisible(true);

    }

    static class MyPanel extends JPanel {
        int[][] matrix;
        int size;

        public MyPanel(int[][] matrix) {
            this.matrix = matrix;
            this.size = matrix.length;
            setPreferredSize(new Dimension(size * 30, size * 30));
            setBackground(Color.WHITE);

            Timer timer = new Timer(800, e -> {
                flipMatrix();
                repaint();
            });
            timer.start();
        }
        private void flipMatrix() {
            for (int i = 0; i < (size / 2); i++) {
                for (int j = i; j < size - i - 1; j++) {
                    int temp = matrix[i][j];
                    matrix[i][j] = matrix[size - j - 1][i];
                    matrix[size - j - 1][i] = matrix[size - i - 1][size - j - 1];
                    matrix[size - i - 1][size - j - 1] = matrix[j][size - i - 1];
                    matrix[j][size - i - 1] = temp;
                }
            }
        }

        public void paintComponent (Graphics g){
            super.paintComponent(g);
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    g.drawString(Integer.toString(matrix[i][j]),j * 20 + 20,i * 20 + 20);
                }
            }
        }
    }

}
