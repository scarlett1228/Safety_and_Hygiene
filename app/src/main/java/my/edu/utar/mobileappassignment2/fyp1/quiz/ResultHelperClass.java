package my.edu.utar.mobileappassignment2.fyp1.quiz;

public class ResultHelperClass {

    String wrong;
    String finalscore;
    String correct;
    String type;
    String timestamp;




    public ResultHelperClass(String correct, String wrong, String finalscore,String type,String timestamp) {
        this.correct = correct;
        this.wrong = wrong;
        this.finalscore = finalscore;
        this.type = type;
        this.timestamp = timestamp;
    }

    public ResultHelperClass() {
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

    public String getWrong() {
        return wrong;
    }

    public void setWrong(String wrong) {
        this.wrong = wrong;
    }

    public String getFinalscore() {
        return finalscore;
    }

    public void setFinalscore(String finalscore) {
        this.finalscore = finalscore;
    }



}