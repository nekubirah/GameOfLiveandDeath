import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameOfLife extends JFrame {
  // Größe des Fensters
  private static final int WIDTH = 500;
  private static final int HEIGHT = 500;

  // Größe der Zellen
  private static final int CELL_SIZE = 10;

  // Anzahl der Zellen in x- und y-Richtung
  private static final int GRID_WIDTH = WIDTH / CELL_SIZE;
  private static final int GRID_HEIGHT = HEIGHT / CELL_SIZE;

  // Zustand der Zellen (lebendig oder tot)
  private boolean[][] cells;

  public GameOfLife() {
    // Initialisierung des Fensters
    setTitle("Game of Life");
    setSize(WIDTH, HEIGHT);
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    // Initialisierung des Zellen-Arrays
    cells = new boolean[GRID_WIDTH][GRID_HEIGHT];

    // Erstelle Button zum Starten und Stoppen des Spiels
    JButton startButton = new JButton("Start");
    startButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        // Starte das Spiel
        start();
      }
    });
    JButton stopButton = new JButton("Stop");
    stopButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        // Stoppe das Spiel
        stop();
      }
    });

    // Füge Buttons dem Fenster hinzu
    JPanel buttonPanel = new JPanel();
    buttonPanel.add(startButton);
    buttonPanel.add(stopButton);
    add(buttonPanel, BorderLayout.SOUTH);

        // Zeichne das Spielfeld
        JPanel gridPanel = new JPanel() {
          public void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawGrid(g);
          }
          
        };
        
        add(gridPanel, BorderLayout.CENTER);
        
        gridPanel.addMouseListener(new MouseAdapter() {
          public void mousePressed(MouseEvent e) {
            // Berechne die Koordinaten der Zelle, die angeklickt wurde
            int x = e.getX() / CELL_SIZE;
            int y = e.getY() / CELL_SIZE;
            // Setze den Zustand der Zelle auf "lebendig"
            if(cells[x][y]){
              cells[x][y] = false;
            }
            else{
            cells[x][y] = true;}
            // Repainte das Spielfeld
            repaint();
          }
        });
      }
    
      // Methode zum Starten des Spiels

      Timer timer;
      public void start() {
        // Erstelle einen Timer, der alle 100 Millisekunden ausgeführt wird
        timer = new Timer(200, new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            // Berechne den nächsten Zustand der Zellen
            updateCells();
            // Repainte das Spielfeld
            repaint();
          }
        });
        timer.start();
      }
    
      // Methode zum Stoppen des Spiels
      public void stop() {

        timer.stop();
      }
    
      
    
      // Methode zum Aktualisieren des Zellen-Arrays
      private void updateCells() {
        // Kopiere den aktuellen Zustand der Zellen in ein neues Array
        boolean[][] newCells = new boolean[GRID_WIDTH][GRID_HEIGHT];
        for (int x = 0; x < GRID_WIDTH; x++) {
          for (int y = 0; y < GRID_HEIGHT; y++) {
            newCells[x][y] = cells[x][y];
          }
        }
    
        // Anwenden der Regeln auf jede Zelle
        for (int x = 0; x < GRID_WIDTH; x++) {
          for (int y = 0; y < GRID_HEIGHT; y++) {
            int liveNeighbors = countLiveNeighbors(x, y);
            if (cells[x][y]) {
              // Regel 1 oder 2
              if (liveNeighbors < 2 || liveNeighbors > 3) {
                newCells[x][y] = false;
              }
            } else {
              // Regel 3
              if (liveNeighbors == 3) {
                newCells[x][y]= true;        }
              }
            }
        
            // Setze das neue Array als aktuelles Array
            }cells = newCells;
  }

  // Zähle die Anzahl der lebendigen Nachbarzellen einer Zelle
  private int countLiveNeighbors(int x, int y) {
    int liveNeighbors = 0;
    for (int dx = -1; dx <= 1; dx++) {
      for (int dy = -1; dy <= 1; dy++) {
        if (dx == 0 && dy == 0) {
          continue;
        }
        int nx = x + dx;
        int ny = y + dy;
        if (nx >= 0 && nx < GRID_WIDTH && ny >= 0 && ny < GRID_HEIGHT) {
          if (cells[nx][ny]) {
            liveNeighbors++;
          }
        }
      }
    }
    return liveNeighbors;
  }

  // Methode zum Zeichnen des Spielfelds
  private void drawGrid(Graphics g) {
    for (int x = 0; x < GRID_WIDTH; x++) {
      for (int y = 0; y < GRID_HEIGHT; y++) {
        if (cells[x][y]) {
          // Zeichne lebende Zelle
          g.setColor(Color.BLACK);
          g.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        } else {
          // Zeichne tote Zelle
          g.setColor(Color.WHITE);
          g.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }
      }
    }
  }

  public static void main(String[] args) {
    GameOfLife game = new GameOfLife();
    game.setVisible(true);
  }
}
        
