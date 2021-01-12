package com.spyrdonapps.weightreductor.frontend.components.playground

import co.touchlab.kermit.Kermit
import com.spyrdonapps.common.model.Weighing
import com.spyrdonapps.common.repository.WeighingRepository
import com.spyrdonapps.weightreductor.frontend.AppDependenciesContext
import com.spyrdonapps.weightreductor.frontend.resources.CustomColor
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.css.*
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.html.ButtonType
import kotlinx.html.js.onClickFunction
import react.*
import react.dom.br
import react.dom.col
import react.dom.div
import react.dom.h1
import styled.css
import styled.styledButton
import styled.styledDiv

external interface WeighingListState : RState {
    var list: List<Weighing>
    var shouldGetWeighings: Boolean
    var weighingToPost: Weighing?
}

class WeighingList : RComponent<RProps, WeighingListState>() {

    override fun WeighingListState.init() {
        list = emptyList()
        shouldGetWeighings = true
        weighingToPost = null
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
        if (state.shouldGetWeighings) {
            // todo inject mainScope
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
                    shouldGetWeighings = false
                }
                mainScope.cancel()
            }
        }
        state.weighingToPost?.let { weighing ->
            val mainScope = MainScope()
            mainScope.launch {
                try {
                    repository?.postWeighing(weighing)
                } catch (e: Exception) {
                    logger?.e(e) { "Error getting weighings" }
                }
                setState {
                    weighingToPost = null
                }
                mainScope.cancel()
            }
        }
        styledDiv {
            css {
                display = Display.flex
                flexDirection = FlexDirection.column
                alignItems = Align.flexStart
            }
            styledButton {
                css {
                    backgroundColor = CustomColor.green
                    color = Color.white
                }
                attrs.apply {
                    type = ButtonType.submit
                    onClickFunction = {
                        it.preventDefault()
                        setState {
                            weighingToPost = Weighing(47f, Clock.System.now())
                        }
                    }
                }
                +"Click to post weighing"
            }
            styledButton {
                css {
                    backgroundColor = CustomColor.green
                    color = Color.white
                }
                attrs.apply {
                    type = ButtonType.submit
                    onClickFunction = {
                        it.preventDefault()
                        setState {
                            shouldGetWeighings = true
                        }
                    }
                }
                +"Reload weighings"
            }
        }
        h1 {
            +"Weighings:"
        }
        simpleList {
            list = state.list.map { item -> "${item.weight} (${item.date})" }
        }
    }
}

fun RBuilder.weighingsList(handler: RProps.() -> Unit): ReactElement {
    return child(WeighingList::class) {
        this.attrs(handler)
    }
}