import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener { //JPanel-панель для проведения основных действий игры
    // ActionListener - список действий
    private final int SIZE = 320;//размер поля
    private final int DOT_SIZE = 16;//размер ячейки в пикселях 16*16
    private final int ALL_DOTS = 400;//-количество клеточек(320*320/16*16)
    private Image dot;//-точка
    private Image apple;
    private int appleX;//-положение нахождения яблока по X
    private int appleY;//-положение нахождения яблока по Y
    private int[] snakeX = new int[ALL_DOTS];//массив для хранения позиции нахождения змейки по X
    private int[] snakeY = new int[ALL_DOTS];//массив для хранения позиции нахождения змейки по Y
    private int dots;//-размер змейки в данный момент
    private Timer timer;
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = true;//-в игре?

    public GameField() {
        setBackground(Color.black);//-цвет игрового поля
        loadImages();
        initGame();
        addKeyListener(new FieldKeyListener());//-вызываем метод, который добавит обработчик событий
        setFocusable(true);//-вызываем метод для фокусировки клавиш на игровом поле
    }

    public void initGame() {//-метод инициализации начала игры
        dots = 3;
        for (int i = 0; i < dots; i++) {
            snakeX[i] = 48 - i * DOT_SIZE;//48/16=3-т.е размер начальной змеи
            snakeY[i] = 48;
        }
        timer = new Timer(250, this);
        timer.start();
        createApple();
    }

    private void createApple() {
        appleX = new Random().nextInt(20) * DOT_SIZE;
        appleY = new Random().nextInt(20) * DOT_SIZE;
    }

    public void loadImages() {//-загружаем картинки
        ImageIcon iia = new ImageIcon("apple.png");
        apple = iia.getImage();
        ImageIcon iid = new ImageIcon("dot.png");
        dot = iid.getImage();
    }

    private void move() {
        for (int i = dots; i > 0; i--) {
            //перемещаем тело змеи
            snakeX[i] = snakeX[i - 1];
            snakeY[i] = snakeY[i - 1];
        }
        if (left) {
            snakeX[0] -= DOT_SIZE;
        }
        if (right) {
            snakeX[0] += DOT_SIZE;
        }
        if (up) {
            snakeY[0] -= DOT_SIZE;
        }
        if (down) {
            snakeY[0] += DOT_SIZE;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {//-выполненное действие
        if (inGame) {
            checkApple(); // съели яблоко?
            checkCollisions();// проверка: не врезались мы?
            move();
        }
        repaint();//- метод, который есть в каждом J-компоненте =>JPanel
        //стандартный метод для перерисовки окна
    }

    private void checkCollisions() { // проверка: не врезались мы?
        for (int i = dots; i > 0; i--) {
            if (i > 4 && snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i]) {
                inGame = false;
            }
        }
        if (snakeX[0] > SIZE) {
            inGame = false;
        }
        if (snakeX[0] < 0) {
            inGame = false;
        }
        if (snakeY[0] > SIZE) {
            inGame = false;
        }
        if (snakeY[0] < 0) {
            inGame = false;
        }
    }

    private void checkApple() {// съели яблоко?
        if (snakeX[0] == appleX && snakeY[0] == appleY) {
            dots++;
            createApple();
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);//-происходит "техническая" перерисовка
        //перерисовываем то, что касается нашей игры
        if (inGame) {
            g.drawImage(apple, appleX, appleY, this);//this-кто перерисовывает(кто отвечает за картинку)
            for (int i = 0; i < dots; i++) {
                g.drawImage(dot, snakeX[i], snakeY[i], this);
            }
        } else {
            String str = "GAME OVER";//-создали текст завершения
            //Font font = new Font("Arial",32,Font.BOLD);//-создали шрифт
            g.setColor(Color.WHITE);//-цвет отрисовки
            //g.setFont(font);// -задали наш шрифт
            g.drawString(str, 125,SIZE/2);// выводим строку в координатах
        }
    }

    //TODO создаем класс обработки нажатия клавиш
    class FieldKeyListener extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) { // переопределяем метод нажатия клавиш
            super.keyPressed(e);
            int key = e.getKeyCode();//-получаем код нажатой клавиши
            if (key == KeyEvent.VK_LEFT && ! right){
                left = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_RIGHT && ! left){
                right = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_UP && ! down){
                up = true;
                left = false;
                right = false;

            }
            if (key == KeyEvent.VK_DOWN && ! up){
                down = true;
                left = false;
                right = false;

            }
        }
    }
}
