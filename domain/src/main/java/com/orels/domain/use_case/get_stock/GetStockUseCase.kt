package com.orels.domain.use_case.get_stock

import com.orels.domain.model.entities.Stock
import com.orels.domain.model.interactors.Repository
import com.orels.domain.use_case.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * @author Orel Zilberman
 * 05/10/2022
 */
class GetStockUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(ticker: String): Flow<Resource<Stock?>> = flow {
        try {
            emit(Resource.Loading())
            val stock = repository.getStock(ticker = ticker)
            emit(Resource.Success(stock))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}