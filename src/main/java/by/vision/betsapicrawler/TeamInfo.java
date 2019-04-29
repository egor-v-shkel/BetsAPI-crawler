package by.vision.betsapicrawler;

public class TeamInfo extends CommonInfo {

    public TeamInfo(CommonInfo cmn){
        this.setLeague(cmn.getLeague());
        this.setRateL(cmn.getRateL());
        this.setRateR(cmn.getRateR());
        this.setTime(cmn.getTime());
        this.setScore(cmn.getScore());
    }


    // Название клуба
    private String ClubName;

    // Голы
    private int Goals;

    // Угловые
    private int Corners;

    // Угловые в первом тайме
    private int CornersHalf;

    // Желтые карточки
    private int CardYellow;

    // Красные карточки
    private int CardRed;

    // Пенальти
    private int Penalties;

    // Замены
    private int Substitutions;

    // Атака
    private int Attacks;

    // Опасная Атака
    private int AttacksDangerous;

    // Удары в створ ворот
    private int TargetOn;

    // Удары мимо ворот
    private int TargetOff;

    // Владение мячом, в %
    private int Possession;

    public String getClubName() {
        return ClubName;
    }

    public void setClubName(String clubName) {
        ClubName = clubName;
    }

    public int getGoals() {
        return Goals;
    }

    public void setGoals(int goals) {
        Goals = goals;
    }

    public int getCorners() {
        return Corners;
    }

    public void setCorners(int corners) {
        Corners = corners;
    }

    public int getCornersHalf() {
        return CornersHalf;
    }

    public void setCornersHalf(int cornersHalf) {
        CornersHalf = cornersHalf;
    }

    public int getCardYellow() {
        return CardYellow;
    }

    public void setCardYellow(int cardYellow) {
        CardYellow = cardYellow;
    }

    public int getCardRed() {
        return CardRed;
    }

    public void setCardRed(int cardRed) {
        CardRed = cardRed;
    }

    public int getPenalties() {
        return Penalties;
    }

    public void setPenalties(int penalties) {
        Penalties = penalties;
    }

    public int getSubstitutions() {
        return Substitutions;
    }

    public void setSubstitutions(int substitutions) {
        Substitutions = substitutions;
    }

    public int getAttacks() {
        return Attacks;
    }

    public void setAttacks(int attacks) {
        Attacks = attacks;
    }

    public int getAttacksDangerous() {
        return AttacksDangerous;
    }

    public void setAttacksDangerous(int attacksDangerous) {
        this.AttacksDangerous = attacksDangerous;
    }

    public int getTargetOn() {
        return TargetOn;
    }

    public void setTargetOn(int targetOn) {
        TargetOn = targetOn;
    }

    public int getTargetOff() {
        return TargetOff;
    }

    public void setTargetOff(int targetOff) {
        TargetOff = targetOff;
    }

    public int getPossession() {
        return Possession;
    }

    public void setPossession(int possession) {
        Possession = possession;
    }
}
