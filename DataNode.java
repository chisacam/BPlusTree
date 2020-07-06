public class DataNode {
    private WordNode[] worddata;
    private int datacount;
    DataNode next;
    //데이터노드 생성자
    public DataNode(){
        this.worddata = new WordNode[3];
        this.datacount = 0;
        this.next = null;
    }
    //지정한 위치의 단어노드 가져오기
    public WordNode getWorddata(int indexnum){
        return this.worddata[indexnum];
    }
    //다음 데이터노드 가져오기
    public DataNode getNext(){
        return this.next;
    }
    //다음 데이터노드 설정하기
    public void setNext(DataNode node){
        this.next = node;
    }
    //데이터 갯수 가져오기
    public int getDataCount(){
        return this.datacount;
    }
    //데이터 갯수 +1
    public void dataCountPlus(){
        this.datacount++;
    }
    //데이터 갯수 설정하기
    public void setDataCount(int newdatacount){
        this.datacount = newdatacount;
    }
    //단어노드를 추가
    public void setWorddata(WordNode newdata){
        if(this.worddata[1] == null) {
            if (this.worddata[0] == null) {
                this.worddata[0] = newdata;
            } else if (this.worddata[0].getWord().compareTo(newdata.getWord()) > 0) {
                WordNode temp = this.worddata[0];
                this.worddata[0] = newdata;
                this.worddata[1] = temp;
            } else if (this.worddata[0].getWord().compareTo(newdata.getWord()) < 0) {
                this.worddata[1] = newdata;
            }this.datacount++;
        }else{
            if(this.worddata[0].getWord().compareTo(newdata.getWord()) > 0){
                this.worddata[2] = this.worddata[1];
                this.worddata[1] = this.worddata[0];
                this.worddata[0] = newdata;
            } else if(this.worddata[0].getWord().compareTo(newdata.getWord()) < 0
                    && this.worddata[1].getWord().compareTo(newdata.getWord()) > 0) {
                this.worddata[2] = this.worddata[1];
                this.worddata[1] = newdata;
            } else if(this.worddata[1].getWord().compareTo(newdata.getWord()) < 0) {
                this.worddata[2] = newdata;
            }this.datacount++;
        }
    }
    //단어노드를 추가하되 중복단어가 들어올때 사용하는 메소드
    public void setWorddata(WordNode newdata, String filename){
        if(this.worddata[1] == null) {
            if (this.worddata[0] == null) {
                this.worddata[0] = newdata;
                this.datacount++;
            } else if (this.worddata[0].getWord().compareTo(newdata.getWord()) > 0) {
                WordNode temp = this.worddata[0];
                this.worddata[0] = newdata;
                this.worddata[1] = temp;
                this.datacount++;
            } else if (this.worddata[0].getWord().compareTo(newdata.getWord()) < 0) {
                this.worddata[1] = newdata;
                this.datacount++;
            }else if(this.worddata[0].getWord().compareTo(newdata.getWord()) == 0){
                this.worddata[0].insertDocNode(filename);
            }else if(this.worddata[1].getWord().compareTo(newdata.getWord()) == 0) {
                this.worddata[1].insertDocNode(filename);
            }
        }else{
            if(this.worddata[0].getWord().compareTo(newdata.getWord()) > 0){
                this.worddata[2] = this.worddata[1];
                this.worddata[1] = this.worddata[0];
                this.worddata[0] = newdata;
                this.datacount++;
            } else if(this.worddata[0].getWord().compareTo(newdata.getWord()) < 0
                    && this.worddata[1].getWord().compareTo(newdata.getWord()) > 0) {
                this.worddata[2] = this.worddata[1];
                this.worddata[1] = newdata;
                this.datacount++;
            } else if(this.worddata[1].getWord().compareTo(newdata.getWord()) < 0) {
                this.worddata[2] = newdata;
                this.datacount++;
            } else if(this.worddata[0].getWord().compareTo(newdata.getWord()) == 0){
                this.worddata[0].insertDocNode(filename);
            }else if(this.worddata[1].getWord().compareTo(newdata.getWord()) == 0){
                this.worddata[1].insertDocNode(filename);
            }else if(this.worddata[2].getWord().compareTo(newdata.getWord()) == 0){
                this.worddata[2].insertDocNode(filename);
            }
        }
    }
    //노드 분할후 이전 노드의 잔여데이터 제거 메소드
    public void setWorddatanull(){
        this.worddata[1] = null;
        this.worddata[2] = null;
    }
    //데이터 노드간 연결 유지
    public void setNextDataLink(DataNode node, DataNode newnode){
        if(node.getNext() == null){
            node.setNext(newnode);
        }else{
            DataNode temp = node.getNext();
            node.setNext(newnode);
            newnode.setNext(temp);
        }
    }
}
