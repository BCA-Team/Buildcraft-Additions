package buildcraftAdditions.villager;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.Buffer;
import java.util.List;
import java.util.Random;

import buildcraft.BuildCraftBuilders;
import buildcraft.BuildCraftEnergy;
import buildcraft.BuildCraftTransport;
import buildcraft.api.blueprints.BlueprintDeployer;
import buildcraft.core.blueprints.RealBlueprintDeployer;
import buildcraft.transport.BlockGenericPipe;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import net.minecraft.world.gen.structure.StructureVillagePieces.Start;
import net.minecraftforge.common.util.ForgeDirection;

public class ComponentPowerPlant extends StructureVillagePieces.House1 {
	public static final ResourceLocation blueprint = new ResourceLocation("bcadditions", "blueprints/Redstoneengine-357d6c42def2dad519636d8e7980d72a5284b3de708febd300bb1fc1525f785b.bpt");
	
	private int averageGroundLevel = -1;
	
	public ComponentPowerPlant(){}

	public ComponentPowerPlant(Start villagePiece, int par2, Random par3Random, StructureBoundingBox par4StructureBoundingBox, int par5){
    	super();
        this.coordBaseMode = par5;
        this.boundingBox = par4StructureBoundingBox;
        
    }
	
    public static ComponentPowerPlant buildComponent (Start villagePiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5){
    	StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p1, p2, p3, 0, 0, 0, 12, 12, 12, p4);
        return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null ? new ComponentPowerPlant(villagePiece, p5, random, structureboundingbox, p4) : null;
    }
    
    @Override
    public boolean addComponentParts (World world, Random random, StructureBoundingBox sbb){
    	if (world.isRemote)
    		return true;
    	
        if (this.averageGroundLevel < 0)
        {
            this.averageGroundLevel = this.getAverageGroundLevel(world, sbb);

            if (this.averageGroundLevel < 0)
            {
                return true;
            }

            this.boundingBox.offset(0, this.averageGroundLevel - this.boundingBox.maxY + 11, 0);
        }
        URL url = ComponentPowerPlant.class.getResource("/assets/bcadditions/blueprints/Redstoneengine-357d6c42def2dad519636d8e7980d72a5284b3de708febd300bb1fc1525f785b.bpt");
        try{
        InputStream f =  Minecraft.getMinecraft().getResourceManager().getResource(blueprint).getInputStream();
        
        		ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        		int nRead;
        		byte[] data = new byte[16384];

        		while ((nRead = f.read(data, 0, data.length)) != -1) {
        		  buffer.write(data, 0, nRead);
        		}

        		buffer.flush();

        		data =  buffer.toByteArray();
        		int i = this.boundingBox.minX;
                int j = this.boundingBox.minY;
                int k = this.boundingBox.minZ;
        		BlueprintDeployer.instance.deployBlueprintFromFileStream(world, i, j-3, k, ForgeDirection.getOrientation(this.getMetadataWithOffset(BuildCraftBuilders.builderBlock, 0)), data);
        } catch (Throwable e){
        	System.out.println(e.getStackTrace());
        }
        
        
        	
        spawnVillagers(world, sbb, 0, 0, 0, 2);
        return true;
    }
    
    @Override
    protected int getVillagerType (int par1)
    {
        return 457;
    }
}
