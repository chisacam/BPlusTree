import java.io.Serializable;

public class IndexNode implements Serializable {
    private String[] words = new String[3];
    private int countwords = 0;
    private boolean hasdatalink = false;
    IndexNode ileftlink = null;
    IndexNode imidlink = null;
    IndexNode irightlink = null;
    IndexNode itemplink;
    IndexNode parent = null;
    DataNode dleftlink = null;
    DataNode dmidlink = null;
    DataNode drightlink = null;
    DataNode dtemplink;

    public IndexNode() {
    }

    public IndexNode getHasParent() {
        return this.parent;
    }

    public boolean getHasDataLink() {
        return this.hasdatalink;
    }

    public void setHasDataLink(boolean v) {
        this.hasdatalink = v;
    }

    public void setWordnull() {
        this.words[1] = null;
        this.words[2] = null;
    }

    public String getWords(int selindex) {
        return this.words[selindex];
    }

    public void setWords(String newdata) {
        if (this.words[1] == null) {
            if (this.words[0] == null) {
                this.words[0] = newdata;
                ++this.countwords;
            } else if (this.words[0].compareTo(newdata) > 0) {
                String temp = this.words[0];
                this.words[0] = newdata;
                this.words[1] = temp;
                ++this.countwords;
            } else if (this.words[0].compareTo(newdata) < 0) {
                this.words[1] = newdata;
                ++this.countwords;
            }
        } else if (this.words[0].compareTo(newdata) > 0) {
            this.words[2] = this.words[1];
            this.words[1] = this.words[0];
            this.words[0] = newdata;
            ++this.countwords;
        } else if (this.words[0].compareTo(newdata) < 0 && this.words[1].compareTo(newdata) > 0) {
            this.words[2] = this.words[1];
            this.words[1] = newdata;
            ++this.countwords;
        } else if (this.words[1].compareTo(newdata) < 0) {
            this.words[2] = newdata;
            ++this.countwords;
        }

    }

    public int getCountwords() {
        return this.countwords;
    }

    public void setCountwords(int num) {
        this.countwords = num;
    }
}
