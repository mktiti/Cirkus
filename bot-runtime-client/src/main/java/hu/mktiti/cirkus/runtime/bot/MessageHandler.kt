package hu.mktiti.cirkus.runtime.bot

import hu.mktiti.cirkus.runtime.common.Call
import hu.mktiti.kreator.InjectableType
import java.lang.Exception

@InjectableType
interface MessageHandler {

    fun sendActorBinaryRequest()

    fun reportBotError(exception: Exception)

    fun sendResponse(method: String, data: Any?)

    fun log(message: String)

    fun waitForCall(): Call?

    fun waitForBot(): ByteArray?

}