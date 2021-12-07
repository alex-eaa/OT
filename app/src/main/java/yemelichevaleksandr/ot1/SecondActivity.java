package yemelichevaleksandr.ot1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.Random;

public class SecondActivity extends Activity {

    TextView tvVopros;
    Button bt1, bt2, bt3, bt4, bt5;
    String variant, prav, punkt;
    int intPravotvet, intVopros, j, j1, j2, j3, j4, j5 = 0;
    ScrollView scroll1;
    String[] test;
    Question[] questionsArray;
    int[] numbers = {2, 3, 4, 5, 6};
    int[] intTest = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30,
            31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60,
            61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90,
            91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116,
            117, 118, 119, 120, 121, 122, 123, 124, 125, 126, 127, 128, 129, 130, 131, 132, 133, 134, 135, 136, 137, 138, 139, 140,
            141, 142, 143, 144, 145, 146, 147, 148, 149, 150, 151, 152, 153, 154, 155, 156, 157, 158, 159, 160, 161, 162, 163, 164,
            165, 166, 167, 168, 169, 170, 171, 172, 173, 174, 175, 176, 177, 178, 179, 180, 181, 182, 183, 184, 185, 186, 187, 188,
            189, 190, 191, 192, 193, 194, 195, 196, 197, 198, 199, 200, 201, 202, 203, 204, 205, 206, 207};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secondactivity);

        scroll1 = (ScrollView) findViewById(R.id.scroll1);
        tvVopros = (TextView) findViewById(R.id.tvVopros);
        bt1 = (Button) findViewById(R.id.bt1);
        bt2 = (Button) findViewById(R.id.bt2);
        bt3 = (Button) findViewById(R.id.bt3);
        bt4 = (Button) findViewById(R.id.bt4);
        bt5 = (Button) findViewById(R.id.bt5);

        // перемешиваем массив
        shuffleArray(intTest);

        test = getResources().getStringArray(R.array.test);

        // создаём билет
        Page();
    }

    @Override
    public void onBackPressed() {
// создаём алертдиалог для прерывания тестирования
        showDialogStop();
    }

    private void showDialogStop() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_stop, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(false);
        dialogBuilder.setTitle("СТОП !");
        dialogBuilder.setIcon(R.drawable.ic_baseline_close_24);

        dialogBuilder.setPositiveButton("ДА", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        dialogBuilder.setNegativeButton("НЕТ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        dialogBuilder.create().show();

    }

    public void Page() {

        scroll1.fullScroll(View.FOCUS_UP);

        j = intTest[intVopros];

        shuffleArray(numbers);
        j1 = numbers[0];
        j2 = numbers[1];
        j3 = numbers[2];
        j4 = numbers[3];
        j5 = numbers[4];

        tvVopros.setText(intVopros + 1 + ". " + getResources().getStringArray(R.array.test)[j * 7]);
        bt1.setText(getResources().getStringArray(R.array.test)[j * 7 + j1]);
        bt2.setText(getResources().getStringArray(R.array.test)[j * 7 + j2]);
        bt3.setText(getResources().getStringArray(R.array.test)[j * 7 + j3]);
        bt4.setText(getResources().getStringArray(R.array.test)[j * 7 + j4]);
        bt5.setText(getResources().getStringArray(R.array.test)[j * 7 + j5]);
    }

    public void Click1(View v) {
        variant = bt1.getText().toString();
        Proverka();
    }

    public void Click2(View v) {
        variant = bt2.getText().toString();
        Proverka();

    }

    public void Click3(View v) {
        variant = bt3.getText().toString();
        Proverka();

    }

    public void Click4(View v) {
        variant = bt4.getText().toString();
        Proverka();
    }

    public void Click5(View v) {
        variant = bt5.getText().toString();
        Proverka();
    }

    public void Proverka() {
// проверяем правильность выбранного ответа
        prav = getResources().getStringArray(R.array.test)[j * 7 + 2];

        if (variant.equals(prav)) {
// если ответ верный показываем алертдиалогДА
            ++intPravotvet;
            showDialogYes();

        } else {
            // если ответ не правильный берём пункт правил и создаём алертдиалогНет
            punkt = getResources().getStringArray(R.array.test)[j * 7 + 1];
            showDialogNo();
        }
        // добавляем в переменную с текущим номером вопроса 1
        ++intVopros;
    }

    private void showDialogNo() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_no, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(false);
        dialogBuilder.setTitle("НЕ верно !");
        dialogBuilder.setIcon(R.drawable.ic_baseline_cancel_24);
        TextView tvNo2 = (TextView) dialogView.findViewById(R.id.tvNo2);
        tvNo2.setText(variant);
        TextView tvYes2 = (TextView) dialogView.findViewById(R.id.tvYes2);
        tvYes2.setText(prav);
        TextView tvPunkt2 = (TextView) dialogView.findViewById(R.id.tvPunkt2);
        tvPunkt2.setText(punkt);

        dialogBuilder.setPositiveButton("Дальше", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                if (intVopros > 19) {
                    showResult();
                } else {
                    Page();
                }
            }
        });

        dialogBuilder.create().show();
    }

    private void showDialogYes() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(SecondActivity.this);
        alertDialog.setCancelable(false);
        // Указываем Title
        alertDialog.setTitle("ВЕРНО !");
        // задаем иконку
        alertDialog.setIcon(R.drawable.ic_baseline_check_circle_outline_24);
        // Обработчик на нажатие ДА
        alertDialog.setPositiveButton("Дальше", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                if (intVopros > 19) {
                    showResult();
                } else {
                    Page();
                }
            }
        });
// показываем Alert
        alertDialog.show();
    }

    public void showResult() {
// выводим результат
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_result, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(false);
        dialogBuilder.setTitle("Результат");
        dialogBuilder.setIcon(R.drawable.ic_baseline_mail_outline_24);
        TextView tvRight = (TextView) dialogView.findViewById(R.id.tvRight);
        TextView tvMark = (TextView) dialogView.findViewById(R.id.tvMark);
        tvRight.setText(intPravotvet + " из 20");

        if (intPravotvet < 14) {
            tvMark.setText("неудовлетворительно");
            tvMark.setTextColor(Color.RED);
        } else if (intPravotvet > 13 && intPravotvet < 16) {
            tvMark.setText("удовлетворительно");
            tvMark.setTextColor(Color.MAGENTA);
        } else if (intPravotvet > 15 && intPravotvet < 20) {
            tvMark.setText("хорошо");
            tvMark.setTextColor(Color.BLUE);
        } else if (intPravotvet == 20) {
            tvMark.setText("отлично");
            tvMark.setTextColor(Color.GREEN);
        }

        dialogBuilder.setPositiveButton("На главную", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                finish();
            }
        });

        dialogBuilder.create().show();
    }

    static void shuffleArray(int[] ar) {
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }


}


