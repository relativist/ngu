import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * Created by a.sitnikov on 21.07.14.
 */
public class First {
    String key;
    Integer value;

    public First(String key, Integer value) {
        this.key = key;
        this.value = value;
    }

    public static void main(String[] args) throws IOException {
        String filename = "./laba1/source.txt";
        Reader reader = null;
        StringBuilder word = new StringBuilder();
        HashMap<String, Integer> myHashMap = new HashMap<String, Integer>();
        List<First> list = new ArrayList<First>();
        char c;
        int i;
        int prev = 0;
        double totalWordsCount = 0.0;
        boolean flag = false;

        System.out.println("arg size = "+args.length);
        if(args.length==0)
            System.out.println("Selected file: "+filename);
        else{
            System.out.println("Selected file: "+args[0]);
            filename=args[0];

        }




        try {
            reader = new InputStreamReader(new FileInputStream(filename), "UTF-8");

            while ((i = reader.read()) != -1) {
                c = (char) i;

                if (!Character.isLetterOrDigit(c) && flag) {
                    totalWordsCount++;
                    if (myHashMap.containsKey(String.valueOf(word))) {
                        prev = myHashMap.get(String.valueOf(word));
                        myHashMap.remove(String.valueOf(word));
                        myHashMap.put(String.valueOf(word), prev + 1);
                    } else myHashMap.put(String.valueOf(word), 1);

                    word.delete(0, word.length());
                    flag = false;
                } else if (Character.isLetterOrDigit(c)) {
                    word.append(c);
                    flag = true;
                }
            }
        } catch (IOException e) {
            System.err.println("Error while reading file: " + e.getLocalizedMessage());
        } finally {
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace(System.err);
                }
            }
        }

        for (String key : myHashMap.keySet()) {
            //System.out.println("Key = (" + key + "), Value = " +  myHashMap.get(key));
            list.add(new First(key, myHashMap.get(key)));
        }

        System.out.println("Total words: " + totalWordsCount + "");
        System.out.println();

        Collections.sort(list, new Comparator<First>() {
            public int compare(First o1, First o2) {
                return o1.value.compareTo(o2.value);
            }
        });

        for (int j = list.size() - 1; j >= 0; j--) {
            System.out.println(list.get(j).key + "\t" + list.get(j).value + "\t" + new BigDecimal(list.get(j).value / totalWordsCount * 100).setScale(1, RoundingMode.UP) + "%");
        }

    }
}

