package com.payzout.business.portfolio;

import java.util.List;

public interface PortfolioInterface {
    void portfolioFetchSuccess(List<Portfolio> portfolioList);

    void portfolioNotFound(String message);

    void portfolioFetchFailed(String message);
}
