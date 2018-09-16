package hu.mktiti.cirkus.runtime.engine

import hu.mktiti.cirkus.api.BotInterface
import hu.mktiti.cirkus.api.GameEngine
import hu.mktiti.cirkus.runtime.common.BotDefinitionException
import hu.mktiti.kreator.Injectable
import hu.mktiti.kreator.InjectableType
import hu.mktiti.kreator.inject
import org.reflections.Reflections
import java.lang.reflect.Constructor
import java.lang.reflect.Modifier
import java.lang.reflect.Proxy

@InjectableType
interface EngineClientHelper {

    fun <T : BotInterface> createProxyForBot(botClass: Class<T>, invokeLogic: (String, List<Any?>) -> Any?): T

    fun <T : BotInterface> searchAndCreateEngine(botClass: Class<T>, botA: BotInterface, botB: BotInterface): GameEngine<*>?

}

@Injectable(default = true)
class DefaultEngineClientHelper(
        private val reflections: Reflections = inject()
) : EngineClientHelper {

    override fun <T : BotInterface> createProxyForBot(botClass: Class<T>, invokeLogic: (String, List<Any?>) -> Any?): T {
        val proxy: Any = Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), arrayOf(botClass)) { _, method, arguments ->
            invokeLogic(method.name, arguments.asList())
        }
        return if (botClass.isInstance(proxy)) {
            botClass.cast(proxy)
        } else {
            throw RuntimeException()
        }
    }

    override fun <T : BotInterface> searchAndCreateEngine(botClass: Class<T>, botA: BotInterface, botB: BotInterface): GameEngine<*>? {
        val classes: List<Class<out GameEngine<*>>> = reflections.getSubTypesOf(GameEngine::class.java).toList()
        val constructors: List<Constructor<out GameEngine<*>>> =
                classes
                        .filter { !Modifier.isAbstract(it.modifiers) && Modifier.isPublic(it.modifiers) }
                        .mapNotNull { it.getConstructor(botClass, botClass) }

        return when (constructors.size) {
            0 -> null
            1 -> constructors.first().newInstance(botA, botB)
            else ->
                throw BotDefinitionException("Multiple valid bot found for bot interface (public non-abstract class with no-arg public constructor)!")
        }
    }

}