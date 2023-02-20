package com.example.canvas

import android.graphics.*
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.canvas.ui.DrawView
import com.google.android.material.slider.Slider


//private lateinit var extraCanvas: Canvas

class MainActivity : AppCompatActivity() {

    companion object {
        private const val PALETTE_VIEW = 0
        private const val TOOLS_VIEW = 1
        private const val SIZE_VIEW = 2
    }
    private val viewModel: CanvasViewModel by viewModels()

    private var toolsList: List<ToolsLayout> = listOf()

    private val paletteLayout: ToolsLayout by lazy { findViewById(R.id.paletteLayout) }
    private val toolsLayout: ToolsLayout by lazy { findViewById(R.id.toolLayout) }
    private val ivTools: ImageView by lazy { findViewById(R.id.ivTools) }
    private val drawView: DrawView by lazy { findViewById(R.id.viewDraw) }
    private val ivClear: ImageView by lazy { findViewById(R.id.ivClear) }
    private val sliderSize: Slider by lazy { findViewById(R.id.sliderSize) }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolsList = listOf(paletteLayout, toolsLayout)
        viewModel.viewState.observe(this, ::render)

        paletteLayout.setOnClickListener {
            viewModel.processUiEvent(UiEvent.OnPaletteClicked(it))
        }

        toolsLayout.setOnClickListener {
            viewModel.processUiEvent(UiEvent.OnToolsClick(it))
        }

        ivTools.setOnClickListener {
            viewModel.processUiEvent(UiEvent.OnToolbarClicked)
        }


        ivClear.setOnClickListener {
            viewModel.processUiEvent(UiEvent.OnDrawViewFill(drawView = drawView))
        }

        sliderSize.addOnChangeListener { slider, value, fromUser ->
            viewModel.processUiEvent(UiEvent.OnSizeChange(value * 10))
        }



    }




    private fun render(viewState: ViewState) {


        with(toolsList[PALETTE_VIEW]) {
            render(viewState.colorList)
            isVisible = viewState.isPaletteVisible
        }

        with(toolsList[TOOLS_VIEW]) {
            render(viewState.toolsList)
            isVisible = viewState.isToolsVisible
        }

//        with  (toolsList[SIZE_VIEW]) {
//            render(viewState.sizeList)
//            isVisible = viewState.isBrushSizeChangerVisible
//        }




        drawView.render(viewState.canvasViewState)
    }


}






