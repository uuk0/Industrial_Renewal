package cassiokf.industrialrenewal.model.smartmodel;

import cassiokf.industrialrenewal.model.smartmodel.composite.PillarBaseComposite;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.IModelState;

import java.util.Collection;
import java.util.function.Function;

public class PillarEnergyCableBaseModel implements IModel
{
    public static final ResourceLocation TEXTURE_SHEET = new ResourceLocation("industrialrenewal:blocks/anvil_base");

    public static final ModelResourceLocation MODEL_CORE = new ModelResourceLocation("industrialrenewal:pillar/pillar_energy_core");
    public static final ModelResourceLocation MODEL_MASTER = EnergyCableBaseModel.MODEL_MASTER;

    public static final ModelResourceLocation MODEL_DOWN = EnergyCableBaseModel.MODEL_DOWN;
    public static final ModelResourceLocation MODEL_UP = EnergyCableBaseModel.MODEL_UP;
    public static final ModelResourceLocation MODEL_NORTH = EnergyCableBaseModel.MODEL_NORTH;
    public static final ModelResourceLocation MODEL_SOUTH = EnergyCableBaseModel.MODEL_SOUTH;
    public static final ModelResourceLocation MODEL_WEST = EnergyCableBaseModel.MODEL_WEST;
    public static final ModelResourceLocation MODEL_EAST = EnergyCableBaseModel.MODEL_EAST;

    public static final ModelResourceLocation MODEL2_DOWN = EnergyCableBaseModel.MODEL2_DOWN;
    public static final ModelResourceLocation MODEL2_UP = EnergyCableBaseModel.MODEL2_UP;
    public static final ModelResourceLocation MODEL2_NORTH = EnergyCableBaseModel.MODEL2_NORTH;
    public static final ModelResourceLocation MODEL2_SOUTH = EnergyCableBaseModel.MODEL2_SOUTH;
    public static final ModelResourceLocation MODEL2_WEST = EnergyCableBaseModel.MODEL2_WEST;
    public static final ModelResourceLocation MODEL2_EAST = EnergyCableBaseModel.MODEL2_EAST;

    public static final ModelResourceLocation MODEL3_DOWN = new ModelResourceLocation("industrialrenewal:pillar/con_down");
    public static final ModelResourceLocation MODEL3_UP = new ModelResourceLocation("industrialrenewal:pillar/con_up");
    public static final ModelResourceLocation MODEL3_NORTH = new ModelResourceLocation("industrialrenewal:pillar/con_north");
    public static final ModelResourceLocation MODEL3_SOUTH = new ModelResourceLocation("industrialrenewal:pillar/con_south");
    public static final ModelResourceLocation MODEL3_WEST = new ModelResourceLocation("industrialrenewal:pillar/con_west");
    public static final ModelResourceLocation MODEL3_EAST = new ModelResourceLocation("industrialrenewal:pillar/con_east");

    // return all other resources used by this model (not strictly needed for this example because we load all the subcomponent
    //   models during the bake anyway)
    @Override
    public Collection<ResourceLocation> getDependencies()
    {
        return ImmutableList.copyOf(new ResourceLocation[]{MODEL_CORE, MODEL_DOWN, MODEL_UP, MODEL_WEST, MODEL_EAST, MODEL_NORTH, MODEL_SOUTH,
                MODEL2_DOWN, MODEL2_UP, MODEL2_WEST, MODEL2_EAST, MODEL2_NORTH, MODEL2_SOUTH});
    }

    // return all the textures used by this model (not strictly needed for this example because we load all the subcomponent
    //   models during the bake anyway)
    @Override
    public Collection<ResourceLocation> getTextures()
    {
        return ImmutableList.copyOf(new ResourceLocation[]{TEXTURE_SHEET});
    }

    @Override
    public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter)
    {
        try
        {
            IModel subComponent = ModelLoaderRegistry.getModel(MODEL_CORE);
            IBakedModel bakedModelCore = subComponent.bake(state, format, bakedTextureGetter);

            subComponent = ModelLoaderRegistry.getModel(MODEL_MASTER);
            IBakedModel bakedModelMaster = subComponent.bake(state, format, bakedTextureGetter);

            subComponent = ModelLoaderRegistry.getModel(MODEL_DOWN);
            IBakedModel bakedModelDown = subComponent.bake(state, format, bakedTextureGetter);

            subComponent = ModelLoaderRegistry.getModel(MODEL_UP);
            IBakedModel bakedModelUp = subComponent.bake(state, format, bakedTextureGetter);

            subComponent = ModelLoaderRegistry.getModel(MODEL_WEST);
            IBakedModel bakedModelWest = subComponent.bake(state, format, bakedTextureGetter);

            subComponent = ModelLoaderRegistry.getModel(MODEL_EAST);
            IBakedModel bakedModelEast = subComponent.bake(state, format, bakedTextureGetter);

            subComponent = ModelLoaderRegistry.getModel(MODEL_NORTH);
            IBakedModel bakedModelNorth = subComponent.bake(state, format, bakedTextureGetter);

            subComponent = ModelLoaderRegistry.getModel(MODEL_SOUTH);
            IBakedModel bakedModelSouth = subComponent.bake(state, format, bakedTextureGetter);

            subComponent = ModelLoaderRegistry.getModel(MODEL2_DOWN);
            IBakedModel bakedModel2Down = subComponent.bake(state, format, bakedTextureGetter);

            subComponent = ModelLoaderRegistry.getModel(MODEL2_UP);
            IBakedModel bakedModel2Up = subComponent.bake(state, format, bakedTextureGetter);

            subComponent = ModelLoaderRegistry.getModel(MODEL2_WEST);
            IBakedModel bakedModel2West = subComponent.bake(state, format, bakedTextureGetter);

            subComponent = ModelLoaderRegistry.getModel(MODEL2_EAST);
            IBakedModel bakedModel2East = subComponent.bake(state, format, bakedTextureGetter);

            subComponent = ModelLoaderRegistry.getModel(MODEL2_NORTH);
            IBakedModel bakedModel2North = subComponent.bake(state, format, bakedTextureGetter);

            subComponent = ModelLoaderRegistry.getModel(MODEL2_SOUTH);
            IBakedModel bakedModel2South = subComponent.bake(state, format, bakedTextureGetter);

            subComponent = ModelLoaderRegistry.getModel(MODEL3_DOWN);
            IBakedModel bakedModel3Down = subComponent.bake(state, format, bakedTextureGetter);

            subComponent = ModelLoaderRegistry.getModel(MODEL3_UP);
            IBakedModel bakedModel3Up = subComponent.bake(state, format, bakedTextureGetter);

            subComponent = ModelLoaderRegistry.getModel(MODEL3_WEST);
            IBakedModel bakedModel3West = subComponent.bake(state, format, bakedTextureGetter);

            subComponent = ModelLoaderRegistry.getModel(MODEL3_EAST);
            IBakedModel bakedModel3East = subComponent.bake(state, format, bakedTextureGetter);

            subComponent = ModelLoaderRegistry.getModel(MODEL3_NORTH);
            IBakedModel bakedModel3North = subComponent.bake(state, format, bakedTextureGetter);

            subComponent = ModelLoaderRegistry.getModel(MODEL3_SOUTH);
            IBakedModel bakedModel3South = subComponent.bake(state, format, bakedTextureGetter);

            return new PillarBaseComposite(bakedModelCore, bakedModelMaster, bakedModelDown, bakedModelUp, bakedModelWest, bakedModelEast, bakedModelNorth, bakedModelSouth,
                    bakedModel2Down, bakedModel2Up, bakedModel2West, bakedModel2East, bakedModel2North, bakedModel2South,
                    bakedModel3Down, bakedModel3Up, bakedModel3West, bakedModel3East, bakedModel3North, bakedModel3South);
        } catch (Exception exception)
        {
            System.err.println("WebModel.bake() failed due to exception:" + exception);
            return ModelLoaderRegistry.getMissingModel().bake(state, format, bakedTextureGetter);
        }
    }
}
