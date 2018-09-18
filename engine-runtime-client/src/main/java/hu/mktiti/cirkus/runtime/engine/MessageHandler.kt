package hu.mktiti.cirkus.runtime.engine

import hu.mktiti.cirkus.api.GameResult
import hu.mktiti.cirkus.api.LogTarget
import hu.mktiti.cirkus.runtime.common.CallTarget
import hu.mktiti.kreator.annotation.InjectableType
import java.lang.Exception

@InjectableType
interface MessageHandler {

    fun sendActorBinaryRequest()

    fun callFunction(target: CallTarget, methodName: String, params: List<Any?>): Any?

    fun log(target: LogTarget, message: String)

    fun sendResult(result: GameResult)

    fun reportError(exception: Exception)

}