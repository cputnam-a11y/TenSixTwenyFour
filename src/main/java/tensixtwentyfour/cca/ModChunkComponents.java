package tensixtwentyfour.cca;

import org.ladysnake.cca.api.v3.chunk.ChunkComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.chunk.ChunkComponentInitializer;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistryV3;

import static tensixtwentyfour.TenSixTwentyFour.id;

public class ModChunkComponents implements ChunkComponentInitializer {
    public static final ComponentKey<CloneStateChunkComponent> CLONE_STATE =
            ComponentRegistryV3.INSTANCE.getOrCreate(id("clone_state"), CloneStateChunkComponent.class);

    @Override
    public void registerChunkComponentFactories(ChunkComponentFactoryRegistry registry) {
        registry.register(CLONE_STATE, CloneStateChunkComponent::new);
    }
}
