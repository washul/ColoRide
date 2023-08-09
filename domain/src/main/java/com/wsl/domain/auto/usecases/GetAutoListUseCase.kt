package com.wsl.domain.auto.usecases

import android.net.Uri
import com.wsl.domain.UseCase
import com.wsl.domain.auto.datasource.AutoDataSource
import com.wsl.domain.auto.mappers.AutoMapper
import com.wsl.domain.model.Auto
import com.wsl.utils.Failure
import com.wsl.utils.Result
import com.wsl.utils.map
import com.wsl.utils.onSuccess
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class GetAutoListUseCase(private val autoDataSource: AutoDataSource) : UseCase<List<Auto>> {
    override suspend fun invoke(): Result<Failure, List<Auto>> {
        return try {
            val autoMapper = AutoMapper()

            var autoList: List<Auto> = emptyList()
            var autoSelected: Auto = Auto(uuid ="")

            //Get auto selected by the user
            coroutineScope {

                async {
                    autoDataSource.getAutoSelected().map {
                        autoSelected = autoMapper.mapFromAutoEntity(it)
                    }
                }.await()
                async {
                    autoList = autoDataSource.getAutoValueFromLocalSource()
                        .map {
                            Auto(
                                uuid = it.uuid,
                                name = it.name,
                                image = Uri.parse(it.image),
                                seatNumber = it.seatNumber,
                                isSelected = autoSelected.uuid == it.uuid
                            )
                        }
                }.await()

            }

            Result.Success(autoList)
        } catch (e: Throwable) {
            e.printStackTrace()
            Result.Failure(Failure.CustomError("No auto list founded"))
        }
    }
}