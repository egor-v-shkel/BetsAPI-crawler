package by.vision.betsapicrawler;

import java.io.*;

public class Settings implements Serializable {
    //TODO define serialVersionUID
    //static final long serialVersionUID =

    private final String DEFAULT_FILE_PATH = Main.JAR_DIR + "\\Settings.ser";
    private String currentPath = Main.JAR_DIR;

    // flag var, which shows serialization status
    private boolean serStat = true;
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
    //private int proxyTimeout = 15000;
    //Telegram settings
    private String botToken;
    private long chatID;
    private String botName;

    /**
     * Serialize object as default Settings.ser file to default location.
     */
    //serialize object to jar file location
    public void serialize(){
        serialize(DEFAULT_FILE_PATH);
    }

    /**
     * Serialize object to defined path as defined .ser file.
     * @param path is where settings file should be stored.
     */
    public void serialize(String path){
        try {
            FileOutputStream fileOut =
                    new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
            MyLogger.ROOT_LOGGER.info("Serialized data saved as"+path);
            MyLogger.ROOT_LOGGER.info(this::toString);
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    /**
     * Deserialize object from default settings file location.
     */
    public void deserialize(){
        this.deserialize(DEFAULT_FILE_PATH);
    }

    /**
     * Deserialize object from defined path
     * @param path is from where settings file should be loaded.
     */
    public void deserialize(String path) {
        Settings s = new Settings();
        try {
            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            s = (Settings) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException e) {
            serStat = false;
            MyLogger.STDOUT_LOGGER.warn("No local settings was found");
        } finally {
            //TODO split this class in Settings.class (which will contain only fields) and SettingsApplier(which will
            // provide all actions with Settings object)
            this.logic = s.logic;
            this.onTargetMin = s.onTargetMin;
            this.offTargetMin = s.offTargetMin;
            this.possessionMin = s.possessionMin;
            this.rateMin = s.rateMin;
            this.timeSelectMax = s.timeSelectMax;
            this.timeSelectMin = s.timeSelectMin;
            if(serStat){
                this.botName = s.botName;
                this.chatID = s.chatID;
                this.botToken = s.botToken;
            }
            MyLogger.ROOT_LOGGER.info("Settings was successfully loaded\n");
            MyLogger.ROOT_LOGGER.info(this::toString);
        }
    }


    // Logic options
    public enum Logic {
        AND,
        OR

    }

    public String getBotName() {
        return botName;
    }

    public void setBotName(String botName) {
        this.botName = botName;
    }

    public String getBotToken() {
        return botToken;
    }

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }

    public long getChatID() {
        return chatID;
    }

    public void setChatID(long chatID) {
        this.chatID = chatID;
    }

    public Logic getLogic() {
        return logic;
    }

    public void setLogic(Logic logic) {
        this.logic = logic;
    }

    public int getOffTargetMin() {
        return offTargetMin;
    }

    public void setOffTargetMin(int offTargetMin) {
        this.offTargetMin = offTargetMin;
    }

    public int getOnTargetMin() {
        return onTargetMin;
    }

    public void setOnTargetMin(int onTargetMin) {
        this.onTargetMin = onTargetMin;
    }

    public String getCurrentPath() {
        return currentPath;
    }

    public void setCurrentPath(String currentPath) {
        this.currentPath = currentPath;
    }

    public int getPossessionMin() {
        return possessionMin;
    }

    public void setPossessionMin(int possessionMin) {
        this.possessionMin = possessionMin;
    }

    public double getRateMin() {
        return rateMin;
    }

    public void setRateMin(double rateMin) {
        this.rateMin = rateMin;
    }

    public boolean getSerStat() {
        return serStat;
    }

    public int getTimeSelectMax() {
        return timeSelectMax;
    }

    public void setTimeSelectMax(int timeSelectMax) {
        this.timeSelectMax = timeSelectMax;
    }

    public int getTimeSelectMin() {
        return timeSelectMin;
    }

    public void setTimeSelectMin(int timeSelectMin) {
        this.timeSelectMin = timeSelectMin;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        String newLine = "\n";
        StringBuffer settings = sb.append("Внутренние настройки").append(newLine)
                .append("Путь настроек по умолчанию: ").append(DEFAULT_FILE_PATH).append(newLine)
                .append("Текущий путь: ").append(currentPath).append(newLine)
                .append("Сохранены ли настройки? ").append(serStat?"Да":"Нет").append(newLine)
                .append(newLine)
                .append("Настройки пользователя: \n")
                .append("Логика: ").append(logic).append(newLine)
                .append(String.format("Минута матча, для отправки сообщения - min(%s), max(%s)", timeSelectMin, timeSelectMax)).append(")\n")
                .append("Минимальный коэффициент: ").append(rateMin).append(newLine)
                .append("Владение мячом, минимум: ").append(possessionMin).append(newLine)
                .append("Удар по воротам, минимум: ").append(onTargetMin).append(newLine)
                .append("Удар мимо ворот, минимум: ").append(offTargetMin).append(newLine)
                //.append("Тайм-аут прокси, мс - ").append(proxyTimeout).append(newLine)
                .append(newLine)
                .append("Настройки телеги:").append(newLine)
                .append("Чат ID: ").append(chatID).append(newLine)
                .append("Токен бота: ").append(botToken).append(newLine)
                .append("Имя бота: ").append(botName).append(newLine);
        return settings.toString();
    }
/* public int getProxyTimeout() {
        return proxyTimeout;
    }

    public void setProxyTimeout(int proxyTimeout) {
        this.proxyTimeout = proxyTimeout;
    }*/
}