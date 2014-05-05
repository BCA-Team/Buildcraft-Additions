package buildcraftAdditions.villager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Random;

import buildcraft.BuildCraftBuilders;
import buildcraft.BuildCraftEnergy;
import buildcraft.BuildCraftTransport;
import buildcraft.api.blueprints.BlueprintDeployer;
import buildcraft.core.blueprints.RealBlueprintDeployer;
import buildcraft.transport.BlockGenericPipe;
import net.minecraft.block.Block;
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
    public boolean addComponentParts (World world, Random random, StructureBoundingBox sbb)
    {
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
        
        URL url = ComponentPowerPlant.class.getResource("/assets/bcadditions/blueprints/redstone-b56a3e73126b0b7710609f3fa4b674514259d82eac9cbe2ebb77f8c107d755e3.bpt");
        File file = new File(url.getFile());
        System.out.println(file.exists());
        
        int i = this.boundingBox.minX;
        int j = this.boundingBox.minY;
        int k = this.boundingBox.minZ;
        FileInputStream f;
		try {
			f = new FileInputStream(file);
			byte [] data = new byte [(int) file.length()];
			f.read (data);
			f.close();
			
			BlueprintDeployer.instance.deployBlueprintFromFileStream(world, i, j-3, k, ForgeDirection.getOrientation(this.getMetadataWithOffset(BuildCraftBuilders.builderBlock, 0)), data);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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
