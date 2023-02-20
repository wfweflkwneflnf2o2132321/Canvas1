package com.example.canvas

import android.graphics.Color
import android.view.View
import com.example.canvas.base.BaseViewModel
import com.example.canvas.base.Event
import com.example.canvas.data.COLOR
import com.example.canvas.data.SIZE
import com.example.canvas.data.TOOLS

class CanvasViewModel : BaseViewModel<ViewState>() {
    override fun initialViewState(): ViewState =
        ViewState(
            colorList = enumValues<COLOR>().map { ToolItem.ColorModel(it.value) },
            toolsList = enumValues<TOOLS>().map { ToolItem.ToolModel(it) },
            //sizeList = enumValues<SIZE>().map { ToolItem.SizeModel(it.value) },
            canvasViewState = CanvasViewState(
                color = COLOR.BLACK,
                size = 1f,
                tools = TOOLS.NORMAL
            ),
            isToolsVisible = false,
            isBrushSizeChangerVisible = false,
            isPaletteVisible = false,

        )

    init {
        processDataEvent(DataEvent.OnSetDefaultTools(tool = TOOLS.NORMAL, color = COLOR.BLACK))
    }

    override fun reduce(event: Event, previousState: ViewState): ViewState? {
        when (event) {

            is UiEvent.OnToolbarClicked -> {
                return previousState.copy(
                    isToolsVisible = !previousState.isToolsVisible,
                    isPaletteVisible = false,
                )
            }

            is UiEvent.OnToolsClick -> {
                when (event.index) {
                    TOOLS.PALETTE.ordinal -> {
                        return previousState.copy(isPaletteVisible = !previousState.isPaletteVisible)
                    }
                    /*TOOLS.SIZE.ordinal -> {
                        return previousState.copy(isToolsVisible = !previousState.isToolsVisible)
                    }*/



                    else -> {

                        val toolsList = previousState.toolsList.mapIndexed() { index, model ->
                            if (index == event.index) {
                                model.copy(isSelected = true)
                            } else {
                                model.copy(isSelected = false)
                            }
                        }

                        return previousState.copy(
                            toolsList = toolsList,
                            canvasViewState = previousState.canvasViewState.copy(tools = TOOLS.values()[event.index])
                        )
                    }
                }
            }

            is UiEvent.OnPaletteClicked -> {
                val selectedColor = COLOR.values()[event.index]

                val toolsList = previousState.toolsList.map {
                    if (it.type == TOOLS.PALETTE) {
                        it.copy(selectedColor = selectedColor)
                    } else {
                        it
                    }
                }

                return previousState.copy(
                    toolsList = toolsList,
                    canvasViewState = previousState.canvasViewState.copy(color = selectedColor)
                )
            }

            /*is UiEvent.OnSizeClick -> {
                val selectedSize = SIZE.values()[event.index]
                val toolsList = previousState.toolsList.map {
                    if (it.type == TOOLS.SIZE) {
                        it.copy(selectedSize = selectedSize)
                    } else{
                        it
                    }
                }
                return previousState.copy(
                    toolsList = toolsList,
                    canvasViewState = previousState.canvasViewState.copy (size = selectedSize))
            }*/

            is UiEvent.OnDrawViewFill -> {
                event.drawView.clear()
                event.drawView.setBackgroundColor(Color.WHITE)
                return null
            }

            is UiEvent.OnSizeChange -> {

                return previousState.copy(
                    canvasViewState = previousState.canvasViewState.copy(size = event.value)
                )
            }



            is DataEvent.OnSetDefaultTools -> {
                val toolsList = previousState.toolsList.map { model ->
                    if (model.type == event.tool) {
                        model.copy(isSelected = true, selectedColor = event.color)
                    } else {
                        model.copy(isSelected = false)
                    }
                }

                return previousState.copy(
                    toolsList = toolsList
                )
            }

            else -> return null
        }
    }



}

private operator fun Any.get(index: View): SIZE {
TODO()
}


