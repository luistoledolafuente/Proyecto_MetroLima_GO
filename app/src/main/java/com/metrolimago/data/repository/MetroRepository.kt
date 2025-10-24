package com.metrolimago.data.repository

import com.metrolimago.data.dao.EstacionDao
import com.metrolimago.data.model.EstacionEntity
import kotlinx.coroutines.flow.Flow

class MetroRepository(private val estacionDao: EstacionDao) {
    fun getEstaciones(): Flow<List<EstacionEntity>> = estacionDao.getEstaciones()
}
