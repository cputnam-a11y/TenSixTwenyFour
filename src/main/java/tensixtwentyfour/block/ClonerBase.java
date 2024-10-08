package tensixtwentyfour.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import tensixtwentyfour.TenSixTwentyFour;
import tensixtwentyfour.cca.ModChunkComponents;

public class ClonerBase extends Block {
    public ClonerBase() {
        super(Settings.create().breakInstantly());
    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (player.getStackInHand(hand).getItem() instanceof BlockItem) {
            if (world.isClient) {
                return ItemActionResult.SUCCESS;
            }
            if (player.isSneaking() && player.getStackInHand(hand).isEmpty()) {
                ModChunkComponents.CLONE_STATE.get(world.getChunk(pos)).remove(pos);
                return ItemActionResult.SUCCESS;
            }
            TenSixTwentyFour.LOGGER.info("Adding State: {}, at Pos: {}", ((BlockItem) player.getStackInHand(hand).getItem()).getBlock().getDefaultState(), pos);
            ModChunkComponents.CLONE_STATE.get(world.getChunk(pos)).put(pos, ((BlockItem) player.getStackInHand(hand).getItem()).getBlock().getDefaultState());
            return ItemActionResult.SUCCESS;
        }
        return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
    }
}
