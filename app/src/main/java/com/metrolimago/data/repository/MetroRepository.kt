package com.metrolimago.data.repository

import com.metrolimago.data.dao.EstacionDao

class MetroRepository(private val estacionDao: EstacionDao) {
    val todasLasEstaciones = estacionDao.obtenerTodas()
}