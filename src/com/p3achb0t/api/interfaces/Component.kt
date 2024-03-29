package com.p3achb0t.api.interfaces

interface Component : Node {
    fun getButtonText(): String
    fun getButtonType(): Int
    fun getChildIndex(): Int
    fun getChildren(): Array<Component>
    fun getClickMask(): Int
    fun getClientCode(): Int
    fun getColor(): Int
    fun getColor2(): Int
    fun getCs1ComparisonValues(): IntArray
    fun getCs1Comparisons(): IntArray
    fun getCs1Instructions(): Array<IntArray>
    fun getCycle(): Int
    fun getDragDeadTime(): Int
    fun getDragDeadZone(): Int
    fun getFill(): Boolean
    fun getFontId(): Int
    fun getHasListener(): Boolean
    fun getHeight(): Int
    fun getHeightAlignment(): Int
    fun getId(): Int
    fun getInvTransmitTriggers(): IntArray
    fun getInventorySprites(): IntArray
    fun getInventoryXOffsets(): IntArray
    fun getInventoryYOffsets(): IntArray
    fun getIsDraggable(): Boolean
    fun getIsHidden(): Boolean
    fun getIsIf3(): Boolean
    fun getItemActions(): Array<String>
    fun getItemId(): Int
    fun getItemIds(): IntArray
    fun getItemQuantities(): IntArray
    fun getItemQuantity(): Int
    fun getLineWid(): Int
    fun getModelAngleX(): Int
    fun getModelAngleY(): Int
    fun getModelAngleZ(): Int
    fun getModelFrame(): Int
    fun getModelFrameCycle(): Int
    fun getModelId(): Int
    fun getModelId2(): Int
    fun getModelOffsetX(): Int
    fun getModelOffsetY(): Int
    fun getModelOrthog(): Boolean
    fun getModelTransparency(): Boolean
    fun getModelType(): Int
    fun getModelType2(): Int
    fun getModelZoom(): Int
    fun getMouseOverColor(): Int
    fun getMouseOverColor2(): Int
    fun getMouseOverRedirect(): Int
    fun getNoClickThrough(): Boolean
    fun getOnChatTransmit(): Any
    fun getOnClanTransmit(): Any
    fun getOnClick(): Any
    fun getOnClickRepeat(): Any
    fun getOnDialogAbort(): Any
    fun getOnDrag(): Any
    fun getOnDragComplete(): Any
    fun getOnFriendTransmit(): Any
    fun getOnHold(): Any
    fun getOnInvTransmit(): Any
    fun getOnKey(): Any
    fun getOnLoad(): Any
    fun getOnMiscTransmit(): Any
    fun getOnMouseLeave(): Any
    fun getOnMouseOver(): Any
    fun getOnMouseRepeat(): Any
    fun getOnOp(): Any
    fun getOnRelease(): Any
    fun getOnScrollWheel(): Any
    fun getOnStatTransmit(): Any
    fun getOnStockTransmit(): Any
    fun getOnSubChange(): Any
    fun getOnTargetEnter(): Any
    fun getOnTargetLeave(): Any
    fun getOnTimer(): Any
    fun getOnVarTransmit(): Any
    fun getOpbase(): String
    fun getOps(): Array<String>
    fun getOutline(): Int
    fun getPaddingX(): Int
    fun getPaddingY(): Int
    fun getParent(): Component
    fun getParentId(): Int
    fun getRawHeight(): Int
    fun getRawWidth(): Int
    fun getRawX(): Int
    fun getRawY(): Int
    fun getRectangleMode(): RectangleMode
    fun getRootIndex(): Int
    fun getScrollHeight(): Int
    fun getScrollWidth(): Int
    fun getScrollX(): Int
    fun getScrollY(): Int
    fun getSequenceId(): Int
    fun getSequenceId2(): Int
    fun getSpellName(): String
    fun getSpriteAngle(): Int
    fun getSpriteFlipH(): Boolean
    fun getSpriteFlipV(): Boolean
    fun getSpriteId(): Int
    fun getSpriteId2(): Int
    fun getSpriteShadow(): Int
    fun getSpriteTiling(): Boolean
    fun getStatTransmitTriggers(): IntArray
    fun getTargetVerb(): String
    fun getText(): String
    fun getText2(): String
    fun getTextLineHeight(): Int
    fun getTextShadowed(): Boolean
    fun getTextXAlignment(): Int
    fun getTextYAlignment(): Int
    fun getTransparency(): Int
    fun getType(): Int
    fun getVarTransmitTriggers(): IntArray
    fun getWidth(): Int
    fun getWidthAlignment(): Int
    fun getX(): Int
    fun getXAlignment(): Int
    fun getY(): Int
    fun getYAlignment(): Int
    fun get__bd(): Boolean
    fun get__cj(): Boolean
    fun get__eo(): Boolean
    fun get__fl(): Boolean
    fun get__fo(): Boolean
    fun get__fq(): Boolean
    fun get__cc(): Array<ByteArray>
    fun get__cu(): Array<ByteArray>
    fun get__aj(): Int
    fun get__as(): Int
    fun get__au(): Int
    fun get__bb(): Int
    fun get__bc(): Int
    fun get__bk(): Int
    fun get__fa(): Int
    fun get__fc(): Int
    fun get__ft(): Int
    fun get__fz(): Int
    fun get__cb(): IntArray
    fun get__cz(): IntArray
    fun get__fe(): IntArray
    fun get__ed(): Any
    fun get__ej(): Any
    fun get__et(): Any
    fun get__ez(): Any
}
