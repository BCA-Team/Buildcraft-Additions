package buildcraftAdditions.villager;

import java.io.File;
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
	private boolean lock = false;
	int colorBottomPart = 12;
    int colorTopPart = 1;
    public ResourceLocation bptCreativeEngine = new ResourceLocation("bcadditions", "blueprints/CreativeEngine-4d51adf8e7ea8e20374f67f8985965bcbaf5e208efdf4691fe767f84d34348bb.bpt");
	
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
        
       /*//floor
        fillWithMetadataBlocks(world, sbb, 1, -1, 1, 11, -1, 11, Blocks.stained_hardened_clay, colorBottomPart, Blocks.stained_hardened_clay, colorBottomPart, false);
        
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
        
        //door
        this.placeDoorAtCurrentPosition(world, sbb, random, 6, 0, 1, this.getMetadataWithOffset(Blocks.wooden_door, 3));
        
        //extra doors to cover bad MC rotation
        this.placeDoorAtCurrentPosition(world, sbb, random, 6, 0, 11, this.getMetadataWithOffset(Blocks.wooden_door, 4));
        this.placeDoorAtCurrentPosition(world, sbb, random, 1, 0, 6, this.getMetadataWithOffset(Blocks.wooden_door, 4));
        this.placeDoorAtCurrentPosition(world, sbb, random, 11, 0, 6, this.getMetadataWithOffset(Blocks.wooden_door, 4));
        
        //hole in ceiling
        this.fillWithAir(world, sbb, 4, 4, 4, 8, 4, 8);
        
        //engine top outline
        this.fillWithMetadataBlocks(world, sbb, 4, 5, 4, 4, 8, 4,  Blocks.stained_hardened_clay, 15, Blocks.stained_hardened_clay, 15, false);
        this.fillWithMetadataBlocks(world, sbb, 8, 5, 4, 8, 8, 4, Blocks.stained_hardened_clay, 15, Blocks.stained_hardened_clay, 15, false);
        this.fillWithMetadataBlocks(world, sbb, 4, 5, 8, 4, 8, 8, Blocks.stained_hardened_clay, 15, Blocks.stained_hardened_clay, 15, false);
        this.fillWithMetadataBlocks(world, sbb, 8, 5, 8, 8, 8, 8, Blocks.stained_hardened_clay, 15, Blocks.stained_hardened_clay, 15, false);
        
        //top part of the engine
        this.fillWithMetadataBlocks(world, sbb, 5, 5, 4, 7, 7, 4, Blocks.stained_hardened_clay, colorTopPart, Blocks.stained_hardened_clay, colorTopPart, false);
        this.fillWithMetadataBlocks(world, sbb, 5, 5, 8, 7, 7, 8, Blocks.stained_hardened_clay, colorTopPart, Blocks.stained_hardened_clay, colorTopPart, false);
        this.fillWithMetadataBlocks(world, sbb, 4, 5, 5, 4, 7, 7, Blocks.stained_hardened_clay, colorTopPart, Blocks.stained_hardened_clay, colorTopPart, false);
        this.fillWithMetadataBlocks(world, sbb, 8, 5, 5, 8, 7, 7, Blocks.stained_hardened_clay, colorTopPart, Blocks.stained_hardened_clay, colorTopPart, false);
        
        this.fillWithMetadataBlocks(world, sbb, 4, 8, 4, 8, 8, 8, Blocks.stained_hardened_clay, 15, Blocks.stained_hardened_clay, 15, false);
        this.fillWithMetadataBlocks(world, sbb, 5, 8, 5, 7, 8, 7, Blocks.stained_hardened_clay, colorTopPart, Blocks.stained_hardened_clay, colorTopPart, false);
        
        //torches on the wall
        this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 2, 2, 2, sbb);
        this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 10, 2, 2, sbb);
        this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 2, 2, 10, sbb);
        this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 10, 2, 10, sbb);
        
        //redstone wiring
        this.fillWithBlocks(world, sbb, 2, 0, 3, 2, 0, 10, Blocks.redstone_wire, Blocks.redstone_wire, false);
        this.placeBlockAtCurrentPosition(world, Blocks.lever, 5, 2, 0, 2, sbb);
        this.placeBlockAtCurrentPosition(world, Blocks.unpowered_repeater, this.getMetadataWithOffset(Blocks.unpowered_repeater, 1), 3, 0, 10, sbb);
        this.fillWithBlocks(world, sbb, 4, 0, 10, 10, 0, 10, Blocks.redstone_wire, Blocks.redstone_wire, false);
        this.fillWithBlocks(world, sbb, 10, 0, 3, 10, 0, 10, Blocks.redstone_wire, Blocks.redstone_wire, false);
        this.fillWithMetadataBlocks(world, sbb, 3, 0, 4, 3, 0, 8, Blocks.unpowered_repeater, this.getMetadataWithOffset(Blocks.unpowered_repeater, 1), Blocks.unpowered_repeater, this.getMetadataWithOffset(Blocks.unpowered_repeater, 2), false);
        this.fillWithMetadataBlocks(world, sbb, 4, 0, 9, 8, 0, 9, Blocks.unpowered_repeater, this.getMetadataWithOffset(Blocks.unpowered_repeater, 2), Blocks.unpowered_repeater, this.getMetadataWithOffset(Blocks.unpowered_repeater, 3), false);
        this.fillWithMetadataBlocks(world, sbb, 9, 0, 4, 9, 0, 8, Blocks.unpowered_repeater, this.getMetadataWithOffset(Blocks.unpowered_repeater, 3), Blocks.unpowered_repeater, this.getMetadataWithOffset(Blocks.unpowered_repeater, 0), false);
        
        int i = this.boundingBox.minX;
        int j = this.boundingBox.minY;
        int k = this.boundingBox.minZ;        
        
        //piping
        for (int teller=4; teller<=8; teller++){
        	BlockGenericPipe.placePipe(BlockGenericPipe.createPipe(BuildCraftTransport.pipePowerWood), world, i + teller, j + 1, k + 4, BuildCraftTransport.genericPipeBlock, 0);
        	BlockGenericPipe.placePipe(BlockGenericPipe.createPipe(BuildCraftTransport.pipePowerStone), world, i + teller, j + 2, k + 4, BuildCraftTransport.genericPipeBlock, 0);
        	BlockGenericPipe.placePipe(BlockGenericPipe.createPipe(BuildCraftTransport.pipePowerWood), world, i + teller, j + 1, k + 8, BuildCraftTransport.genericPipeBlock, 0);
        	BlockGenericPipe.placePipe(BlockGenericPipe.createPipe(BuildCraftTransport.pipePowerStone), world, i + teller, j + 2, k + 8, BuildCraftTransport.genericPipeBlock, 0);
        	BlockGenericPipe.placePipe(BlockGenericPipe.createPipe(BuildCraftTransport.pipeItemsCobblestone), world, i + teller, j - 1, k+ 4, BuildCraftTransport.genericPipeBlock, 0);
        	BlockGenericPipe.placePipe(BlockGenericPipe.createPipe(BuildCraftTransport.pipeItemsCobblestone), world, i + teller, j - 1, k+ 8, BuildCraftTransport.genericPipeBlock, 0);
        }
        for (int teller=4; teller<=8; teller++){
        	BlockGenericPipe.placePipe(BlockGenericPipe.createPipe(BuildCraftTransport.pipePowerWood), world, i + 8, j + 1, k + teller, BuildCraftTransport.genericPipeBlock, 0);
        	BlockGenericPipe.placePipe(BlockGenericPipe.createPipe(BuildCraftTransport.pipePowerStone), world, i + 8, j + 2, k + teller, BuildCraftTransport.genericPipeBlock, 0);
        	BlockGenericPipe.placePipe(BlockGenericPipe.createPipe(BuildCraftTransport.pipeItemsCobblestone), world, i + 4, j - 1, k + teller, BuildCraftTransport.genericPipeBlock, 0);
        	BlockGenericPipe.placePipe(BlockGenericPipe.createPipe(BuildCraftTransport.pipeItemsCobblestone), world, i + 4, j - 1, k + teller, BuildCraftTransport.genericPipeBlock, 0);
        }
        
        for (int teller = 6; teller<=7; teller++)
        	BlockGenericPipe.placePipe(BlockGenericPipe.createPipe(BuildCraftTransport.pipePowerStone), world, i + teller, j + 2, k + 6, BuildCraftTransport.genericPipeBlock, 0);
        
        for (int teller = 3; teller<=8; teller++){
        	BlockGenericPipe.placePipe(BlockGenericPipe.createPipe(BuildCraftTransport.pipePowerStone), world, i + 6, j + teller, k + 6, BuildCraftTransport.genericPipeBlock, 0);
        }
        
        this.placeBlockAtCurrentPosition(world, Blocks.chest, 0, 6, 0, 6, sbb);
        BlockGenericPipe.placePipe(BlockGenericPipe.createPipe(BuildCraftTransport.pipeItemsCobblestone), world, i + 6, j - 1, k + 5, BuildCraftTransport.genericPipeBlock, 0);
        BlockGenericPipe.placePipe(BlockGenericPipe.createPipe(BuildCraftTransport.pipeItemsWood), world, i + 6, j - 1, k + 6, BuildCraftTransport.genericPipeBlock, 0);
        this.placeBlockAtCurrentPosition(world, BuildCraftEnergy.engineBlock, 0, 6, -2, 6, sbb);
        this.placeBlockAtCurrentPosition(world, Blocks.redstone_torch, 1, 6, -3, 6, sbb);*/
        
        URL url = ComponentPowerPlant.class.getResource("/assets/bcadditions/blueprints/combustion-6f3d68e6b2040cef2279575035a268d9d4388e7412d47d35a05b21694c7e5c18.bpt");
        File file = new File(url.getFile());
        System.out.println(file.exists());
        
        int i = this.boundingBox.minX;
        int j = this.boundingBox.minY;
        int k = this.boundingBox.minZ;
        System.out.println(i +" "+j + " "+ k);
        BlueprintDeployer bptd = new RealBlueprintDeployer();
        
        bptd.deployBlueprint(world, i, j, k, ForgeDirection.SOUTH, file);
        
        	
        spawnVillagers(world, sbb, 0, 0, 0, 2);
        return true;
    }
    
    @Override
    protected int getVillagerType (int par1)
    {
        return 457;
    }
}
