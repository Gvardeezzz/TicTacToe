public class Controller {
    private final Model model;
    private final View view;

    String playerOneName;
    String playerTwoName;
    PlayerMark playerMark1;
    PlayerMark playerMark2;


    public Controller(Model model, View view){
        this.model = model;
        this.view = view;
    }
    public void mainLoop(){
        ConsoleHelper.printMessage("Enter 1st player name");
        playerOneName = model.createPlayer();
        ConsoleHelper.printMessage("Enter 2nd player name");
        playerTwoName = model.createPlayer();

        model.init();

        playerMark1 = PlayerMark.CROSS;
        playerMark2 = PlayerMark.ZERO;

        String currentPlayer = playerOneName;
        PlayerMark currentMark = playerMark1;

        boolean isWinnerFound = false;

        while (model.hasMotion(model.getGameField())){
            model.putMark(model.getGameField(), currentMark);
            view.refresh(model.getGameField());

            if(model.isWin(model.getGameField(), currentMark)){
                ConsoleHelper.printMessage(String.format("Winner is %s!",currentPlayer));
                Integer []s = model.getGameStat().get(currentPlayer);
                s[0]++;
                model.getGameStat().put(currentPlayer,s);

                s = model.getGameStat().get(changeName(currentPlayer));
                s[2]++;
                model.getGameStat().put(changeName(currentPlayer),s);
                isWinnerFound = true;
                break;
            }
            currentMark = changeMark(currentMark);
           currentPlayer = changeName(currentPlayer);
        }
        if (!isWinnerFound){
            ConsoleHelper.printMessage("Draw");
            Integer []s = model.getGameStat().get(playerOneName);
            s[1]++;
            model.getGameStat().put(playerOneName,s);
            s = model.getGameStat().get(playerTwoName);
            s[1]++;
            model.getGameStat().put(playerTwoName,s);
        }
        model.sendStatisticsToFile(model.makeOutputData(model.getGameStat()));
        ConsoleHelper.printMessage("Do you want to play again?");
        ConsoleHelper.printMessage("If no, enter word \"exit\". For continue playing enter any symbol");
        String wantToPlay = ConsoleHelper.readMessage();
        if(!wantToPlay.equalsIgnoreCase("exit")) mainLoop();
    }

    public String changeName(String name){
        if(name.equals(playerOneName)) return playerTwoName;
        return playerOneName;
    }

    public PlayerMark changeMark(PlayerMark mark){
        if(mark == playerMark1) return playerMark2;
        return playerMark1;
    }
}
