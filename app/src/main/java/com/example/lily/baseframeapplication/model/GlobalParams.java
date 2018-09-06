package com.example.lily.baseframeapplication.model;

import com.example.lily.baseframeapplication.model.response.BaseResponse;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 * Created by Administrator on 2017/1/22.
 */

public class GlobalParams extends BaseResponse implements Serializable {
    @SerializedName("balUrl") public String balUrl;
    @SerializedName("finUrl") public String finUrl;
    @SerializedName("gestureFlag") public String gestureFlag;
    @SerializedName("mobile") public String mobile;
    @SerializedName("myFinUrl") public String myFinUrl;
    @SerializedName("privacyAgreementUrl") public String privacyAgreementUrl;
    @SerializedName("registAgreementUrl") public String registAgreementUrl;
    @SerializedName("totalAssetUrl") public String totalAssetUrl;
    @SerializedName("aboutUsUrl") public String aboutUsUrl;
    @SerializedName("myNewsUrl") public String myNewsUrl;
    @SerializedName("myBillUrl") public String myBillUrl;
    @SerializedName("myReceivableUrl") public String myReceivableUrl;
    @SerializedName("customerServicePhone") public String customerServicePhone;
    @SerializedName("finCalendarUrl") public String finCalendarUrl;
    @SerializedName("finBillUrl") public String finBillUrl;
    @SerializedName("tpurseServicetAgreementUrl") public String
            tpurseServicetAgreementUrl;
    @SerializedName("vcServiceAgreementUrl") public String
            vcServiceAgreementUrl;
    @SerializedName("vcPrivacyAgreementUrl") public String
            vcPrivacyAgreementUrl;
    @SerializedName("tpursePayAgreementUrl") public String
            tpursePayAgreementUrl;
}
