package by.vision.betsapicrawler;

import java.io.*;

public class Settings implements Serializable {
    //TODO define serialVersionUID
    //static final long serialVersionUID =

    private static final String DEFAULT_FILE_PATH = Main.JAR_DIR + "\\Settings.ser";
    private static File currentFile = new File(DEFAULT_FILE_PATH);

    //Primary settings

    //Logic options
    public enum Logic {
        AND,
        OR
    }
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
    public void serialize() throws IllegalArgumentException {
        serialize(currentFile.getAbsolutePath());
    }

    /**
     * Serialize object to defined path as defined .ser file.
     * @param path should be absolute.
     */
    public void serialize(String path) throws IllegalArgumentException  {

        pathCheck(path);

        try {
            FileOutputStream fileOut =
                    new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();

            currentFile = new File(path);

            MyLogger.ROOT_LOGGER.info("Serialized data saved in: "+path);
            MyLogger.ROOT_LOGGER.info(this::toString);
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    /**
     * Deserialize object from default settings file location.
     */
    public void deserialize() throws IllegalArgumentException{
        this.deserialize(DEFAULT_FILE_PATH);
    }

    /**
     * Deserialize object from defined path
     * @param path is from where settings file should be loaded.
     */
    public void deserialize(String path) throws IllegalArgumentException {

        pathCheck(path);

        Settings settings = new Settings();
        try {
            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            settings = (Settings) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException e) {
            MyLogger.STDOUT_LOGGER.warn("No settings local was found");
        } finally {
            /*this.logic = settings.logic;
            this.onTargetMin = settings.onTargetMin;
            this.offTargetMin = settings.offTargetMin;
            this.possessionMin = settings.possessionMin;
            this.rateMin = settings.rateMin;
            this.timeSelectMax = settings.timeSelectMax;
            this.timeSelectMin = settings.timeSelectMin;

            if(checkNotNullTgSettings()){
                this.botName = settings.botName;
                this.chatID = settings.chatID;
                this.botToken = settings.botToken;
            }*/

            StageBuilder.settings = settings;

            currentFile = new File(path);

            MyLogger.ROOT_LOGGER.info("Settings was successfully loaded\n");
            MyLogger.ROOT_LOGGER.info(this::toString);
        }
    }

    private void pathCheck(String path) throws IllegalArgumentException {
        if(path == null || !path.endsWith(".ser")) throw new IllegalArgumentException("Not an settings file");
    }

    /**
     * Prevent zeroes and nulls to be shown in GUI
     * @return true if all fields related to Telegram settings not null or != 0
     * and vice versa
     */
    public boolean checkNotNullTgSettings() {
        return (chatID != 0 ||
                botName != null ||
                botToken != null);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String newLine = "\n";
        StringBuilder settings = sb.append("Внутренние настройки").append(newLine)
                .append("Путь настроек по умолчанию: ").append(DEFAULT_FILE_PATH).append(newLine)
                .append("Текущий путь: ").append(currentFile.getAbsolutePath()).append(newLine)
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

    public File getCurrentFile() {
        return currentFile;
    }

    public void setCurrentFile(File currentFile) {
        this.currentFile = currentFile;
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
/* public int getProxyTimeout() {
        return proxyTimeout;
    }

    public void setProxyTimeout(int proxyTimeout) {
        this.proxyTimeout = proxyTimeout;
    }*/
}