package tensixtwentyfour.mixin.client;
// thanks to enjarai for the direction on this mixin

import org.spongepowered.asm.mixin.Mixin;


@Mixin(targets = "net/minecraft/client/render/chunk/RenderedChunk")
public class RenderedChunkMixin {
//    @Shadow
//    @Final
//    private WorldChunk chunk;
//    @WrapMethod(method = "getBlockState")
//    private BlockState disguiseBlockState(BlockPos pos, Operation<BlockState> original) {
//        BlockState state = original.call(pos);
//
//        return state;
//    }
}
