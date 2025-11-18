package com.example.spectrumstitch

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.spectrumstitch.databinding.ActivityMainBinding
import com.leinardi.android.speeddial.SpeedDialActionItem
import com.leinardi.android.speeddial.SpeedDialView
import com.skydoves.colorpickerview.ColorEnvelope
import com.skydoves.colorpickerview.ColorPickerDialog
import com.skydoves.colorpickerview.ColorPickerView
import com.skydoves.colorpickerview.flag.FlagMode
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener
import com.skydoves.colorpickerview.sliders.AlphaSlideBar
import com.skydoves.colorpickerview.sliders.BrightnessSlideBar


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val editedSquares = mutableListOf(-1)
    private var currentColor = Color.WHITE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        val speedDialView = findViewById<SpeedDialView>(R.id.speedDial)
        speedDialView.addActionItem(
            SpeedDialActionItem.Builder(0, R.drawable.white_palette)
                .create()
        )
//        binding.fab.setOnClickListener {
//            // Toggle visibility of other FABs
//            val isVisible = binding.fabOption1.visibility == View.VISIBLE
//            binding.fabOption1.visibility = if (isVisible) View.GONE else View.VISIBLE
//            binding.fabOption2.visibility = if (isVisible) View.GONE else View.VISIBLE
//        }
//
//        binding.fabOption1.setOnClickListener {
//            Snackbar.make(it, "Option 1 selected", Snackbar.LENGTH_SHORT).show()
//        }
//
//        binding.fabOption2.setOnClickListener {
//            Snackbar.make(it, "Option 2 selected", Snackbar.LENGTH_SHORT).show()
//        }

        val myButton: Button = findViewById(R.id.button_grid)

        myButton.setOnClickListener {
            // This is the action that will happen when the button is clicked
            val gridLayout: GridLayout = findViewById(R.id.gridLayout)
            gridLayout.removeAllViews()
            gridLayout.columnCount = 8
            gridLayout.rowCount = 8

            var lastTouchedSquare: View? = null

            for (i in 0 until 64) {
                val square = View(this)

                // Set the layout parameters for the square
                val params = GridLayout.LayoutParams()
                params.width = 100 // Set square size
                params.height = 100
                square.layoutParams = params

                // Create a Drawable with a white background and a black border
                val drawable = GradientDrawable()
                drawable.setColor(Color.WHITE) // Set the square's background to white
                drawable.setStroke(2, Color.BLACK) // Set a black border with 2px width
                square.background = drawable

                // Add the square to the grid layout
                gridLayout.addView(square)
            }

        }



//        val b1: Button = findViewById(R.id.button1)
//        val b2: Button = findViewById(R.id.button2)
//        val b3: Button = findViewById(R.id.button3)
//        val b4: Button = findViewById(R.id.button4)
//
//        b1.setOnClickListener({
//            currentColor = Color.WHITE
//        })
//        b2.setOnClickListener({
//            currentColor = Color.BLUE
//        })
//        b3.setOnClickListener({
//            currentColor = Color.RED
//        })
//
//        b4.setOnClickListener({
//            var dialog = ColorPickerDialog.Builder(this)
//                .setTitle("Color Picker Dialog")
//                .setPreferenceName("MyColorPickerDialog")
//                .setPositiveButton("Confirm",
//                    ColorEnvelopeListener { envelope, _ ->
//                        setLayoutColor(envelope)
//                    }
//                )
//                .setNegativeButton("Cancel") { dialogInterface, _ ->
//                    dialogInterface.dismiss()
//                }
//                .attachAlphaSlideBar(true) // default value is true
//                .attachBrightnessSlideBar(true) // default value is true
//                .setBottomSpace(12) // set a bottom space between the last slide bar and buttons
//            dialog.show()
//        })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    // Helper function to toggle the square color
    fun toggleColor(square: View) {
        if (currentColor != null)
        {
            // Set the new color for the square's background
            val newDrawable = GradientDrawable()
            newDrawable.setColor(currentColor) // Set the background color to blue or white
            newDrawable.setStroke(2, Color.BLACK) // Keep the black border
            square.background = newDrawable
        }
    }
    override fun dispatchTouchEvent(event: MotionEvent): Boolean {

        val grid = findViewById<GridLayout>(R.id.gridLayout)
        // Get padding and content size
        val paddingLeft = grid.paddingLeft
        val paddingTop = grid.paddingTop
        val paddingRight = grid.paddingRight
        val paddingBottom = grid.paddingBottom

        val contentWidth = grid.width - paddingLeft - paddingRight
        val contentHeight = grid.height - paddingTop - paddingBottom

        // Calculate square dimensions
        val squareWidth = contentWidth / grid.columnCount
        val squareHeight = contentHeight / grid.rowCount

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                // Track when a touch event occurs on the screen
                Log.d("GlobalCursorTrack", "Touch started at x=${event.x}, y=${event.y}")
            }
            MotionEvent.ACTION_MOVE -> {
                // Track when the user moves their finger across the screen (touch move)
//                Log.d("GlobalCursorTrack", "Touch moved at x=${event.x}, y=${event.y}")

                val cursorX = event.x
                val cursorY = event.y

                val location = IntArray(2)
                grid.getLocationOnScreen(location)

                val gridX = event.rawX - location[0]
                val gridY = event.rawY - location[1]

                // Account for scrolling if applicable
                val scrollX = grid.scrollX
                val scrollY = grid.scrollY
                val adjustedX = gridX + scrollX
                val adjustedY = gridY + scrollY

                // Calculate the column and row
                val column = (adjustedX / squareWidth).toInt()
                val row = (adjustedY / squareHeight).toInt()

                // Ensure the calculated row and column are within the grid bounds
                if (column in 0 until grid.columnCount && row in 0 until grid.rowCount) {
                    if (! editedSquares.contains(row * grid.columnCount + column)) {
                        val squareIndex = row * grid.columnCount + column
                        val square = grid.getChildAt(squareIndex)
                        editedSquares.add(row * grid.columnCount + column)
                        toggleColor(square)
                        Log.d("ListOutput", editedSquares.toString())
                        // Perform any action with the square (e.g., change its color)
                        Log.d("GridTracking", "Cursor is over square at row=$row, column=$column")
                    }
                }
            }
            MotionEvent.ACTION_UP -> {
                // Track when the user lifts their finger
                Log.d("GlobalCursorTrack", "Touch ended at x=${event.x}, y=${event.y}")
                editedSquares.clear()
            }
        }
        return super.dispatchTouchEvent(event)  // Propagate the event further
    }

    override fun onGenericMotionEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_HOVER_MOVE) {
            // Track when the pointer moves (for mouse or pointer devices)
            Log.d("GlobalCursorTrack", "Pointer moving at x=${event.x}, y=${event.y}")
        }
        return super.onGenericMotionEvent(event)  // Propagate the event further
    }
    private fun setLayoutColor(envelope: ColorEnvelope) {
        val color = envelope.color
        currentColor = color
        Log.d("What", color.toString())
    }

}
