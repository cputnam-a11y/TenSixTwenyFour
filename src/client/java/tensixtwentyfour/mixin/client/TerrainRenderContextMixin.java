package tensixtwentyfour.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import net.fabricmc.fabric.impl.client.indigo.renderer.render.TerrainRenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import tensixtwentyfour.cca.ModChunkComponents;

@SuppressWarnings("UnstableApiUsage")
@Mixin(TerrainRenderContext.class)
public class TerrainRenderContextMixin {
    @ModifyVariable(method = "tessellateBlock", at = @At("HEAD"), argsOnly = true)
    private BakedModel tessellateBlock(BakedModel model, @Local(argsOnly = true) BlockState state, @Local(argsOnly = true) BlockPos pos){
        state = ModChunkComponents.CLONE_STATE.get(MinecraftClient.getInstance().world.getChunk(pos)).get(pos);
        if (state != null) {
            return MinecraftClient.getInstance().getBlockRenderManager().getModel(state);
        }
        return model;


    }
}
