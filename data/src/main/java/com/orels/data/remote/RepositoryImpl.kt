package com.orels.data.remote

import com.orels.data.remote.model.entities.BaseResponse
import com.orels.data.remote.model.entities.StockResponse
import com.orels.domain.model.entities.*
import com.orels.domain.model.interactors.Repository
import javax.inject.Inject

/**
 * @author Orel Zilberman
 * 05/10/2022
 */
class RepositoryImpl @Inject constructor(
    private val api: StocksAPI
) : Repository {
    override suspend fun getStock(ticker: String): Stock =
        api.getStockDetails(ticker = ticker).toStock()
}

fun BaseResponse?.toBase(): Base = Base(raw = this?.raw, fmt = this?.fmt)
fun StockResponse.toStock(): Stock = Stock(
    financialData = FinancialData(
        ebitdaMargins = financialData?.ebitdaMargins?.toBase(),
        profitMargins = financialData?.profitMargins?.toBase(),
        grossMargins = financialData?.grossMargins?.toBase(),
        operatingCashflow = financialData?.operatingCashflow?.toBase(),
        operatingMargins = financialData?.operatingMargins?.toBase(),
        ebitda = financialData?.ebitda?.toBase(),
        grossProfits = financialData?.grossProfits?.toBase(),
        freeCashflow = financialData?.freeCashflow?.toBase(),
        targetMedianPrice = financialData?.targetMedianPrice?.toBase(),
        currentPrice = financialData?.currentPrice?.toBase(),
        returnOnAssets = financialData?.returnOnAssets?.toBase(),
        targetMeanPrice = financialData?.targetMedianPrice?.toBase(),
        debtToEquity = financialData?.debtToEquity?.toBase(),
        returnOnEquity = financialData?.returnOnEquity?.toBase(),
        targetHighPrice = financialData?.targetHighPrice?.toBase(),
        totalCash = financialData?.totalCash?.toBase(),
        totalDebt = financialData?.totalDebt?.toBase(),
        totalRevenue = financialData?.totalRevenue?.toBase(),
        totalCashPerShare = financialData?.totalCashPerShare?.toBase(),
        financialCurrency = financialData?.financialCurrency,
    ), defaultKeyStatistics = DefaultKeyStatistics(
        enterpriseToRevenue = defaultKeyStatistics?.enterpriseToRevenue?.toBase(),
        sharesOutstanding = defaultKeyStatistics?.sharesOutstanding?.toBase(),
        sharesShort = defaultKeyStatistics?.sharesShort?.toBase(),
        trailingEps = defaultKeyStatistics?.trailingEps?.toBase(),
        heldPercentInsiders = defaultKeyStatistics?.heldPercentInsiders?.toBase(),
        beta = defaultKeyStatistics?.beta?.toBase(),
        enterpriseValue = defaultKeyStatistics?.enterpriseValue?.toBase(),
        forwardPE = defaultKeyStatistics?.forwardPE?.toBase(),
        impliedSharesOutstanding = defaultKeyStatistics?.impliedSharesOutstanding?.toBase(),
        priceToBook = defaultKeyStatistics?.priceToBook?.toBase()
    ), summaryDetails = SummaryDetails(
        trailingPE = summaryDetails?.trailingPE?.toBase(),
        forwardPE = summaryDetails?.forwardPE?.toBase()
    )
)