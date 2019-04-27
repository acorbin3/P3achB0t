package com.p3achb0t.analyser

import com.p3achb0t.rsclasses.*
import jdk.internal.org.objectweb.asm.ClassReader
import jdk.internal.org.objectweb.asm.tree.ClassNode
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import java.nio.channels.Channels
import java.util.jar.JarFile
import kotlin.collections.Iterable
import kotlin.collections.set

// TODO - Download file from http://cdn.dreambot.org/hooks.txt
// TODO - Parse file to identify the classes
// TODO - parse file to identify the fields

class DreamBotAnalyzer{
    private var webAddress: String = "http://cdn.dreambot.org/hooks.txt"
    val hookDir = "\\hooks\\"

    var hookFile = ""

    val analyzers = mutableMapOf<String, RSClasses>()
    val classRefObs = mutableMapOf<String, RSClasses>()
    fun getDreamBotHooks():String{

        //Download gamepack
        println("Getting Hooks from Dreambot")
        val url = URL(webAddress)
        val readableByteChannel = Channels.newChannel(url.openStream())
        val path = System.getProperty("user.dir")
        val fileOutStream = FileOutputStream(path + hookDir + "hooks.txt")
        val fileChannel = fileOutStream.channel
        fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE)
        println("downloaded!")
        hookFile = path + hookDir  + "hooks.txt"
        return path + hookDir  + "hooks.txt"

    }

    fun parseHooks(){
        // Init all classes we care about
        analyzers[AbstractProducer::class.java.simpleName] = AbstractProducer()
        analyzers[Actor::class.java.simpleName] = Actor()
        analyzers[AnimatedObject::class.java.simpleName] = AnimatedObject()
        analyzers[Associate::class.java.simpleName] = Associate()
        analyzers[BoundaryObject::class.java.simpleName] = BoundaryObject()
        analyzers[Cache::class.java.simpleName] = Cache()
        analyzers[CacheNode::class.java.simpleName] = CacheNode()
        analyzers[Canvas::class.java.simpleName] = Canvas()
        analyzers[ClanMember::class.java.simpleName] = ClanMember()
        analyzers[ClanMemberHandler::class.java.simpleName] = ClanMemberHandler()
        analyzers[ClassData::class.java.simpleName] = ClassData()
        analyzers[Client::class.java.simpleName] = Client()
        analyzers[CollisionMap::class.java.simpleName] = CollisionMap()
        analyzers[CullingCluster::class.java.simpleName] = CullingCluster()
        analyzers[Facade::class.java.simpleName] = Facade()
        analyzers[FileAccessor::class.java.simpleName] = FileAccessor()
        analyzers[FileBuffer::class.java.simpleName] = FileBuffer()
        analyzers[FloorObject::class.java.simpleName] = FloorObject()
        analyzers[Friend::class.java.simpleName] = Friend()
        analyzers[FriendHandler::class.java.simpleName] = FriendHandler()
        analyzers[GameObject::class.java.simpleName] = GameObject()
        analyzers[GameShell::class.java.simpleName] = GameShell()
        analyzers[GEOThing::class.java.simpleName] = GEOThing()
        analyzers[GM::class.java.simpleName] = GM()
        analyzers[GrandExchangeItem::class.java.simpleName] = GrandExchangeItem()
        analyzers[GraphicsProducer::class.java.simpleName] = GraphicsProducer()
        analyzers[GS::class.java.simpleName] = GS()
        analyzers[HashTable::class.java.simpleName] = HashTable()
        analyzers[HealthBar::class.java.simpleName] = HealthBar()
        analyzers[HealthBarComposite::class.java.simpleName] = HealthBarComposite()
        analyzers[HealthBarData::class.java.simpleName] = HealthBarData()
        analyzers[HuffmanEncoding::class.java.simpleName] = HuffmanEncoding()
        analyzers[ImageRGB::class.java.simpleName] = ImageRGB()
        analyzers[IsaacCipher::class.java.simpleName] = IsaacCipher()
        analyzers[Item::class.java.simpleName] = Item()
        analyzers[ItemComposite::class.java.simpleName] = ItemComposite()
        analyzers[ItemLayer::class.java.simpleName] = ItemLayer()
        analyzers[ItemNode::class.java.simpleName] = ItemNode()
        analyzers[Iterable::class.java.simpleName] = Iterable()
        analyzers[Keyboard::class.java.simpleName] = Keyboard()
        analyzers[LinkedList::class.java.simpleName] = LinkedList()
        analyzers[LoginNode::class.java.simpleName] = LoginNode()
        analyzers[MessageChannel::class.java.simpleName] = MessageChannel()
        analyzers[MessageNode::class.java.simpleName] = MessageNode()
        analyzers[Model::class.java.simpleName] = Model()
        analyzers[Mouse::class.java.simpleName] = Mouse()
        analyzers[MouseTracker::class.java.simpleName] = MouseTracker()
        analyzers[NameComposite::class.java.simpleName] = NameComposite()
        analyzers[NameProvider::class.java.simpleName] = NameProvider()
        analyzers[NameProviderHandler::class.java.simpleName] = NameProviderHandler()
        analyzers[Node::class.java.simpleName] = Node()
        analyzers[Npc::class.java.simpleName] = Npc()
        analyzers[NpcComposite::class.java.simpleName] = NpcComposite()
        analyzers[ObjectComposite::class.java.simpleName] = ObjectComposite()
        analyzers[Packet::class.java.simpleName] = Packet()
        analyzers[Player::class.java.simpleName] = Player()
        analyzers[PlayerComposite::class.java.simpleName] = PlayerComposite()
        analyzers[Projectile::class.java.simpleName] = Projectile()
        analyzers[Queue::class.java.simpleName] = Queue()
        analyzers[Rasterizer2D::class.java.simpleName] = Rasterizer2D()
        analyzers[Rasterizer3D::class.java.simpleName] = Rasterizer3D()
        analyzers[ReferenceTable::class.java.simpleName] = ReferenceTable()
        analyzers[Region::class.java.simpleName] = Region()
        analyzers[Renderable::class.java.simpleName] = Renderable()
        analyzers[RuneScript::class.java.simpleName] = RuneScript()
        analyzers[RuneScriptReference::class.java.simpleName] = RuneScriptReference()
        analyzers[ScriptEvent::class.java.simpleName] = ScriptEvent()
        analyzers[Sequence::class.java.simpleName] = Sequence()
        analyzers[SettingsClass::class.java.simpleName] = SettingsClass()
        analyzers[SocialHandler::class.java.simpleName] = SocialHandler()
        analyzers[Socket::class.java.simpleName] = Socket()
        analyzers[SocketWrapper::class.java.simpleName] = SocketWrapper()
        analyzers[SomeNode::class.java.simpleName] = SomeNode()
        analyzers[Stream::class.java.simpleName] = Stream()
        analyzers[StringConstants::class.java.simpleName] = StringConstants()
        analyzers[Tile::class.java.simpleName] = Tile()
        analyzers[VarpBit::class.java.simpleName] = VarpBit()
        analyzers[WallObject::class.java.simpleName] = WallObject()
        analyzers[Widget::class.java.simpleName] = Widget()
        analyzers[WidgetNode::class.java.simpleName] = WidgetNode()
        analyzers[World::class.java.simpleName] = World()


        // Open file
        // Parse file and update classes
        // Update fields
        var currentClass = ""
        File(this.hookFile).forEachLine {
            //Class line "@Client identified as: client"
            if(it.contains("@")){
                val list = it.split(" ")
                val className = list[0].replace("@","")
                val obsName = list.last()
                currentClass = className

                (analyzers[className] as RSClasses).obsName = obsName
                classRefObs[obsName] = analyzers[className]!!
                println(className)
            }
            //Field line: "Client.accountStatus cu ae -2093036075"
            else if(it.contains(currentClass)){
                val fieldLine = it.replace("$currentClass.", "")
                val splitField = fieldLine.split(" ")
                val field: RSClasses.Field = RSClasses.Field()
                field.fieldName = splitField[0]
                field.fieldTypeObsName = splitField[1]
                if(splitField.size>2)
                    field.obsName = splitField[2]
                if(splitField.size>3)
                    field.modifier = splitField[3].replace("L","").toLong()
                analyzers[currentClass]?.fields?.set(field.fieldName, field)
                classRefObs[analyzers[currentClass]?.obsName]?.fields?.set(field.obsName, field)
                classRefObs[analyzers[currentClass]?.obsName]?.normalizedFields?.set(field.fieldName, field)
                println("\t$field")



            }
        }
    }

    fun createAccessorFieldsForClass(clazz: Class<*>) {
        println("---${clazz.name}----")
        for (field in analyzers[clazz.simpleName]?.normalizedFields!!) {
            if (field.value.modifier > 0) {
                println("var " + field.key + " = 0")
            } else {
                println("var " + field.key + " = \"\"")
            }
        }
        println("-----------------")
        println(
            "constructor()\n" +
                    "constructor(fields: MutableMap<String, Field?>) : super() {"
        )
        for (field in analyzers[clazz.simpleName]?.normalizedFields!!) {
            //x = fields["x"]?.resultValue?.toInt() ?: -1
            if (field.value.modifier > 0) {
                println("\t" + field.key + " = fields[\"" + field.key + "\"]?.resultValue?.toInt() ?: -1")
            } else {
                println("\t" + field.key + " = fields[\"" + field.key + "\"]?.resultValue.toString()")
            }
        }
        println("}")
        println("\n")
    }

    fun parseJar(jar: JarFile){
        // We are going to look at the Jar and find the Class Nodes so can get more data
        val enumeration = jar.entries()
        while(enumeration.hasMoreElements()){
            val entry = enumeration.nextElement()
            if(entry.name.endsWith(".class")){
                val classReader = ClassReader(jar.getInputStream(entry))
                val classNode = ClassNode()
                classReader.accept(classNode, ClassReader.SKIP_DEBUG)// Missing | ClassReader.SKIP_FRAMES

                if(classNode.name in classRefObs) {
                    classRefObs[classNode.name]?.node = classNode
                    analyzers[this.classRefObs[classNode.name]!!::class.java.simpleName]?.node = classNode
                }
            }
        }
    }
    fun getSuperName(classNode: ClassNode):String{
        return if(classNode.superName == "java/lang/Object"){
            " -> "+ classNode.superName
        } else{
            return if(classNode.superName in classRefObs) {
                " -> " + classRefObs[classNode.superName]!!::class.java.simpleName + classRefObs[classNode.superName]?.node?.let {
                    getSuperName(
                        it
                    )
                }
            }else{
                " -> " + classNode.superName
            }

        }
    }
    fun generateSuperGraph(){
        for(classNodeEntry in classRefObs){
            val classNode = classNodeEntry.value.node
            val classNodeName = classNodeEntry.value::class.java.simpleName

            val superName = getSuperName(classNode)
            println("$classNodeName $superName")
        }
    }
}