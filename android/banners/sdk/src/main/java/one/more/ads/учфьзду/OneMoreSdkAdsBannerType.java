package one.more.ads.учфьзду;

public enum OneMoreSdkAdsBannerType {

    BANNER("banner"),
    BANNER_LARGE("bannerLarge"),
    BANNER_RECTANGLE("bannerRectangle");

    private String mValue;

    OneMoreSdkAdsBannerType(String value){
        mValue = value;
    }

    public String getValue() {
        return mValue;
    }
}
