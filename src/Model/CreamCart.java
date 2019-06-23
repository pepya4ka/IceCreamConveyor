package Model;

import javax.swing.*;

public class CreamCart extends Thread  {
    //Переменные класса
    private int temperature = 0;
    private double pressure = 0;
    private boolean generatorActive = false;
    private boolean enoughComponents = false;
    private boolean working = true;
    private String status;
    private Stages stage = Stages.PASTERIZATION;
    private String tip = "Все готово к работе";
    private int portions = 0;
    private int portionsInKg = 0;
    private final int STAGE_CYCLES = 1500;

    //Метод, запускающий поток
    @Override
    public void run() {
        //счётчик прогресса этапа
        int stageCounter = 0;
        //Жизненный цикл потока
        while (working) {
            try {
                //задержка в 10 мс между итерациями (для снижения нагрузки на цп)
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // Проверка на рабочее состояние процесса производства
            if (checkProcess(stageCounter)) {
                stageCounter++;
                // переключение этапа и сброс счётчика
                if (stageCounter >= STAGE_CYCLES) {
                    switchStage();
                    stageCounter = 0;
                }
                // присваивание переменной статуса текущего состояния
                // (для инфопанели)
                status = stage.getStatusMessage();
            }
        }
    }

    //Метод, отвечающий за проверку состояния конвеера
    private boolean checkProcess(int stageCounter) {
        // Проверка на завершение процесса приготовления
        if (stage == Stages.COMPLETION) {
            tip = "Процесс закончен. Добавьте ингридиентов.";
//            JOptionPane.showMessageDialog(null, "Процесс закончен. Добавьте ингридиентов.", "Сообщение", 1);
            if (enoughComponents) {
                switchStage();
                return true;
            }
            return false;
        }
        // Проверка генератора
        if (!generatorActive) {
            tip = "Ошибка питания. Перезапустите генератор.";
//            JOptionPane.showMessageDialog(null, "Ошибка питания. Перезапустите генератор", "Ошибка", JOptionPane.ERROR_MESSAGE);
            int select=JOptionPane.showConfirmDialog(null,"Перезапустить генератор?","Ошибка",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
            if (select == 0) {
                generatorActive = true;
            }
            return false;
        }
        // Проверка ингридиентов
        if (!enoughComponents) {
            tip = "Недостача компонентов. Добавьте ингридиенты.";
//            JOptionPane.showMessageDialog(null, "Недостача компонентов. Добавьте ингридиенты", "Ошибка", JOptionPane.ERROR_MESSAGE);
            int select=JOptionPane.showConfirmDialog(null,"Добавить ингридиенты?","Ошибка",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
            if (select == 0) {
                enoughComponents = true;
            }
            return false;
        }
        // Проверка уровня давления
        if (stage.getPressure() > pressure) {
            tip = "Повысьте давление чтобы продолжить.";
//            JOptionPane.showMessageDialog(null, "Повысьте давление чтобы продолжить", "Сообщение", 1);
            return false;
        }
        // Проверка уровня давления
        if (stage.getPressure() < pressure) {
            tip = "Понизьте давление чтобы продолжить.";
//            JOptionPane.showMessageDialog(null, "Понизьте давление чтобы продолжить", "Сообщение", 1);
            return false;
        }
        // Проверка уровня температуры
        if (stage.getTemperature() > temperature) {
            tip = "Повысьте температуру чтобы продолжить.";
//            JOptionPane.showMessageDialog(null, "Повысьте температуру чтобы продолжить", "Сообщение", 1);
            return false;
        }
        // Проверка уровня температуры
        if (stage.getTemperature() < temperature) {
            tip = "Понизьте температуру чтобы продолжить.";
//            JOptionPane.showMessageDialog(null, "Понизьте температуру чтобы продолжить", "Сообщение", 1);
            return false;
        }
        // Подсчёт прогресса
        tip = "Ожидайте готовности: " + (STAGE_CYCLES - stageCounter);
        return true;
    }

    //Метод, отвечающий за смену состояния
    private void switchStage() {
        switch (stage) {
            case PASTERIZATION:
                stage = Stages.GOMOGENIZATION;
                break;
            case GOMOGENIZATION:
                stage = Stages.COOLING;
                break;
            case COOLING:
                stage = Stages.FREEZING;
                break;
            case FREEZING:
                stage = Stages.COMPLETION;
                setEnoughComponents(false);
                portions += 500;
                portionsInKg += 40;
                break;
            case COMPLETION:
                stage = Stages.PASTERIZATION;
                break;
            default:
                stage = Stages.PASTERIZATION;
                break;
        }
    }

    //Методы изменения приватных переменных извне
    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }
    public void setPressure(double pressure) {
        this.pressure = pressure;
    }
    public void setGeneratorActive(boolean generatorActive) {
        this.generatorActive = generatorActive;
    }
    public void setEnoughComponents(boolean enoughComponents) {
        this.enoughComponents = enoughComponents;
    }
    public void setWorking(boolean working) {
        this.working = working;
    }

    //Методы получения приватных переменных извне
    public int getTemperature() {
        return temperature;
    }
    public double getPressure() {
        return pressure;
    }
    public boolean isGeneratorActive() {
        return generatorActive;
    }
    public boolean isEnoughComponents() {
        return enoughComponents;
    }
    public boolean isWorking() {
        return working;
    }
    public String getStatus() {
        return status;
    }
    public String getTip() {
        return tip;
    }
    public String getPortions() {
        StringBuffer stringBuffer = new StringBuffer(portions + " (" + portionsInKg + " кг)");
        return stringBuffer.toString();
    }
}
