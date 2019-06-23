package Frame;

import Model.CreamCart;
import Model.Ocasion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

/*
    Класс - хранилище действий, привязываемых к кнопкам.
 */
public class Actions {

    /*
        Абстрактный класс - родительский для всех действий
        Содержит 2 конструктора:
            С конвеером мороженого, для всех основных действий
            С конвеером мороженого и объектом событий, для действия,
                запускающего потоки (и завершающего).

        Все остальные классы отнаследованы от данного.
     */
    public static abstract class BaseAction extends AbstractAction {
        //Переменные так же наследуются (доступны в классах - наследниках)
        protected CreamCart cart;
        protected Ocasion ocasion;

        //Конструктор с ковеером и событиями
        public BaseAction(CreamCart cart, Ocasion ocasion) {
            this.cart = cart;
            this.ocasion = ocasion;
        }

        //Конструктор с ковеером
        public BaseAction(CreamCart cart) {
            this.cart = cart;
        }

        //Абстрактный метод, реализованный в классах - наследниках
        public abstract void actionPerformed(ActionEvent e);
    }

    //Класс действия запускающий потоки
    public static class StartAction extends BaseAction {
        //Конструктор класса
        public StartAction(CreamCart cart, Ocasion ocasion) {
            //Вызов конструктора родителя
            super(cart, ocasion);
        }

        //Обработчик события
        @Override
        public void actionPerformed(ActionEvent e) {
            //Если потоки ещё не были запущены, то стартует их
            if (!cart.isAlive()) {
                cart.start();
                ocasion.start();
                JButton button = (JButton) e.getSource();
                button.setText("Завершить работу");
            //Иначе завершает работу приложения
            } else {
                cart.setWorking(false);
                JButton button = (JButton) e.getSource();
                button.getTopLevelAncestor().dispatchEvent(new WindowEvent((Window) button.getTopLevelAncestor(),
                        WindowEvent.WINDOW_CLOSING));
            }
        }
    }

    //Класс действия, повышающего значение температуры
    public static class TemperatureUp extends BaseAction {
        //Конструктор класса
        public TemperatureUp(CreamCart cart) {
            //Вызов конструктора родителя
            super(cart);
        }

        //Обработчик события
        @Override
        public void actionPerformed(ActionEvent e) {
            if (cart.isAlive() & cart.getTemperature() < 100) {
                cart.setTemperature(cart.getTemperature() + 5);
            }
        }
    }


    //Класс действия, понижающего значение температуры
    public static class TemperatureDown extends BaseAction {
        //Конструктор класса
        public TemperatureDown(CreamCart cart) {
            //Вызов конструктора родителя
            super(cart);
        }

        //Обработчик события
        @Override
        public void actionPerformed(ActionEvent e) {
            if (cart.isAlive() & cart.getTemperature() > 0) {
                cart.setTemperature(cart.getTemperature() - 5);
            }
        }
    }


    //Класс действия, повышающего значение давления
    public static class PressureUp extends BaseAction {
        //Конструктор класса
        public PressureUp(CreamCart cart) {
            //Вызов конструктора родителя
            super(cart);
        }

        //Обработчик события
        @Override
        public void actionPerformed(ActionEvent e) {
            if (cart.isAlive() & cart.getPressure() < 10) {
                cart.setPressure(cart.getPressure() + 0.5);
            }
        }
    }


    //Класс действия, понижающего значение давления
    public static class PressureDown extends BaseAction {
        //Конструктор класса
        public PressureDown(CreamCart cart) {
            //Вызов конструктора родителя
            super(cart);
        }

        //Обработчик события
        @Override
        public void actionPerformed(ActionEvent e) {
            if (cart.isAlive() & cart.getPressure() > 0) {
                cart.setPressure(cart.getPressure() - 0.5);
            }
        }
    }


    //Класс действия, запускаюжего генератор
    public static class RebootGenerator extends BaseAction {
        //Конструктор класса
        public RebootGenerator(CreamCart cart) {
            //Вызов конструктора родителя
            super(cart);
        }

        //Обработчик события
        @Override
        public void actionPerformed(ActionEvent e) {
            if (cart.isAlive()) {
                cart.setGeneratorActive(true);
            }
        }
    }


    //Класс действия, добавляющего ингридиенты
    public static class AddIngridients extends BaseAction {
        //Конструктор класса
        public AddIngridients(CreamCart cart) {
            //Вызов конструктора родителя
            super(cart);
        }

        //Обработчик события
        @Override
        public void actionPerformed(ActionEvent e) {
            if (cart.isAlive()) {
                cart.setEnoughComponents(true);
            }
        }
    }
}
