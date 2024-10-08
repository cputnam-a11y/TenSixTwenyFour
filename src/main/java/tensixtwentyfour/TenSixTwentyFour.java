package tensixtwentyfour;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tensixtwentyfour.block.ModBlocks;
import tensixtwentyfour.cca.ModChunkComponents;

import java.util.Map;

public class TenSixTwentyFour implements ModInitializer {
    public static final String MOD_ID = "tensixtwentyfour";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModBlocks.init();
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(CommandManager.literal("cloner-server").executes(context -> {
                PlayerEntity player = context.getSource().getPlayer();
                Chunk chunk = player.getWorld().getChunk(player.getBlockPos());
                for (Map.Entry<BlockPos, BlockState> entry : ModChunkComponents.CLONE_STATE.get(chunk).entrySet()) {
                    context.getSource().sendFeedback(() -> Text.literal(
                            String.format("%s, %s", entry.getKey().toString(),
                                    entry.getValue().toString())
                    ), false);
                }
                return 1;
            }).then(CommandManager.literal("sync").executes(context -> {
                PlayerEntity player = context.getSource().getPlayer();
                Chunk chunk = player.getWorld().getChunk(player.getBlockPos());
                ModChunkComponents.CLONE_STATE.sync(chunk);
                chunk.setNeedsSaving(true);
                return 1;
            })));
        });
    }

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }
}