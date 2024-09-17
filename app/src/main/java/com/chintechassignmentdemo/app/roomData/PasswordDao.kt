package com.chintechassignmentdemo.app.roomData

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PasswordDao {
    @Insert
    suspend fun insert(password: Password)

    @Update
    suspend fun update(password: Password)

    @Query("DELETE FROM passwords WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT * FROM passwords")
    fun getAllPasswords(): Flow<List<Password>>
}