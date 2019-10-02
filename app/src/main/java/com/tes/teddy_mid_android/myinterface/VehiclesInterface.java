package com.tes.teddy_mid_android.myinterface;

public interface VehiclesInterface {

    public void showProgress();
    public void hideProgress();
    public void requestDataSucces(String responseJson);
    public void requestDataError(String error);

}
