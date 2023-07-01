
import android.content.Context
import android.net.Uri
import com.google.gson.Gson
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.get
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.InputStreamReader
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.abs

data class Purchase(
    val name: String,
    val symbol: String,
    val openDate: Date,
    val openPrice: Double,
    val amount: Float
)

@Serializable
data class HistoricalPrice(
    val date: String,
    val open: Double,
    val high: Double,
    val low: Double,
    val close: Double,
    val volume: Int,
    val adjClose: Double,
    val unadjustedVolume: Int,
    val change: Double,
    val changePercent: Double,
    val vwap: Double
)

@Serializable
data class StockResponse(val symbol: String, val historical: List<HistoricalPrice>?)

class PortfolioComparison {
    private val apiKey = "e5773d8801a3c6ae07d6b0976740ac1f"
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    private val gson = Gson()

    suspend fun run(uri: Uri, context: Context) {
        val purchases = readPurchasesFromCSV(context, uri)
        val sp500Prices = fetchSP500Prices()

        val sp500Buys = mutableListOf<Purchase>()

        for (purchase in purchases) {
            val equivalentSP500Price = findEquivalentSP500Price(purchase.openDate, sp500Prices)
            val sp500Buy = Purchase(
                name = "S&P 500",
                symbol = "SPX",
                openDate = purchase.openDate,
                openPrice = equivalentSP500Price,
                amount = ((purchase.amount * purchase.openPrice) / equivalentSP500Price).toFloat()
            )
            sp500Buys.add(sp500Buy)
        }
        println()
        // Now you have a list of "buy" equivalents of the S&P 500 for each purchase
        // You can use this list to compare portfolio growth over time vs the S&P 500
    }

    private fun readPurchasesFromCSV(context: Context, uri: Uri): List<Purchase> {
        val fileContent = readFileContentFromUri(context, uri)
        val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.US)
        val purchases = mutableListOf<Purchase>()

        val lines = fileContent.split("\n")
        val header = lines[1]
        val columns = header.split(",")

        val nameIndex = columns.indexOf("Name")
        val symbolIndex = columns.indexOf("Symbol")
        val openDateIndex = columns.indexOf("Open Date")
        val openPriceIndex = columns.indexOf("Open Price")
        val amountIndex = columns.indexOf("Amount")

        for (line in lines.drop(2)) {
            try {
                val values = line.split(",")
                val name = values[nameIndex]
                val symbol = values[symbolIndex]
                val openDate = dateFormat.parse(values[openDateIndex])
                val openPrice = values[openPriceIndex].toDouble()
                val amount = values[amountIndex].toFloat()

                val purchase = Purchase(name, symbol, openDate, openPrice, amount)
                purchases.add(purchase)
            } catch (e: Exception) {
                println()
            }
        }

        return purchases
    }


    private fun readFileContentFromUri(context: Context, uri: Uri): String {
        val contentResolver = context.contentResolver
        val inputStream = contentResolver.openInputStream(uri)
        val reader = BufferedReader(InputStreamReader(inputStream))

        val stringBuilder = StringBuilder()
        var line: String? = reader.readLine()
        while (line != null) {
            stringBuilder.append("\n")
            stringBuilder.append(line)
            line = reader.readLine()
        }

        inputStream?.close()
        reader.close()

        return stringBuilder.toString()
    }

    private suspend fun fetchSP500Prices(): List<HistoricalPrice> {
        val apiKey = "e5773d8801a3c6ae07d6b0976740ac1f"
        val symbol = "SPY"
        val endpoint =
            "https://financialmodelingprep.com/api/v3/historical-price-full/$symbol?from=2020-01-01&to=2023-06-23&apikey=$apiKey"
        val client = HttpClient {
            install(JsonFeature) {
                serializer = KotlinxSerializer(Json { ignoreUnknownKeys = true })
            }
        }

        val responseString: String = client.get(endpoint)
        val gson = Gson()
        val response = gson.fromJson(responseString, StockResponse::class.java)

//        val stockResponse = StockResponse(response)
        client.close()

        return response.historical ?: emptyList()
    }

    private fun parseSP500Prices(apiResponse: String): List<HistoricalPrice> {
        val response = Gson().fromJson(apiResponse, StockResponse::class.java)
        return response.historical ?: emptyList()
    }

    private fun findEquivalentSP500Price(date: Date, sp500Prices: List<HistoricalPrice>): Double {
        val targetTimestamp = date.time
        var closestPrice: Double? = null
        var minDifference = Long.MAX_VALUE

        for (price in sp500Prices) {
            val timestamp = dateFormat.parse(price.date)?.time ?: continue
            val difference = abs(targetTimestamp - timestamp)

            if (difference < minDifference) {
                minDifference = difference
                closestPrice = price.open
            }
        }

        return closestPrice ?: 0.0
    }
}

