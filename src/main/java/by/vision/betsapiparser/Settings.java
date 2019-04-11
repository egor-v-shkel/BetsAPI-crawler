package by.vision.betsapiparser;

public class Settings {

    // ID чата в ТГ
    public static long tgChatID = -333530356;

    //coefficient
    public static double coefMin;

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

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        String end = "\n";
        StringBuffer settings = sb.append("Settings: \n")
                .append("ID чата в ТГ - ").append(tgChatID).append(end)
                .append("Минимальный коэффициент - ").append(coefMin).append(end)
                .append("Минута матча, для отправки сообщения - min(").append(timeSelectMin).append("), max(").append(timeSelectMax).append(")\n")
                .append("Владение мячом, минимум - ").append(possessionMin).append(end)
                .append("Удар по воротам, минимум - ").append(targetOnMin).append(end)
                .append("Удар мимо ворот, минимум - ").append(targetOffMin).append(end)
                .append("Логика - ").append(logic).append(end)
                .append("Тайм-аут прокси, мс - ").append(proxyTimeout).append(end);
        return settings.toString();
    }
}