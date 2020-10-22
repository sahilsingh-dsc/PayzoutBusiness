package com.payzout.business.portfolio;

public interface PortfolioInterface {
    void portfolioFetchSuccess(GetPortfolio getPortfolio);

    //void portfolioFetchSuccess(List<PortfolioResponse.Data> portfolioList);

    void portfolioNotFound(String message);

    void portfolioFetchFailed(String message);
}
