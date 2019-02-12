public class ParseInfo {


    // Лига
    private String League;

    // ID матча
    private String IdMatch;

    // Время матча
    private int Time;

    // Название клуба (слева)
    private String ClubL;

    // Название клуба (справа)
    private String ClubR;

    // Счет
    private String Score;

    // Ссылка на матч
    private String UrlMatch;

    // Ставка слева
    private String RateL;

    // Ставка центр
    private String RateC;

    // Ставка справа
    private String RateR;

    // HTML страница с данными матча
    private String Response;

    // Спарсенная инфа по матчу (левая колонка)
    private MatchInfo MatchinfoL = new MatchInfo();

    // Спарсенная инфа по матчу (правая колонка)
    private MatchInfo MatchinfoR = new MatchInfo();

}
