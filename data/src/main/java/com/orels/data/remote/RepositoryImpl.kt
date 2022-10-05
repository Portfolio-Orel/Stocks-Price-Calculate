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
        ebitdaMargins = financialData?.ebitdaMargins?.toBase() ?: Base(),
        profitMargins = financialData?.profitMargins?.toBase() ?: Base(),
        grossMargins = financialData?.grossMargins?.toBase() ?: Base(),
        operatingCashflow = financialData?.operatingCashflow?.toBase() ?: Base(),
        operatingMargins = financialData?.operatingMargins?.toBase() ?: Base(),
        ebitda = financialData?.ebitda?.toBase() ?: Base(),
        grossProfits = financialData?.grossProfits?.toBase() ?: Base(),
        freeCashflow = financialData?.freeCashflow?.toBase() ?: Base(),
        targetMedianPrice = financialData?.targetMedianPrice?.toBase() ?: Base(),
        currentPrice = financialData?.currentPrice?.toBase() ?: Base(),
        returnOnAssets = financialData?.returnOnAssets?.toBase() ?: Base(),
        targetMeanPrice = financialData?.targetMedianPrice?.toBase() ?: Base(),
        debtToEquity = financialData?.debtToEquity?.toBase() ?: Base(),
        returnOnEquity = financialData?.returnOnEquity?.toBase() ?: Base(),
        targetHighPrice = financialData?.targetHighPrice?.toBase() ?: Base(),
        totalCash = financialData?.totalCash?.toBase() ?: Base(),
        totalDebt = financialData?.totalDebt?.toBase() ?: Base(),
        totalRevenue = financialData?.totalRevenue?.toBase() ?: Base(),
        totalCashPerShare = financialData?.totalCashPerShare?.toBase() ?: Base(),
        financialCurrency = financialData?.financialCurrency,
    ), defaultKeyStatistics = DefaultKeyStatistics(
        enterpriseToRevenue = defaultKeyStatistics?.enterpriseToRevenue?.toBase() ?: Base(),
        sharesOutstanding = defaultKeyStatistics?.sharesOutstanding?.toBase() ?: Base(),
        sharesShort = defaultKeyStatistics?.sharesShort?.toBase() ?: Base(),
        trailingEps = defaultKeyStatistics?.trailingEps?.toBase() ?: Base(),
        heldPercentInsiders = defaultKeyStatistics?.heldPercentInsiders?.toBase() ?: Base(),
        beta = defaultKeyStatistics?.beta?.toBase() ?: Base(),
        enterpriseValue = defaultKeyStatistics?.enterpriseValue?.toBase() ?: Base(),
        forwardPE = defaultKeyStatistics?.forwardPE?.toBase() ?: Base(),
        impliedSharesOutstanding = defaultKeyStatistics?.impliedSharesOutstanding?.toBase()
            ?: Base(),
        priceToBook = defaultKeyStatistics?.priceToBook?.toBase() ?: Base()
    ), summaryDetails = SummaryDetails(
        trailingPE = summaryDetails?.trailingPE?.toBase() ?: Base(),
        forwardPE = summaryDetails?.forwardPE?.toBase() ?: Base()
    ),
    ticker = symbol
)