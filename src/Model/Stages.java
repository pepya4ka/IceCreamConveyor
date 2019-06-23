package Model;

/*
    Класс перечисления состояний
 */
public enum Stages {
    //Состояния
    PASTERIZATION(70, 1, "Этап пастеризации"),
    GOMOGENIZATION(80, 8.5, "Этап гомогенизации"),
    COOLING(5, 1, "Охлаждение"),
    FREEZING(0, 2, "Заморозка"),
    COMPLETION(0, 1, "Продукт готов");

    // Переменные состояний
    private int temperature;
    private double pressure;
    private String statusMessage;

    //Конструктор элементов событий
    private Stages(int temperature, double pressure, String statusMessage) {
        this.temperature = temperature;
        this.pressure = pressure;
        this.statusMessage = statusMessage;
    }

    //Методы получения приватных переменных извне
    public int getTemperature() {
        return temperature;
    }
    public double getPressure() {
        return pressure;
    }
    public String getStatusMessage() {
        return statusMessage;
    }
}
