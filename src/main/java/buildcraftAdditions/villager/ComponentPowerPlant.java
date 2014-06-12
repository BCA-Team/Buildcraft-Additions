package buildcraftAdditions.villager;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Random;

import buildcraft.api.blueprints.BlueprintDeployer;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import net.minecraft.world.gen.structure.StructureVillagePieces.Start;
import net.minecraftforge.common.util.ForgeDirection;

public class ComponentPowerPlant extends StructureVillagePieces.House1 {
	public static final ResourceLocation redstoneEngine = new ResourceLocation("bcadditions", "blueprints/Redstone-Engine-df2e537ac33b4d684e783cd4b41653bc872d638e1f0c1afecada2a30e670aa39.bpt");
	public static final ResourceLocation stirlingEngine = new ResourceLocation("bcadditions", "blueprints/Stirling-Engine-6a8295b667031a4687a626ecb7c6bef997dc7e62c19f9e0941c64b33198f87d4.bpt");

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
        	
        try{
        	 InputStream f;
        	if (random.nextBoolean())
        		f =  Minecraft.getMinecraft().getResourceManager().getResource(redstoneEngine).getInputStream();
        	else
        		f =  Minecraft.getMinecraft().getResourceManager().getResource(stirlingEngine).getInputStream();
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
        BlueprintDeployer.instance.deployBlueprintFromFileStream(world, i, j-3, k, ForgeDirection.EAST, data);
        } catch (Throwable e){
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
