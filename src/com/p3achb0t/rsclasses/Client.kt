package com.p3achb0t.rsclasses

import jdk.internal.org.objectweb.asm.tree.ClassNode

class Client : RSClasses {

    var accountStatus = ""
    var baseX = 0
    var baseY = 0
    var cameraPitch = ""
    var cameraX = 0
    var cameraY = 0
    var cameraYaw = 0
    var cameraZ = 0
    var canvas = ""
    var clanMembersHandler = ""
    var clickModifier = ""
    var collisionMaps = ""
    var crosshairColor = ""
    var currentWorld = ""
    var destinationX = 0
    var destinationY = ""
    var experience = ""
    var fps = ""
    var gameCycle = ""
    var gameSettings = ""
    var gameSocket = ""
    var gameState = ""
    var grandExchangeItems = ""
    var groundItemList = ""
    var groundItemModelCache = ""
    var hintArrowNPCIndex = ""
    var hintArrowPlayerIndex = ""
    var hintArrowType = ""
    var hintArrowX = ""
    var hintArrowY = ""
    var hintArrowZ = ""
    var host = ""
    var idleTime = ""
    var isSpellSelected = ""
    var isWorldSelectorOpen = ""
    var itemTable = ""
    var keyboard = ""
    var lastAction = ""
    var lastActionDifference = ""
    var lastActionDifferenceMod = ""
    var lastActionTime = 0
    var lastActionTimeMod = 0
    var lastButtonClick = 0
    var lastButtonClickModA = ""
    var lastButtonClickModM = ""
    var lastClickModifier = 0
    var lastClickModifierModA = ""
    var lastClickModifierModM = ""
    var lastClickX = 0
    var lastClickY = 0
    var level = ""
    var localNPCs = ArrayList<NpcComposite>()
    var localPlayer = ""
    var loginState = 0
    var lowestAvailableCameraPitch = 0
    var mapAngle = ""
    var menuActions = ""
    var menuCount = 0
    var menuHeight = 0
    var menuOpcodes = ""
    var menuOptions = ""
    var menuShiftClick = ""
    var menuVariable = ""
    var menuVisible = ""
    var menuWidth = ""
    var menuX = 0
    var menuXInteractions = ""
    var menuY = 0
    var menuYInteractions = ""
    var message0 = ""
    var message1 = ""
    var message2 = ""
    var messageContainer = ""
    var mouse = ""
    var npcCompositeCache = ""
    var npcModelCache = ""
    var objectCompositeCache = ""
    var objectModelCache = ""
    var password = ""
    var plane = ""
    var playerIndex = ""
    var playerModelCache = ""
    var players = ""
    var projectiles = ""
    var realLevel = ""
    var region = ""
    var selectedItemID = 0
    var selectedItemIndex = 0
    var selectedItemName = ""
    var selectedSpellName = ""
    var selectionState = 0
    var settings = ""
    var settingsObject = ""
    var socialHandler = ""
    var socketWrapper = ""
    var tileHeights = ""
    var tileSettings = ""
    var username = ""
    var varps = ""
    var widgetBoundsX = ""
    var widgetBoundsY = ""
    var widgetHeights = ""
    var widgetModelCache = ""
    var widgetNodes = ""
    var widgetWidths = ""
    var widgets = ""
    var worlds = ""
    var zoom = ""
    var zoomExact = ""
    companion object {
        const val deobName = "Client"
    }

    enum class GameState(val intState: Int) {
        LoggedOut(10),
        LoggingIn(20),
        LoggedIn(30)
    }

    constructor()
    constructor(fields: MutableMap<String, Field>) : super() {
        accountStatus = fields["accountStatus"]?.resultValue.toString()
        baseX = fields["baseX"]?.resultValue?.toInt() ?: -1
        baseY = fields["baseY"]?.resultValue?.toInt() ?: -1
        cameraPitch = fields["cameraPitch"]?.resultValue.toString()
        cameraX = fields["cameraX"]?.resultValue?.toInt() ?: -1
        cameraY = fields["cameraY"]?.resultValue?.toInt() ?: -1
        cameraYaw = fields["cameraYaw"]?.resultValue?.toInt() ?: -1
        cameraZ = fields["cameraZ"]?.resultValue?.toInt() ?: -1
        canvas = fields["canvas"]?.resultValue.toString()
        clanMembersHandler = fields["clanMembersHandler"]?.resultValue.toString()
        clickModifier = fields["clickModifier"]?.resultValue.toString()
        collisionMaps = fields["collisionMaps"]?.resultValue.toString()
        crosshairColor = fields["crosshairColor"]?.resultValue.toString()
        currentWorld = fields["currentWorld"]?.resultValue.toString()
        destinationX = fields["destinationX"]?.resultValue?.toInt() ?: -1
        destinationY = fields["destinationY"]?.resultValue.toString()
        experience = fields["experience"]?.resultValue.toString()
        fps = fields["fps"]?.resultValue.toString()
        gameCycle = fields["gameCycle"]?.resultValue.toString()
        gameSettings = fields["gameSettings"]?.resultValue.toString()
        gameSocket = fields["gameSocket"]?.resultValue.toString()
        gameState = fields["gameState"]?.resultValue.toString()
        grandExchangeItems = fields["grandExchangeItems"]?.resultValue.toString()
        groundItemList = fields["groundItemList"]?.resultValue.toString()
        groundItemModelCache = fields["groundItemModelCache"]?.resultValue.toString()
        hintArrowNPCIndex = fields["hintArrowNPCIndex"]?.resultValue.toString()
        hintArrowPlayerIndex = fields["hintArrowPlayerIndex"]?.resultValue.toString()
        hintArrowType = fields["hintArrowType"]?.resultValue.toString()
        hintArrowX = fields["hintArrowX"]?.resultValue.toString()
        hintArrowY = fields["hintArrowY"]?.resultValue.toString()
        hintArrowZ = fields["hintArrowZ"]?.resultValue.toString()
        host = fields["host"]?.resultValue.toString()
        idleTime = fields["idleTime"]?.resultValue.toString()
        isSpellSelected = fields["isSpellSelected"]?.resultValue.toString()
        isWorldSelectorOpen = fields["isWorldSelectorOpen"]?.resultValue.toString()
        itemTable = fields["itemTable"]?.resultValue.toString()
        keyboard = fields["keyboard"]?.resultValue.toString()
        lastAction = fields["lastAction"]?.resultValue.toString()
        lastActionDifference = fields["lastActionDifference"]?.resultValue.toString()
        lastActionDifferenceMod = fields["lastActionDifferenceMod"]?.resultValue.toString()
        lastActionTime = fields["lastActionTime"]?.resultValue?.toInt() ?: -1
        lastActionTimeMod = fields["lastActionTimeMod"]?.resultValue?.toInt() ?: -1
        lastButtonClick = fields["lastButtonClick"]?.resultValue?.toInt() ?: -1
        lastButtonClickModA = fields["lastButtonClickModA"]?.resultValue.toString()
        lastButtonClickModM = fields["lastButtonClickModM"]?.resultValue.toString()
        lastClickModifier = fields["lastClickModifier"]?.resultValue?.toInt() ?: -1
        lastClickModifierModA = fields["lastClickModifierModA"]?.resultValue.toString()
        lastClickModifierModM = fields["lastClickModifierModM"]?.resultValue.toString()
        lastClickX = fields["lastClickX"]?.resultValue?.toInt() ?: -1
        lastClickY = fields["lastClickY"]?.resultValue?.toInt() ?: -1
        level = fields["level"]?.resultValue.toString()
//        localNPCs = fields["localNPCs"]?.resultValue.toString()
        localPlayer = fields["localPlayer"]?.resultValue.toString()
        loginState = fields["loginState"]?.resultValue?.toInt() ?: -1
        lowestAvailableCameraPitch = fields["lowestAvailableCameraPitch"]?.resultValue?.toInt() ?: -1
        mapAngle = fields["mapAngle"]?.resultValue.toString()
        menuActions = fields["menuActions"]?.resultValue.toString()
        menuCount = fields["menuCount"]?.resultValue?.toInt() ?: -1
        menuHeight = fields["menuHeight"]?.resultValue?.toInt() ?: -1
        menuOpcodes = fields["menuOpcodes"]?.resultValue.toString()
        menuOptions = fields["menuOptions"]?.resultValue.toString()
        menuShiftClick = fields["menuShiftClick"]?.resultValue.toString()
        menuVariable = fields["menuVariable"]?.resultValue.toString()
        menuVisible = fields["menuVisible"]?.resultValue.toString()
        menuWidth = fields["menuWidth"]?.resultValue.toString()
        menuX = fields["menuX"]?.resultValue?.toInt() ?: -1
        menuXInteractions = fields["menuXInteractions"]?.resultValue.toString()
        menuY = fields["menuY"]?.resultValue?.toInt() ?: -1
        menuYInteractions = fields["menuYInteractions"]?.resultValue.toString()
        message0 = fields["message0"]?.resultValue.toString()
        message1 = fields["message1"]?.resultValue.toString()
        message2 = fields["message2"]?.resultValue.toString()
        messageContainer = fields["messageContainer"]?.resultValue.toString()
        mouse = fields["mouse"]?.resultValue.toString()
        npcCompositeCache = fields["npcCompositeCache"]?.resultValue.toString()
        npcModelCache = fields["npcModelCache"]?.resultValue.toString()
        objectCompositeCache = fields["objectCompositeCache"]?.resultValue.toString()
        objectModelCache = fields["objectModelCache"]?.resultValue.toString()
        password = fields["password"]?.resultValue.toString()
        plane = fields["plane"]?.resultValue.toString()
        playerIndex = fields["playerIndex"]?.resultValue.toString()
        playerModelCache = fields["playerModelCache"]?.resultValue.toString()
        players = fields["players"]?.resultValue.toString()
        projectiles = fields["projectiles"]?.resultValue.toString()
        realLevel = fields["realLevel"]?.resultValue.toString()
        region = fields["region"]?.resultValue.toString()
        selectedItemID = fields["selectedItemID"]?.resultValue?.toInt() ?: -1
        selectedItemIndex = fields["selectedItemIndex"]?.resultValue?.toInt() ?: -1
        selectedItemName = fields["selectedItemName"]?.resultValue.toString()
        selectedSpellName = fields["selectedSpellName"]?.resultValue.toString()
        selectionState = fields["selectionState"]?.resultValue?.toInt() ?: -1
        settings = fields["settings"]?.resultValue.toString()
        settingsObject = fields["settingsObject"]?.resultValue.toString()
        socialHandler = fields["socialHandler"]?.resultValue.toString()
        socketWrapper = fields["socketWrapper"]?.resultValue.toString()
        tileHeights = fields["tileHeights"]?.resultValue.toString()
        tileSettings = fields["tileSettings"]?.resultValue.toString()
        username = fields["username"]?.resultValue.toString()
        varps = fields["varps"]?.resultValue.toString()
        widgetBoundsX = fields["widgetBoundsX"]?.resultValue.toString()
        widgetBoundsY = fields["widgetBoundsY"]?.resultValue.toString()
        widgetHeights = fields["widgetHeights"]?.resultValue.toString()
        widgetModelCache = fields["widgetModelCache"]?.resultValue.toString()
        widgetNodes = fields["widgetNodes"]?.resultValue.toString()
        widgetWidths = fields["widgetWidths"]?.resultValue.toString()
        widgets = fields["widgets"]?.resultValue.toString()
        worlds = fields["worlds"]?.resultValue.toString()
        zoom = fields["zoom"]?.resultValue.toString()
        zoomExact = fields["zoomExact"]?.resultValue.toString()
    }
    override fun analyze(node: ClassNode, rsClassesMap: Map<String,RSClasses>) {
        if(node.name == "client"){
            this.obsName = node.name
            this.found = true
            println("Client: " + this.obsName)
        }
    }
}