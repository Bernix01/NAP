package nimbus.ec.nap;

/**
 * Created by gbern_000 on 10/12/2014.
 */
public class MrItem
{
    String title;
    String description;
    String date;
    String subject;
    Double grade;

    public String getTitle () {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSubject(){
        return subject;
    }

    public void setSubject(String subject){
        this.subject = subject;
    }
    public Double getGrade(){
        return grade;
    }
    public void setGrade(String grade){
        this.grade = Double.parseDouble(grade);
    }
}