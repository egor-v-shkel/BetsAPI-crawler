import java.util.List;

public class Settings {

    // Токен Telegram бота
    public String TgToken = "";

    // ID чата в ТГ
    public String TgChatId = "";

    // Прокси для отправки сообщения в Telegram
    public String ProxyTelegram = "";

    // Минута матча, для отправки сообщения
    //public int TimeSelect = 10;

    // Владение мячом, минимум
    public int PossessionMin = 65;

    // Удар по воротам, минимум
    public int TargetOnMin = 2;

    // Удар мимо ворот, минимум
    public int TargetOffMin = 5;

    // И/ИЛИ
    public logic Logic = logic.OR;

    // Список прокси для сайта
    //private List<String> lstProxy =new List<String>();

    // Логика условий
    public enum logic {
        AND,
        OR
    }
}