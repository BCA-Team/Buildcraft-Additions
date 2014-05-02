package buildcraftAdditions.villager;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import net.minecraft.world.gen.structure.StructureVillagePieces.Start;

public class ComponentPowerPlant extends StructureVillagePieces.House1 {
	
	private int averageGroundLevel = -1;
	private boolean lock = false;
	int colorBottomPart = 12;
    int colorTopPart = 1;
	
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
        if(!lock){
        
        
        int engine = random.nextInt(4);
        if (engine == 0){
        	colorBottomPart = 9;
        	}
        if (engine == 1){
        	colorBottomPart=8;
        }
        if (engine == 2){
        	colorBottomPart = 14;
        	colorTopPart = 7;
        }
        lock = true;
        }
        
        fillWithMetadataBlocks(world, sbb, 1, -1, 1, 11, -1, 11, Blocks.stained_hardened_clay, colorBottomPart, Blocks.stained_hardened_clay, colorBottomPart, false); // floor
        //walls
        this.fillWithMetadataBlocks(world, sbb, 1, 0, 1, 11, 1, 1, Blocks.stained_hardened_clay, colorBottomPart, Blocks.stained_hardened_clay, colorBottomPart, false);
        this.fillWithMetadataBlocks(world, sbb, 1, 0, 1, 1, 1, 11, Blocks.stained_hardened_clay, colorBottomPart, Blocks.stained_hardened_clay, colorBottomPart, false);
        this.fillWithMetadataBlocks(world, sbb, 11, 0, 1, 11, 1, 11, Blocks.stained_hardened_clay, colorBottomPart, Blocks.stained_hardened_clay, colorBottomPart, false);
        this.fillWithMetadataBlocks(world, sbb, 1, 0, 11, 11, 1, 11, Blocks.stained_hardened_clay, colorBottomPart, Blocks.stained_hardened_clay, colorBottomPart, false);
        
        this.fillWithMetadataBlocks(world, sbb, 1, 2, 1, 11, 2, 1, Blocks.stained_hardened_clay, 15, Blocks.stained_hardened_clay, 15, false);
        this.fillWithMetadataBlocks(world, sbb, 1, 2, 1, 1, 2, 11, Blocks.stained_hardened_clay, 15, Blocks.stained_hardened_clay, 15, false);
        this.fillWithMetadataBlocks(world, sbb, 11, 2, 1, 11, 2, 11, Blocks.stained_hardened_clay, 15, Blocks.stained_hardened_clay, 15, false);
        this.fillWithMetadataBlocks(world, sbb, 1, 2, 11, 11, 2, 11, Blocks.stained_hardened_clay, 15, Blocks.stained_hardened_clay, 15, false);
        
        this.fillWithMetadataBlocks(world, sbb, 1, 3, 1, 11, 3, 1, Blocks.stained_hardened_clay, colorBottomPart, Blocks.stained_hardened_clay, colorBottomPart, false);
        this.fillWithMetadataBlocks(world, sbb, 1, 3, 1, 1, 3, 11, Blocks.stained_hardened_clay, colorBottomPart, Blocks.stained_hardened_clay, colorBottomPart, false);
        this.fillWithMetadataBlocks(world, sbb, 11, 3, 1, 11, 3, 11, Blocks.stained_hardened_clay, colorBottomPart, Blocks.stained_hardened_clay, colorBottomPart, false);
        this.fillWithMetadataBlocks(world, sbb, 1, 3, 11, 11, 3, 11, Blocks.stained_hardened_clay, colorBottomPart, Blocks.stained_hardened_clay, colorBottomPart, false);
        
        this.fillWithMetadataBlocks(world, sbb, 1, 4, 1, 11, 4, 11, Blocks.stained_hardened_clay, colorBottomPart, Blocks.stained_hardened_clay, colorBottomPart, false);
        
        this.placeDoorAtCurrentPosition(world, sbb, random, 6, 0, 1, this.getMetadataWithOffset(Blocks.wooden_door, 4));
        
        this.fillWithAir(world, sbb, 4, 4, 4, 8, 4, 8);
        
        this.fillWithMetadataBlocks(world, sbb, 4, 5, 4, 4, 8, 4,  Blocks.stained_hardened_clay, 15, Blocks.stained_hardened_clay, 15, false);
        this.fillWithMetadataBlocks(world, sbb, 8, 5, 4, 8, 8, 4, Blocks.stained_hardened_clay, 15, Blocks.stained_hardened_clay, 15, false);
        this.fillWithMetadataBlocks(world, sbb, 4, 5, 8, 4, 8, 8, Blocks.stained_hardened_clay, 15, Blocks.stained_hardened_clay, 15, false);
        this.fillWithMetadataBlocks(world, sbb, 8, 5, 8, 8, 8, 8, Blocks.stained_hardened_clay, 15, Blocks.stained_hardened_clay, 15, false);
        
        
        this.fillWithMetadataBlocks(world, sbb, 5, 5, 4, 7, 7, 4, Blocks.stained_hardened_clay, colorTopPart, Blocks.stained_hardened_clay, colorTopPart, false);
        this.fillWithMetadataBlocks(world, sbb, 5, 5, 8, 7, 7, 8, Blocks.stained_hardened_clay, colorTopPart, Blocks.stained_hardened_clay, colorTopPart, false);
        this.fillWithMetadataBlocks(world, sbb, 4, 5, 5, 4, 7, 7, Blocks.stained_hardened_clay, colorTopPart, Blocks.stained_hardened_clay, colorTopPart, false);
        this.fillWithMetadataBlocks(world, sbb, 8, 5, 5, 8, 7, 7, Blocks.stained_hardened_clay, colorTopPart, Blocks.stained_hardened_clay, colorTopPart, false);
        
        this.fillWithMetadataBlocks(world, sbb, 4, 8, 4, 8, 8, 8, Blocks.stained_hardened_clay, 15, Blocks.stained_hardened_clay, 15, false);
        this.fillWithMetadataBlocks(world, sbb, 5, 8, 5, 7, 8, 7, Blocks.stained_hardened_clay, colorTopPart, Blocks.stained_hardened_clay, colorTopPart, false);
        
        this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 2, 2, 2, sbb);
        this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 10, 2, 2, sbb);
        this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 2, 2, 10, sbb);
        this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 10, 2, 10, sbb);
        
        this.fillWithBlocks(world, sbb, 2, 0, 3, 2, 0, 10, Blocks.redstone_wire, Blocks.redstone_wire, false);
        this.placeBlockAtCurrentPosition(world, Blocks.lever, 5, 2, 0, 2, sbb);
        this.placeBlockAtCurrentPosition(world, Blocks.unpowered_repeater, 2, 3, 0, 10, sbb);
        this.fillWithBlocks(world, sbb, 4, 0, 10, 10, 0, 10, Blocks.redstone_wire, Blocks.redstone_wire, false);
        this.fillWithBlocks(world, sbb, 10, 0, 3, 10, 0, 10, Blocks.redstone_wire, Blocks.redstone_wire, false);
        this.fillWithMetadataBlocks(world, sbb, 3, 0, 3, 3, 0, 8, Blocks.unpowered_repeater, 2, Blocks.unpowered_repeater, 2, false);
        this.fillWithMetadataBlocks(world, sbb, 4, 0, 9, 8, 0, 9, Blocks.unpowered_repeater, 3, Blocks.unpowered_repeater, 3, false);
        this.fillWithMetadataBlocks(world, sbb, 9, 0, 3, 9, 0, 8, Blocks.unpowered_repeater, 0, Blocks.unpowered_repeater, 0, false);
        
        spawnVillagers(world, sbb, 0, 0, 0, 2);
        return true;
    }
    
    @Override
    protected int getVillagerType (int par1)
    {
        return 457;
    }
}
