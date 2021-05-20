/*
 * Copyright (c) 2016-2017, Adam <Adam@sigterm.info>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package net.runelite.cache.definitions;

import java.util.Map;
import lombok.Data;

@Data
public class ObjectDefinition
{
	public int id;
	public short[] retextureToFind;
	public int decorDisplacement = 16;
	public boolean isHollow = false;
	public String name = "null";
	public int[] objectModels;
	public int[] objectTypes;
	public short[] recolorToFind;
	public int mapAreaId = -1;
	public short[] textureToReplace;
	public int sizeX = 1;
	public int sizeY = 1;
	public int anInt2083 = 0;
	public int[] anIntArray2084;
	public int offsetX = 0;
	public boolean mergeNormals = false;
	public int wallOrDoor = -1;
	public int animationID = -1;
	public int varbitID = -1;
	public int ambient = 0;
	public int contrast = 0;
	public String[] actions = new String[5];
	public int interactType = 2;
	public int mapSceneID = -1;
	public int blockingMask = 0;
	public short[] recolorToReplace;
	public boolean shadow = true;
	public int modelSizeX = 128;
	public int modelSizeHeight = 128;
	public int modelSizeY = 128;
	public int objectID;
	public int offsetHeight = 0;
	public int offsetY = 0;
	public boolean obstructsGround = false;
	public int contouredGround = -1;
	public int supportsItems = -1;
	public int[] configChangeDest;
	public boolean isRotated = false;
	public int varpID = -1;
	public int ambientSoundId = -1;
	public boolean aBool2111 = false;
	public int anInt2112 = 0;
	public int anInt2113 = 0;
	public boolean blocksProjectile = true;
	public boolean randomizeAnimStart;
	public Map<Integer, Object> params = null;
}
