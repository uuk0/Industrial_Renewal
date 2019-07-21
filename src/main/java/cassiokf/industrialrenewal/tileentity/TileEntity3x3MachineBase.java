package cassiokf.industrialrenewal.tileentity;

import cassiokf.industrialrenewal.blocks.Block3x3x3Base;
import cassiokf.industrialrenewal.util.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import java.util.List;

public abstract class TileEntity3x3MachineBase<TE extends TileEntity3x3MachineBase> extends TileFluidHandlerBase implements ICapabilityProvider
{
    private boolean master;
    private boolean breaking;
    private TE masterTE;
    private boolean masterChecked = false;
    private boolean faceChecked = false;
    private int faceIndex;

    @Override
    public void onLoad()
    {
        this.getMaster();
    }

    public TE getMaster()
    {
        if (masterTE == null || masterTE.isInvalid())
        {
            List<BlockPos> list = Utils.getBlocksIn3x3x3Centered(this.pos);
            for (BlockPos currentPos : list)
            {
                Block block = world.getBlockState(currentPos).getBlock();
                if (block instanceof Block3x3x3Base && ((TileEntity3x3MachineBase) world.getTileEntity(currentPos)).isMaster())
                {
                    masterTE = (TE) world.getTileEntity(currentPos);
                    return masterTE;
                }
            }
            return null;
        }
        return masterTE;
    }

    public void breakMultiBlocks()
    {
        if (!this.isMaster())
        {
            if (getMaster() != null)
            {
                getMaster().breakMultiBlocks();
            }
            return;
        }
        if (!breaking)
        {
            breaking = true;
            List<BlockPos> list = Utils.getBlocksIn3x3x3Centered(this.pos);
            for (BlockPos currentPos : list)
            {
                Block block = world.getBlockState(currentPos).getBlock();
                if (block instanceof Block3x3x3Base) world.setBlockToAir(currentPos);
            }
        }
    }

    public EnumFacing getMasterFacing()
    {
        if (faceChecked) return EnumFacing.byIndex(faceIndex);

        EnumFacing facing = world.getBlockState(getMaster().getPos()).getValue(Block3x3x3Base.FACING);
        faceChecked = true;
        faceIndex = facing.getIndex();
        return facing;
    }

    public boolean isMaster()
    {
        if (masterChecked) return this.master;

        IBlockState state = this.world.getBlockState(this.pos);
        if (!(state.getBlock() instanceof Block3x3x3Base)) return false;
        master = state.getValue(Block3x3x3Base.MASTER);
        this.markDirty();
        masterChecked = true;
        return master;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        compound.setBoolean("master", this.isMaster());
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        this.master = compound.getBoolean("master");
        super.readFromNBT(compound);
    }
}
