package labs.pooh.mycanteen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_hello_select.*

class HelloSelectActivity : AppCompatActivity() {

    companion object {
        const val BUTTON_MAP_POSITION_X = "BUTTON_MAP_POSITION_X"
        const val BUTTON_MAP_POSITION_Y = "BUTTON_MAP_POSITION_Y"
        const val BUTTON_SEARCH_POSITION_X = "BUTTON_SEARCH_POSITION_X"
        const val BUTTON_SEARCH_POSITION_Y = "BUTTON_SEARCH_POSITION_Y"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hello_select)

        fabSearch.setOnClickListener {
            markButtonsClickable(false)
            startActivity(Intent(applicationContext, MainActivity::class.java).apply {
                getCenterPositionOf(fabSearch).let { (x, y) ->
                    putExtra(BUTTON_SEARCH_POSITION_X, x)
                    putExtra(BUTTON_SEARCH_POSITION_Y, y)
                }
            })
        }

        fabMap.setOnClickListener {
            markButtonsClickable(false)
            startActivity(Intent(applicationContext, MapSearchActivity::class.java).apply {
                getCenterPositionOf(fabMap).let { (x, y) ->
                    putExtra(BUTTON_MAP_POSITION_X, x)
                    putExtra(BUTTON_MAP_POSITION_Y, y)
                }
            })
        }
    }

    override fun onResume() {
        super.onResume()
        markButtonsClickable()
    }

    private fun markButtonsClickable(clickable: Boolean = true) {
        fabMap.isClickable = clickable
        fabSearch.isClickable = clickable
    }

    private fun getCenterPositionOf(view: View): Pair<Int, Int> {
        val x = view.x + view.width / 2
        val y = view.y + view.height / 2
        return Pair(x.toInt(), y.toInt())
    }
}