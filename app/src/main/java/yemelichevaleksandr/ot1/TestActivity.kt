package yemelichevaleksandr.ot1

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import yemelichevaleksandr.ot1.TestModel.Companion.NUMBER_QUESTIONS_IN_TEST
import yemelichevaleksandr.ot1.databinding.SecondactivityBinding

class TestActivity : AppCompatActivity(), TestActivityView {

    private lateinit var binding: SecondactivityBinding
    private val presenter = TestPresenter(this, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = SecondactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter.getFirstQuestion()

        binding.bt1.setOnClickListener { if (it is Button) presenter.checkAnswer(it.text.toString()) }
        binding.bt2.setOnClickListener { if (it is Button) presenter.checkAnswer(it.text.toString()) }
        binding.bt3.setOnClickListener { if (it is Button) presenter.checkAnswer(it.text.toString()) }
        binding.bt4.setOnClickListener { if (it is Button) presenter.checkAnswer(it.text.toString()) }
        binding.bt5.setOnClickListener { if (it is Button) presenter.checkAnswer(it.text.toString()) }

    }

    override fun renderQuestion(question: Question) {
        val arrayBtn = arrayListOf(
            binding.bt1,
            binding.bt2,
            binding.bt3,
            binding.bt4,
            binding.bt5,
        )
        arrayBtn.shuffle()

        binding.tvVopros.text = question.question
        arrayBtn[0].text = question.answersList[0]
        arrayBtn[1].text = question.answersList[1]
        arrayBtn[2].text = question.answersList[2]
        arrayBtn[3].text = question.answersList[3]
        arrayBtn[4].text = question.answersList[4]
    }

    override fun showDialogYes() {
        AlertDialog.Builder(this).apply {
            setCancelable(false)
            setTitle("ВЕРНО !")
            setIcon(R.drawable.ic_baseline_check_circle_outline_24)
            setPositiveButton("Дальше"
            ) { dialog, _ ->
                dialog.cancel()
                presenter.getNextQuestion()
            }
            show()
        }
    }

    override fun showDialogNo(question: Question, answer: String) {
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_no, null)

        AlertDialog.Builder(this).apply {
            setView(dialogView)
            setCancelable(false)
            setTitle("НЕ верно !")
            setIcon(R.drawable.ic_baseline_cancel_24)

            dialogView.findViewById<TextView>(R.id.tvNo2).text = answer
            dialogView.findViewById<TextView>(R.id.tvYes2).text = question.answersList[0]
            dialogView.findViewById<TextView>(R.id.tvPunkt2).text = question.info

            setPositiveButton("Дальше") { dialog, _ ->
                dialog.cancel()
                presenter.getNextQuestion()
            }
            create().show()
        }


    }

    @SuppressLint("SetTextI18n")
    override fun showResult(numberCorrectAnswers: Int) {
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_result, null)
        AlertDialog.Builder(this).apply {
            setView(dialogView)
            setCancelable(false)
            setTitle("Результат")
            setIcon(R.drawable.ic_baseline_mail_outline_24)
            dialogView.findViewById<TextView>(R.id.tvRight).text =
                "$numberCorrectAnswers из $NUMBER_QUESTIONS_IN_TEST"

            val tvMark = dialogView.findViewById<TextView>(R.id.tvMark)
            if (numberCorrectAnswers < 14) {
                tvMark.text = "неудовлетворительно"
                tvMark.setTextColor(Color.RED)
            } else if (numberCorrectAnswers in 14..15) {
                tvMark.text = "удовлетворительно"
                tvMark.setTextColor(Color.MAGENTA)
            } else if (numberCorrectAnswers in 16..19) {
                tvMark.text = "хорошо"
                tvMark.setTextColor(Color.BLUE)
            } else if (numberCorrectAnswers == 20) {
                tvMark.text = "отлично"
                tvMark.setTextColor(Color.GREEN)
            }
            setPositiveButton("На главную"
            ) { dialog, _ ->
                dialog.cancel()
                finish()
            }
            create().show()
        }
    }

    private fun showDialogStop() {
        val dialogView = this.layoutInflater.inflate(R.layout.dialog_stop, null)

        AlertDialog.Builder(this).apply {
            setView(dialogView)
            setCancelable(false)
            setTitle("СТОП !")
            setIcon(R.drawable.ic_baseline_close_24)

            setPositiveButton("ДА") { _, _ -> finish() }
            setNegativeButton("НЕТ") { dialog, _ -> dialog.cancel() }
            create().show()
        }
    }

    override fun onBackPressed() {
        showDialogStop()
    }
}