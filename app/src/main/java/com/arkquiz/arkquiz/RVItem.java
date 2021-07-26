package com.arkquiz.arkquiz;

public class RVItem {
    private int ranking, countryCode, score;
    private String nickname;

    public RVItem(){}

    public RVItem(int ranking, String nickname, int countryCode, int score) {
        this.ranking = ranking;
        this.countryCode = countryCode;
        this.nickname = nickname;
        this.score=score;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public int getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(int countryCode) {
        this.countryCode = countryCode;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

}
