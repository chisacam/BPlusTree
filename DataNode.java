import java.io.Serializable;

public class DataNode implements Serializable {
    private WordNode[] worddata = new WordNode[3];
    private int datacount = 0;
    DataNode next = null;

    public DataNode() {
    }

    public WordNode getWorddata(int indexnum) {
        return this.worddata[indexnum];
    }

    public DataNode getNext() {
        return this.next;
    }

    public void setNext(DataNode node) {
        this.next = node;
    }

    public int getDataCount() {
        return this.datacount;
    }

    public void setDataCount(int newdatacount) {
        this.datacount = newdatacount;
    }

    public void setWorddata(WordNode newdata) {
        if (this.worddata[1] == null) {
            if (this.worddata[0] == null) {
                this.worddata[0] = newdata;
            } else if (this.worddata[0].getWord().compareTo(newdata.getWord()) > 0) {
                WordNode temp = this.worddata[0];
                this.worddata[0] = newdata;
                this.worddata[1] = temp;
            } else if (this.worddata[0].getWord().compareTo(newdata.getWord()) < 0) {
                this.worddata[1] = newdata;
            }

            ++this.datacount;
        } else {
            if (this.worddata[0].getWord().compareTo(newdata.getWord()) > 0) {
                this.worddata[2] = this.worddata[1];
                this.worddata[1] = this.worddata[0];
                this.worddata[0] = newdata;
            } else if (this.worddata[0].getWord().compareTo(newdata.getWord()) < 0 && this.worddata[1].getWord().compareTo(newdata.getWord()) > 0) {
                this.worddata[2] = this.worddata[1];
                this.worddata[1] = newdata;
            } else if (this.worddata[1].getWord().compareTo(newdata.getWord()) < 0) {
                this.worddata[2] = newdata;
            }

            ++this.datacount;
        }

    }

    public void setWorddata(WordNode newdata, String filename) {
        if (this.worddata[1] == null) {
            if (this.worddata[0] == null) {
                this.worddata[0] = newdata;
                ++this.datacount;
            } else if (this.worddata[0].getWord().compareTo(newdata.getWord()) > 0) {
                WordNode temp = this.worddata[0];
                this.worddata[0] = newdata;
                this.worddata[1] = temp;
                ++this.datacount;
            } else if (this.worddata[0].getWord().compareTo(newdata.getWord()) < 0) {
                this.worddata[1] = newdata;
                ++this.datacount;
            } else if (this.worddata[0].getWord().compareTo(newdata.getWord()) == 0) {
                this.worddata[0].insertDocNode(filename);
            } else if (this.worddata[1].getWord().compareTo(newdata.getWord()) == 0) {
                this.worddata[1].insertDocNode(filename);
            }
        } else if (this.worddata[0].getWord().compareTo(newdata.getWord()) > 0) {
            this.worddata[2] = this.worddata[1];
            this.worddata[1] = this.worddata[0];
            this.worddata[0] = newdata;
            ++this.datacount;
        } else if (this.worddata[0].getWord().compareTo(newdata.getWord()) < 0 && this.worddata[1].getWord().compareTo(newdata.getWord()) > 0) {
            this.worddata[2] = this.worddata[1];
            this.worddata[1] = newdata;
            ++this.datacount;
        } else if (this.worddata[1].getWord().compareTo(newdata.getWord()) < 0) {
            this.worddata[2] = newdata;
            ++this.datacount;
        } else if (this.worddata[0].getWord().compareTo(newdata.getWord()) == 0) {
            this.worddata[0].insertDocNode(filename);
        } else if (this.worddata[1].getWord().compareTo(newdata.getWord()) == 0) {
            this.worddata[1].insertDocNode(filename);
        } else if (this.worddata[2].getWord().compareTo(newdata.getWord()) == 0) {
            this.worddata[2].insertDocNode(filename);
        }

    }

    public void setWorddatanull() {
        this.worddata[1] = null;
        this.worddata[2] = null;
    }

    public void setNextDataLink(DataNode node, DataNode newnode) {
        if (node.getNext() == null) {
            node.setNext(newnode);
        } else {
            DataNode temp = node.getNext();
            node.setNext(newnode);
            newnode.setNext(temp);
        }

    }
}
