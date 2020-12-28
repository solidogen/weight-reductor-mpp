package com.spyrdonapps.weightreductor.frontend

import com.spyrdonapps.common.model.ShoppingListItem
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import react.*
import react.dom.h1
import react.dom.li
import react.dom.ul

val FrontendAppComponent = functionalComponent<RProps> {
    val appDependencies = useContext(AppDependenciesContext)
    val repository = appDependencies.repository

    val (list, setList) = useState(emptyList<ShoppingListItem>())

    useEffectWithCleanup(dependencies = listOf()) {
        val mainScope = MainScope()

        mainScope.launch {
            setList(repository.fetchHome())
        }
        return@useEffectWithCleanup { mainScope.cancel() }
    }

    h1 {
        +"Items from BE:"
    }
    ul {
        list.forEach { item ->
            li {
                +"${item.name} (${item.id})"
            }
        }
    }
    // todo button to call add new (like fullstack sample)
}