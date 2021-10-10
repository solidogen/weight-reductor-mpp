package com.spyrdonapps.weightreductor.frontend.utils

import com.spyrdonapps.weightreductor.frontend.logger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface Navigator {
    val mainPagesStateFlow: StateFlow<SitePage>
    fun navigateTo(sitePage: SitePage)
}

internal class DefaultNavigator : Navigator {

    private val mutableMainPagesStateFlow: MutableStateFlow<SitePage> = MutableStateFlow(SitePage.Home)

    override val mainPagesStateFlow: StateFlow<SitePage>
        get() = mutableMainPagesStateFlow

    override fun navigateTo(sitePage: SitePage) {
        val currentSitePage = mainPagesStateFlow.value
        if (currentSitePage != sitePage) {
            logger.d { "Navigator navigating to: $sitePage" }
        }
        mutableMainPagesStateFlow.value = sitePage
    }
}