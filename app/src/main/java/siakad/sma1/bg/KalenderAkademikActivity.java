package siakad.sma1.bg;

import android.icu.util.Calendar;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

public class KalenderAkademikActivity extends AppCompatActivity {

    MaterialCalendarView materialCalendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kalender_akademik);

        //deklarasi widget yang ada di layout activity_main
        materialCalendarView = (MaterialCalendarView) findViewById(R.id.calendarView);
        materialCalendarView.state().edit()
                .setFirstDayOfWeek(Calendar.WEDNESDAY)
                .setMinimumDate(CalendarDay.from(1900, 1, 1))
                .setMaximumDate(CalendarDay.from(2045, 12, 31))
                // Maksud dari MONTHS adalah calender tersebut akan tampil berbentuk 4 minggu atau 1 bulan
                // jika calendar mode tersebut di ganti menjadi WEEKS maka akan yang tampil akan 1 minggu
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                //menampilkan toast jika berhasil di klik
                Toast.makeText(KalenderAkademikActivity.this, "" + date, Toast.LENGTH_SHORT).show();
            }
        });


    }
}