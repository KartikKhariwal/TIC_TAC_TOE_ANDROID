package com.example.tic_tac_toe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() , View.OnClickListener{
    lateinit var board: Array<Array<Button>>
    var PLAYER:Boolean =true  //Player 1 or 2
    var TURN_COUNT:Int=0  //Number of turns
    var boardstatus =Array(3){IntArray(3)}
    var player1=0 //to mantain leaderboard
    var player2=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        board=arrayOf(
            arrayOf(btn00,btn01,btn02),
            arrayOf(btn10,btn11,btn12),
            arrayOf(btn20,btn21,btn22)
        )
        for(i in board ){
            for(button in i){
                button.setOnClickListener(this)
            }
        }
        initializeStatus();
        resetbtn.setOnClickListener {
            initializeStatus();
            TURN_COUNT=0
            PLAYER=true
        }

        resetleader.setOnClickListener {
            updateLeaderboard(-player1,-player2)
        }
    }

    private fun initializeStatus() {
        displaytext.text="Player 1 turn : X "
        for(i in 0..2){
            for(j in 0..2){
                boardstatus[i][j]=-1
                board[i][j].isEnabled=true
                board[i][j].setText("-")
            }
        }
        TURN_COUNT=0
        PLAYER=true
    }

    override fun onClick(view: View) {
        var player=if(PLAYER) 1 else 2
        var symbol=if(PLAYER)"X" else "O"
        updateDisplay("Player $player turn : $symbol ")
        when(view.id){
            R.id.btn00->{
                updatevalue(row=0,col=0,player=PLAYER)
            }
            R.id.btn01->{
                updatevalue(row=0,col=1,player=PLAYER)
            }
            R.id.btn02->{
                updatevalue(row=0,col=2,player=PLAYER)
            }
            R.id.btn10->{
                updatevalue(row=1,col=0,player=PLAYER)
            }
            R.id.btn11->{
                updatevalue(row=1,col=1,player=PLAYER)
            }
            R.id.btn12->{
                updatevalue(row=1,col=2,player=PLAYER)
            }
            R.id.btn20->{
                updatevalue(row=2,col=0,player=PLAYER)
            }
            R.id.btn21->{
                updatevalue(row=2,col=1,player=PLAYER)
            }
            R.id.btn22-> {
                updatevalue(row=2,col=2,player=PLAYER)
            }
        }
        val win=checkwin()
        if(win){
            updateDisplay("Player $player with symbol $symbol WON")
            disableAll()
            if(PLAYER)
                updateLeaderboard(1,0)
            else
                updateLeaderboard(0,1)
            return;
        }

        if(TURN_COUNT==9){
            updateDisplay("GAME DRAWN")
            disableAll()
            updateLeaderboard(1,1)
            return ;
        }

        PLAYER=!PLAYER
        player=if(PLAYER) 1 else 2
        symbol=if(PLAYER)"X" else "O"
        updateDisplay("Player $player turn : $symbol ")

    }

    private fun updateLeaderboard(i: Int, i1: Int) {
        player1+=i
        player2+=i1
        pl1.text="$player1"
        pl2.text="$player2"
    }

    private fun disableAll() {
        for(i in 0..2){
            for(j in 0..2){
                board[i][j].isEnabled=false
            }
        }
        Toast.makeText(this,"Press Reset to start New Game",Toast.LENGTH_LONG).show()
    }

    private fun updateDisplay(s: String) {
        displaytext.text=s
    }

    private fun checkwin():Boolean {
        for(i in 0..2){
            if(boardstatus[i][0]!=-1&&boardstatus[i][0]==boardstatus[i][1]&&boardstatus[i][1]==boardstatus[i][2]){
                return true;
            }
            if(boardstatus[0][i]!=-1&&boardstatus[0][i]==boardstatus[1][i]&&boardstatus[1][i]==boardstatus[2][i]){
                return true;
            }
        }
        if(boardstatus[0][0]!=-1&&boardstatus[0][0]==boardstatus[1][1]&&boardstatus[0][0]==boardstatus[2][2]){
            return true;
        }
        if(boardstatus[1][1]!=-1&&boardstatus[0][2]==boardstatus[1][1]&&boardstatus[0][2]==boardstatus[2][0]){
            return true;
        }
        return false
    }

    private fun updatevalue(row: Int, col: Int, player: Boolean) {
        board[row][col].isEnabled=false
        when(PLAYER){
            true -> {
                board[row][col].text="X"
                boardstatus[row][col]=1
            }
            false -> {
                board[row][col].text="O"
                boardstatus[row][col]=0
            }
        }
        TURN_COUNT+=1

    }
}