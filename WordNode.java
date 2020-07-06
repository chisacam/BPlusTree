public class WordNode {
    private int length;
    private String word;
    DocNode head;
    public WordNode(String newword){
        this.word = newword;
        this.length = 1;
        this.head = null;
    }

    public int getLength(){
        return this.length;
    }

    public String getWord(){
        return this.word;
    }

    public DocNode getHead(){
        return this.head;
    }

    public void setHead(DocNode newhead){
        this.head = newhead;
    }
    public void insertDocNode(String docname){
        if(this.head == null){
            this.head = new DocNode(docname);
            this.length++;
        }else if(this.head.getDocname().equals(docname)){
            this.head.docfreqplus();
        }else{
            DocNode node = new DocNode(docname);
            node.next = this.head;
            this.head = node;
            this.length++;
        }

    }
    public void lengthPlus(){
        this.length++;
    }
}
