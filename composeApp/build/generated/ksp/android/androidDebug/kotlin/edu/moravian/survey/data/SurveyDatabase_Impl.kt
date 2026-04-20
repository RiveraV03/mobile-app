package edu.moravian.survey.`data`

import androidx.room.InvalidationTracker
import androidx.room.RoomOpenDelegate
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.room.util.TableInfo
import androidx.room.util.TableInfo.Companion.read
import androidx.room.util.dropFtsSyncTriggers
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import javax.`annotation`.processing.Generated
import kotlin.Lazy
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import kotlin.collections.Map
import kotlin.collections.MutableList
import kotlin.collections.MutableMap
import kotlin.collections.MutableSet
import kotlin.collections.Set
import kotlin.collections.mutableListOf
import kotlin.collections.mutableMapOf
import kotlin.collections.mutableSetOf
import kotlin.reflect.KClass

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class SurveyDatabase_Impl : SurveyDatabase() {
  private val _surveyDao: Lazy<SurveyDao> = lazy {
    SurveyDao_Impl(this)
  }

  protected override fun createOpenDelegate(): RoomOpenDelegate {
    val _openDelegate: RoomOpenDelegate = object : RoomOpenDelegate(1,
        "1e310baa982b1ea1710850fde3af56aa", "1ef4c14511cbd466f6feb6eaa6cdcfc2") {
      public override fun createAllTables(connection: SQLiteConnection) {
        connection.execSQL("CREATE TABLE IF NOT EXISTS `survey_results` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `score` INTEGER NOT NULL, `timestamp` INTEGER NOT NULL, `soundsAnswer` TEXT, `emotionsIndices` TEXT, `emotionsOther` TEXT)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)")
        connection.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '1e310baa982b1ea1710850fde3af56aa')")
      }

      public override fun dropAllTables(connection: SQLiteConnection) {
        connection.execSQL("DROP TABLE IF EXISTS `survey_results`")
      }

      public override fun onCreate(connection: SQLiteConnection) {
      }

      public override fun onOpen(connection: SQLiteConnection) {
        internalInitInvalidationTracker(connection)
      }

      public override fun onPreMigrate(connection: SQLiteConnection) {
        dropFtsSyncTriggers(connection)
      }

      public override fun onPostMigrate(connection: SQLiteConnection) {
      }

      public override fun onValidateSchema(connection: SQLiteConnection):
          RoomOpenDelegate.ValidationResult {
        val _columnsSurveyResults: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsSurveyResults.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsSurveyResults.put("score", TableInfo.Column("score", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsSurveyResults.put("timestamp", TableInfo.Column("timestamp", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSurveyResults.put("soundsAnswer", TableInfo.Column("soundsAnswer", "TEXT", false, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSurveyResults.put("emotionsIndices", TableInfo.Column("emotionsIndices", "TEXT",
            false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSurveyResults.put("emotionsOther", TableInfo.Column("emotionsOther", "TEXT", false,
            0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysSurveyResults: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesSurveyResults: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoSurveyResults: TableInfo = TableInfo("survey_results", _columnsSurveyResults,
            _foreignKeysSurveyResults, _indicesSurveyResults)
        val _existingSurveyResults: TableInfo = read(connection, "survey_results")
        if (!_infoSurveyResults.equals(_existingSurveyResults)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |survey_results(edu.moravian.survey.data.SurveyResult).
              | Expected:
              |""".trimMargin() + _infoSurveyResults + """
              |
              | Found:
              |""".trimMargin() + _existingSurveyResults)
        }
        return RoomOpenDelegate.ValidationResult(true, null)
      }
    }
    return _openDelegate
  }

  protected override fun createInvalidationTracker(): InvalidationTracker {
    val _shadowTablesMap: MutableMap<String, String> = mutableMapOf()
    val _viewTables: MutableMap<String, Set<String>> = mutableMapOf()
    return InvalidationTracker(this, _shadowTablesMap, _viewTables, "survey_results")
  }

  public override fun clearAllTables() {
    super.performClear(false, "survey_results")
  }

  protected override fun getRequiredTypeConverterClasses(): Map<KClass<*>, List<KClass<*>>> {
    val _typeConvertersMap: MutableMap<KClass<*>, List<KClass<*>>> = mutableMapOf()
    _typeConvertersMap.put(SurveyDao::class, SurveyDao_Impl.getRequiredConverters())
    return _typeConvertersMap
  }

  public override fun getRequiredAutoMigrationSpecClasses(): Set<KClass<out AutoMigrationSpec>> {
    val _autoMigrationSpecsSet: MutableSet<KClass<out AutoMigrationSpec>> = mutableSetOf()
    return _autoMigrationSpecsSet
  }

  public override
      fun createAutoMigrations(autoMigrationSpecs: Map<KClass<out AutoMigrationSpec>, AutoMigrationSpec>):
      List<Migration> {
    val _autoMigrations: MutableList<Migration> = mutableListOf()
    return _autoMigrations
  }

  public override fun surveyDao(): SurveyDao = _surveyDao.value
}
