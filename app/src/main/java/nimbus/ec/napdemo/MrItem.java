package nimbus.ec.napdemo;

/**
 * Created by gbern_000 on 10/12/2014.
 */
public class MrItem
{
    String title;
    String description;
    String date;
    String subject;
    Double ta;
    Double ti;
    Double tg;
    Double le;
    Double pe;
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
    public Double getTa(){
        return ta;
    }
    public void setTa(String ta){
        this.ta = Double.parseDouble(ta);
    }
    public Double getTg(){
        return tg;
    }
    public void setTg(String tg){
        this.tg = Double.parseDouble(tg);
    }
    public Double getTi(){
        return ti;
    }
    public void setTi(String ti){
        this.ti = Double.parseDouble(ti);
    }
    public Double getle(){
        return le;
    }
    public void setLe(String le){
        this.le = Double.parseDouble(le);
    }
    public Double getPe(){
        return pe;
    }
    public void setPe(String pe){
        this.pe = Double.parseDouble(pe);
    }
}