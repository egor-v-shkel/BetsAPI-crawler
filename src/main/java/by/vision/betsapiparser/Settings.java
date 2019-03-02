package by.vision.betsapiparser;

class Settings {

    // ID чата в ТГ
    public static long tgChatID = -333530356;

    // Минута матча, для отправки сообщения
    public static int timeSelectMin = 70;
    public static int timeSelectMax = 90;

    // Владение мячом, минимум
    public static int possessionMin = 0;

    // Удар по воротам, минимум
    public static int targetOnMin = 0;

    // Удар мимо ворот, минимум
    public static int targetOffMin = 0;

    // И/ИЛИ
    public static Logic logic = Logic.AND;

    //Тайм-аут прокси, мс
    public static int proxyTimeout = 15000;

    // Логика условий
    public enum Logic {
        AND,
        OR
    }
}