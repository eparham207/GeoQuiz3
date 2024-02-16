package com.parham.msu.moregeoquiz


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import com.parham.msu.moregeoquiz.databinding.ActivityMainBinding
import kotlin.math.roundToInt

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    private val questionBank = listOf(
        Question(R.string.question_australia, answer = true),
        Question(R.string.question_ocean, answer = true),
        Question(R.string.question_mideast, answer = false),
        Question(R.string.question_Africa, answer = false),
        Question(R.string.question_americas, answer = true),
        Question(R.string.question_asia, answer = true)
    )
    private var currentIndex = 0

    private var correctAnswerCount = 0

    private var totalQuestion = questionBank.size

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.trueButton.setOnClickListener{
            checkAnswer(true)
            setButtonState(false)
        }
        binding.FalseButton.setOnClickListener{
            checkAnswer(false)
            setButtonState(false)
        }

        binding.nextButton.setOnClickListener{
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
            setButtonState(true)
        }

        binding.questionTextview.setOnClickListener{
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
            setButtonState(true)

        }
        binding.previousButton.setOnClickListener{
            currentIndex = if (currentIndex > 0) {
                (currentIndex - 1) % questionBank.size
            } else {
                questionBank.size - 1 // Set currentIndex to the last index if it's already 0
            }
            updateQuestion()
            setButtonState(true)
        }

        updateQuestion()
    }
    private fun updateQuestion() {
        val questiontextResId = questionBank[currentIndex].textResId
        binding.questionTextview.setText(questiontextResId)
    }
    private fun checkAnswer(userAnswer:Boolean) {
        val correctAnswer = questionBank[currentIndex].answer

        if (userAnswer == correctAnswer) {
            correctAnswerCount++
        }

        val messageResId = if (userAnswer == correctAnswer){
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
            .show()
        if (currentIndex == questionBank.size - 1) {
            finalScore()
            reset()
        }
    }

    private fun setButtonState(enabled:Boolean) {
        binding.trueButton.isEnabled = enabled
        binding.FalseButton.isEnabled = enabled

    }

    private fun finalScore() {
        val percentage = ((correctAnswerCount * 100.0 / questionBank.size).roundToInt())
        val formattedPercentage = String.format("%.1f", percentage.toDouble())

        val message = "Quiz complete! Your score is $formattedPercentage%"
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
    private fun reset() {
        correctAnswerCount = 0
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

}
