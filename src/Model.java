import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Model {
    private PlayerMark [][]gameField = new PlayerMark[3][3];
    private HashMap<String,Integer[]> gameStat = new HashMap<>();
    
    public void init(){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gameField[i][j] = PlayerMark.SPACE;
            }
        }
    }
    
    public void putMark(PlayerMark [][] gameField, PlayerMark mark){
        int x = -1;
        int y = -1;

        if(mark == PlayerMark.CROSS) ConsoleHelper.printMessage("Player 1 moves");
        else ConsoleHelper.printMessage("Player 2 moves");

        while (x < 0){
            ConsoleHelper.printMessage("Enter number of row");
            try {
                x = Integer.parseInt(ConsoleHelper.readMessage());
                if(x > 2) throw new NumberFormatException();
            }
            catch (NumberFormatException ex){
                x = -1;
                ConsoleHelper.printMessage("This row not exists! Please, enter number between 0 and 2!");
            }
        }
        while (y < 0){
            ConsoleHelper.printMessage("Enter number of column");
            try {
                y = Integer.parseInt(ConsoleHelper.readMessage());
                if(y > 2) throw new NumberFormatException();
            }
            catch (NumberFormatException ex){
                y = -1;
                ConsoleHelper.printMessage("This column not exists! Please, enter number between 0 and 2!");
            }
        }

        if(gameField[x][y] == PlayerMark.SPACE) gameField[x][y] = mark;
        else {
            ConsoleHelper.printMessage("This cell is not free! Please, try again!");
            putMark(gameField,mark);
        }
    }
    
    public boolean hasMotion(PlayerMark [][] gameField) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (gameField[i][j] == PlayerMark.SPACE) return true; 
            }
        }
        return false;
    }
    
    public boolean isWin(PlayerMark [][] gameField, PlayerMark mark){
        for (int i = 0; i < 3; i++) {
            if((gameField[i][0] == mark && gameField[i][1] == mark && gameField[i][2] == mark) ||
                    (gameField[0][i] == mark && gameField[1][i] == mark && gameField[2][i] == mark)){
                return true;
            }
            
            if(gameField[0][0] == mark && gameField[1][1] == mark && gameField [2][2] == mark) return true;
            if (gameField[0][2] == mark && gameField[1][1] == mark && gameField[2][0] == mark) return true;
        }
        return false;
    } 
    
    public String createPlayer(){
        String name = "";
        while (name.equals("")) {
            name = ConsoleHelper.readMessage();
        }
        if(gameStat.isEmpty()|| !gameStat.containsKey(name)) {
            gameStat.put(name, new Integer[]{0, 0, 0});
        }
        return name;
    }

    public void setGameStat(HashMap<String, Integer[]> gameStat) {
        this.gameStat = gameStat;
    }

    public void sendStatisticsToFile(ArrayList<String> strings){
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("/home/sergey/gamestat.txt");
            for (int i = 0; i < strings.size(); i++) {
                fileOutputStream.write(strings.get(i).getBytes(StandardCharsets.UTF_8));
            }
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> makeOutputData(HashMap<String,Integer[]> stat){
        ArrayList<String> result = new ArrayList<>();
        String title = "|NAME      |WINS      |DRAWS     |LOSES     |\r\n";
        result.add(title);
        for(Map.Entry<String,Integer[]> entry: stat.entrySet()){
            StringBuilder sb = new StringBuilder("|");
            String name = entry.getKey();
            Integer []value = entry.getValue();
            sb.append(name);
            while (sb.length()<11){
                sb.append(" ");
            }
            sb.append("|");
            int len = 11;
            for (int i = 0; i < 3; i++) {
                sb.append(value[i]);
                while (sb.length()<len+11){
                    sb.append(" ");
                }
                len = len+11;
                sb.append("|");
            }
            sb.append("\r\n");
            result.add(sb.toString());
        }
        return result;
    }

    public PlayerMark[][] getGameField() {
        return gameField;
    }

    public HashMap<String, Integer[]> getGameStat() {
        return gameStat;
    }
}
