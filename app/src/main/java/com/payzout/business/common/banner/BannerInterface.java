package com.payzout.business.common.banner;

import java.util.List;

public interface BannerInterface {
    void bannerFetchSuccess(List<Banner> bannerList);

    void bannerFetchError(String message);

    void bannerNoFound(String message);
}
