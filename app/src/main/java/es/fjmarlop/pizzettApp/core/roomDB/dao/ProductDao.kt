package es.fjmarlop.pizzettApp.core.roomDB.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import es.fjmarlop.pizzettApp.core.roomDB.entities.ProductEntity

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRoomProducts(products: List<ProductEntity>)

}