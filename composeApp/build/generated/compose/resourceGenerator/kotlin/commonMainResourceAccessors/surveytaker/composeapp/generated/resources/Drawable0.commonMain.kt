@file:OptIn(InternalResourceApi::class)

package surveytaker.composeapp.generated.resources

import kotlin.OptIn
import kotlin.String
import kotlin.collections.MutableMap
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.InternalResourceApi
import org.jetbrains.compose.resources.ResourceContentHash
import org.jetbrains.compose.resources.ResourceItem

private const val MD: String = "composeResources/surveytaker.composeapp.generated.resources/"

@delegate:ResourceContentHash(1_701_585_963)
internal val Res.drawable.arrow_back: DrawableResource by lazy {
      DrawableResource("drawable:arrow_back", setOf(
        ResourceItem(setOf(), "${MD}drawable/arrow_back.xml", -1, -1),
      ))
    }

@delegate:ResourceContentHash(1_804_394_025)
internal val Res.drawable.arrow_back_ios: DrawableResource by lazy {
      DrawableResource("drawable:arrow_back_ios", setOf(
        ResourceItem(setOf(), "${MD}drawable/arrow_back_ios.svg", -1, -1),
      ))
    }

@InternalResourceApi
internal fun _collectCommonMainDrawable0Resources(map: MutableMap<String, DrawableResource>) {
  map.put("arrow_back", Res.drawable.arrow_back)
  map.put("arrow_back_ios", Res.drawable.arrow_back_ios)
}
