package com.example.zpp.calculator;

import android.util.Log;
import java.util.Stack;

public class CounterByEquation {
    private String equation;
    private Stack<String> stack;  //存储符号
    private String []queue = new String[50];  //存放中缀表达式
    private int count=0;

    public CounterByEquation(String equation){
        this.equation = equation;
    }

    //中缀表达式转后缀表达式
    String solveEquation(){
        int length = equation.length();
        stack = new Stack<>();
        for(int i=0;i<length;i++){
            char curChar = equation.charAt(i);
            if(curChar=='('||curChar=='+'||curChar=='-'||curChar=='*'||curChar=='/'||curChar=='%'||curChar=='^'){
                String top = "";
                if(!stack.empty())
                    top = stack.peek();
                if(curChar=='(')  {
                    stack.push(curChar+"");
                } else if (curChar == '%' || curChar == '^') {
                    if(!stack.empty()&&(top.equals("^")||top.equals("%"))){
                        queue[count++] = stack.pop();
                    }
                    stack.push(curChar+"");
                } else if ((curChar == '+' || curChar == '-')) {
                    while (true) {
                        if (stack.empty()) break;
                        if (stack.peek().equals("(")) break;
                        queue[count++] = stack.pop();
                    }
                    stack.push(curChar+"");
                } else if (curChar == '*' || curChar == '/') {
                    if (!stack.empty()){
                        while (true) {
                            if (stack.peek().equals("*") || stack.peek().equals("/") || stack.peek().equals("%")
                                    || stack.peek().equals("^")) {
                                queue[count++] = stack.pop();
                            }else{
                                break;
                            }
                            if (stack.empty()) break;
                            if (stack.peek().equals("(")) break;
                        }
                    }
                    stack.push(curChar+"");
                }
            }
            else if (curChar==')'){
                while(true){
                    String tmp = stack.pop();
                    if(tmp.equals("(")) break;
                    else queue[count++] = tmp;
                }
            }
            else{  //数字
                String numStr = curChar+"";
                for(i++;i<length;i++)
                {
                    char curBackChar = equation.charAt(i);
                    if(curBackChar==')'||curBackChar=='+'||curBackChar=='-'||curBackChar=='*'||curBackChar=='/'||curBackChar=='%'||curBackChar=='^'){
                        i--;
                        break;
                    }
                    else
                        numStr+=curBackChar;
                }
                queue[count++] = numStr;
            }
        }

        while(true){
            if(stack.empty()) break;
            queue[count++] = stack.pop();
        }
        String logStr = "";
        for(int i=0;i<count;i++){
            logStr+=queue[i]+" ";
        }
        Log.e("ERROR！",logStr);
 
        //用中缀表达式求值
        for(int i=0;i<count;i++){
            String member = queue[i];
            int numLength = member.length();
            if(member.charAt(numLength-1)=='!'){
                member = member.substring(0,numLength-1);
                int num = Integer.parseInt(member);
                int sum = 1;
                while(num>0){
                    sum *= (num--);
                }
                queue[i] = sum+"";
                continue;
            }
            if(member.equals("+")||member.equals("-")||member.equals("*")||member.equals("/")||member.equals("%")||member.equals("^")){
                double first = Double.parseDouble(queue[i-2]);
                double second = 0;
                if(!queue[i-1].equals("-"))
                    second = Double.parseDouble(queue[i-1]);
                if (member.equals("+")) first += second;
                if (member.equals("-")){
                    //处理  x^(-1)的情况
                    if(queue[i+1].equals("^")){
                        first = 1 / first;
                        //为了不影响接下来的运算，在数组去除^
                        for(int j = i+1;j<count-1;j++)
                            queue[j] = queue[j+1];
                        count--;
                    }else{
                        first -= second;
                    }
                }
                if (member.equals("*")) first *= second;
                if (member.equals("/")) first /= second;
                if (member.equals("%")) first %= second;
                if (member.equals("^")){
                    double sum = 1;
                    for(int j = (int)second;j>0;j--){
                        sum*=first;
                    }
                    first = sum;
                }
                queue[i-2] = first+"";
                //前移
                for(int j = i-1;j<count-2;j++){
                    queue[j] = queue[j+2];
                }
                count-=2;
                i-=2;
            }
        }
        return queue[0];
    }
}