package android.system.myapplication

import android.content.Context
import android.system.myapplication.models.MyObjectBox
import io.objectbox.BoxStore
import io.objectbox.model.ValidateOnOpenMode
import java.io.File

object Database {
    private var database: BoxStore? = null

    @Synchronized
    fun getDatabase(context: Context): BoxStore {
        if (database != null) {
            return database!!
        }

        val store = File(context.filesDir, "objectbox")

        val builder = MyObjectBox
            .builder()
            .androidContext(context)
            .baseDirectory(store)
            .validateOnOpen(ValidateOnOpenMode.WithLeaves)
            .validateOnOpenPageLimit(20)
            .maxReaders(200)

        database = builder.build()
        return database!!
    }
}
