package com.spyrdonapps.weightreductor.frontend.components.playground

import co.touchlab.kermit.Kermit
import com.spyrdonapps.common.model.Weighing
import com.spyrdonapps.common.repository.WeighingRepository
import com.spyrdonapps.weightreductor.frontend.AppDependenciesContext
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import react.*
import react.dom.h1

external interface BeItemsListState : RState {
    var list: List<Weighing>
    var isInitialized: Boolean
}

class BeItemsList : RComponent<RProps, BeItemsListState>() {

    override fun BeItemsListState.init() {
        list = emptyList()
        isInitialized = false
    }

    override fun RBuilder.render() {
        // TODO I need to move api call to componentDidMount, but I don't know how to get Context
        //  without RBuilder.
        var repository: WeighingRepository? = null
        var logger: Kermit? = null
        AppDependenciesContext.Consumer { dependencies ->
            repository = dependencies.repository
            logger = dependencies.logger
        }
        if (!state.isInitialized) {
            val mainScope = MainScope()
            mainScope.launch {
                val items: List<Weighing> = try {
                    repository?.getAllWeighings().orEmpty()
                } catch (e: Exception) {
                    logger?.e(e) { "Error getting weighings" }
                    emptyList()
                }
                setState {
                    list = items
                    isInitialized = true
                }
                mainScope.cancel()
            }
        }

        h1 {
            +"Items from BE:"
        }
        simpleList {
            list = state.list.map { item -> "${item.weight} (${item.date})" }
        }
    }
}

fun RBuilder.beItemsList(handler: RProps.() -> Unit): ReactElement {
    return child(BeItemsList::class) {
        this.attrs(handler)
    }
}