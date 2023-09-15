import javax.swing.*;

public class MainWindow extends JFrame {//JFrame - класс для того что бы сделать наш "окном"
    public MainWindow(){
        setTitle("Змейка");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//-крестик в верхней правой части экрана;прекращение работы
        setSize(320,345);//размер игрового окна;отсчет нуля идет от верхнего левого угла
        setLocation(400,400);
        add(new GameField());
        setVisible(true);//создаем основное окно

    }

    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow();// наш основной класс - от куда наша программа начинает работу
    }
}
