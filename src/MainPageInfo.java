public class MainPageInfo {


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
    private static MatchInfo matchInfoL = new MatchInfo();

    // Спарсенная инфа по матчу (правая колонка)
    private static MatchInfo matchInfoR = new MatchInfo();

    public static MatchInfo getMatchInfoL() {
        return matchInfoL;
    }

    public static MatchInfo getMatchInfoR() {
        return matchInfoR;
    }

    public String getLeague() {
        return League;
    }

    public void setLeague(String league) {
        League = league;
    }

    public String getIdMatch() {
        return IdMatch;
    }

    public void setIdMatch(String idMatch) {
        IdMatch = idMatch;
    }

    public int getTime() {
        return Time;
    }

    public void setTime(int time) {
        Time = time;
    }

    public String getClubL() {
        return ClubL;
    }

    public void setClubL(String clubL) {
        ClubL = clubL;
    }

    public String getClubR() {
        return ClubR;
    }

    public void setClubR(String clubR) {
        ClubR = clubR;
    }

    public String getScore() {
        return Score;
    }

    public void setScore(String score) {
        Score = score;
    }

    public String getUrlMatch() {
        return UrlMatch;
    }

    public void setUrlMatch(String urlMatch) {
        UrlMatch = urlMatch;
    }

    public String getRateL() {
        return RateL;
    }

    public void setRateL(String rateL) {
        RateL = rateL;
    }

    public String getRateC() {
        return RateC;
    }

    public void setRateC(String rateC) {
        RateC = rateC;
    }

    public String getRateR() {
        return RateR;
    }

    public void setRateR(String rateR) {
        RateR = rateR;
    }

    public String getResponse() {
        return Response;
    }

    public void setResponse(String response) {
        Response = response;
    }
}
