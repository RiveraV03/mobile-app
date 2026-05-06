package edu.moravian.sketchpad.data

/**
 * Preferences Repository - structure for 6+ preference values
 *
 * Preferences managed:
 * 1. DEFAULT_BRUSH_SIZE - Default brush size (1-20)
 * 2. DEFAULT_BRUSH_COLOR - Default brush color (ARGB)
 * 3. THEME_MODE - Light/dark theme
 * 4. GALLERY_SORT_ORDER - Sort gallery by name/date/recent
 * 5. AUTO_SAVE_ENABLED - Enable auto-save feature
 * 6. LAST_OPENED_SKETCH_ID - Last sketch user opened
 */
class PreferencesRepository {
    // Preference keys
    companion object {
        const val DEFAULT_BRUSH_SIZE = "default_brush_size"
        const val DEFAULT_BRUSH_COLOR = "default_brush_color"
        const val THEME_MODE = "theme_mode"
        const val GALLERY_SORT_ORDER = "gallery_sort_order"
        const val AUTO_SAVE_ENABLED = "auto_save_enabled"
        const val LAST_OPENED_SKETCH_ID = "last_opened_sketch_id"
    }

    // Default values
    var defaultBrushSize: Float = 5f
    var defaultBrushColor: Int = 0xFF000000.toInt()
    var themeMode: String = "light"
    var gallerySortOrder: String = "recent"
    var autoSaveEnabled: Boolean = true
    var lastOpenedSketchId: Int = 0
}