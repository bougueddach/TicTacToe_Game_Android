package com.bougueddach.tictactoy

import android.annotation.SuppressLint
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.thread
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    var buttons = ArrayList<Button>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttons.addAll(listOf(bu1, bu2, bu3, bu4, bu5, bu6, bu7, bu8, bu9))
    }

    var player1 = ArrayList<Int>()
    var player2 = ArrayList<Int>()
    var activePlayer = 1
    fun buClick(view: View) {
        val selectedBu = view as Button
        var cellID = 0
        for (i in buttons.indices) {
            if (selectedBu.id.toString() == buttons[i].id.toString()) {
                cellID = i + 1
            }
        }
        //  Toast.makeText(this, "ID $cellID ", Toast.LENGTH_LONG).show()
        playGame(cellID, selectedBu)
    }

    fun playGame(cellID: Int, selectedBU: Button) {
        if (activePlayer == 1) {
            selectedBU.text = "X"
            selectedBU.setBackgroundColor(Color.RED)
            player1.add(cellID)
            if(checkWinner(player1)==true)
                return
            activePlayer = 2
            autolPlay()
        }else if (activePlayer == 2) {
            selectedBU.text = "O"
            selectedBU.setBackgroundColor(Color.GREEN)
            player2.add(cellID)
            checkWinner(player2)
            activePlayer = 1
        }
        selectedBU.isEnabled = false
    }

    fun checkWinner(T: ArrayList<Int>):Boolean {
        var winner: Int = -1

        if (T.containsAll(listOf<Int>(1, 2, 3)) || T.containsAll(listOf<Int>(1, 5, 9))
                || T.containsAll(listOf<Int>(1, 4, 7)) || T.containsAll(listOf<Int>(2, 5, 8))
                || T.containsAll(listOf<Int>(3, 6, 9)) || T.containsAll(listOf<Int>(4, 5, 6))
                || T.containsAll(listOf<Int>(7, 8, 9)) || T.containsAll(listOf<Int>(3, 5, 7))) {
            winner = activePlayer
            for (i in buttons.indices) {
                buttons[i].isEnabled = false
            }
            Toast.makeText(this, "Player $winner WIN the game", Toast.LENGTH_LONG).show()
            return true
        }
        return false
    }

    fun autolPlay() {
        var emptyCellsIDs = ArrayList<Int>()
        for (cellID in 1..9) {
            if (!(player1.contains(cellID) || player2.contains(cellID)))
                emptyCellsIDs.add(cellID)
        }
        if(emptyCellsIDs.isEmpty()){
            Toast.makeText(this, "No one won STUPIDS", Toast.LENGTH_LONG).show()
            return
        }
        val randIndex = Random().nextInt(emptyCellsIDs.size - 0) + 0
        val randCellID = emptyCellsIDs[randIndex]
        playGame(randCellID, buttons[randCellID - 1])
    }

    fun buReplayClicked(view: View){
        for (i in buttons.indices) {
            buttons[i].text = ""
            buttons[i].setBackgroundResource(R.color.buttonColor)
            buttons[i].isEnabled=true
        }
        player1.clear()
        player2.clear()
    }
}
