public class View {

    public void refresh(PlayerMark [][] marks) {
        StringBuilder resultString = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            resultString = new StringBuilder("|");
            for (int j = 0; j < 3; j++) {
                PlayerMark mark = marks[i][j];
                switch (mark){
                    case SPACE:
                        resultString.append("-|");
                        break;

                    case CROSS:
                        resultString.append("X|");
                        break;

                    case ZERO:
                        resultString.append("0|");
                        break;
                }
            }
            System.out.println(resultString.toString());
        }
    }
}
