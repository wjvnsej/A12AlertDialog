package com.kosmo.a12alertdialog;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //목록형 대화상자에서 사용할 배열
    private String[] sports = {"축구", "야구","배구", "농구"};
    //체크박스, 라디오 대화상자에서 사용할 배열
    private String[] girlGroup = {"트와이스","AOA","모모랜드","블랙핑크"};
    //라디오 대화상자에서 체크한 항목의 인덱스 저장용 변수
    private int radio_index = -1;
    //체크박스 대화상자에서 체크한 항목 저장용 변수
    boolean[] which_checks = {false, false, true, true};
    //진행대화상자 객체 생성
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //버튼위젯가져오기
        Button btnAllertBasic = (Button)findViewById(R.id.btn_alert_basic);
        Button btnAllertCheck = (Button)findViewById(R.id.btn_alert_checkbox);
        Button btnAllertRadio = (Button)findViewById(R.id.btn_alert_radio);
        Button btnAllertList = (Button)findViewById(R.id.btn_alert_list);
        Button btnAllertProgress = (Button)findViewById(R.id.btn_alert_proogress);
        Button btnAllertCustom = (Button)findViewById(R.id.btn_alert_custom);
        //기본대화상자
        btnAllertBasic.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertBasic = new AlertDialog.Builder(v.getContext());
                alertBasic.setCancelable(false);
                alertBasic.setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("대화상자제목")
                        .setMessage("여기는 메세지가 들어갑니다.")
                        .setPositiveButton("확인버튼",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(MainActivity.this,
                                                "확인클릭합니다.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                })
                        .setNegativeButton("취소버튼",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(MainActivity.this,
                                                "취소클릭합니다.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                })
                        .show();
            }
        });
        //목록형 대화상자 : 항목 중 하나만 선택가능
        /*
        .setItems(목록에 출력 할 배열, 리스너)
            : 배열이 항목에 출력되고 항목을 클릭할 시 바로 리스너가
            감지하여 콜백된다. 콜백함수쪽으로 선택한 항목의 인덱스가
            전달된다.
         */
        btnAllertList.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(view.getContext())
                        .setCancelable(false)
                        .setIcon(android.R.drawable.ic_lock_power_off)
                        .setTitle("당신이 좋아하는 스포츠는?")
                        .setItems(sports, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                //선택한 항목의 인덱스가 매개변수 i를 통해 전달된다.
                                Toast.makeText(MainActivity.this,
                                        sports[i],
                                        Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this,
                                        "목록취소함",
                                        Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
        });

        //라디오형 대화상자 - 항목 중 하나만 선택가능
        /*
        .setSingleChoiceItems(항목을 구성할 배열,
            디폴트로 선택될 항목의 인덱스,
            리스너)
        단 두번째 항목이 마이너스 값이면 선택항목이 없는 상태로 대화창이 설정된다.
         */
        btnAllertRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext())
                        .setIcon(android.R.drawable.ic_dialog_email)
                        .setTitle("당신이 좋아하는 걸그룹은?")
                        .setSingleChoiceItems(girlGroup, radio_index, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                radio_index = which;
                            }
                        })
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                Toast.makeText(MainActivity.this,
                                        girlGroup[radio_index],
                                        Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("CANCLE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this,
                                        "취소하셨습니다.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
        });

        //체크박스형 대화상자 - 여러개 선택가능
        /*
        .setMultiChoiceItems(항목에 출력할 배열,
            디폴트로 선택될 항목의 boolean값을 담은 배열,
            리스너)
        ※ 전역변수로 which_checks 변수가 생성되어 있음
         */
        btnAllertCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext())
                        .setIcon(android.R.drawable.ic_dialog_dialer)
                        .setTitle("당신이 좋아흐는 걸그룹은?(여러개선택)")
                        .setMultiChoiceItems(girlGroup, which_checks, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i, boolean b) {
                                /*
                                매개변수
                                    i : 선택한 체크박스의 인덱스 값
                                    b : 선택한 항목의 체크여부(boolean)
                                        즉, 체크되었을 때 true가 전달됨.
                                 */
                                Toast.makeText(MainActivity.this,
                                        String.format("which : %d, isChecked : %b", i, b),
                                        Toast.LENGTH_SHORT).show();
                                /*
                                사용자가 선택한 항목의 인덱스를 통해 체크여부를 저장한다.
                                 */
                                which_checks[i] = b;
                            }
                        })
                        .setPositiveButton("YES!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //선택한 체크 항목을 배열의 크기만큼 반복하면서
                                //StringBuffer 객체에서 콤마로 구분해서 저장한다.
                                StringBuffer buf = new StringBuffer();
                                for(int i = 0; i < girlGroup.length; i++){
                                    if(which_checks[i] == true){
                                        buf.append(girlGroup[i] + " ");
                                    }
                                }
                                Toast.makeText(MainActivity.this,
                                        buf,
                                        Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton("NO!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this,
                                        "NO! 버튼을 클릭하셨습니다.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
        });

        //ProgressDialog 객체 생성
        progress = new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //progress.setIcon(android.R.drawable.ic_menu_day); //시스템의 기본아이콘 사용
        progress.setIcon(R.drawable.ryan); //커스텀 이미지를 아이콘으로 사용
        progress.setTitle("KOSMO 여러분들");
        progress.setMessage("열공하는 중입니다. 조용히하세여");

        //버튼을 누르면 진행대화상자가 띄워진다.
        btnAllertProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //버튼을 눌렀을 때 대화상자가 열려있지 않은 상태라면
                if(!progress.isShowing()){
                    //대화상자를 띄워준다.
                    progress.show();
                }

                //2초간 대기 후 진행창 닫기
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //대화창 닫아주기
                        progress.dismiss(); //대화창이 열린상태라면 닫아준다.
                        //progress.cancel(); //대화창을 무조건 닫아준다.
                    }
                }, 3000);
            }
        });

        //커스텀 대화상자
        /*
        순서
        1. layout폴더에 사용자가 정의한 대화상자 xml파일을 생성한다.
        2. getLayoutInflater().inflate() 메소드를 통해 레이아웃을
            인플레이트(전개)한다.
        3. Builder 객체를 통해 대화창을 설정할 때 setView() 생성자를
            통해 2번에서 전개한 레이아웃을 대화상자에 적용한다.
         */
        btnAllertCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //커스텀뷰로 레이아웃 전개함(인플레이션)
                View view = getLayoutInflater().inflate(R.layout.custom_dialog, null);
                //대화상자내에 있는 입력상자에서 입력된 내용을 읽어오기 위해 위젯을 가져온다.
                final EditText sportTxt = (EditText)view.findViewById(R.id.sport_txt);

                new AlertDialog.Builder(v.getContext())
                        .setView(view)
                        .setIcon(android.R.drawable.ic_media_play)
                        .setTitle("커스텀대화상자")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //확인을 눌렀을 때는 입력한 내용을 출력한다.
                                Toast.makeText(MainActivity.this,
                                        sportTxt.getText(),
                                        Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this,
                                        "취소를 눌렀습니다.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
        });

    }
}






















