package com.doinWondrs.betterme;

public class DevModel {

    private String devName;
    private String devLinkedIn;
    private String devGitHub;
    private int devImg;
    private String devQuote;

    public DevModel(String devName, String devLinkedIn, String devGitHub, int devImg, String devQuote) {
        this.devName = devName;
        this.devLinkedIn = devLinkedIn;
        this.devGitHub = devGitHub;
        this.devImg = devImg;
        this.devQuote = devQuote;
    }

    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public String getDevLinkedIn() {
        return devLinkedIn;
    }

    public void setDevLinkedIn(String devLinkedIn) {
        this.devLinkedIn = devLinkedIn;
    }

    public String getDevGitHub() {
        return devGitHub;
    }

    public void setDevGitHub(String devGitHub) {
        this.devGitHub = devGitHub;
    }

    public int getDevImg() {
        return devImg;
    }

    public void setDevImg(int devImg) {
        this.devImg = devImg;
    }

    public String getDevQuote() {
        return devQuote;
    }

    public void setDevQuote(String devQuote) {
        this.devQuote = devQuote;
    }
}
