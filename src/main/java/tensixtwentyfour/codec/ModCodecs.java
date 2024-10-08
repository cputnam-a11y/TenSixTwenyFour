package tensixtwentyfour.codec;

import com.mojang.serialization.Codec;

import java.util.HashMap;

public class ModCodecs {
    public static <K, V> Codec<HashMap<K, V>> hashMap(Codec<K> keyCodec, Codec<V> valueCodec) {
        return Codec.unboundedMap(keyCodec, valueCodec).xmap(HashMap::new, (map) -> map);

    }
}
