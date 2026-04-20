package edu.moravian.survey.`data`

import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import javax.`annotation`.processing.Generated
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.mutableListOf
import kotlin.reflect.KClass

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class SurveyDao_Impl(
  __db: RoomDatabase,
) : SurveyDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfSurveyResult: EntityInsertAdapter<SurveyResult>
  init {
    this.__db = __db
    this.__insertAdapterOfSurveyResult = object : EntityInsertAdapter<SurveyResult>() {
      protected override fun createQuery(): String =
          "INSERT OR ABORT INTO `survey_results` (`id`,`score`,`timestamp`,`soundsAnswer`,`emotionsIndices`,`emotionsOther`) VALUES (nullif(?, 0),?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: SurveyResult) {
        statement.bindLong(1, entity.id)
        statement.bindLong(2, entity.score.toLong())
        statement.bindLong(3, entity.timestamp)
        val _tmpSoundsAnswer: String? = entity.soundsAnswer
        if (_tmpSoundsAnswer == null) {
          statement.bindNull(4)
        } else {
          statement.bindText(4, _tmpSoundsAnswer)
        }
        val _tmpEmotionsIndices: String? = entity.emotionsIndices
        if (_tmpEmotionsIndices == null) {
          statement.bindNull(5)
        } else {
          statement.bindText(5, _tmpEmotionsIndices)
        }
        val _tmpEmotionsOther: String? = entity.emotionsOther
        if (_tmpEmotionsOther == null) {
          statement.bindNull(6)
        } else {
          statement.bindText(6, _tmpEmotionsOther)
        }
      }
    }
  }

  public override suspend fun insert(result: SurveyResult): Long = performSuspending(__db, false,
      true) { _connection ->
    val _result: Long = __insertAdapterOfSurveyResult.insertAndReturnId(_connection, result)
    _result
  }

  public override suspend fun getAll(): List<SurveyResult> {
    val _sql: String = "SELECT * FROM survey_results ORDER BY timestamp DESC"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfScore: Int = getColumnIndexOrThrow(_stmt, "score")
        val _columnIndexOfTimestamp: Int = getColumnIndexOrThrow(_stmt, "timestamp")
        val _columnIndexOfSoundsAnswer: Int = getColumnIndexOrThrow(_stmt, "soundsAnswer")
        val _columnIndexOfEmotionsIndices: Int = getColumnIndexOrThrow(_stmt, "emotionsIndices")
        val _columnIndexOfEmotionsOther: Int = getColumnIndexOrThrow(_stmt, "emotionsOther")
        val _result: MutableList<SurveyResult> = mutableListOf()
        while (_stmt.step()) {
          val _item: SurveyResult
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpScore: Int
          _tmpScore = _stmt.getLong(_columnIndexOfScore).toInt()
          val _tmpTimestamp: Long
          _tmpTimestamp = _stmt.getLong(_columnIndexOfTimestamp)
          val _tmpSoundsAnswer: String?
          if (_stmt.isNull(_columnIndexOfSoundsAnswer)) {
            _tmpSoundsAnswer = null
          } else {
            _tmpSoundsAnswer = _stmt.getText(_columnIndexOfSoundsAnswer)
          }
          val _tmpEmotionsIndices: String?
          if (_stmt.isNull(_columnIndexOfEmotionsIndices)) {
            _tmpEmotionsIndices = null
          } else {
            _tmpEmotionsIndices = _stmt.getText(_columnIndexOfEmotionsIndices)
          }
          val _tmpEmotionsOther: String?
          if (_stmt.isNull(_columnIndexOfEmotionsOther)) {
            _tmpEmotionsOther = null
          } else {
            _tmpEmotionsOther = _stmt.getText(_columnIndexOfEmotionsOther)
          }
          _item =
              SurveyResult(_tmpId,_tmpScore,_tmpTimestamp,_tmpSoundsAnswer,_tmpEmotionsIndices,_tmpEmotionsOther)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getMostRecent(): SurveyResult? {
    val _sql: String = "SELECT * FROM survey_results ORDER BY timestamp DESC LIMIT 1"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfScore: Int = getColumnIndexOrThrow(_stmt, "score")
        val _columnIndexOfTimestamp: Int = getColumnIndexOrThrow(_stmt, "timestamp")
        val _columnIndexOfSoundsAnswer: Int = getColumnIndexOrThrow(_stmt, "soundsAnswer")
        val _columnIndexOfEmotionsIndices: Int = getColumnIndexOrThrow(_stmt, "emotionsIndices")
        val _columnIndexOfEmotionsOther: Int = getColumnIndexOrThrow(_stmt, "emotionsOther")
        val _result: SurveyResult?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpScore: Int
          _tmpScore = _stmt.getLong(_columnIndexOfScore).toInt()
          val _tmpTimestamp: Long
          _tmpTimestamp = _stmt.getLong(_columnIndexOfTimestamp)
          val _tmpSoundsAnswer: String?
          if (_stmt.isNull(_columnIndexOfSoundsAnswer)) {
            _tmpSoundsAnswer = null
          } else {
            _tmpSoundsAnswer = _stmt.getText(_columnIndexOfSoundsAnswer)
          }
          val _tmpEmotionsIndices: String?
          if (_stmt.isNull(_columnIndexOfEmotionsIndices)) {
            _tmpEmotionsIndices = null
          } else {
            _tmpEmotionsIndices = _stmt.getText(_columnIndexOfEmotionsIndices)
          }
          val _tmpEmotionsOther: String?
          if (_stmt.isNull(_columnIndexOfEmotionsOther)) {
            _tmpEmotionsOther = null
          } else {
            _tmpEmotionsOther = _stmt.getText(_columnIndexOfEmotionsOther)
          }
          _result =
              SurveyResult(_tmpId,_tmpScore,_tmpTimestamp,_tmpSoundsAnswer,_tmpEmotionsIndices,_tmpEmotionsOther)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getById(id: Long): SurveyResult? {
    val _sql: String = "SELECT * FROM survey_results WHERE id = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfScore: Int = getColumnIndexOrThrow(_stmt, "score")
        val _columnIndexOfTimestamp: Int = getColumnIndexOrThrow(_stmt, "timestamp")
        val _columnIndexOfSoundsAnswer: Int = getColumnIndexOrThrow(_stmt, "soundsAnswer")
        val _columnIndexOfEmotionsIndices: Int = getColumnIndexOrThrow(_stmt, "emotionsIndices")
        val _columnIndexOfEmotionsOther: Int = getColumnIndexOrThrow(_stmt, "emotionsOther")
        val _result: SurveyResult?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpScore: Int
          _tmpScore = _stmt.getLong(_columnIndexOfScore).toInt()
          val _tmpTimestamp: Long
          _tmpTimestamp = _stmt.getLong(_columnIndexOfTimestamp)
          val _tmpSoundsAnswer: String?
          if (_stmt.isNull(_columnIndexOfSoundsAnswer)) {
            _tmpSoundsAnswer = null
          } else {
            _tmpSoundsAnswer = _stmt.getText(_columnIndexOfSoundsAnswer)
          }
          val _tmpEmotionsIndices: String?
          if (_stmt.isNull(_columnIndexOfEmotionsIndices)) {
            _tmpEmotionsIndices = null
          } else {
            _tmpEmotionsIndices = _stmt.getText(_columnIndexOfEmotionsIndices)
          }
          val _tmpEmotionsOther: String?
          if (_stmt.isNull(_columnIndexOfEmotionsOther)) {
            _tmpEmotionsOther = null
          } else {
            _tmpEmotionsOther = _stmt.getText(_columnIndexOfEmotionsOther)
          }
          _result =
              SurveyResult(_tmpId,_tmpScore,_tmpTimestamp,_tmpSoundsAnswer,_tmpEmotionsIndices,_tmpEmotionsOther)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
