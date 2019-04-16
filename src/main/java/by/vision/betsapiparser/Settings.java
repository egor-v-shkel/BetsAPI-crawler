package by.vision.betsapiparser;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class Settings implements Serializable {

    // Telegram chat ID
    private long tgChatID = -333530356;

    //Minimal rate of any team
    private double rateMin = 1.0;

    //Time range of match
    private int timeSelectMin = 5;
    private int timeSelectMax = 35;

    // Minimal possession of any team
    private int possessionMin = 55;

    // On Target
    private int onTargetMin = 2;

    // Off Target
    private int offTargetMin = 1;

    // Logic
    private Logic logic = Logic.AND;

    //Proxy timeout
    private int proxyTimeout = 15000;

    // Logic options
    public enum Logic {
        AND,
        OR
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        String end = "\n";
        StringBuffer settings = sb.append("Settings: \n")
                .append("ID чата в ТГ - ").append(tgChatID).append(end)
                .append("Минимальный коэффициент - ").append(rateMin).append(end)
                .append("Минута матча, для отправки сообщения - min(").append(timeSelectMin).append("), max(").append(timeSelectMax).append(")\n")
                .append("Владение мячом, минимум - ").append(possessionMin).append(end)
                .append("Удар по воротам, минимум - ").append(onTargetMin).append(end)
                .append("Удар мимо ворот, минимум - ").append(offTargetMin).append(end)
                .append("Логика - ").append(logic).append(end)
                .append("Тайм-аут прокси, мс - ").append(proxyTimeout).append(end);
        return settings.toString();
    }



    //deserialize object near jar file location
    private Settings deserialize(){
        return this.deserialize(App.getPath()+App.SETTINGS_FILE_NAME);
    }

    //deserialize object to selected path
    private Settings deserialize(String path) {
        Settings s = new Settings();
        try {
            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            s = (Settings) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException e) {
            MyLogger.STDOUT_LOGGER.info("No local settings was found");
            e.printStackTrace();
        } finally {
            return s;
        }
    }

    public long getTgChatID() {
        return tgChatID;
    }

    public void setTgChatID(long tgChatID) {
        this.tgChatID = tgChatID;
    }

    public double getRateMin() {
        return rateMin;
    }

    public void setRateMin(double rateMin) {
        this.rateMin = rateMin;
    }

    public int getTimeSelectMin() {
        return timeSelectMin;
    }

    public void setTimeSelectMin(int timeSelectMin) {
        this.timeSelectMin = timeSelectMin;
    }

    public int getTimeSelectMax() {
        return timeSelectMax;
    }

    public void setTimeSelectMax(int timeSelectMax) {
        this.timeSelectMax = timeSelectMax;
    }

    public int getPossessionMin() {
        return possessionMin;
    }

    public void setPossessionMin(int possessionMin) {
        this.possessionMin = possessionMin;
    }

    public int getOnTargetMin() {
        return onTargetMin;
    }

    public void setOnTargetMin(int onTargetMin) {
        this.onTargetMin = onTargetMin;
    }

    public int getOffTargetMin() {
        return offTargetMin;
    }

    public void setOffTargetMin(int offTargetMin) {
        this.offTargetMin = offTargetMin;
    }

    public Logic getLogic() {
        return logic;
    }

    public void setLogic(Logic logic) {
        this.logic = logic;
    }

    public int getProxyTimeout() {
        return proxyTimeout;
    }

    public void setProxyTimeout(int proxyTimeout) {
        this.proxyTimeout = proxyTimeout;
    }
}