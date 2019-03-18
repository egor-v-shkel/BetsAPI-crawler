package by.vision.betsapiparser;

class Settings {

    // ID чата в ТГ
    public static long tgChatID;

    // Минута матча, для отправки сообщения
    public static int timeSelectMin;
    public static int timeSelectMax;

    // Владение мячом, минимум
    public static int possessionMin;

    // Удар по воротам, минимум
    public static int targetOnMin;

    // Удар мимо ворот, минимум
    public static int targetOffMin;

    // И/ИЛИ
    public static Logic logic;

    //Тайм-аут прокси, мс
    public static int proxyTimeout;

    // Логика условий
    public enum Logic {
        AND,
        OR
    }
}