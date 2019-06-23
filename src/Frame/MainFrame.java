package Frame;

import Model.CreamCart;
import Model.Ocasion;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/*
    Класс, отвечающий за настройку окна приложения
 */
public class MainFrame {
    // Переменные класса
    private Font font = new Font("Impact", Font.BOLD, 32);
    private int width = 1400;
    private int height = 700;
    private JFrame screen = new JFrame() {};
    private JPanel infoPanel = new JPanel();
    private JPanel inputPanel = new JPanel();
    private JPanel msgPanel = new JPanel();

    // Структуры, хранящие пары Ключ:Значение (информационные компоненты)
    // и управленческие
    private Map<String, JComponent> data = new HashMap<>();
    private Map<String, JComponent> buttons = new HashMap<>();

    //Базовый конструктор класса
    public MainFrame() {}

    //Конструктор с кастомными базовыми значениями высоты и длины окна
    public MainFrame(int width, int height) {
        this.width = width;
        this.height = height;
    }

    //Основная настройка окна
    public void setupScreen(CreamCart cart, Ocasion ocasion) {
        screen.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        screen.setBounds(dimension.width/4, dimension.height/4, width, height);
        screen.setTitle("Производство мороженого");
        screen.setLayout(new GridLayout(2, 2)); //выбор способа размещения элементов

        setupPanel(cart, ocasion);
    }

    //Настройка панелей окна
    private void setupPanel(CreamCart cart, Ocasion ocasion) {

        //настройка информационной панели
        infoPanel.setLayout(new GridLayout(3, 2)); //выбор способа размещения элементов
        infoPanel.setName("info_panel");

        screen.add(infoPanel);  //добавление панели на основной экран

        //Создание, добавление на панель и добавление в структуру элементов
        data.put("temperature", setupInfoComponent(infoPanel,"Температура", "0"));
        data.put("pressure", setupInfoComponent(infoPanel,"Давление", "0"));
        data.put("status", setupInfoComponent(infoPanel,"Состояние", "-"));


        //настройка панели кнопок
        inputPanel.setLayout(new GridLayout(3, 2)); //выбор способа размещения элементов
        inputPanel.setName("input_panel");

        screen.add(inputPanel);  //добавление панели на основной экран

        //Создание, добавление на панель и добавление в структуру элементов
        buttons.put("heat_up", setupInputComponent(inputPanel, "Греть", new Actions.TemperatureUp(cart)));
        buttons.put("cool_down", setupInputComponent(inputPanel, "Охлаждать", new Actions.TemperatureDown(cart)));
        buttons.put("press_up", setupInputComponent(inputPanel, "Повысить давление", new Actions.PressureUp(cart)));
        buttons.put("press_down", setupInputComponent(inputPanel, "Понизить давление", new Actions.PressureDown(cart)));
//        buttons.put("run_gen", setupInputComponent(inputPanel, "Запустить генератор", new Actions.RebootGenerator(cart)));
        buttons.put("add", setupInputComponent(inputPanel, "Засыпать ингридиенты", new Actions.AddIngridients(cart)));


        //настройка дополнительной панели сообщений
        msgPanel.setLayout(new FlowLayout()); //выбор способа размещения элементов
        screen.add(msgPanel);  //добавление панели на основной экран

        //Создание, добавление на панель и добавление в структуру элементов
        data.put("tips", setupInfoComponent(msgPanel, "Советы: ", "Все готово к работе"));
        data.put("portions", setupInfoComponent(msgPanel, "Готово штук: ", "0"));
        buttons.put("start", setupInputComponent(msgPanel, "Начать работу", new Actions.StartAction(cart, ocasion)));

        //отрисовка окна
        screen.setVisible(true);
    }

    // Метод, создающий информационные компоненты, и возвращающий их
    private JTextArea setupInfoComponent(JPanel panel, String labelText, String defaultText) {
        JLabel label = new JLabel(labelText + ": ");
        label.setFont(font);
        JTextArea text = new JTextArea();
        label.setLabelFor(text);
        text.setFont(font);
        text.setText(defaultText);
        panel.add(label);
        panel.add(text);
        return text;
    }

    // Метод, создающий кнопки, и возвращающий их
    private JButton setupInputComponent(JPanel panel, String buttonText, Actions.BaseAction action) {
        JButton button = new JButton(buttonText);
        button.setFont(font);
        button.addActionListener(action);
        panel.add(button);
        return button;
    }

    //Методы получения приватных переменных извне
    public Map<String, JComponent> getData() {
        return data;
    }
    public Map<String, JComponent> getButtons() {
        return buttons;
    }
}
