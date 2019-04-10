package by.vision.betsapiparser;

public class Settings {

    // ID чата в ТГ
    public static long tgChatID = -333530356;

    // Минута матча, для отправки сообщения
    public static int timeSelectMin;
    public static int timeSelectMax;

    // Владение мячом, минимум
    public static int possessionMin = 55;

    // Удар по воротам, минимум
    public static int targetOnMin = 1;

    // Удар мимо ворот, минимум
    public static int targetOffMin = 3;

    // И/ИЛИ
    public static Logic logic = Logic.AND;

    //Тайм-аут прокси, мс
    public static int proxyTimeout;

    // Логика условий
    public enum Logic {
        AND,
        OR
    }
}