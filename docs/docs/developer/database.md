---
sidebar_position: 8
---

# 💾 Base de datos

## Arquitectura

El proyecto utiliza **Room** como abstracción sobre SQLite.

```
Room Database
    ↓
DAO (Data Access Objects)
    ↓
Entities (Tablas)
    ↓
SQLite
```

---

## Configuración

### AppDatabase

```kotlin
@Database(
    entities = [
        UserEntity::class,
        SessionEntity::class,
        ActivityEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun sessionDao(): SessionDao
    abstract fun activityDao(): ActivityDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "app_db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
```

---

## Entidades (Tablas)

### UserEntity

```kotlin
@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val email: String,
    @ColumnInfo(name = "created_at")
    val createdAt: Long,
    @ColumnInfo(name = "updated_at")
    val updatedAt: Long
)
```

### SessionEntity

```kotlin
@Entity(
    tableName = "sessions",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class SessionEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "user_id")
    val userId: String,
    val token: String,
    @ColumnInfo(name = "expires_at")
    val expiresAt: Long
)
```

---

## DAOs (Data Access Objects)

```kotlin
@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserById(userId: String): UserEntity?

    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<UserEntity>>

    @Update
    suspend fun updateUser(user: UserEntity)

    @Delete
    suspend fun deleteUser(user: UserEntity)
}

@Dao
interface SessionDao {
    @Insert
    suspend fun insertSession(session: SessionEntity)

    @Query("SELECT * FROM sessions WHERE user_id = :userId")
    suspend fun getUserSession(userId: String): SessionEntity?

    @Query("DELETE FROM sessions WHERE user_id = :userId")
    suspend fun deleteUserSessions(userId: String)
}
```

---

## Migraciones

Si cambias el schema de BD, necesitas migrar:

```kotlin
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE users ADD COLUMN phone TEXT DEFAULT NULL"
        )
    }
}

Room.databaseBuilder(context, AppDatabase::class.java, "app_db")
    .addMigrations(MIGRATION_1_2)
    .build()
```

---

## Pruebas de BD

```kotlin
@RunWith(AndroidJUnit4::class)
class UserDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var userDao: UserDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        ).build()
        userDao = database.userDao()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun insertAndRead() = runTest {
        val user = UserEntity("1", "Juan", "juan@test.com", 0, 0)
        userDao.insertUser(user)
        
        val retrieved = userDao.getUserById("1")
        assertEquals(user, retrieved)
    }
}
```

---

**Siguiente:** [Testing](./testing)
