package edu.moravian.sketchpad.data

/**
 * Repository that acts as the single source of truth for sketch and stroke data.
 */
class SketchRepository(
    private val dao: SketchDao,
) {
    // Sketch operations
    suspend fun insertSketch(sketch: Sketch): Long = dao.insertSketch(sketch)

    suspend fun getAllSketches(): List<Sketch> = dao.getAllSketches()

    suspend fun getSketchById(id: Long): Sketch? = dao.getSketchById(id)

    suspend fun deleteSketch(sketch: Sketch) = dao.deleteSketch(sketch)

    suspend fun deleteSketchById(id: Long) = dao.deleteSketchById(id)

    // Stroke operations
    suspend fun insertStroke(stroke: StrokeData): Long = dao.insertStroke(stroke)

    suspend fun getStrokesForSketch(sketchId: Long): List<StrokeData> =
        dao.getStrokesForSketch(sketchId)

    suspend fun deleteStroke(stroke: StrokeData) = dao.deleteStroke(stroke)

    suspend fun deleteStrokesForSketch(sketchId: Long) = dao.deleteStrokesForSketch(sketchId)
}