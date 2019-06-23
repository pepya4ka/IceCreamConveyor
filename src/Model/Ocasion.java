package Model;

import Frame.MainFrame;

/*
    Класс событий
 */
public class Ocasion extends Thread  {
    //Связанный объект машины
    private CreamCart cart;

    //Конструктор класса
    public Ocasion(CreamCart cart) {
        this.cart = cart;
    }

    //Метод запуска
    @Override
    public void run() {
        //Жизненный цикл потока (пока работает конвеер)
        while (cart.isWorking()) {
            try {
                //Генерация события
                generateEvent();
                //Раз в 15-30 секунд
                Thread.sleep((Math.round(Math.random() * 15 + 15)) * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    //Рандомный выбор между двумя вариантами событий
    private void generateEvent() {
        int random = (int) Math.round(Math.random());
        switch (random) {
            case 0:
                shutdownGenerator();
                break;
            case 1:
                emptyIngridients();
                break;
            default:
                break;
        }
    }

    //Отключение генератора
    private void shutdownGenerator() {
        cart.setGeneratorActive(false);
    }

    //Опустошение ингридиентов
    private void emptyIngridients() {
        cart.setEnoughComponents(false);
    }
}
