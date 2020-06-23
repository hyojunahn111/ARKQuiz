package com.example.arkquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Random;
import java.util.Scanner;

public class QuizpageEasy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizpage_easy);

        Button button7 = (Button)findViewById(R.id.button7);
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }

        });

        String[] Q = new String[5];
        Q[1] = "다음 공룡의 이름은?";
        Q[2] = "다음 소리를 내는 공룡의 이름은?";
        Q[3] = "오벨리스크의 위치는?";
        Q[4] = "다음 공룡이 나오는 맵은?"; //라그나로크, 더 아일랜드, 더 센터, 스코치드어스, 에버레이션, 익스팅션, 발게로, 제네시스, 크리스탈 아일랜드
        Q[5] = "다음 동굴의 이름은?"; // 강함의 동굴, 하늘군주의 동굴, 사냥꾼의 동굴, 거대함의 동굴, 공백의 동굴, 교활함의 동굴, 그림자의 동굴, 깊이의 동굴, 면역의 동굴, 무리의 동굴, 바위의 동굴, 짐승의 동굴, 현명함의 동굴

        String[] D1 = new String[5];
        D1[1] = "파라사우롤로푸르스";
        D1[2] = "프테라노돈";
        D1[3] = "하이랜드 NE";
        D1[4] = "익스팅션";
        D1[5] = "교활함의 동굴";

        String[] D2 = new String[5];
        D2[1] = "파라케라테리움";
        D2[2] = "아르젠타비스";
        D2[3] = "하이랜드 E";
        D2[4] = "에버레이션";
        D2[5] = "강함의 동굴";

        String[] D3 = new String[5];
        D3[1] = "마나가르마";
        D3[2] = "안칼로사우르스";
        D3[3] = "정글 1";
        D3[4] = "더 센터";
        D3[5] = "공백의 동굴";

        String[] D4 = new String[5];
        D4[1] = "그리핀";
        D4[2] = "케찰";
        D4[3] = "정글 2";
        D4[4] = "라그나로크";
        D4[5] = "하늘군주의 동굴";

        int[] ans = new int[3];
        ans[0] = 3;
        ans[1] = 2;
        ans[2] = 1;

        Random rd = new Random();

        int ran = rd.nextInt(Q.length);
        int answer = 0;
        int DE = 0; //DinoEgg

        //1

        System.out.println(Q[ran]);
        System.out.println("1" + D1[ran]);
        System.out.println("2" + D2[ran]);
        System.out.println("3" + D3[ran]);
        System.out.println("4" + D4[ran]);
        answer = ans[ran];

        Scanner scan = new Scanner(System.in);
        System.out.println("정답을 선택하세요");
        String input = scan.nextLine();
        int inputN = Integer.parseInt(input);

        if(inputN == answer) {
            System.out.println("정답입니다.");
            DE = DE + 100;
        }
        else {
            System.out.println("오답입니다.");
        }
        ran = rd.nextInt(ans.length);

        // 2
        System.out.println(Q[ran]);
        System.out.println("1" + D1[ran]);
        System.out.println("2" + D2[ran]);
        System.out.println("3" + D3[ran]);
        System.out.println("4" + D4[ran]);
        answer = ans[ran];

        scan = new Scanner(System.in);
        System.out.println("정답을 선택하세요");
        input = scan.nextLine();
        inputN = Integer.parseInt(input);

        if(inputN == answer) {
            System.out.println("정답입니다.");
            DE = DE + 100;
        }
        else {
            System.out.println("오답입니다.");
        }
        ran = rd.nextInt(ans.length);

        //3
        System.out.println(Q[ran]);
        System.out.println("1" + D1[ran]);
        System.out.println("2" + D2[ran]);
        System.out.println("3" + D3[ran]);
        System.out.println("4" + D4[ran]);
        answer = ans[ran];

        scan = new Scanner(System.in);
        System.out.println("정답을 선택하세요");
        input = scan.nextLine();
        inputN = Integer.parseInt(input);

        if(inputN == answer) {
            System.out.println("정답입니다.");
            DE = DE + 100;
        }
        else {
            System.out.println("오답입니다.");
        }
        ran = rd.nextInt(ans.length);

        //4
        System.out.println(Q[ran]);
        System.out.println("1" + D1[ran]);
        System.out.println("2" + D2[ran]);
        System.out.println("3" + D3[ran]);
        System.out.println("4" + D4[ran]);
        answer = ans[ran];

        scan = new Scanner(System.in);
        System.out.println("정답을 선택하세요");
        input = scan.nextLine();
        inputN = Integer.parseInt(input);

        if(inputN == answer) {
            System.out.println("정답입니다.");
            DE = DE + 100;
        }
        else {
            System.out.println("오답입니다.");
        }
        ran = rd.nextInt(ans.length);

        //5
        System.out.println(Q[ran]);
        System.out.println("1" + D1[ran]);
        System.out.println("2" + D2[ran]);
        System.out.println("3" + D3[ran]);
        System.out.println("4" + D4[ran]);
        answer = ans[ran];

        scan = new Scanner(System.in);
        System.out.println("정답을 선택하세요");
        input = scan.nextLine();
        inputN = Integer.parseInt(input);

        if(inputN == answer) {
            System.out.println("정답입니다.");
            DE = DE + 100;
        }
        else {
            System.out.println("오답입니다.");
        }
        ran = rd.nextInt(ans.length);

        System.out.println("얻은 공룡알 : " + DE);






    }
}