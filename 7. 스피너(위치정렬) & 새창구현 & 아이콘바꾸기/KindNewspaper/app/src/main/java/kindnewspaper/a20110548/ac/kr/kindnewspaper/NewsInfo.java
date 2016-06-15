package kindnewspaper.a20110548.ac.kr.kindnewspaper;

//---------------------------------------------------------------------
public class NewsInfo {
    String title;
    String content;
    String author;
    String link;

    public NewsInfo(String title, String content, String author,String link) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.link =link;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    public String getLink() {
        return link;
    }
}
