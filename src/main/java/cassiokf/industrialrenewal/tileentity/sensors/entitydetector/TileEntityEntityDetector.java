package cassiokf.industrialrenewal.tileentity.sensors.entitydetector;

import cassiokf.industrialrenewal.network.NetworkHandler;
import cassiokf.industrialrenewal.network.PacketEntityDetector;
import cassiokf.industrialrenewal.network.PacketReturnEntityDetector;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class TileEntityEntityDetector extends TileEntity implements ITickable {

    private EnumFacing blockFacing = EnumFacing.DOWN;
    private int distanceD = 6;
    private int tick = 0;
    private entityEnum eEnum = entityEnum.ALL;

    public enum entityEnum {
        ALL(0),
        PLAYERS(1),
        MOBHOSTIL(2),
        MOBPASSIVE(3),
        ITEMS(4),
        CARTS(5);

        public int intValue;

        entityEnum(int value) {
            intValue = value;
        }

        public static entityEnum valueOf(int no) {
            if (no > entityEnum.values().length - 1) {
                no = 0;
            }
            for (entityEnum l : entityEnum.values()) {
                if (l.intValue == no) return l;
            }
            throw new IllegalArgumentException("entityEnum not found");
        }
    }

    private Class<? extends Entity> getEntityToFilter() {
        switch (eEnum) {
            default:
            case ALL:
                return Entity.class;
            case PLAYERS:
                return EntityPlayer.class;
            case MOBHOSTIL:
                return EntityMob.class;
            case MOBPASSIVE:
                return EntityCreature.class;
            case ITEMS:
                return EntityItem.class;
            case CARTS:
                return EntityMinecart.class;
        }
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return (oldState.getBlock() != newState.getBlock());
    }

    @Override
    public void update() {
        if (!this.world.isRemote && ((tick % 20) == 0)) {
            tick = 0;
            changeState(passRedstone());
        }
        tick++;
    }

    private void changeState(boolean value) {
        IBlockState state = this.world.getBlockState(this.pos).getActualState(this.world, this.pos);
        boolean actualValue = state.getValue(BlockEntityDetector.ACTIVE);
        if (actualValue != value) {
            this.world.setBlockState(this.pos, state.withProperty(BlockEntityDetector.BASE, this.getBlockFacing()).withProperty(BlockEntityDetector.ACTIVE, value), 3);
            syncChanges();
        }
    }

    public boolean passRedstone() {
        IBlockState state = this.world.getBlockState(this.pos);
        EnumFacing inFace = state.getValue(BlockEntityDetector.FACING);
        return checkEntity(getEntityToFilter(), inFace);
    }


    private boolean checkEntity(Class<? extends Entity> entity, EnumFacing facing) {
        int distance = distanceD + 1;
        double posX = this.pos.getX() + 1;
        double posY = this.pos.getY() + 1;
        double posZ = this.pos.getZ() + 1;
        switch (facing) {
            case DOWN:
                posY = posY + distance;
                break;
            case UP:
                posY = posY - distance;
                break;
            case NORTH:
                posZ = posZ - distance;
                break;
            case SOUTH:
                posZ = posZ + distance;
                break;
            case WEST:
                posX = posX - distance;
                break;
            case EAST:
                posX = posX + distance;
                break;
        }
        List<? extends Entity> entities = this.world.getEntitiesWithinAABB(entity,
                new AxisAlignedBB((double) this.pos.getX(), (double) this.pos.getY(), (double) this.pos.getZ(), posX, posY, posZ));
        return !entities.isEmpty();
    }

    public void setBlockFacing(EnumFacing facing) {
        blockFacing = facing;
        markDirty();
    }

    public EnumFacing getBlockFacing() {
        return blockFacing;
    }

    public entityEnum getEntityEnum() {
        return eEnum;
    }

    public void setNextEntityEnum(boolean value) {
        int old = getEntityEnum().intValue;
        if (value) {
            eEnum = TileEntityEntityDetector.entityEnum.valueOf(old + 1);
        }
        syncChanges();
    }

    public void setEntityEnum(int value) {
        eEnum = entityEnum.valueOf(value);
        markDirty();
    }

    public void setNextDistance() {
        distanceD = distanceD + 1;
        if (distanceD > 8) {
            distanceD = 1;
        }
        syncChanges();
    }

    public void setDistance(int distance) {
        distanceD = distance;
        markDirty();
    }

    @Override
    public void onLoad() {
        if (world.isRemote) {
            NetworkHandler.INSTANCE.sendToServer(new PacketReturnEntityDetector(this));
        }
    }

    private void syncChanges() {
        if (!this.world.isRemote) {
            NetworkHandler.INSTANCE.sendToAllAround(new PacketEntityDetector(TileEntityEntityDetector.this), new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 32));
        }
        this.world.notifyNeighborsOfStateChange(this.pos, this.blockType, true);
        markDirty();
    }

    public int getDistance() {
        return distanceD;
    }

    @Override
    public NBTTagCompound writeToNBT(final NBTTagCompound tag) {
        tag.setInteger("baseFacing", blockFacing.getIndex());
        tag.setInteger("distance", distanceD);
        tag.setInteger("EnumConfig", this.eEnum.intValue);
        return super.writeToNBT(tag);
    }

    @Override
    public void readFromNBT(final NBTTagCompound tag) {
        super.readFromNBT(tag);
        blockFacing = EnumFacing.getFront(tag.getInteger("baseFacing"));
        distanceD = tag.getInteger("distance");
        eEnum = entityEnum.valueOf(tag.getInteger("EnumConfig"));
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(getPos(), 0, getUpdateTag());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onDataPacket(final NetworkManager net, final SPacketUpdateTileEntity pkt) {
        readFromNBT(pkt.getNbtCompound());
    }
}
