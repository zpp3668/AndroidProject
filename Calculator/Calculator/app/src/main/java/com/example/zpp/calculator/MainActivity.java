package com.example.zpp.calculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener {

    private TextView Equation, Result;
    private Check check;//检查输入是否有误
    private CounterByEquation counterByEquation;//中缀表达式计算结果
    private String equation = "";
    private Button buttons[] = new Button[23];

    //设置按钮id
    private int buttonIds[] = new int[]{
            R.id.CE,R.id.LBracket,R.id.RBracket, R.id.BACKSPACE, R.id.Remainder,R.id.Involution,R.id.Reciprocal, R.id.Add,
            R.id.N9, R.id.N8, R.id.N7, R.id.Subtract, R.id.N6, R.id.N5, R.id.N4, R.id.Multiply, R.id.N3, R.id.N2, R.id.N1,
            R.id.Divide, R.id.Zero, R.id.Point, R.id.Equal
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    //初始化控件
    void init() {
        int length = buttons.length;
        for (int i = 0; i < length; i++) {
            buttons[i] = (Button) findViewById(buttonIds[i]);
            buttons[i].setOnClickListener(this);
        }
        Equation = (TextView) findViewById(R.id.Equation);
        Result = (TextView) findViewById(R.id.Result);
        check = new Check();
        counterByEquation = new CounterByEquation(equation);
    }

    //点击按钮事件
    @Override
    public void onClick(View v) {
        int id = v.getId();
        Button button = (Button) findViewById(id);
        String text = button.getText().toString();
        switch (id) {
            //点击数字按钮
            case R.id.N1:
            case R.id.N2:
            case R.id.N3:
            case R.id.N4:
            case R.id.N5:
            case R.id.N6:
            case R.id.N7:
            case R.id.N8:
            case R.id.N9:
            case R.id.Zero:
                check.setEquation(equation);
                if (check.checkNumberInput()) {
                    equation = check.getEquation();
                    equation += text;
                }break;
            //点击+ - * / 按钮
            case R.id.Add:
            case R.id.Subtract:
            case R.id.Multiply:
            case R.id.Divide:
                check.setEquation(equation);
                if (check.checkOperationsInput()) {
                    equation = check.getEquation();
                    equation += text;
                }break;
            //点击回退按钮
            case R.id.BACKSPACE:
                check.setEquation(equation);
                check.backSpace();
                equation = check.getEquation();
                break;
            //点击 x^y
            case R.id.Involution:
                check.setEquation(equation);
                if(check.checkInvolutionInput())
                    equation+="^";
                break;
            // 1/x
            case R.id.Reciprocal:
                check.setEquation(equation);
                if(check.checkReciprocalInput())
                    equation+="^(-1)";
                break;
            // （
            case R.id.LBracket:
                check.setEquation(equation);
                if(check.checkLBracketInput())
                    equation+="(";
                break;
            //)
            case R.id.RBracket:
                check.setEquation(equation);
                if(check.checkRBracketInput())
                    equation+=')';
                break;
            // %
            case R.id.Remainder:
                check.setEquation(equation);
                if (check.checkRemainderInput())
                    equation += '%';
                break;
            // CE
            case R.id.CE:
                equation = "";
                Result.setText("");
                break;
            // 小数点
            case R.id.Point:
                check.setEquation(equation);
                if (check.checkPointInput()) {
                    equation = check.getEquation();
                    equation += text;
                }break;
            // =
            case R.id.Equal:
                Result.setText(new CounterByEquation(equation).solveEquation());
                break;
        }
        Equation.setText(equation);
    }
}