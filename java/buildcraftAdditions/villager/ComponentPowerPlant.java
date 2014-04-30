package buildcraftAdditions.villager;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import net.minecraft.world.gen.structure.StructureVillagePieces.Start;

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
        if (this.averageGroundLevel < 0)
        {
            this.averageGroundLevel = this.getAverageGroundLevel(world, sbb);

            if (this.averageGroundLevel < 0)
            {
                return true;
            }

            this.boundingBox.offset(0, this.averageGroundLevel - this.boundingBox.maxY + 11, 0);
        }
        
        
        fillWithMetadataBlocks(world, sbb, 1, 0, 1, 11, 0, 11, Blocks.stained_hardened_clay, 12, Blocks.stained_hardened_clay, 12, false); // floor
        //walls
        this.fillWithMetadataBlocks(world, sbb, 1, 1, 1, 11, 1, 1, Blocks.stained_hardened_clay, 12, Blocks.stained_hardened_clay, 12, false);
        this.fillWithMetadataBlocks(world, sbb, 1, 1, 1, 1, 1, 11, Blocks.stained_hardened_clay, 12, Blocks.stained_hardened_clay, 12, false);
        this.fillWithMetadataBlocks(world, sbb, 11, 1, 1, 11, 1, 11, Blocks.stained_hardened_clay, 12, Blocks.stained_hardened_clay, 12, false);
        this.fillWithMetadataBlocks(world, sbb, 1, 1, 11, 11, 1, 11, Blocks.stained_hardened_clay, 12, Blocks.stained_hardened_clay, 12, false);
        
        this.fillWithMetadataBlocks(world, sbb, 1, 2, 1, 11, 2, 1, Blocks.stained_hardened_clay, 15, Blocks.stained_hardened_clay, 15, false);
        this.fillWithMetadataBlocks(world, sbb, 1, 2, 1, 1, 2, 11, Blocks.stained_hardened_clay, 15, Blocks.stained_hardened_clay, 15, false);
        this.fillWithMetadataBlocks(world, sbb, 11, 2, 1, 11, 2, 11, Blocks.stained_hardened_clay, 15, Blocks.stained_hardened_clay, 15, false);
        this.fillWithMetadataBlocks(world, sbb, 1, 2, 11, 11, 2, 11, Blocks.stained_hardened_clay, 15, Blocks.stained_hardened_clay, 15, false);
        
        this.fillWithMetadataBlocks(world, sbb, 1, 3, 1, 11, 4, 1, Blocks.stained_hardened_clay, 12, Blocks.stained_hardened_clay, 12, false);
        this.fillWithMetadataBlocks(world, sbb, 1, 3, 1, 1, 4, 11, Blocks.stained_hardened_clay, 12, Blocks.stained_hardened_clay, 12, false);
        this.fillWithMetadataBlocks(world, sbb, 11, 3, 1, 11, 4, 11, Blocks.stained_hardened_clay, 12, Blocks.stained_hardened_clay, 12, false);
        this.fillWithMetadataBlocks(world, sbb, 1, 3, 11, 11, 4, 11, Blocks.stained_hardened_clay, 12, Blocks.stained_hardened_clay, 12, false);
        
        
        spawnVillagers(world, sbb, 0, 0, 0, 2);
        return true;
    }
    
    @Override
    protected int getVillagerType (int par1)
    {
        return 457;
    }
}
