import app.cash.turbine.test
import com.fappslab.viacep.arch.viewmodel.ViewAction
import com.fappslab.viacep.arch.viewmodel.ViewModel
import com.fappslab.viacep.arch.viewmodel.ViewState
import io.mockk.MockKVerificationScope
import io.mockk.coVerify
import kotlin.test.assertEquals

typealias CoInvokeType = suspend MockKVerificationScope.() -> Unit

suspend fun <S : ViewState, A : ViewAction, VM : ViewModel<S, A>> VM.verify(tests: ViewModelEventList<S, A>.() -> Unit) {

    val data = ViewModelEventList<S, A>().apply(tests)

    state.test {
        assertEquals(state.value, awaitItem())
        data.states.forEach { assertEquals(it, awaitItem()) }
        cancelAndConsumeRemainingEvents()
    }

    action.test {
        data.actions.forEach { assertEquals(it, awaitItem()) }
        cancelAndConsumeRemainingEvents()
    }

    data.invokes.forEach {
        coVerify { it() }
    }
}

class ViewModelEventList<S : ViewState, A : ViewAction>(
    val states: MutableList<S> = mutableListOf(),
    val actions: MutableList<A> = mutableListOf(),
    val invokes: MutableList<CoInvokeType> = mutableListOf()
) {
    fun emitAction(item: () -> A) {
        actions.add(item())
    }

    fun emitState(item: () -> S) {
        states.add(item())
    }

    fun invoke(times: Int = 1, item: CoInvokeType) {
        for (i in times downTo 0) invokes.add(item)
    }
}
