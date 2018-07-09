package com.blogspot.tellwap.tafakarinayesu;

/**
 * Created by chami o 3/3/18.
 */

public class AddData {
    String dataId;
    String day;
    String event;
    String palsm;
    String somoLaKwanza;
    String getSomoLaKwanzaMstari;
    String somoLaPili;
    String somoLaPiliMstari;
    String wimboWaKatikati;
    String wimboWaKatikatiMstari;
    String shangilio;
    String shangilioMstari;
    String injili;
    String injiliMstari;
    String wimboWaMwanzo;
    String getWimboWaMwanzoMstari;

    public AddData(){

    }

    public String getDataId() {
        return dataId;
    }

    public String getDay() {
        return day;
    }

    public String getEvent() {
        return event;
    }

    public String getPalsm() {
        return palsm;
    }

    public String getSomoLaKwanza() {
        return somoLaKwanza;
    }

    public String getGetSomoLaKwanzaMstari() {
        return getSomoLaKwanzaMstari;
    }

    public String getSomoLaPili() {
        return somoLaPili;
    }

    public String getSomoLaPiliMstari() {
        return somoLaPiliMstari;
    }

    public String getWimboWaKatikati() {
        return wimboWaKatikati;
    }

    public String getWimboWaKatikatiMstari() {
        return wimboWaKatikatiMstari;
    }

    public String getShangilio() {
        return shangilio;
    }

    public String getShangilioMstari() {
        return shangilioMstari;
    }

    public String getInjili() {
        return injili;
    }

    public String getInjiliMstari() {
        return injiliMstari;
    }

    public String getWimboWaMwanzo() {
        return wimboWaMwanzo;
    }

    public String getGetWimboWaMwanzoMstari() {
        return getWimboWaMwanzoMstari;
    }

    public AddData(String dataId, String day, String event, String palsm, String somoLaKwanza,
                   String getSomoLaKwanzaMstari, String somoLaPili, String somoLaPiliMstari,
                   String wimboWaKatikati, String wimboWaKatikatiMstari, String shangilio,
                   String shangilioMstari, String injili, String injiliMstari,
                   String wimboWaMwanzo, String getWimboWaMwanzoMstari) {
        this.dataId = dataId;
        this.day = day;
        this.event = event;

        this.palsm = palsm;
        this.somoLaKwanza = somoLaKwanza;
        this.getSomoLaKwanzaMstari = getSomoLaKwanzaMstari;
        this.somoLaPili = somoLaPili;
        this.somoLaPiliMstari = somoLaPiliMstari;
        this.wimboWaKatikati = wimboWaKatikati;
        this.wimboWaKatikatiMstari = wimboWaKatikatiMstari;
        this.shangilio = shangilio;
        this.shangilioMstari = shangilioMstari;
        this.injili = injili;
        this.injiliMstari = injiliMstari;
        this.wimboWaMwanzo = wimboWaMwanzo;
        this.getWimboWaMwanzoMstari = getWimboWaMwanzoMstari;
    }
}
