package Frame;

import Model.CreamCart;

import javax.swing.*;
import java.util.Map;

public class Manager extends Thread {
    private Map<String, JComponent> elems;
    private CreamCart cart;

    public Manager(Map<String, JComponent> elems, CreamCart cart) {
        this.elems = elems;
        this.cart = cart;
    }

    @Override
    public void run() {

        // Получение текстовых окон из структуры данных
        JTextArea temperature = (JTextArea) elems.get("temperature");
        temperature.setEditable(true);
        JTextArea pressure = (JTextArea) elems.get("pressure");
        JTextArea status = (JTextArea) elems.get("status");
        JTextArea tips = (JTextArea) elems.get("tips");
        JTextArea portions = (JTextArea) elems.get("portions");

        //Работает, пока конвеер запущен
        while (cart.isWorking()) {
            //Обновление компонентов
            temperature.setText(String.format("%d C", cart.getTemperature()));
            pressure.setText(String.format("%.1f мПа", cart.getPressure()));
            status.setText(cart.getStatus());
            tips.setText(cart.getTip());
            portions.setText(String.format("%d штук", cart.getPortions()));
        }
    }
}
