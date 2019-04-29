package by.vision.betsapicrawler;

import java.io.*;

public class Settings implements Serializable {
    //TODO define serialVersionUID
    //static final long serialVersionUID =

    private final String DEFAULT_PATH_TO_FILE = Main.getPath() + "Settings.ser";

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
    //Telegram settings
    private String token;
    private long chatID;
    private String botName;

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        String end = "\n";
        StringBuffer settings = sb.append("Settings: \n")
                .append("ID чата в ТГ - ").append(chatID).append(end)
                .append("Минимальный коэффициент - ").append(rateMin).append(end)
                .append("Минута матча, для отправки сообщения - min(").append(timeSelectMin).append("), max(").append(timeSelectMax).append(")\n")
                .append("Владение мячом, минимум - ").append(possessionMin).append(end)
                .append("Удар по воротам, минимум - ").append(onTargetMin).append(end)
                .append("Удар мимо ворот, минимум - ").append(offTargetMin).append(end)
                .append("Логика - ").append(logic).append(end)
                .append("Тайм-аут прокси, мс - ").append(proxyTimeout).append(end);
        return settings.toString();
    }


    //serialize object to jar file location
    public void serialize(){
        serialize(DEFAULT_PATH_TO_FILE);
    }

    //serialize object to defined path
    public void serialize(String path){
        try {
            FileOutputStream fileOut =
                    new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
            MyLogger.ROOT_LOGGER.info("Serialized data saved as"+path);
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    //deserialize object from jar file location
    public void deserialize(){
        this.deserialize(DEFAULT_PATH_TO_FILE);
    }

    //deserialize object from defined path
    public void deserialize(String path) {
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
            this.logic = s.logic;
            this.onTargetMin = s.onTargetMin;
            this.offTargetMin = s.offTargetMin;
            this.possessionMin = s.possessionMin;
            this.rateMin = s.rateMin;
            this.proxyTimeout = s.proxyTimeout;
            this.chatID = s.chatID;
            this.timeSelectMax = s.timeSelectMax;
            this.timeSelectMin = s.timeSelectMin;
        }
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public long getChatID() {
        return chatID;
    }

    public void setChatID(long chatID) {
        this.chatID = chatID;
    }

    public String getBotName() {
        return botName;
    }

    public void setBotName(String botName) {
        this.botName = botName;
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