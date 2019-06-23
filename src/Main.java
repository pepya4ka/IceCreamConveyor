import Frame.MainFrame;
import Frame.Manager;
import Model.CreamCart;
import Model.Ocasion;

import javax.swing.*;
import java.util.Map;

// Основной поток приложения
public class Main {
    public static void main(String[] args) {
            //Создание основных объектов
            CreamCart cart = new CreamCart();
            Ocasion ocasion = new Ocasion(cart);

            MainFrame frame = new MainFrame();
            frame.setupScreen(cart, ocasion);
            Manager manager = new Manager(frame.getData(), cart);
            manager.start();
    }
}
