import java.io.Serializable;

public class DocNode implements Serializable {
    private String docname;
    private double docfreq;
    DocNode next;

    public DocNode(String _docnum) {
        this.docname = _docnum;
        this.docfreq = 1.0D;
        this.next = null;
    }

    public DocNode(String _docnum, double _docfreq) {
        this.docname = _docnum;
        this.docfreq = _docfreq;
        this.next = null;
    }

    public String getDocname() {
        return this.docname;
    }

    public double getDocfreq() {
        return this.docfreq;
    }

    public void docfreqplus() {
        ++this.docfreq;
    }
}
