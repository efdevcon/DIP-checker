import org.devcon.dipchecker.DIPDefinition
import org.devcon.dipchecker.EIPDefinition
import org.devcon.dipchecker.EIPHeaders
import org.devcon.dipchecker.checkFolder
import java.io.File

fun main() {
    println(checkFolder(File("/home/ligi/git/ethereum/EIPs/EIPS"), EIPDefinition()))
    println(checkFolder(File("/home/ligi/git/devcon/DIPs/DIPs"), DIPDefinition()))
}