package com.p3achb0t.rsclasses

import jdk.internal.org.objectweb.asm.Opcodes
import jdk.internal.org.objectweb.asm.tree.ClassNode

class Client:RSClasses() {
    var name: String = ""

//    client.accountStatus cu ae -2093036075
//    Client.baseX bu fc -554363219
//    Client.baseY al ff -630180725
//    Client.cameraPitch if hs 922344117
//    Client.cameraX cy hf 2010431587
//    Client.cameraY client hb -1441849569
//    Client.cameraYaw ar ha -1800659677
//    Client.cameraZ fu hu 361032413
//    Client.canvas bf ac
//    Client.clanMembersHandler bi pj
//    Client.clickModifier bi m -1
//    Client.collisionMaps client w
//    Client.crosshairColor client ja 1032015607
//    Client.currentWorld client bf -1131016759
//    Client.destinationX client pc -1274219677
//    Client.destinationY client pw -64301225
//    Client.experience client ki
//    Client.fps broken
//    Client.gameCycle client cr -920219497
//    Client.gameSettings hm l
//    Client.gameSocket broken
//    Client.gameState client bs -1368908321
//    Client.grandExchangeItems client st
//    Client.groundItemList client ka
//    Client.groundItemModelCache jz t
//    Client.hintArrowNPCIndex broken
//    Client.hintArrowPlayerIndex broken
//    Client.hintArrowType broken
//    Client.hintArrowX broken
//    Client.hintArrowY broken
//    Client.hintArrowZ broken
//    Client.host gr ev
//    Client.idleTime bi b -1
//    Client.isSpellSelected client lc
//    Client.isWorldSelectorOpen cu bb
//    Client.itemTable bm n
//    Client.keyboard ay n
//    Client.lastAction bi x -8370000357435067951L
//    Client.lastActionDifference gk h -7859540649334811869L
//    Client.lastActionDifferenceMod gk h -7859540649334811869L
//    Client.lastActionTime gk h -7859540649334811869L
//    Client.lastActionTimeMod gk h 5455904988763124875L
//    Client.lastButtonClick bi a -1163928735
//    Client.lastButtonClickModA bi a 466169997
//    Client.lastButtonClickModM bi a -1
//    Client.lastClickModifier bi m -1580461499
//    Client.lastClickModifierModA bi m -1
//    Client.lastClickModifierModM bi m -1
//    Client.lastClickX bi v -1073869461
//    Client.lastClickY bi r 96346601
//    Client.level client kf
//    Client.localNPCs client ek
//    Client.localPlayer aa jo
//    Client.loginState cu ax -1840527851
//    Client.lowestAvailableCameraPitch client iv -906877067
//    Client.mapAngle client hy 687965223
//    Client.menuActions client lb
//    Client.menuCount client lo 672306817
//    Client.menuHeight an kj 162816135
//    Client.menuOpcodes client lx
//    Client.menuOptions client lm
//    Client.menuShiftClick client lr
//    Client.menuVariable client lg
//    Client.menuVisible client kn
//    Client.menuWidth ba kq -498947721
//    Client.menuX ee ko 762266009
//    Client.menuXInteractions client ls
//    Client.menuY ba kl -306890177
//    Client.menuYInteractions client lq
//    Client.message0 cu av
//    Client.message1 cu af
//    Client.message2 cu bf
//    Client.messageContainer cd n
//    Client.mouse bi g
//    Client.npcCompositeCache jx l
//    Client.npcModelCache jx g
//    Client.objectCompositeCache js g
//    Client.objectModelCache js c
//    Client.password cu bx
//    Client.plane d jd 1203325845
//    Client.playerIndex client ju 505958869
//    Client.playerModelCache is f
//    Client.players client jf
//    Client.projectiles client kd
//    Client.realLevel client kv
//    Client.region w gv
//    Client.selectedItemID bw iz -2005029945
//    Client.selectedItemIndex iz la 461173635
//    Client.selectedItemName client it
//    Client.selectedSpellName client mk
//    Client.selectionState client lh 950669299
//    Client.settings hm l
//    Client.settingsObject iq sg
//    Client.socialHandler gk rz
//    Client.socketWrapper client fi
//    Client.tileHeights bu n
//    Client.tileSettings bu h
//    Client.username cu bi
//    Client.varps broken
//    Client.widgetBoundsX client oz
//    Client.widgetBoundsY client ov
//    Client.widgetHeights client oe
//    Client.widgetModelCache iq i
//    Client.widgetNodes client mo
//    Client.widgetWidths client ob
//    Client.widgets gx a
//    Client.worlds bl b
//    Client.zoom broken
//    Client.zoomExact client rr 1833337693
    companion object {
        const val deobName = "Client"
    }
    override fun analyze(node: ClassNode, rsClassesMap: Map<String,RSClasses>) {
        if(node.name == "client"){
            this.name = node.name
            this.found = true
            println("Client: " + this.name)
        }
    }
}