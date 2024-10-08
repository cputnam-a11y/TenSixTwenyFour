package tensixtwentyfour;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.chunk.Chunk;
import tensixtwentyfour.block.ClonerBase;
import tensixtwentyfour.block.ModBlocks;
import tensixtwentyfour.cca.ModChunkComponents;

public class TenSixTwentyFourClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            dispatcher.register(ClientCommandManager.literal("cloner").executes(context -> {
                MinecraftClient.getInstance().execute(() -> {
                    PlayerEntity player = MinecraftClient.getInstance().player;
                    Chunk chunk = player.getWorld().getChunk(player.getBlockPos());
                    for (var entry : ModChunkComponents.CLONE_STATE.get(chunk).entrySet())
                        context.getSource().sendFeedback(
                                Text.literal(
                                        String.format("%s, %s",entry.getKey().toString(),
                                                entry.getValue().toString())
                                )
                        );
                });
                return 1;
            }));
        });
    }
}