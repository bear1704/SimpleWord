package simpleword.aca.com;

public class Word
{

    private String koreanStr;
    private String englishStr;
    private int star = 0;
    private boolean isCheck = false;

    public Word(String korean, String english, int star)
    {
        koreanStr = korean;
        englishStr = english;
        this.star = star;

    }



    public int getStar()
    {
        return star;
    }
    public String getKoreanStr()
    {
        return koreanStr;
    }
    public String getEnglishStr()
    {
        return englishStr;
    }
    public boolean isChecked()
    {
        return isCheck;
    }

}
