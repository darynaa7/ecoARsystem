package diploma.project.eco_ar.core.domain.serialization

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import kotlinx.serialization.KSerializer
import kotlinx.serialization.PolymorphicSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object SnapshotStateListSerializer : KSerializer<SnapshotStateList<Any>> {

    private val delegateSerializer = ListSerializer(PolymorphicSerializer(Any::class))

    override val descriptor = delegateSerializer.descriptor

    override fun serialize(encoder: Encoder, value: SnapshotStateList<Any>) {
        encoder.encodeSerializableValue(delegateSerializer, value.toList())
    }

    override fun deserialize(decoder: Decoder): SnapshotStateList<Any> {
        return decoder.decodeSerializableValue(delegateSerializer).toMutableStateList()
    }
}