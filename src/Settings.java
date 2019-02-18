public class Settings {

    // Токен Telegram бота
    public String tgToken = "";

    // ID чата в ТГ
    public String tgChatId = "";

    // Прокси для отправки сообщения в Telegram
    public String proxyTelegram = "";

    // Минута матча, для отправки сообщения
    public int TimeSelect = 10;

    // Владение мячом, минимум
    public int possessionMin = 65;

    // Удар по воротам, минимум
    public int TargetOnMin = 2;

    // Удар мимо ворот, минимум
    public int TargetOffMin = 5;

    // И/ИЛИ
    public Logic logic = Logic.OR;

    // Список прокси для сайта
    //private List<String> lstProxy =new ArrayList<String>();

    //Тайм-аут прокси, мс
    public int timeout = 30000;

    // Логика условий
    public enum Logic {
        AND,
        OR
    }
}