package tensixtwentyfour.cca;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtOps;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import tensixtwentyfour.codec.ModCodecs;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CloneStateChunkComponent implements AutoSyncedComponent, Map<BlockPos, BlockState> {
    private volatile HashMap<BlockPos, BlockState> cloneMap;
    private final Chunk owner;
    public static final Codec<HashMap<BlockPos, BlockState>> CODEC = ModCodecs.hashMap(BlockPos.CODEC, BlockState.CODEC);
    public CloneStateChunkComponent(Chunk chunk) {
        this.cloneMap = new HashMap<>();
        this.owner = chunk;
    }
    @Override
    public void readFromNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
        HashMap<BlockPos, BlockState> cloneMap = new HashMap<>();
        NbtCompound cloneMapTag = tag.getCompound("cloneMap");
        NbtList entries = cloneMapTag.getList("entries", 10);
        for (int i = 0; i < cloneMapTag.getInt("size"); i++) {
            NbtCompound entryTag = entries.getCompound(i);
            NbtCompound blockPosTag = entryTag.getCompound("key");
            BlockPos key = new BlockPos(blockPosTag.getInt("x"), blockPosTag.getInt("y"), blockPosTag.getInt("z"));
            BlockState value = BlockState.CODEC.parse(NbtOps.INSTANCE, entryTag.get("value")).getOrThrow();
            cloneMap.put(key, value);
        }
        this.cloneMap = cloneMap;
    }

    @Override
    public void writeToNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
        NbtCompound cloneMapTag = new NbtCompound();
        cloneMapTag.putInt("size", cloneMap.size());
        NbtList entries = new NbtList();
        for (Map.Entry<BlockPos, BlockState> entry : cloneMap.entrySet()) {
            NbtCompound entryTag = new NbtCompound();
            NbtCompound blockPosTag = new NbtCompound();
            blockPosTag.putInt("x", entry.getKey().getX());
            blockPosTag.putInt("y", entry.getKey().getY());
            blockPosTag.putInt("z", entry.getKey().getZ());
            entryTag.put("key", blockPosTag);
            entryTag.put("value", BlockState.CODEC.encodeStart(NbtOps.INSTANCE, entry.getValue()).getOrThrow());
            entries.add(entryTag);
        }
        cloneMapTag.put("entries", entries);
    }

    @Override
    public int size() {
        return cloneMap.size();
    }

    @Override
    public boolean isEmpty() {
        return cloneMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return cloneMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return cloneMap.containsValue(value);
    }

    @Override
    public BlockState get(Object key) {
        return cloneMap.get(key);
    }

    @Override
    public @Nullable BlockState put(BlockPos key, BlockState value) {
        BlockState state = cloneMap.put(key, value);
        this.owner.syncComponent(ModChunkComponents.CLONE_STATE);
        this.owner.setNeedsSaving(true);
        return state;
    }

    @Override
    public BlockState remove(Object key) {
        BlockState state = cloneMap.remove(key);
        this.owner.syncComponent(ModChunkComponents.CLONE_STATE);
        this.owner.setNeedsSaving(true);
        return state;
    }

    @Override
    public void putAll(@NotNull Map<? extends BlockPos, ? extends BlockState> m) {
        cloneMap.putAll(m);
        this.owner.syncComponent(ModChunkComponents.CLONE_STATE);
        this.owner.setNeedsSaving(true);
    }

    @Override
    public void clear() {
        cloneMap.clear();
        this.owner.syncComponent(ModChunkComponents.CLONE_STATE);
        this.owner.setNeedsSaving(true);
    }

    @Override
    public @NotNull Set<BlockPos> keySet() {
        return cloneMap.keySet();
    }

    @Override
    public @NotNull Collection<BlockState> values() {
        return cloneMap.values();
    }

    @Override
    public @NotNull Set<Entry<BlockPos, BlockState>> entrySet() {
        return cloneMap.entrySet();
    }
}
