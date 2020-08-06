package com.example.zpp.calculator;

public class Check {
    private String equation;

    public void setEquation(String equation){
        this.equation = equation;
    }

    public String getEquation(){
        return equation;
    }

    //检查输入数字问题
    boolean checkNumberInput(){
        int length = equation.length();
        if(length==0) return true;
        char perChar = equation.charAt(length-1);
        //避免出现01,+001,2^00002等现象
        if(length==1&&perChar=='0') equation = "";
        if(length>1){
            char perPerChar = equation.charAt(length-2);
            if((perPerChar=='+'||perPerChar=='-'||perPerChar=='*'||perPerChar=='/'||perPerChar=='^')&&perChar=='0'){
                equation = equation.substring(0,length-1);
            }
        }
        return true;
    }

    //检查小数点问题
    boolean checkPointInput(){
        int length = equation.length();
        //自动补0
        if(length==0) equation+="0";
        char perChar = equation.charAt(length-1);
        if(perChar=='+'||perChar=='-'||perChar=='*'||perChar=='/'||perChar=='%') equation+="0";
        if(perChar==')'||perChar=='('||perChar=='^') return false;
        int pointNum = 0;
        for(int i = length-1;i>=0;i--){
            char checkChar = equation.charAt(i);
            if(checkChar=='.') pointNum++;
            if(checkChar=='^') return false;
            if(checkChar=='+'||checkChar=='-'||checkChar=='*'||checkChar=='/'||checkChar=='%') break;
        }
        if(pointNum>0) return false;
        return true;
    }

    // + - * /
    boolean checkOperationsInput(){
        int length = equation.length();
        if(length==0) return false;
        char perChar = equation.charAt(length-1);
        if(perChar=='%'||perChar=='.'||perChar=='^'||perChar=='!'||perChar=='(') return false;
        //替换
        if(perChar=='+'||perChar=='-'||perChar=='*'||perChar=='/')
            equation = equation.substring(0,length-1);
        return true;
    }

    //回退处理
    void backSpace(){
        int length = equation.length();
        if(length==0) return;
        char charPer = equation.charAt(length-1);
        equation = equation.substring(0,length-1);
    }

    //检查 x^y
    boolean checkInvolutionInput(){
        int length = equation.length();
        if(length==0) return false;
        char perChar = equation.charAt(length-1);
        if(perChar!='1'&&perChar!='2'&&perChar!='3'&&perChar!='4'&&perChar!='5'&&perChar!='6'&&perChar!='7'
                &&perChar!='8'&&perChar!='9'&&perChar!=')'&&perChar!='e'&&perChar!='π'&&perChar!='0') return false;
        int iNum = 0;
        for(int i = length-1;i>0;i--){
            char iChar = equation.charAt(i);
            if(iChar=='^') iNum++;
            if (iChar=='+'||iChar=='-'||iChar=='*'||iChar=='/'||iChar=='%') break;
        }
        if(iNum>0) return false;
        return true;
    }

    // 1/x
    boolean checkReciprocalInput(){
        int length = equation.length();
        if(length==0) return false;
        char perChar = equation.charAt(length-1);
        if(perChar!='1'&&perChar!='2'&&perChar!='3'&&perChar!='4'&&perChar!='5'&&perChar!='6'&&perChar!='7'
                &&perChar!='8'&&perChar!='9'&&perChar!=')'&&perChar!='0') return false;
        int iNum = 0;
        for(int i = length-1;i>=0;i--){
            char iChar = equation.charAt(i);
            iNum++;
            if (iChar=='+'||iChar=='-'||iChar=='*'||iChar=='/'||iChar=='%') break;
        }
        if(iNum==1&&perChar=='0') return false;
        return true;
    }

    // (
    boolean checkLBracketInput(){
        int length = equation.length();
        if(length==0) return true;
        char perChar = equation.charAt(length-1);
        if(perChar=='+'||perChar=='-'||perChar=='*'||perChar=='/'||perChar=='%'||perChar=='^'||perChar=='(')
            return true;
        return false;
    }

    // )
    boolean checkRBracketInput(){
        int length = equation.length();
        if(length==0) return false;
        char perChar = equation.charAt(length-1);
        int lNum = 0,rNum = 0;
        for(int i = length-1;i>=0;i--){
            char checkChar = equation.charAt(i);
            if(checkChar=='(') lNum++;
            if(checkChar==')') rNum++;
            if(lNum-rNum==1) break;
        }
        if((lNum-rNum==1)&&(perChar=='1'||perChar=='2'||perChar=='3'||perChar=='4'||perChar=='5'||perChar=='6'
                ||perChar=='7' ||perChar=='8'||perChar=='9'||perChar=='0'||perChar==')')) return true;
        return false;
    }

    // %
    boolean checkRemainderInput(){
        int length = equation.length();
        if(length==0) return false;
        char perChar = equation.charAt(length-1);
        if(perChar=='1'||perChar=='2'||perChar=='3'||perChar=='4'||perChar=='5'||perChar=='6'||perChar=='7'
                ||perChar=='8'||perChar=='9'||perChar==')') return true;
        return false;
    }
}

