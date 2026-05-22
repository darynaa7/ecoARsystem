package diploma.project.eco_ar.core.ui.navigation.nested

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation3.runtime.NavKey
import diploma.project.eco_ar.core.domain.serialization.SnapshotStateListSerializer
import kotlinx.serialization.Serializable

typealias NestedChildren = SnapshotStateList<NestedRoute>?

@Immutable
@Serializable
abstract class NestedRoute(
    @Serializable(with = SnapshotStateListSerializer::class)
    val children: NestedChildren = null
) : NavKey