package com.spyrdonapps.weightreductor.frontend.components.playground

import com.spyrdonapps.common.model.ShoppingListItem
import com.spyrdonapps.common.repository.SampleClientRepository
import com.spyrdonapps.weightreductor.frontend.AppDependenciesContext
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import react.*
import react.dom.h1

external interface BeItemsListState : RState {
    var list: List<ShoppingListItem>
}

class BeItemsList : RComponent<RProps, BeItemsListState>() {

    override fun BeItemsListState.init() {
        list = emptyList()
    }

    override fun RBuilder.render() {
        // TODO I need to move api call to componentDidMount, but I don't know how to get Context
        //  without RBuilder.
        var repository: SampleClientRepository? = null
        AppDependenciesContext.Consumer { dependencies ->
            repository = dependencies.repository
        }
        if (state.list.isEmpty()) {
            val mainScope = MainScope()
            mainScope.launch {
                val items = repository?.fetchHome().orEmpty()
                setState {
                    list = items
                }
                mainScope.cancel()
            }
        }

        h1 {
            +"Items from BE:"
        }
        simpleList {
            list = state.list.map { item -> "${item.name} (${item.id})" }
        }
    }
}

fun RBuilder.beItemsList(handler: RProps.() -> Unit): ReactElement {
    return child(BeItemsList::class) {
        this.attrs(handler)
    }
}