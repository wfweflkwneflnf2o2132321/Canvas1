package com.example.canvas

import android.view.View
import com.example.canvas.base.Event
import com.example.canvas.data.COLOR
import com.example.canvas.data.TOOLS
import com.example.canvas.ui.DrawView


data class ViewState(
    val toolsList: List<ToolItem.ToolModel>,
    val colorList: List<ToolItem.ColorModel>,
//    val sizeList: List<ToolItem.SizeModel>,
    val canvasViewState: CanvasViewState,
    val isPaletteVisible: Boolean,
    val isBrushSizeChangerVisible: Boolean,
    val isToolsVisible: Boolean
)

sealed class UiEvent : Event {
    data class OnPaletteClicked(val index: Int) : UiEvent()
    data class OnColorClick(val index: Int) : UiEvent()
    data class OnSizeClick(val index: View) : UiEvent()
    data class OnToolsClick(val index: Int) : UiEvent()
    data class OnDrawViewFill(val drawView: DrawView) : UiEvent()
    data class OnSizeChange(val value: Float) : UiEvent()
    object OnDrawViewClicked : UiEvent()
    object OnToolbarClicked : UiEvent()
}

sealed class DataEvent : Event {
    data class OnSetDefaultTools(val tool: TOOLS, val color: COLOR) : DataEvent()
}