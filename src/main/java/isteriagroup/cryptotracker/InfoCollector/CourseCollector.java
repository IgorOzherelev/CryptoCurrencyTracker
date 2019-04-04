package isteriagroup.cryptotracker.InfoCollector;


import java.net.*;
import java.io.*;
import java.util.Stack;

public class CourseCollector{

    private static String changes[];
    private static String values[];
    private static String dataNames[] = {"EDO/USD", "ETP/USD", "AVT/USD", "ZEC/USD",
        "QTM/USD", "OMG/USD", "EOS/USD",
                "ETC/USD", "XMR/USD", "LTC/USD",
                "BTG/USD", "DSH/USD", "IOT/USD",
                "XRP/USD", "NEO/USD", "ETH/USD", "BTC/USD"};

    private static void parseHtmlDoc(int dataNamesSize, Stack<String> dataVal){

        try {
            URL lab = new URL("https://www.rbc.ru/crypto/?utm_source=topline");
            InputStreamReader isr = new InputStreamReader(lab.openStream(), "UTF-8");

            BufferedReader d = new BufferedReader(isr);
            String line;

            while ((line = d.readLine()) != null) {
                if (line.lastIndexOf("<span class=\"currencies__inset__inner\">") != -1) {
                    line = d.readLine();
                    if ((line.lastIndexOf("</span>") == -1) & ((line.lastIndexOf("</div>") == -1))) {
                        String lineTmp = line.replaceAll(" ", "");

                        if (dataVal.size() == 2 * dataNamesSize) {
                            break;
                        }

                        dataVal.push(lineTmp);
                    }
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println(dataVal);
        }
    }

    private static void fillMass(String changes[], String values[], Stack<String> dataVal){

        int dataSize = dataVal.size();

        int i = 0;
        int tmp;

        for (i = 0; i < dataSize; i++) {
            if (i % 2 == 0) {
                tmp = i / 2;
                changes[tmp] = dataVal.pop();
            }

            if (i % 2 == 1) {
                tmp = (i - 1) / 2;
                values[tmp] = dataVal.pop();
            }
        }
    }

    private static void printGottenData(int dataNamesSize, String changes[], String values[], String dataNames[]){

        int i = 0;

        for (i = 0; i < dataNamesSize; i++) {
            System.out.println(dataNames[dataNamesSize - 1 - i] + " " + values[i] + " " + changes[i] + " ");
        }
    }


    private static void replaceCommaByDot(String mass[]){
        int i = 0;
        int size = mass.length;

        for (i = 0; i < size; i++)
        {
            mass[i] = mass[i].replaceAll(",", ".");
        }
    }

    public static void runCollector(){
        Stack<String> dataVal = new Stack<>();

        int dataNamesSize = dataNames.length;

        changes = new String[dataNamesSize];
        values = new String[dataNamesSize];

        parseHtmlDoc(dataNamesSize, dataVal);
        fillMass(changes, values, dataVal);

        replaceCommaByDot(changes);
        replaceCommaByDot(values);

    }

    public static String[] getChanges() {
        return changes;
    }

    public static String[] getValues() {
        return values;
    }

    public static String[] getDataNames() {
        return dataNames;
    }
}