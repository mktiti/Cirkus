package hu.mktiti.cirkus.runtime.handler.log

import hu.mktiti.cirkus.runtime.handler.log.LogSender.*
import hu.mktiti.kreator.Injectable
import hu.mktiti.kreator.InjectableArity
import hu.mktiti.kreator.InjectableType

@InjectableType
interface LogConverter {

    fun convert(entry: ActorLogEntry): String

}

@Injectable(arity = InjectableArity.SINGLETON)
class DefaultLogConverter : LogConverter {

    private fun prefix(sender: LogSender): String = when (sender) {
        RUNTIME -> "[cirkus] "
        ENGINE -> "[game] "
        SELF -> ""
    }

    override fun convert(entry: ActorLogEntry): String = prefix(entry.sender) + entry.message

}