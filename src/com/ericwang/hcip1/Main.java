package com.ericwang.hcip1;

import java.util.*;
import java.io.*;
public class Main {
    public static boolean flag1 = false;
    public static String mod = "";
    public static ArrayList<ArrayList<String>> lib;
    public static String thisLib = "";
    public static String outFile = "";
    public static boolean needsHtml = false;


    public static int tempNum = 0;
    public static void main(String[] args) {

        List<String> options = Arrays.asList(args);

        // String arrStr[] = new String[options.size()];
      //  String arrStr[] = null;
        ArrayList<String> arrTem = new ArrayList<>();
        for(int i = 0; i < options.size(); i++){
            arrTem.add(options.get(i));
           // arrStr[i] = options.get(i);
        }

//============================================================================
        String help = "--help", h = "-h",
                version = "--version", v = "-v",
                library = "--library", l = "-l",
                outfile = "--outfile", o = "-o",
                mode = "--mode", m = "-m",
                count = "--count", c = "-c",
                html = "--html", t = "-t",
                generate = "--generate", g = "-g",
                l1 = "lorem", l2 = "anguish",
                m1 = "paragraph", m2 = "word", m3 = "bullet",
                beginStr = "generator", empStr = "";

//        for (int i = 0; i < arrStr.length; i++) {
//            arrTem.add(arrStr[i]);
//        }


        if (arrTem.get(0).contentEquals(beginStr))  // add a condition to check invalid command
        {
            if (arrTem.size() != 1) {
                //  version command
                if (haveEle(arrTem, version, v)) {
                    if (noError(arrTem, version, v)) {
                        if (arrTem.size() == 2) {
                            System.out.println("version : 1.1.0" );
                            return;
                        } else {
                            System.out.println("Invalid Input : --version/-v is a standalone command option, can't be used with other command options");
                            return;
                        }
                    } else {
                        System.out.println("Invalid Input : don't --version/-v command option more than one time");
                        return;
                    }

                }
                // help command
                if (haveEle(arrTem, help, h)) {
                    if (noError(arrTem, help, h)) {
                        if (arrTem.size() == 2) {
                            help();
                        } else {
                            System.out.println("Invalid Input : --help/-h is a standalone command option, can't be used with other command options");
                            return;
                        }
                    } else {
                        System.out.println("Invalid Input : don't --help/-h command option more than one time");
                        return;
                    }
                }


                // no version, no help   move onto next step

                boolean hvG = haveEle(arrTem, generate, g);boolean hvH = haveEle(arrTem, help, h);
                boolean hvV = haveEle(arrTem, version, v);boolean hvT = haveEle(arrTem, html, t);
                boolean hvL = haveEle(arrTem, library, l);boolean hvO = haveEle(arrTem, outfile, o);
                boolean hvM = haveEle(arrTem, mode, m);boolean hvC = haveEle(arrTem,count,c);


                int lenOfArr = arrTem.size();

                if (hvG) {
                    if (noError(arrTem, generate, g)) {
                        if (hvH || hvV) {
                            if (hvH) {
                                System.out.println("Invalid input: you can't user help command with generate command");
                                return;
                            }
                            if (hvV) {
                                System.out.println("Invalid input: you can't user version command with generate command");
                                return;
                            }

                        }else{
                            if (arrTem.size() >= 2) {
                                setFlag1(true);
                            } else {
                                System.out.println("Invalid input: generator is not a standalone command, you should have at least one valid parameter for it");
                                return;
                            }
                        }
                    } else {
                        System.out.println("Invalid input: you can't use generate command more than one time");
                        return;
                    }

                } else if (!(hvG && noError(arrTem, generate, g))) {
                    if (!hvH && !hvV) {
                        System.out.println("Invalid input: missing -g/--generate, please make sure type --generate/-g in the command line");
                        return;
                    }

                }



                if (getFlag1()) {
                        ArrayList<String> appearTimes = new ArrayList<>();
                        appearTimes.add("o0" + countStrOcc(arrTem, library));appearTimes.add("o1" + countStrOcc(arrTem, l));
                        appearTimes.add("o2" + countStrOcc(arrTem, outfile));appearTimes.add("o3" + countStrOcc(arrTem, o));
                        appearTimes.add("o4" + countStrOcc(arrTem, mode));appearTimes.add("o5" + countStrOcc(arrTem, m));
                        appearTimes.add("o6" + countStrOcc(arrTem, count));appearTimes.add("o7" + countStrOcc(arrTem, c));
                        appearTimes.add("o8" + countStrOcc(arrTem, html)); appearTimes.add("o9" + countStrOcc(arrTem, t));

                        HashMap<String, String> cmdDataPool = new HashMap<>();
                        cmdDataPool.put(appearTimes.get(0), library);cmdDataPool.put(appearTimes.get(1), l);
                        cmdDataPool.put(appearTimes.get(2), outfile);cmdDataPool.put(appearTimes.get(3), o);
                        cmdDataPool.put(appearTimes.get(4), mode);cmdDataPool.put(appearTimes.get(5), m);
                        cmdDataPool.put(appearTimes.get(6), count);cmdDataPool.put(appearTimes.get(7), c);
                        cmdDataPool.put(appearTimes.get(8), html);cmdDataPool.put(appearTimes.get(9), t);

                        ArrayList<String> finalizeValidCMD = appearsOneTime(cmdDataPool,appearTimes);


                        if(hvT)
                        {
                            if(noError(finalizeValidCMD, html, t)){
                                int pos1 = arrTem.indexOf(html);
                                int pos2 = arrTem.indexOf(t);
                                if(pos1!=-1){
                                    if(pos1+1 <= lenOfArr-1){
                                        if(!arrTem.get(pos1+1).matches("[a-zA-Z]")){
                                            setNeedsHtml(true);
                                        }else{
                                            System.out.println("Invalid input : html command doesn't need a parameter");
                                        }
                                    }
                                }
                                if(pos2!=-1){
                                    if(pos2+1 <= lenOfArr-1){
                                        if(!arrTem.get(pos2+1).matches("[a-zA-Z]")){
                                            setNeedsHtml(true);
                                        }else{
                                            System.out.println("Invalid input : html command doesn't need a parameter");
                                        }
                                    }

                                }
                            }else{
                                System.out.println("Invalid input : html can't be used more than one time");
                                return;
                            }
                        }

                    if (hvL) {
                        if(noError(finalizeValidCMD, library,l)){

                            int pos1 = arrTem.indexOf(library);
                            int pos2 = arrTem.indexOf(l);

                            if(pos1!= -1){
                                if(pos1+1 <= lenOfArr-1){
                                    if(arrTem.get(pos1+1).charAt(0) != '-'){
                                        // get this libary and set the library
                                        if(arrTem.get(pos1+1).contentEquals(l1)){
                                            setLib(loremText());
                                            setThisLib(l1);
                                        }else if(arrTem.get(pos1+1).contentEquals(l2))
                                        {
                                            setLib(anguishText());
                                            setThisLib(l2);
                                        }
                                        else{
                                            System.out.println("Invalid input : library is not found, only two libraries are available currently, lorem, anguish ");
                                        }

                                    }else{
                                        System.out.println("Invalid input : library command is existed, but it doesn't have an valid parameter");
                                        return;
                                    }
                                }else{
                                    System.out.println("Invalid input : library command is existed, but it doesn't have a parameter");
                                    return;
                                }
                            }

                            if(pos2!= -1){
                                if(pos2+1 <= lenOfArr-1){
                                    if(arrTem.get(pos2+1).charAt(0) != '-'){
                                        // get this libary and set the library
                                        if(arrTem.get(pos2+1).contentEquals(l1)){
                                            setLib(loremText());
                                            setThisLib(l1);
                                        }else if(arrTem.get(pos2+1).contentEquals(l2))
                                        {
                                            setLib(anguishText());
                                            setThisLib(l2);
                                        }
                                        else{
                                            System.out.println("Invalid input : library is not found, only two libraries are available currently, lorem, anguish ");
                                        }

                                    }else{
                                        System.out.println("Invalid input : library command is existed, but it doesn't have an valid parameter");
                                        return;
                                    }
                                }else{
                                    System.out.println("Invalid input : library command is existed, but it doesn't have a parameter");
                                    return;
                                }
                            }
                        }
                        else{
                            System.out.println("Invalid input : library command can't be used more than one time ");
                            return;
                        }
                    }else{
                        setLib(loremText());
                        setThisLib(l1);
                    }

                    if(hvO){
                        if(noError(finalizeValidCMD, outfile, o)){
                            int pos1 = arrTem.indexOf(outfile);
                            int pos2 = arrTem.indexOf(o);
                            if(pos1!= -1){
                                if(pos1+1 <= lenOfArr-1){
                                    if(arrTem.get(pos1+1).charAt(0) != '-'){
                                        boolean isRightFormat =checkFileExtension(arrTem.get(pos1+1));
                                        if(isRightFormat){
                                            setOutFile(arrTem.get(pos1+1));
                                        }else{
                                            System.out.println("Invalid input: please define proper file extension st. txt");
                                            return;
                                        }
                                    }else{
                                        System.out.println("Invalid input : Outfile command is existed, but it doesn't have an valid parameter");
                                        return;
                                    }
                                }else{
                                    System.out.println("Invalid input : Outfile command is existed, but it doesn't have a parameter");
                                    return;
                                }
                            }
                            if(pos2 != -1){
                                if(pos2+1 <= lenOfArr-1){
                                    if(arrTem.get(pos2+1).charAt(0) != '-'){
                                        boolean isRightFormat =checkFileExtension(arrTem.get(pos2+1));
                                        if(isRightFormat){
                                            // define the output file
                                        }else{
                                            System.out.println("Invalid input: please define proper file extension st. txt");
                                            return;
                                        }
                                    }else{
                                        System.out.println("Invalid input : Outfile command is existed, but it doesn't have an valid parameter");
                                        return;
                                    }
                                }else{
                                    System.out.println("Invalid input : Outfile command is existed, but it doesn't have a parameter");
                                    return;
                                }
                            }

                        }
                        else{
                            System.out.println("Invalid input : Outfile command can't be used more than one time ");
                            return;
                        }

                    }

                        // for mode


                    if(hvM){
                        if(noError(finalizeValidCMD, mode, m)){
                            int pos1 = arrTem.indexOf(mode);
                            int pos2 = arrTem.indexOf(m);
                            if(pos1!=-1){
                                if(pos1+1 <= lenOfArr-1){
                                    if(arrTem.get(pos1+1).charAt(0) != '-')
                                    {
                                        if(arrTem.get(pos1+1).contentEquals(m1)){
                                            setMod(m1);
                                        }
                                        else if(arrTem.get(pos1+1).contentEquals(m2))
                                        {
                                            setMod(m2);
                                        }
                                        else if(arrTem.get(pos1+1).contentEquals(m3))
                                        {
                                            setMod(m3);
                                        }
                                        else{
                                            System.out.println("Invalid input : invalid output mode");
                                            return;
                                        }

                                    }
                                    else{
                                        System.out.println("Invalid input : Outfile command is existed, but it doesn't have an valid parameter");
                                        return;
                                    }

                                }else{
                                    System.out.println("Invalid input : mode command is existed, but it doesn't have a parameter");
                                    return;
                                }
                            }
                            if(pos2!=-1){
                                if(pos2+1 <= lenOfArr-1){
                                    if(arrTem.get(pos2+1).charAt(0) != '-')
                                    {
                                        if(arrTem.get(pos2+1).contentEquals(m1)){
                                            setMod(m1);
                                        }
                                        else if(arrTem.get(pos2+1).contentEquals(m2))
                                        {
                                            setMod(m2);
                                        }
                                        else if(arrTem.get(pos2+1).contentEquals(m3))
                                        {
                                            setMod(m3);
                                        }
                                        else{
                                            System.out.println("Invalid input : invalid output mode");
                                            return;
                                        }

                                    }
                                    else{
                                        System.out.println("Invalid input : Outfile command is existed, but it doesn't have an valid parameter");
                                        return;
                                    }

                                }else{
                                    System.out.println("Invalid input : mode command is existed, but it doesn't have a parameter");
                                    return;
                                }
                            }

                        }else{
                            System.out.println("Invalid input : mode command can't be used more the one time");
                            return;
                        }
                    }
                    else{
                        setMod(m1);
                    }

                // for count

                if(hvC){
                    if (noError(finalizeValidCMD, count, c)) {
                        int pos1 = arrTem.indexOf(count);
                        int pos2 = arrTem.indexOf(c);
                        if(pos1 !=-1){
                            if(pos1+1 <= lenOfArr-1){
                                if(arrTem.get(pos1+1).charAt(0) != '-' && !arrTem.get(pos1+1).matches("[a-zA-Z]") ){
                                   setTempNum(Integer.parseInt(arrTem.get(pos1+1)));
                                }
                                else if(arrTem.get(pos1+1).matches("[a-zA-Z]")){
                                    System.out.println("Invalid input : Outfile command is existed, but its parameter should be a numeric value");
                                    return;
                                }
                                else{
                                    System.out.println("Invalid input : Outfile command is existed, but it doesn't have an valid parameter");
                                    return;
                                }
                            }
                            else{
                                System.out.println("Invalid input : Outfile command is existed, but it doesn't have a parameter");
                                return;
                            }

                        }
                        if(pos2 != -1){
                            if(pos2+1 <= lenOfArr-1){
                                if(arrTem.get(pos2+1).charAt(0) != '-' && !arrTem.get(pos2+1).matches("[a-zA-Z]") ){
                                    setTempNum(Integer.parseInt(arrTem.get(pos1+1)));
                                }
                                else if(arrTem.get(pos2+1).matches("[a-zA-Z]")){
                                    System.out.println("Invalid input : Outfile command is existed, but its parameter should be a numeric value");
                                    return;
                                }
                                else{
                                    System.out.println("Invalid input : Outfile command is existed, but it doesn't have an valid parameter");
                                    return;
                                }
                            }
                            else{
                                System.out.println("Invalid input : Outfile command is existed, but it doesn't have a parameter");
                                return;
                            }
                        }
                    }else{
                        System.out.println("Invalid input : count command can not be used more than one time");
                        return;
                    }
                }else{
                    switch (getMod()) {
                        case "paragraph":
                            setTempNum(1);
                            setMod(m1);
                            break;
                        case "word":
                            setTempNum(3);
                            setMod(m2);
                            break;
                        case "bullet":
                            setTempNum(5);
                            setMod(m3);
                            break;
                    }

                }

                    ArrayList<String> textFromLib = randomG(getLib(),getMod(),getTempNum(),getThisLib());

                    if(isNeedsHtml()){
                        if (getOutFile() != empStr) {
                            writeToFile(textFromLib, getOutFile());
                        } else {
                            if (getMod() == m1) {

                                for (int i = 0; i < textFromLib.size(); i++) {

                                    System.out.println("<p> " + textFromLib.get(i) + " </p>");
                                }
                            }
                            if (getMod() == m2) {
                                for (int i = 0; i < textFromLib.size(); i++) {
                                    System.out.println("<h1> " + textFromLib.get(i).charAt(0) + " </h1>");
                                }
                            }
                            if (getMod() == m3) {
                                System.out.println("<ul>\n");
                                for (int i = 0; i < textFromLib.size(); i++) {
                                    System.out.println("  <li> " + textFromLib.get(i) + " </li>\n");
                                }
                                System.out.println("</ul>\n");
                            }
                        }
                    }
                    else{
                        if (getOutFile() != empStr) {
                            writeToFile(textFromLib, getOutFile());
                        }else{
                            if (getMod() == m1) {

                                for (int i = 0; i < textFromLib.size(); i++) {

                                    System.out.println( textFromLib.get(i));
                                }
                            }
                            if (getMod() == m2) {
                                for (int i = 0; i < textFromLib.size(); i++) {
                                    System.out.println(textFromLib.get(i).charAt(0));
                                }
                            }
                            if (getMod() == m3) {

                                for (int i = 0; i < textFromLib.size(); i++) {
                                    System.out.println(textFromLib.get(i) );
                                }

                            }
                        }

                    }
                }
            }

        }
        else if(arrTem.size()==1 && arrTem.get(0).contentEquals("generator")){
            System.out.println("Invalid Input : generator is not a stand alone command, please enter more commands ");
            return;
        }
        else{
            System.out.println("Invalid Input : you didn't use generator at beginning");
            return;
        }
    }


    private static ArrayList<String> randomG(ArrayList<ArrayList<String>>libStr,String mod, int printTimes, String thisLib)
    {
        ArrayList<String> libContent= new ArrayList<>();
        String t1 = "",t2 = "",t3 = "",t4 = "",t5 = "",smt = "", space = " ",temp="";
        Random rand = new Random();
        ArrayList<String> messyText = new ArrayList<>();
        for(int i = 0; i<libStr.size(); i++)
        {

            t1 = libStr.get(0).get(rand.nextInt(4)+0);
            t2 = libStr.get(1).get(rand.nextInt(4)+0);
            t3 = libStr.get(2).get(rand.nextInt(4)+0);
            t4 = libStr.get(3).get(rand.nextInt(4)+0);
            t5 = libStr.get(4).get(rand.nextInt(4)+0);
            messyText.add(t1);
            messyText.add(t2);
            messyText.add(t3);
            messyText.add(t4);
            messyText.add(t5);
        }

        if(thisLib!="")
        {
            if(thisLib == "lorem")
            {
                switch(mod)
                {
                    case "paragraph":
                        for(int i = 0; i<printTimes; i++)
                        {
                            t1 = libStr.get(0).get(rand.nextInt(4)+0);
                            t2 = libStr.get(1).get(rand.nextInt(4)+0);
                            t3 = libStr.get(2).get(rand.nextInt(4)+0);
                            t4 = libStr.get(3).get(rand.nextInt(4)+0);
                            t5 = libStr.get(4).get(rand.nextInt(4)+0);
                            smt = t1+space+t2+space+t3+space+t4+space+t5;
                            libContent.add(smt);
                        }
                        break;
                    case "word":
                        for(int i = 0; i<printTimes; i++)
                        {
                            boolean isFound = true;
                            do
                            {
                                temp = messyText.get(rand.nextInt(messyText.size()-1 -0)+0);
                                isFound = libContent.contains(temp);
                                libContent.add(temp);
                            }
                            while(!isFound);
                        }
                        break;
                    case "bullet":
                        for(int i = 0; i<printTimes; i++)
                        {
                            t1 = libStr.get(0).get(rand.nextInt(4)+0);
                            t2 = libStr.get(1).get(rand.nextInt(4)+0);
                            t3 = libStr.get(2).get(rand.nextInt(4)+0);
                            t4 = libStr.get(3).get(rand.nextInt(4)+0);
                            t5 = libStr.get(4).get(rand.nextInt(4)+0);
                            if(isNeedsHtml()){
                                smt = t1+space+t2+space+t3+space+t4+space+t5;
                            }else{
                                smt = ". "+t1+space+t2+space+t3+space+t4+space+t5;
                            }


                            libContent.add(smt);
                        }
                        break;
                }
            }
            else if(thisLib == "anguish")
            {
                switch(mod)
                {
                    case "paragraph":
                        for(int i = 0; i<printTimes; i++)
                        {
                            t1 = libStr.get(0).get(rand.nextInt(4)+0);
                            t2 = libStr.get(1).get(rand.nextInt(4)+0);
                            t3 = libStr.get(2).get(rand.nextInt(4)+0);
                            t4 = libStr.get(3).get(rand.nextInt(4)+0);
                            t5 = libStr.get(4).get(rand.nextInt(4)+0);
                            smt = t1+space+t2+space+t3+space+t4+space+t5;
                            libContent.add(smt);
                        }
                        break;
                    case "word":
                        for(int i = 0; i<printTimes; i++)
                        {
                            temp = messyText.get(rand.nextInt(30)+0);
                            libContent.add(temp);
                        }
                        break;
                    case "bullet":
                        for(int i = 0; i<printTimes; i++)
                        {

                            t1 = libStr.get(0).get(rand.nextInt(4)+0);
                            t2 = libStr.get(1).get(rand.nextInt(4)+0);
                            t3 = libStr.get(2).get(rand.nextInt(4)+0);
                            t4 = libStr.get(3).get(rand.nextInt(4)+0);
                            t5 = libStr.get(4).get(rand.nextInt(4)+0);
                            if(isNeedsHtml()){
                                smt =t1+space+t2+space+t3+space+t4+space+t5;
                            }else{
                                smt ="."+t1+space+t2+space+t3+space+t4+space+t5;
                            }

                            libContent.add(smt);
                        }
                        break;
                }

            }
        }

        return libContent;
    }

    public static ArrayList<ArrayList<String>> getLib() {
        return lib;
    }
    public static void setLib(ArrayList<ArrayList<String>> lib) {
        Main.lib = lib;
    }
    public static boolean getFlag1() {
        return flag1;
    }
    public static void setFlag1(boolean flag1) {
        Main.flag1 = flag1;
    }
    public static String getMod() {
        return mod;
    }
    public static void setMod(String mod) {
        Main.mod = mod;
    }
    public static String getThisLib() {
        return thisLib;
    }
    public static void setThisLib(String thisLib) {
        Main.thisLib = thisLib;
    }
    public static String getOutFile() {
        return outFile;
    }
    public static void setOutFile(String outFile) {
        Main.outFile = outFile;
    }

    private static boolean haveEle(ArrayList<String>arr, String t1, String t2){
        boolean defaultBool = false;
        for(int i = 0; i < arr.size(); i++){
            if(arr.get(i).contentEquals(t1) || arr.get(i).contentEquals(t2)){
                defaultBool = true;
            }
        }
        return defaultBool;
    }
    private static boolean noError (ArrayList<String> arrTem , String target1 , String target2){
        boolean t1 = false, t2 = false;
        int n1 = 0, n2 = 0;
        for(int i = 0; i< arrTem.size(); i++){
            if(arrTem.get(i).contentEquals(target1)){
                t1 = true;
                n1++;
            }
        }
        for(int j = 0; j<arrTem.size();j++){
            if(arrTem.get(j).contentEquals(target2)){
                t2 = true;
                n2++;
            }
        }
        if(n1 == n2){
            return false;
        }
        if(n1 >= 2 ){
            return false;
        }
        if(n2>=2){
            return false;
        }
        return t1^t2;
    }

    private static void help(){
        System.out.println("Valid Input Summary\n\n" +
                "Command \t\t\t\t\tCommand Line Mode Command Description\n\n" +
                "--help, -h                  The command produce a description of the generator program along with\n" +
                "                            instruction on how to use different synopsis to generate desired output,\n" +
                "                            and what each command can do in the program. \n\n" +
                "--version,-v                The command allows user to check the current version of the application.\n\n"+
                "--library, -l <library>     The command specifies the source of the nonsense language being generated,\n" +
                "                            the default library is lorem, if not anguish is not being called\n\n"+
                "--outfile, -o <filename>    This command specifies the output file that the generated text will be\n"+
                "                            written to, if this command is not being specified, the generated text\n" +
                "                            will be written to console, and output file must to have .txt extension,\n"+
                "                            else, the format of output file is not acceptable\n\n"+
                "--mode, -m <mode>           This command specifies the output mode of generated language, there are\n" +
                "                            three mode are allowed in this program, which are paragraph, word, bullet\n" +
                "                            respectively. The mode of language will be set to paragraph if no mode\n" +
                "                            are specified\n\n"+
                "--count, -c <count>         This command allows you to specify number of time that you want our program\n" +
                "                            to generate the txt, if the count is not defined, then the program will\n" +
                "                            assigned a parameter to it by default based on language mode, so 1 for\n" +
                "                            paragraph, 3 for word, and 5 for bullet\n\n"+
                "--html, -t                  This command will sorted and enclosed the generated text with HTML format,\n" +
                "                            paragraph will be enclosed in <p> tag, <h1> for word, and <ul> and <ui>\n" +
                "                            for bullet\n\n"+
                "--generate, -g              The command is needed to be included to run the program in the command line\n" +
                "                            mode, else, the program will be run in the interactive mode \n\n"+
                "       \t\t\t\t\tInteractive Mode Command Description\n\n"+
                "generate <options>          This command line is same as above, but run in different mode\n\n"+
                "help                        Same as above\n\n"+
                "version                     Same as above\n\n"+
                "set<option> <value>         This command allows the user to specify a default value for mode,\n" +
                "                            count, library,html, and outfile. So when the command is called,\n" +
                "                            program will automatically write in these value, if you are not\n" +
                "                            going to specify another value to overwrite it\n\n"+
                "show<option>                This command allows you to inspect the current value of the command, if\n" +
                "                            it has one, else, the program will show all the command whose current\n" +
                "                            value has been defined\n\n"+
                "exit, quit                  Exits the program\n\n"

        );

    }
    private static ArrayList<ArrayList<String>>loremText()
    {
        ArrayList<ArrayList<String>> dataPool1 = new ArrayList<>();
        ArrayList<String> article = new ArrayList<>();
        ArrayList<String> noun = new ArrayList<>();
        ArrayList<String> verb = new ArrayList<>();
        ArrayList<String> preposition = new ArrayList<>();
        ArrayList<String> noun2 = new ArrayList<>();
        Collections.addAll(article, "the", "a", "one", "some", "any" );
        Collections.addAll(noun, "boy", "girl", "dog", "town", "car");
        Collections.addAll(verb, "drove", "jumped", "ran", "walked", "skipped");
        Collections.addAll(preposition, "to", "from", "over", "under", "on" );
        Collections.addAll(noun2, "bridge", "street","market","class","library");
        Collections.addAll(dataPool1,article,noun,verb,preposition,noun2);
        return dataPool1;
    }
    private static ArrayList<ArrayList<String>> anguishText()
    {
        ArrayList<ArrayList<String>> dataPool2 = new ArrayList<>();
        ArrayList<String> article2 = new ArrayList<>();
        ArrayList<String> noun2 = new ArrayList<>();
        ArrayList<String> verb2 = new ArrayList<>();
        ArrayList<String> preposition2 = new ArrayList<>();
        Collections.addAll(article2, "the", "a(n)", "one", "some", "any" );
        Collections.addAll(noun2, "man", "woman", "female", "male", "animal");
        Collections.addAll(verb2,  "ran", "walked", "skipped","sang","moves");
        Collections.addAll(preposition2, "on", "away","towards","around","near" );
        Collections.addAll(noun2, "bridge", "street","market","class","library");
        Collections.addAll(dataPool2,article2,noun2,verb2,preposition2,noun2);
        return dataPool2;
    }
    private static String inputStr(String str)
    {
        return str;
    }
    private static Integer countStrOcc(  ArrayList<String> arrTem, String temp2)
    {
        int temp = 0;
        for (int i = 0; i < arrTem.size(); i++)
        {
            if (arrTem.get(i).equals(temp2))
            {
                temp++;
            }
        }
        return temp;
    }
    private static void writeToFile(ArrayList<String> str, String outFile)
    {

        try
        {

            FileOutputStream fos = new FileOutputStream(outFile);
            DataOutputStream writer = new DataOutputStream(new BufferedOutputStream(fos));
            if(isNeedsHtml()){
                if(getMod().contentEquals("paragraph")){
                    for(int i = 0; i< str.size(); i++)
                    {
                        writer.writeUTF("<p> "+str.get(i)+" </p>\n");
                    }
                }
                if(getMod().contentEquals("word")){
                    for(int i = 0; i< str.size(); i++)
                    {
                        writer.writeUTF("<h1> "+str.get(i)+" </h1>\n");
                    }
                }
                if(getMod().contentEquals("bullet")){
                    writer.writeUTF("<ul>\n");
                    for(int i = 0; i< str.size(); i++)
                    {
                        writer.writeUTF("  <li> "+str.get(i)+" </li>\n");
                    }
                    writer.writeUTF("</ul>\n");
                }
            }else{

                for(int i = 0; i< str.size(); i++)
                {
                    writer.writeUTF(str.get(i)+'\n');
                }
            }

            writer.close();

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public static boolean isNeedsHtml() {
        return needsHtml;
    }

    public static void setNeedsHtml(boolean needsHtml) {
        Main.needsHtml = needsHtml;
    }

    private static ArrayList<String> appearsOneTime(HashMap<String, String> cmdDataPool, ArrayList<String> appearTimes ){
        ArrayList<String>validCMD = new ArrayList<>();
        for (int i = 0; i < appearTimes.size(); i++) {
            if (Character.getNumericValue(appearTimes.get(i).charAt(2)) == 1) {
                if (cmdDataPool.containsKey(appearTimes.get(i))) {
                    validCMD.add(cmdDataPool.get(appearTimes.get(i)));
                }
            }
        }
        return validCMD;
    }


    private static Boolean checkFileExtension(String fileExtension){
        int len = fileExtension.length();
        return fileExtension.length()>=5 && fileExtension.charAt(len-1)=='t'&&fileExtension.charAt(len-2)=='x'&&
                fileExtension.charAt(len-3)=='t'&&fileExtension.charAt(len-4)=='.';
    }

    public static int getTempNum() {
        return tempNum;
    }

    public static void setTempNum(int tempNum) {
        Main.tempNum = tempNum;
    }

}
