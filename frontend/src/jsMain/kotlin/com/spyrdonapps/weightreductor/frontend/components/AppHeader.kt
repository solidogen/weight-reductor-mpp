package com.spyrdonapps.weightreductor.frontend.components

import androidx.compose.runtime.Composable
import com.spyrdonapps.common.util.utils.Action
import com.spyrdonapps.weightreductor.frontend.utils.*
import org.jetbrains.compose.web.dom.*

@Composable
fun AppHeader(isLoggedIn: Boolean, navigator: Navigator, onLogoutButtonClick: Action) {
    // todo header https://tailwindui.com/preview#component-10058606cac5398d7fa2c73b64089874            https://tailwindcomponents.com/component/navigation-bar-1
    //  - to make popup/hamburger work dynamically - https://stackoverflow.com/questions/62768011/fix-dropdown-auto-open-in-tailwind-ui-navbar-component-with-vue-js
    //  - sticky https://dev.to/cryptic022/sticky-header-and-footer-with-tailwind-2oik
    //  - I need to pass dynamic variables to tailwind classes, I need an external state for this
    //  - currently doing this https://www.youtube.com/watch?v=puaX_nhTMRU in here

    Nav(attrs = { tailwindClasses("bg-gray-100") }) {
        DivTw("max-w-7xl mx-auto") {
            DivTw("flex justify-between") {
                DivTw("flex items-center space-x-4") {
                    LogoWithAppName(navigator)
                    DivTw("flex items-center space-x-1") {
                        NavigationItem("Features") { navigator.navigateTo(SitePage.Features) }
                        NavigationItem("Pricing") { navigator.navigateTo(SitePage.Pricing) }
                    }
                }
                DivTw("flex items-center space-x-1") {
                    if (isLoggedIn) {
                        NavigationItem("Logout") { onLogoutButtonClick.invoke() }
                    } else {
                        NavigationItem("Login") { }
                        HighlightedNavigationItem("Sign up") { }
                    }
                }
            }
        }
    }
}

@Composable
fun NavigationItem(title: String, onClick: Action) {
    DivTw("py-5 px-3 text-gray-700 hover:text-gray-900 cursor-pointer", attrs = {
        onClick { onClick.invoke() }
    }) {
        Text(title)
    }
}

@Composable
fun HighlightedNavigationItem(title: String, onClick: Action) {
    DivTw("py-2 px-3 bg-yellow-400 hover:bg-yellow-300 " +
            "text-yellow-900 hover:text-yellow-800 " +
            "transition duration-300 " +
            "rounded shadow cursor-pointer", attrs = {
        onClick { onClick.invoke() }
    }) {
        Text(title)
    }
}

@Composable
fun LogoWithAppName(navigator: Navigator) {
    DivTw("cursor-pointer", attrs = {
        onClick { navigator.navigateTo(SitePage.Home) }
    }) {
        // this was A with href in tutorial, but I don't use redirections
        DivTw("flex items-center px-2 text-gray-700") {
            Svg(attrs = {
                tailwindClasses("h-6 w-6 mr-1 text-blue-400")
                attr("fill", "none")
                attr("viewBox", "0 0 24 24")
                attr("stroke", "currentColor")
            }) {
                Path(attrs = {
                    attr("stroke-linecap", "round")
                    attr("stroke-linejoin", "round")
                    attr("stroke-width", "2")
                    attr("d", "M12 19l9 2-9-18-9 18 9-2zm0 0v-8")
                })
            }
            SpanTw("font-bold") {
                Text("App Name")
            }
        }
    }
}