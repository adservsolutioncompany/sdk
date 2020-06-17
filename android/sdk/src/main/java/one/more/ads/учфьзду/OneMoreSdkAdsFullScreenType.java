package one.more.ads.учфьзду;

public enum OneMoreSdkAdsFullScreenType {
    VIDEO("video"),
    INTERSTITIAL("interstitial");

    private String mValue;

    OneMoreSdkAdsFullScreenType(String value){
        mValue = value;
    }

    public String getValue() {
        return mValue;
    }
}
